package br.com.letscode.interview.answer.resource.service;

import br.com.letscode.interview.answer.resource.domain.MovieCard;
import br.com.letscode.interview.answer.resource.domain.Question;
import br.com.letscode.interview.answer.resource.domain.Quiz;
import br.com.letscode.interview.answer.resource.domain.Response;
import br.com.letscode.interview.answer.resource.repository.AnswerRepository;
import br.com.letscode.interview.answer.resource.repository.MovieCardRepository;
import br.com.letscode.interview.answer.resource.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private MovieCardRepository movieCardRepository;
    @Mock
    private AnswerRepository answerRepository;
    @Mock
    private MovieCardService movieCardService;

    @InjectMocks
    private QuestionService questionService = new QuestionService();

    @Test
    public void shouldThrowAnExceptionOnCreateNewQuestion_whenNoMorePairsAreAvailable() {
        Quiz quiz = new Quiz();
        quiz.setQuestionList(new ArrayList<>());
        quiz.getQuestionList().add(new Question());
        quiz.getQuestionList().add(new Question());
        quiz.getQuestionList().add(new Question());
        quiz.getQuestionList().add(new Question());
        quiz.getQuestionList().add(new Question());
        quiz.getQuestionList().add(new Question());

        List<MovieCard> movieCardIterable = new ArrayList<>();
        movieCardIterable.add(new MovieCard());
        movieCardIterable.add(new MovieCard());
        movieCardIterable.add(new MovieCard());

        when(movieCardRepository.findAll()).thenReturn(movieCardIterable);

        assertThrows(IllegalStateException.class, () -> questionService.createNewQuestion(quiz));
    }

    @Test
    public void shouldThrowAnExceptionOnEvaluateResponse_whenNoCardMatchesTheResponse() {
        Question question = new Question();
        question.setCardOne(createMovieCard(1));
        question.setCardTwo(createMovieCard(2));

        Response response = new Response();
        response.setImdbId("3");

        assertThrows(IllegalArgumentException.class, () -> questionService.evaluateResponse(question, response));
    }

    @Test
    public void shouldEvaluateTheResponseCorrectly_whenTheCardTwoMatchesTheResponse() {
        Question question = new Question();
        question.setCardOne(createMovieCard(1));
        question.setCardTwo(createMovieCard(2));

        Response response = new Response();
        response.setImdbId("2");

        assertTrue(questionService.evaluateResponse(question, response).getCorrect());
    }

    @Test
    public void shouldEvaluateTheResponseCorrectly_whenTheCardOneDoesNotMatchTheResponse() {
        Question question = new Question();
        question.setCardOne(createMovieCard(1));
        question.setCardTwo(createMovieCard(2));

        Response response = new Response();
        response.setImdbId("1");

        assertFalse(questionService.evaluateResponse(question, response).getCorrect());
    }

    @Test
    public void shouldCreateANewQuestion_whenQuestionListIsEmpty() {
        Quiz quiz = new Quiz();
        quiz.setQuestionList(new ArrayList<>());

        List<MovieCard> movieCardIterable = new ArrayList<>();
        movieCardIterable.add(createMovieCard(1));
        movieCardIterable.add(createMovieCard(2));
        movieCardIterable.add(createMovieCard(3));

        when(movieCardRepository.findAll()).thenReturn(movieCardIterable);
        when(movieCardRepository.findAll()).thenReturn(movieCardIterable);

        assertNotNull(questionService.createNewQuestion(quiz));
    }

    private MovieCard createMovieCard(Integer randomInteger){
        MovieCard movieCard = new MovieCard();
        movieCard.setImdbId(randomInteger.toString());
        movieCard.setImdbRating(randomInteger.doubleValue());
        movieCard.setImdbVotes(randomInteger);
        movieCard.setPosterUrl(randomInteger.toString());
        movieCard.setTitle(randomInteger.toString());
        movieCard.setReleasedYear(randomInteger.toString());

        return movieCard;
    }
}
