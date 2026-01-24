package com.ysj.sogong.domain.member.entity;

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
public class Member
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  @Column(unique = true)
  private String username;
  private String password;
}
