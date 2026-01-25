package com.ysj.sogong.global.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@EnableWebSecurity
@Configuration
public class SecurityConfig
{
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
            .requestMatchers("/.well-known/**", "/error").permitAll() // Chrome DevTools 경로 허용
            .requestMatchers("/static/**", "/css/**", "/js/**", "/images/**", "/gen/**", "/member/profile/img/**").permitAll() // 정적 리소스 허용
            .requestMatchers("/member/join", "/member/login", "/home").permitAll()
            .anyRequest().authenticated())
        .formLogin(form -> form
            .loginPage("/member/login") // GET (security -> me)
            .loginProcessingUrl("/member/login") // POST (me -> security)
            .defaultSuccessUrl("/member/myPage") // GET
            .failureHandler((request, response, exception) -> {
              // 여기서 에러 원인을 로그로 찍어보세요!
              log.error("로그인 실패 원인: {}", exception.getMessage());
              response.sendRedirect("/user/login?error");})
            .permitAll())
        .logout(logout -> logout
            .logoutUrl("/member/logout") // POST (me -> security)
            .logoutSuccessUrl("/home") // GET
            .invalidateHttpSession(true) // 세션 삭제
            .permitAll()
        );

    return http.build();
  }

  @Bean
    // 비밀번호 암호와 검증
    // 인증 시 암호화된 비밀번호를 비교
    // Bcrypt 알고리즘 사용
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}
