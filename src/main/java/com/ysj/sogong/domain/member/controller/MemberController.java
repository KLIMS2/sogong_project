package com.ysj.sogong.domain.member.controller;

import com.ysj.sogong.domain.member.dto.MemberDto;
import com.ysj.sogong.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
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

  @GetMapping("/join")
  public String showJoin()
  {
    return "/member/join";
  }

  @PostMapping("/join")
  @ResponseBody
  public MemberDto doJoin(MemberDto memberForm)
  {
    MemberDto member = memberService.createMember(memberForm);

    return member;
  }

  @GetMapping("/login")
  public String showLogin()
  {
    return "/member/login";
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/logout")
  public String showLogout()
  {
    return "/member/logout";
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/myPage")
  @ResponseBody
  public UserDetails myPage(Principal principal)
  {
    return memberService.loadUserByUsername(principal.getName());
  }
}
