package com.wechat.program.app;

import com.wechat.program.app.request.SendSmsDto;
import com.wechat.program.app.service.SmsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProgramAppApplicationTests {


	@Autowired
	private SmsService smsService;

	@Test
	void contextLoads() {


	}

	@Test
	public void testSendSms() {

	}

}
