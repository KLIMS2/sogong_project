package com.ysj.sogong;

import com.ysj.sogong.domain.member.controller.MemberController;
import com.ysj.sogong.domain.member.entity.Member;
import com.ysj.sogong.domain.member.form.MemberForm;
import com.ysj.sogong.domain.member.service.MemberService;
import com.ysj.sogong.global.request.Rq;
import com.ysj.sogong.global.utility.Util;
import jakarta.transaction.Transactional;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.ui.Model;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@SpringBootTest
class SogongApplicationTests
{
	@Autowired
	private MockMvc mvc;

	@Autowired
	private MemberService memberService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	@DisplayName("join test")
	@Rollback(value = false)
	void t1() throws Exception
	{
		// get
		System.out.println("\n--- get ---");
		mvc.perform(get("/member/join")
						.with(csrf()))
				.andDo(print())
				.andExpect(status().is2xxSuccessful());

		// post
		System.out.println("\n--- post ---");
		String username = "test_user";
		String password = "1234";

		ResultActions resultActions = mvc.perform(post("/member/join")
				.param("username", username)
				.param("password", password)
				.param("passwordConfirm", password)
				.characterEncoding("UTF-8")
				.with(csrf())
		).andDo(print());

		resultActions
				.andExpect(status().is2xxSuccessful())
				.andExpect(handler().handlerType(MemberController.class))
				.andExpect(handler().methodName("doJoin"));

		// DB
		Member member = memberService.findMember(username);
		assertThat(member).isNotNull();
		assertThat(member.getId()).isEqualTo(4);
		assertThat(member.getUsername()).isEqualTo(username);
		assertThat(true).isEqualTo(passwordEncoder.matches(password, member.getPassword()));
	}

	@Test
	@DisplayName("get annotaion value in MemberForm")
	void t2()
	{
		List<String> fieldNames = Util.getFieldNames(MemberForm.class);
		for(String fieldName : fieldNames)
		{
			System.out.println("---\n" + fieldName + "\n---");

			Map<String, Object> map = Util.getAnnotaionValues(MemberForm.class, fieldName);
			map.forEach((name, value) -> {
				System.out.println(name + ": " + value);
			});
		}
	}

	@Test
	@DisplayName("JSoup test")
	void t3()
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


