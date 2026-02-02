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
    final String JOIN_FORM = "/member/join";
    
    // 값 입력 유무, 값의 길이 등의 유효성 검사
    if(bindingResult.hasErrors())
    {
      model.addAttribute("member", memberForm);
      return JOIN_FORM;
    }

    // 아이디 중복 유효성 검사
    Member findMember = memberService.findMember(memberForm.getUsername());
    if(findMember != null)
    {
      model.addAttribute("member", memberForm);
      return JOIN_FORM;
    }
    
    // 비밀번호 확인 유효성 검사
    if(!memberForm.getPassword().equals(memberForm.getPasswordConfirm()))
    {
      model.addAttribute("member", memberForm);
      return JOIN_FORM;
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