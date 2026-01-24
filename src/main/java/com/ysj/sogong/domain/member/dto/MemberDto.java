package com.ysj.sogong.domain.member.dto;

import lombok.*;

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
}
