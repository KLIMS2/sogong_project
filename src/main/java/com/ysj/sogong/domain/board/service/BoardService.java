package com.ysj.sogong.domain.board.service;

import com.ysj.sogong.domain.board.boardClass.entity.BoardClass;
import com.ysj.sogong.domain.board.boardClass.service.BoardClassService;
import com.ysj.sogong.domain.board.entity.Board;
import com.ysj.sogong.domain.board.form.BoardForm;
import com.ysj.sogong.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardService
{
  private final BoardRepository boardRepository;
  private final BoardClassService boardClassService;

  public Board createBoard(BoardForm boardForm)
  {
    BoardClass boardClass = boardClassService.findBoardClass(boardForm.getBoardClassId());
    Board board = Board.builder()
        .title(boardForm.getTitle())
        .boardClass(boardClass)
        .username(boardForm.getLoginedMember().getUsername())
        .build();
    return boardRepository.save(board);
  }
}
