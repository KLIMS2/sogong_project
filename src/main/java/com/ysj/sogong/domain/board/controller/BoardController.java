package com.ysj.sogong.domain.board.controller;

import com.ysj.sogong.domain.boardClass.entity.BoardClass;
import com.ysj.sogong.domain.boardClass.service.BoardClassService;
import com.ysj.sogong.domain.board.entity.Board;
import com.ysj.sogong.domain.board.form.BoardForm;
import com.ysj.sogong.domain.board.service.BoardService;
import com.ysj.sogong.global.security.dto.LoginedMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
public class BoardController
{
  private final BoardService boardService;
  private final BoardClassService boardClassService;

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/create")
  public String showCreate(Model model)
  {
    List<BoardClass> boardClasses = boardClassService.findBoardClassAll();
    model.addAttribute("boardClasses", boardClasses);
    return "/board/create";
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/create")
  public String doCreate(BoardForm boardForm,
                         @AuthenticationPrincipal LoginedMember loginedMember,
                         Model model)
  {
    boardForm.setLoginedMember(loginedMember);
    Board board = boardService.createBoard(boardForm);
    model.addAttribute("board", board);
    return "/board/detail";
  }
}
