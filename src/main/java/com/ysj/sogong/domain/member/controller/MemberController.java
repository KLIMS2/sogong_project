package com.ysj.sogong.domain.member.controller;

import com.ysj.sogong.domain.member.entity.Member;
import com.ysj.sogong.domain.member.repository.MemberRepository;
import com.ysj.sogong.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController
{
  private final MemberService memberService;
  private final PasswordEncoder passwordEncoder;

  @GetMapping("/join")
  public String showJoin()
  {
    return "/member/join";
  }

  @PostMapping("/join")
  @ResponseBody
  public Member doJoin(Member member)
  {
    String encodedPassword = passwordEncoder.encode(member.getPassword());
    MemberRepository.members.add(
        Member.builder()
            .username(member.getUsername())
            .password(encodedPassword)
            .build());
    MemberRepository.members.forEach(user -> {
      System.out.println(user.getId());
      System.out.println(user.getUsername());
      System.out.println(user.getPassword());
      System.out.println(passwordEncoder.matches(1234 + "", user.getPassword()));
    });
    return MemberRepository.members.getLast();
  }

  @GetMapping("/login")
  public String showLogin()
  {
    MemberRepository.members.forEach(user -> {
      System.out.println(user.getId());
      System.out.println(user.getUsername());
      System.out.println(user.getPassword());
      System.out.println(passwordEncoder.matches(1234 + "", user.getPassword()));
    });
    return "/member/login";
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/myPage")
  @ResponseBody
  public UserDetails myPage(Principal principal)
  {
    return memberService.loadUserByUsername(principal.getName());
  }
}
