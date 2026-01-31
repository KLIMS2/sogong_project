package com.ysj.sogong;

import com.ysj.sogong.domain.member.entity.Member;
import com.ysj.sogong.domain.member.repository.MemberRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class SogongApplicationTests {

	@Autowired
	private MemberRepository memberRepository;

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("JSoup test")
	void t1()
	{
		String url = "https://www.sogang.ac.kr/ko/story/notification-general";

		try
		{
			Document doc = Jsoup
					.connect(url)
					.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
					.get();

			System.out.println(doc.select("body"));

			/*Element latestElement = doc.select(".board_list tr").get(0);
			String title = latestElement.select(".title").text();
			String link = latestElement.select("a").attr("abs:href");*/
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
