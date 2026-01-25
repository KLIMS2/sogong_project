package com.ysj.sogong.domain.member.dto;

import com.ysj.sogong.domain.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto
{
  private int id;
  private String username;
  private String password;

  public MemberDto(Member member)
  {
    id = member.getId();
    username = member.getUsername();
    password = member.getPassword();
  }
}
