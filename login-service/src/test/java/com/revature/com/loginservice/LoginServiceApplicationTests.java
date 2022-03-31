package com.revature.com.loginservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class LoginServiceApplicationTests {

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private RestTemplate restTemplate;

	@Test
	void contextLoads() {
		assert(encoder != null);

		assert(restTemplate != null);
	}

}
