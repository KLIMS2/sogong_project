package com.ysj.sogong.domain.member.service;

import com.ysj.sogong.domain.member.dto.MemberDto;
import com.ysj.sogong.domain.member.entity.Member;
import com.ysj.sogong.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService
{
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username)
  {
    Member member = memberRepository.findByUsername(username);
    if (member == null)
    {
      throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
    }

    // 권한을 담을 빈 리스트 생성
    List<GrantedAuthority> authorities = new ArrayList<>();

    if ("admin".equals(username)) {
      authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // 관리자 권한 부여
    } else {
      authorities.add(new SimpleGrantedAuthority("ROLE_USER")); // 일반 사용자 권한 부여
    }

    return new User(member.getUsername(), member.getPassword(), authorities);
  }

  public MemberDto createMember(MemberDto memberDto)
  {
    String encodedPassword = passwordEncoder.encode(memberDto.getPassword());
    Member member = Member.builder()
        .username(memberDto.getUsername())
        .password(encodedPassword)
        .build();

    member = memberRepository.save(member);

    MemberDto newMember = MemberDto.builder()
        .id(member.getId())
        .username(member.getUsername())
        .password(member.getPassword())
        .build();

    return newMember;
  }
}
