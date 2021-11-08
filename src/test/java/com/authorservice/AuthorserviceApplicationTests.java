package com.authorservice;

import com.authorservice.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthorserviceApplicationTests {
	@Autowired
	AuthorService authorService;
	@Test
	void contextLoads() {
		for(int i=0; i<10; i++){
			authorService.getAuthorWithBook(1L);
		}
	}

}
