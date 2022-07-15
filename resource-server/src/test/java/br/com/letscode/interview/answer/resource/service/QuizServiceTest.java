package br.com.letscode.interview.answer.resource.service;

import br.com.letscode.interview.answer.resource.domain.Answer;
import br.com.letscode.interview.answer.resource.domain.Question;
import br.com.letscode.interview.answer.resource.domain.Quiz;
import br.com.letscode.interview.answer.resource.domain.Response;
import br.com.letscode.interview.answer.resource.repository.QuizRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;
    @Mock
    private QuestionService questionService;

    @InjectMocks
    private QuizService quizService = new QuizService();

    private String principal = "principal";

    @Test
    public void shouldThrowExceptionOnStart_whenUsernameIsNull() {
        assertThrows(InvalidParameterException.class, () -> quizService.startQuiz(null));
    }

    @Test
    public void shouldThrowExceptionOnFinish_whenUsernameIsNull() {
        assertThrows(InvalidParameterException.class, () -> quizService.startQuiz(null));
    }

    @Test
    public void shouldThrowExceptionOnGetQuestion_whenUsernameIsNull() {
        assertThrows(InvalidParameterException.class, () -> quizService.getQuestion(null, true));
    }

    @Test
    public void shouldThrowExceptionOnFinish_whenNoQuizIsFound() {
        when(quizRepository.findByUsernameAndActiveIsTrue(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> quizService.finishQuiz(principal));
    }

    @Test
    public void shouldFinishAQuizWithoutThrowingException() {
        Quiz quiz = new Quiz();
        quiz.setActive(true);
        when(quizRepository.findByUsernameAndActiveIsTrue(anyString())).thenReturn(quiz);
        quizService.finishQuiz(principal);
        assertFalse(quiz.getActive());
    }

    @Test
    public void shouldThrowExceptionOnGetQuestion_whenNoQuizIsFound() {
        when(quizRepository.findByUsernameAndActiveIsTrue(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> quizService.getQuestion(principal, true));
    }

    @Test
    public void shouldThrowExceptionOnStart_whenAQuizIsFound() {
        when(quizRepository.findByUsernameAndActiveIsTrue(anyString())).thenReturn(createQuiz());
        assertThrows(EntityExistsException.class, () -> quizService.startQuiz(principal));
    }

    @Test
    public void shouldNotThrowExceptionOnStart_whenNoQuizIsFound() {
        Quiz quiz = new Quiz();
        when(quizRepository.findByUsernameAndActiveIsTrue(anyString())).thenReturn(null);
        when(quizRepository.save(isA(Quiz.class))).thenReturn(quiz);
        assertNotNull(quizService.startQuiz(principal));
    }

    @Test
    public void shouldReturnTheOpenedQuestion_whenOneIsAvailable() {
        Quiz quiz = createQuiz();
        Question answeredQuestion = new Question();
        Answer answer = new Answer();
        answeredQuestion.setAnswer(answer);

        Question openedQuestion = new Question();

        quiz.getQuestionList().add(answeredQuestion);
        quiz.getQuestionList().add(openedQuestion);

        when(quizRepository.findByUsernameAndActiveIsTrue(anyString())).thenReturn(quiz);

        assertEquals(openedQuestion, quizService.getQuestion(principal, true));
    }

    @Test
    public void shouldReturnANewQuestion_whenNoOpenedQuestionIsAvailable() {
        Quiz quiz = createQuiz();
        Question answeredQuestion = new Question();
        answeredQuestion.setAnswer(new Answer());
        quiz.getQuestionList().add(answeredQuestion);

        when(quizRepository.findByUsernameAndActiveIsTrue(anyString())).thenReturn(quiz);
        Question newQuestion = new Question();
        when(questionService.createNewQuestion(isA(Quiz.class))).thenReturn(newQuestion);

        assertEquals(newQuestion, quizService.getQuestion(principal, true));
    }

    @Test
    public void shouldThrowExceptionOnCheckAnswer_whenNoOpenQuizIsFound() {
        when(quizRepository.findByUsernameAndActiveIsTrue(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> quizService.checkResponse(principal, new Response()));
    }

    @Test
    public void shouldThrowExceptionOnCheckAnswer_whenNoOpenedQuestionIsAvailable() {
        Quiz quiz = createQuiz();
        Question answeredQuestion = new Question();
        answeredQuestion.setAnswer(new Answer());
        quiz.getQuestionList().add(answeredQuestion);

        when(quizRepository.findByUsernameAndActiveIsTrue(anyString())).thenReturn(quiz);
        assertThrows(EntityNotFoundException.class, () -> quizService.checkResponse(principal, new Response()));
    }

    @Test
    public void shouldIncrementQuizMistakesOnCheckAnswer_whenResponseIsWrong() {
        Quiz quiz = createQuiz();
        Question openedQuestion = new Question();
        quiz.getQuestionList().add(openedQuestion);
        Answer answer = new Answer();
        answer.setCorrect(false);

        when(quizRepository.findByUsernameAndActiveIsTrue(anyString())).thenReturn(quiz);
        when(questionService.evaluateResponse(openedQuestion, new Response())).thenReturn(answer);
        quizService.checkResponse(quiz.getUsername(), new Response());
        assertEquals(1, quiz.getMistakes());
    }

    @Test
    public void shouldFinishQuizOnCheckAnswer_whenAThirdResponseIsWrong() {
        Quiz quiz = createQuiz();
        Question openedQuestion = new Question();
        quiz.getQuestionList().add(openedQuestion);
        Answer answer = new Answer();
        answer.setCorrect(false);

        when(quizRepository.findByUsernameAndActiveIsTrue(anyString())).thenReturn(quiz);
        when(questionService.evaluateResponse(openedQuestion, new Response())).thenReturn(answer);
        quizService.checkResponse(quiz.getUsername(), new Response());
        quizService.checkResponse(quiz.getUsername(), new Response());
        quizService.checkResponse(quiz.getUsername(), new Response());
        assertEquals(false, quiz.getActive());
    }

    @Test
    public void shouldNotValidateANullOrEmptyUsername(){
        assertAll(
            () -> assertThrows(InvalidParameterException.class, () -> quizService.validateUsername(null)),
            () -> assertThrows(InvalidParameterException.class, () -> quizService.validateUsername(""))
        );
    }

    @Test
    public void shouldValidateANotNullUsername(){
        assertDoesNotThrow(() -> quizService.validateUsername("kaduBorgers"));
    }

    @Test
    public void shouldFoundAQuiz(){
        Quiz quiz = createQuiz();
        when(quizRepository.findByUsernameAndActiveIsTrue(anyString())).thenReturn(quiz);
        assertDoesNotThrow(() -> quizService.checkAvailableQuiz(quiz.getUsername()));
    }

    @Test
    public void shouldNotFoundAQuizThenThrowAnException(){
        when(quizRepository.findByUsernameAndActiveIsTrue(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> quizService.checkAvailableQuiz("asdf"));
    }

    private Quiz createQuiz(){
        Quiz quiz = new Quiz();
        quiz.setId(8888L);
        quiz.setUsername("usertest");
        quiz.setActive(true);
        quiz.setCreatedAt(new Date());
        quiz.setMistakes(0);
        quiz.setQuestionList(new ArrayList<>());
        return quiz;
    }
}
