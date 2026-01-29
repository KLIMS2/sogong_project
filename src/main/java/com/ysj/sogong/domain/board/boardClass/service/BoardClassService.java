package com.ysj.sogong.domain.board.boardClass.service;

import com.ysj.sogong.domain.board.boardClass.entity.BoardClass;
import com.ysj.sogong.domain.board.boardClass.repository.BoardClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardClassService
{
  private final BoardClassRepository boardClassRepository;

  public BoardClass createBoardClass(BoardClass boardClass)
  {
    return boardClassRepository.save(boardClass);
  }

  public BoardClass findBoardClass(int id)
  {
    return boardClassRepository.findById(id);
  }

  public List<BoardClass> findBoardClassAll()
  {
    return boardClassRepository.findAll();
  }
}
