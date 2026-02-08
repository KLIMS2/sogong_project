package com.ysj.sogong.domain.member.service;

import com.ysj.sogong.domain.member.entity.Member;
import com.ysj.sogong.domain.member.form.MemberForm;
import com.ysj.sogong.domain.member.repository.MemberRepository;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService
{
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  public Member createMember(MemberForm memberForm)
  {
    String encodedPassword = passwordEncoder.encode(memberForm.getPassword());
    Member member = Member.builder()
        .username(memberForm.getUsername())
        .password(encodedPassword)
        .build();
    return memberRepository.save(member);
  }

  public Member createMember(Member member)
  {
    String encodedPassword = passwordEncoder.encode(member.getPassword());
    member.setPassword(encodedPassword);
    return memberRepository.save(member);
  }

  public Member findMember(String username)
  {
    return memberRepository.findByUsername(username);
  }

  public boolean isUsernameAvailable(String username)
  {
    return findMember(username) == null;
  }
}
