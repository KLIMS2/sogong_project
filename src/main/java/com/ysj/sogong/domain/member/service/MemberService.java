package com.ysj.sogong.domain.member.service;

import com.ysj.sogong.domain.member.entity.Member;
import com.ysj.sogong.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService
{
  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String username)
  {
    Member member = memberRepository.getMember(username);
    if (member == null)
    {
      System.out.println("not found error");
    }

    // 권한을 담을 빈 리스트 생성
    List<GrantedAuthority> authorities = new ArrayList<>();

    if ("admin".equals(username)) {
      authorities.add(new SimpleGrantedAuthority("admin")); // 관리자 권한 부여
    } else {
      authorities.add(new SimpleGrantedAuthority("user")); // 일반 사용자 권한 부여
    }

    return new org.springframework.security.core.userdetails.User(member.getUsername(), member.getPassword(), authorities);
  }
}
