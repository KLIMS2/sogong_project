package com.ysj.sogong.global.security.service;

import com.ysj.sogong.domain.member.entity.Member;
import com.ysj.sogong.domain.member.service.MemberService;
import com.ysj.sogong.global.security.dto.LoginedMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SecurityService implements UserDetailsService
{
  private final MemberService memberService;

  @Override
  public UserDetails loadUserByUsername(String username)
  {
    Member member = memberService.findMember(username);
    if (member == null)
    {
      throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
    }

    final String ADMIN_USERNAME = "admin";
    List<GrantedAuthority> authorities = new ArrayList<>();

    if (username.equals(ADMIN_USERNAME))
    {
      authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // 관리자 권한 부여
    }
    else
    {
      authorities.add(new SimpleGrantedAuthority("ROLE_USER")); // 일반 사용자 권한 부여
    }

    return new LoginedMember(member, authorities);
  }
}
