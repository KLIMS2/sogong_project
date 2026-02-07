package com.ysj.sogong.domain.board.controller;

import com.ysj.sogong.domain.boardClass.entity.BoardClass;
import com.ysj.sogong.domain.boardClass.service.BoardClassService;
import com.ysj.sogong.domain.board.entity.Board;
import com.ysj.sogong.domain.board.form.BoardForm;
import com.ysj.sogong.domain.board.service.BoardService;
import com.ysj.sogong.global.request.Rq;
import com.ysj.sogong.global.security.dto.LoginedMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  public String showCreate(BoardForm boardForm, Model model)
  {
    List<BoardClass> boardClasses = boardClassService.findBoardClassAll();
    model.addAttribute("boardClasses", boardClasses);
    return "/board/create";
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/create")
  public String doCreate(@Valid BoardForm boardForm,
                         BindingResult bindingResult,
                         @AuthenticationPrincipal LoginedMember loginedMember,
                         Model model)
  {
    final String BOARD_CREATE_FORM = "/board/create";

    // 값 입력 유무, 값의 길이 등의 유효성 검사
    if(bindingResult.hasErrors())
    {
      List<BoardClass> boardClasses = boardClassService.findBoardClassAll();
      model.addAttribute("boardClasses", boardClasses);
      return  BOARD_CREATE_FORM;
    }

    // 게시판 생성
    boardForm.setLoginedMember(loginedMember);
    Board board = boardService.createBoard(boardForm);

    return "redirect:/board/detail/%s".formatted(board.getId());
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/detail/{id}")
  public String showDetail(@PathVariable int id, Model model)
  {
    Board board = boardService.findBoard(id);
    if(board != null)
    {
      model.addAttribute("board", board);
    }

    return "/board/detail";
  }
}
