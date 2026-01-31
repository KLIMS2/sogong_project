package com.ysj.sogong.global.init.boardClass;

import com.ysj.sogong.domain.boardClass.entity.BoardClass;
import com.ysj.sogong.domain.boardClass.service.BoardClassService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"test", "dev", "prod"})
@Configuration
public class InitBoardClass
{
  @Bean
  CommandLineRunner commandLineRunnerBoardClass(BoardClassService boardClassService)
  {
    return args -> {
      boardClassService.createBoardClass(new BoardClass("일반공지"));
      boardClassService.createBoardClass(new BoardClass("행사특강"));
      boardClassService.createBoardClass(new BoardClass("학부학사공지"));
      boardClassService.createBoardClass(new BoardClass("학생일반공지"));
      boardClassService.createBoardClass(new BoardClass("onestop 공지사항"));
      boardClassService.createBoardClass(new BoardClass("장학공지"));
    };
  }
}
