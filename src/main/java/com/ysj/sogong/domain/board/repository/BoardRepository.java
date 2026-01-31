package com.ysj.sogong.domain.board.repository;

import com.ysj.sogong.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer>
{
  Board findById(int id);
}
