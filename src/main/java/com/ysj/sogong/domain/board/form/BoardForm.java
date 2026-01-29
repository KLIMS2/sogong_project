package com.ysj.sogong.domain.board.form;

import com.ysj.sogong.global.security.dto.LoginedMember;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardForm
{
  private String title;
  private int boardClassId;
  private LoginedMember loginedMember;
}
