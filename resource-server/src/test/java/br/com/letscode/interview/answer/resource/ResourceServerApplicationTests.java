package br.com.letscode.interview.answer.resource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ResourceServerApplicationTests {

	@Autowired
	private ConfigurableApplicationContext context;

	@Test
	void contextLoads() {
		assertTrue(context.isActive());
	}
}
