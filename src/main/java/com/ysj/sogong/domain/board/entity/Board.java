package com.ysj.sogong.domain.board.entity;

import com.ysj.sogong.domain.board.boardClass.entity.BoardClass;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Board
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String username;
  private String title;

  @ManyToOne
  @JoinColumn(name = "board_class_id")
  private BoardClass boardClass;
}

