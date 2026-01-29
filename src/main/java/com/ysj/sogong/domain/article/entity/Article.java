package com.ysj.sogong.domain.article.entity;

import com.ysj.sogong.domain.board.boardClass.entity.BoardClass;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String title;
  private String content;
  private int boardClassId;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createDate;
}
