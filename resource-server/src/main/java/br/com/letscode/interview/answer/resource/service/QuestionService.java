package br.com.letscode.interview.answer.resource.service;

import br.com.letscode.interview.answer.resource.domain.*;
import br.com.letscode.interview.answer.resource.repository.AnswerRepository;
import br.com.letscode.interview.answer.resource.repository.MovieCardRepository;
import br.com.letscode.interview.answer.resource.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class QuestionService {

    @Autowired
    MovieCardRepository movieCardRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    MovieCardService movieCardService;

    public Question createNewQuestion(Quiz quiz){
        Question newQuestion = new Question();
        Iterable<MovieCard> movieCardIterable = movieCardRepository.findAll();
        List<MovieCard> movieCardList = new ArrayList<>();
        movieCardIterable.forEach(mc -> movieCardList.add(mc));
        Integer movieCardsPossiblePairsCount = movieCardList.size() * (movieCardList.size() - 1);
        if(quiz.getQuestionList().size() < movieCardsPossiblePairsCount) {
            while(true) {
                Random randomIndex = new Random();
                MovieCard cardOne = movieCardList.get(randomIndex.nextInt(movieCardList.size()));
                List<MovieCard> impossiblePairs = new ArrayList<>();
                impossiblePairs.add(cardOne);
                for (Question question : quiz.getQuestionList()) {
                    if (question.getCardOne().equals(cardOne)) {
                        impossiblePairs.add(question.getCardTwo());
                    } else if (question.getCardTwo().equals(cardOne)) {
                        impossiblePairs.add(question.getCardOne());
                    }
                }
                MovieCard cardTwo = null;
                if (movieCardList.size() != impossiblePairs.size()) {
                    movieCardList.removeAll(impossiblePairs);
                    cardTwo = movieCardList.get(randomIndex.nextInt(movieCardList.size()));
                    newQuestion.setCardOne(cardOne);
                    newQuestion.setCardTwo(cardTwo);
                    newQuestion.setQuiz(quiz);
                    questionRepository.save(newQuestion);
                    return newQuestion;
                }
            }
        } else {
            throw new IllegalStateException("Não é possível criar mais questões para este quiz.");
        }
    }

    public Answer evaluateResponse(Question openedQuestion, Response response) {
        Answer answer = new Answer();
        if(openedQuestion.getCardOne().getImdbId().equals(response.getImdbId())){
            answer.setSelectedOption(openedQuestion.getCardOne());
        } else if(openedQuestion.getCardTwo().getImdbId().equals(response.getImdbId())){
            answer.setSelectedOption(openedQuestion.getCardTwo());
        }
        if(answer.getSelectedOption() != null){
            Double scoreCardOne = movieCardService.calculateScore(openedQuestion.getCardOne());
            Double scoreCardTwo = movieCardService.calculateScore(openedQuestion.getCardTwo());
            if (scoreCardOne > scoreCardTwo) {
                answer.setCorrect(answer.getSelectedOption().equals(openedQuestion.getCardOne()));
            } else {
                answer.setCorrect(answer.getSelectedOption().equals(openedQuestion.getCardTwo()));
            }
            answer.setCreatedAt(new Date());
            answerRepository.save(answer);
            return answer;
        } else {
            throw new IllegalArgumentException("Resposta não condiz com as opções disponíveis.");
        }
    }
}
