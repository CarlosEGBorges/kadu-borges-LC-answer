package br.com.letscode.interview.answer.resource.service;

import br.com.letscode.interview.answer.resource.domain.Question;
import br.com.letscode.interview.answer.resource.domain.Quiz;
import br.com.letscode.interview.answer.resource.domain.RankingPosition;
import br.com.letscode.interview.answer.resource.repository.QuizRepository;
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
public class RankingServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private RankingService rankingService = new RankingService();

    @Test
    public void shouldReturnAnEmptyList() {
        when(quizRepository.findAllByActiveIsFalseOrderByUsername()).thenReturn(new ArrayList<>());
        assertTrue(rankingService.getRanking().size() == 0);
    }

    @Test
    public void shouldReturnAnListInTheCorrectOrder() {
        List<Quiz> quizList = createQuizzes();
        when(quizRepository.findAllByActiveIsFalseOrderByUsername()).thenReturn(quizList);
        List<RankingPosition> rankingPositionList = rankingService.getRanking();
        assertAll(
                () -> assertEquals(rankingPositionList.get(0).getPlayer(), "aaaa"),
                () -> assertEquals(rankingPositionList.get(1).getPlayer(), "bbb"),
                () -> assertEquals(rankingPositionList.get(2).getPlayer(), "c")
        );

    }

    private List<Quiz> createQuizzes(){
        List<Quiz> quizList = new ArrayList<>();
        Quiz quiz = new Quiz();
        quiz.setUsername("aaaa");
        quiz.setActive(false);
        quiz.setQuestionList(createQuestions(4));
        quiz.setMistakes(2);
        quizList.add(quiz);

        quiz = new Quiz();
        quiz.setUsername("bbb");
        quiz.setActive(false);
        quiz.setQuestionList(createQuestions(3));
        quiz.setMistakes(2);
        quizList.add(quiz);

        quiz = new Quiz();
        quiz.setUsername("c");
        quiz.setActive(false);
        quiz.setQuestionList(createQuestions(2));
        quiz.setMistakes(2);
        quizList.add(quiz);

        return quizList;
    }

    private List<Question> createQuestions(int numberOfQuestions){
        List<Question> questionList = new ArrayList<>();
        for(int i = 0; i < numberOfQuestions; i++){
            questionList.add(new Question());
        }
        return questionList;
    }
}
