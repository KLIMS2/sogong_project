package com.ysj.sogong.domain.member.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MemberForm
{
  @NotEmpty
  private String username;

  @NotEmpty
  private String password;
}
