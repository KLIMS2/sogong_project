package com.ysj.sogong.domain.board.form;

import com.ysj.sogong.global.security.dto.LoginedMember;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardForm
{
  @Size(min = 1, max = 50)
  @NotEmpty(message = "제목을 입력해주세요")
  private String title;

  @NotNull(message = "게시판 종류를 선택해주세요")
  private Integer boardClassId;

  private LoginedMember loginedMember;
}
