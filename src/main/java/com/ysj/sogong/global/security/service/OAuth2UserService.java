package com.ysj.sogong.global.security.service;

import com.ysj.sogong.domain.member.entity.Member;
import com.ysj.sogong.domain.member.service.MemberService;
import com.ysj.sogong.global.exception.member.MemberNotFoundException;
import com.ysj.sogong.global.exception.oAuth2.OAuthTypeMatchNotFoundException;
import com.ysj.sogong.global.security.dto.LoginedMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OAuth2UserService extends DefaultOAuth2UserService
{
  private final MemberService memberService;

  @Transactional
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException
  {
    final List<String> OAUTH_TYPE_LIST = new ArrayList<>()
    {{
      add("KAKAO");
    }};

    OAuth2User oAuth2User = super.loadUser(userRequest);
    Map<String, Object> attributes = oAuth2User.getAttributes();
    String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
    String oAuthId = oAuth2User.getName();
    String oAuthType = userRequest.getClientRegistration().getRegistrationId().toUpperCase();

    if (!OAUTH_TYPE_LIST.contains(oAuthType))
    {
      throw new OAuthTypeMatchNotFoundException();
    }

    Member member = null;

    // OAuth ID로 가입된 회원이 없는 경우
    // 로그인시 회원가입을 실시(새 회원 생성)
    if (isNew(oAuthType, oAuthId))
    {
      switch (oAuthType)
      {
        case "KAKAO" -> {
          Map attributesProperties = (Map) attributes.get("properties");
          Map attributesKakaoAccount = (Map) attributes.get("kakao_account");
          String username = "%s_%s".formatted(oAuthType, oAuthId);
          /*String nickname = (String) attributesProperties.get("nickname");
          String profileImage = (String) attributesProperties.get("profile_image");
          String email = "%s@kakao.com".formatted(oAuthId);

          if ((boolean) attributesKakaoAccount.get("has_email"))
          {
            email = (String) attributesKakaoAccount.get("email");
          }*/

          member = Member.builder()
              .username(username)
              .password("")
              .build();

          member = memberService.createMember(member);
        }
      }
    }
    else
    { // 가입한 회원이 있으면 회원 데이터 가져옴
      member = memberService.findMember("%s_%s".formatted(oAuthType, oAuthId));
      if(member == null)
      {
        throw new MemberNotFoundException();
      }
    }

    final String ADMIN_USERNAME = "admin";
    List<GrantedAuthority> authorities = new ArrayList<>();

    if (member.getUsername().equals(ADMIN_USERNAME))
    {
      authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // 관리자 권한 부여
    }
    else
    {
      authorities.add(new SimpleGrantedAuthority("ROLE_USER")); // 일반 사용자 권한 부여
    }

    return new LoginedMember(member, authorities, attributes, userNameAttributeName);
  }

  private boolean isNew(String oAuthType, String oAuthId)
  {
    return memberService.findMember("%s_%s".formatted(oAuthType, oAuthId)) == null;
  }
}
