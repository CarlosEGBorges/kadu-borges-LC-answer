package br.com.letscode.interview.answer.resource.controller;

import br.com.letscode.interview.answer.resource.domain.Answer;
import br.com.letscode.interview.answer.resource.domain.Question;
import br.com.letscode.interview.answer.resource.domain.Quiz;
import br.com.letscode.interview.answer.resource.domain.Response;
import br.com.letscode.interview.answer.resource.service.QuizService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = QuizController.class)
public class QuizControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private QuizService quizService;

    @InjectMocks
    private QuizController quizController = new QuizController();

    private MockPrincipal mockUser;
    private OAuth2Authentication mockOAuth2Object;

    @BeforeEach
    void mocksOAuth2Object() {
        mockUser = new MockPrincipal();
        mockOAuth2Object = new OAuth2Authentication(null, mockUser);
    }

    @Test
    public void shouldNotBeGrantedPermission()
            throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/quiz/start").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldNotThrowExceptionOnFinish() {
        doNothing().when(quizService).finishQuiz(anyString());
        quizController.finishQuiz(mockOAuth2Object);
    }

    @Test
    public void shouldNotThrowExceptionOnGetQuestion() {
        Question question = createQuestion();
        when(quizService.getQuestion(mockOAuth2Object.getPrincipal().toString(), true)).thenReturn(question);
        assertEquals(quizController.getQuestion(mockOAuth2Object).getId(), question.getId());
    }

    @Test
    public void shouldNotThrowExceptionOnStart() {
        Quiz quiz = createQuiz();
        when(quizService.startQuiz(anyString())).thenReturn(quiz);
        assertEquals(quizController.startQuiz(mockOAuth2Object).getId(), quiz.getId());
    }

    @Test
    public void shouldNotThrowExceptionOnAnswerQuestion() {
        Answer answer = createAnswer();
        when(quizService.checkResponse(mockOAuth2Object.getPrincipal().toString(), new Response())).thenReturn(answer);
        assertEquals(quizController.answerQuestion(new Response(), mockOAuth2Object).getId(), answer.getId());
    }

    @Test
    public void shouldThrowExceptionOnFinish() {
        doThrow(NoSuchElementException.class).when(quizService).finishQuiz(anyString());
        assertThrows(ResponseStatusException.class, () -> quizController.finishQuiz(mockOAuth2Object));
    }

    @Test
    public void shouldThrowExceptionOnStart() {
        when(quizService.startQuiz(anyString())).thenThrow(NoSuchElementException.class);
        assertThrows(ResponseStatusException.class, () -> quizController.startQuiz(mockOAuth2Object));
    }

    @Test
    public void shouldThrowExceptionOnGetQuestion() {
        when(quizService.getQuestion(mockOAuth2Object.getPrincipal().toString(), true)).thenThrow(EntityNotFoundException.class);
        assertThrows(ResponseStatusException.class, () -> quizController.getQuestion(mockOAuth2Object));
    }

    private Quiz createQuiz(){
        Quiz quiz = new Quiz();
        quiz.setId(9999L);
        quiz.setUsername("usertest");
        quiz.setActive(true);
        quiz.setCreatedAt(new Date());
        quiz.setMistakes(0);
        return quiz;
    }

    private Question createQuestion(){
        Question question = new Question();
        question.setId(7777L);
        return question;
    }

    private Answer createAnswer(){
        Answer answer = new Answer();
        answer.setId(6666L);
        answer.setCorrect(true);
        return answer;
    }
}