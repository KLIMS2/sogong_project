package com.ysj.sogong.domain.member.controller;

import com.ysj.sogong.domain.member.entity.Member;
import com.ysj.sogong.domain.member.form.MemberForm;
import com.ysj.sogong.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
  public String doJoin(@Valid MemberForm memberForm, BindingResult bindingResult, Model model)
  {
    if(bindingResult.hasErrors())
    {
      model.addAttribute("member", memberForm);
      return "/member/join";
    }

    memberService.createMember(memberForm);
    return "/index";
  }

  @GetMapping("/login")
  public String showLogin()
  {
    return "/member/login";
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/myPage")
  public String myPage()
  {
    return "/member/myPage";
  }
}