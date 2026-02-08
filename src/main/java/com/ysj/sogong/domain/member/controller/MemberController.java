package com.ysj.sogong.domain.member.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController
{
  private final MemberService memberService;

  @GetMapping("/join")
  public String showJoin(MemberForm memberForm)
  {
    return "/member/join";
  }

  @PostMapping("/join")
  public String doJoin(@Valid MemberForm memberForm,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes redirectAttributes)
  {
    final String JOIN_FORM = "/member/join";

    // 중복 확인 버튼 처리
    if(memberForm.isCheckUsername())
    {
      model.addAttribute("isValid", memberService.isUsernameAvailable(memberForm.getUsername()));
      return JOIN_FORM;
    }

    // 값 입력 유무, 값의 길이 등의 유효성 검사
    if(bindingResult.hasErrors())
    {
      return JOIN_FORM;
    }

    // 비밀번호 일치 여부 검사
    if(!memberForm.getPassword().equals(memberForm.getPasswordConfirm()))
    {
      bindingResult.rejectValue("passwordConfirm", "Not_Confirmed", "비밀번호와 일치하지 않습니다");
      return JOIN_FORM;
    }

    // 아이디 중복 검사
    if(!memberService.isUsernameAvailable(memberForm.getUsername()))
    {
      bindingResult.rejectValue("username", "Overlap_Username", "이미 사용된 아이디입니다");
      return JOIN_FORM;
    }

    // 회원가입
    memberService.createMember(memberForm);

    // PRG 패턴 - 타임리프 표준 방식으로 작업
    redirectAttributes.addFlashAttribute("successMessage", "%s님 회원가입이 완료되었습니다".formatted(memberForm.getUsername()));

    return "redirect:/member/login";
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