package com.ysj.sogong.domain.member.repository;

import com.ysj.sogong.domain.member.dto.MemberDto;
import com.ysj.sogong.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer>
{
   Member findByUsername(String username);
}
