package com.ysj.sogong.domain.member.service;

import com.ysj.sogong.domain.member.entity.Member;
import com.ysj.sogong.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService
{
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  public Member createMember(Member memberDto)
  {
    String encodedPassword = passwordEncoder.encode(memberDto.getPassword());
    Member member = Member.builder()
        .username(memberDto.getUsername())
        .password(encodedPassword)
        .build();
    return memberRepository.save(member);
  }

  public Member findMember(String username)
  {
    Member member = memberRepository.findByUsername(username);
    if(member == null)
    {
      return null;
    }
    return member;
  }
}
