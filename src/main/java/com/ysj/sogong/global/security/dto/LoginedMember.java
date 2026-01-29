package com.ysj.sogong.global.security.dto;

import com.ysj.sogong.domain.member.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class LoginedMember extends User implements OAuth2User
{
  private final int id;
  private Map<String, Object> attributes;
  private String userNameAttributeName;

  public LoginedMember(Member member, List<GrantedAuthority> authorities)
  {
    super(member.getUsername(), member.getPassword(), authorities);
    id = member.getId();
  }

  public LoginedMember(Member member, List<GrantedAuthority> authorities, Map<String, Object> attributes, String userNameAttributeName)
  {
    this(member, authorities);
    this.attributes = attributes;
    this.userNameAttributeName = userNameAttributeName;
  }

  @Override
  public Set<GrantedAuthority> getAuthorities()
  {
    return super.getAuthorities().stream().collect(Collectors.toSet());
  }

  @Override
  public Map<String, Object> getAttributes()
  {
    return this.attributes;
  }

  @Override
  public String getName()
  {
    if (userNameAttributeName != null && attributes != null)
    {
      Object nameAttribute = getAttribute(userNameAttributeName);
      if (nameAttribute != null)
      {
        return nameAttribute.toString();
      }
    }
    return super.getUsername();
  }
}