package br.com.letscode.interview.answer.resource.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = QuizController.class)
public class RankingControllerTest {
}
