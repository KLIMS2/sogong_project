package com.ysj.sogong.global.test.data;

import com.ysj.sogong.domain.member.dto.MemberDto;
import com.ysj.sogong.domain.member.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Profile("test")
@Configuration
public class InitData
{
  @Bean
  CommandLineRunner commandLineRunner(MemberService memberService, PasswordEncoder passwordEncoder)
  {
    return args -> {
      String password = passwordEncoder.encode("1234");
      memberService.createMember(new MemberDto(1, "user1", password));
      memberService.createMember(new MemberDto(2, "user2", password));
      memberService.createMember(new MemberDto(3, "user3", password));
    };
  }
}
