package com.ysj.sogong.domain.boardClass.repository;

import com.ysj.sogong.domain.boardClass.entity.BoardClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardClassRepository extends JpaRepository<BoardClass, Integer>
{
  BoardClass findById(int id);
}
