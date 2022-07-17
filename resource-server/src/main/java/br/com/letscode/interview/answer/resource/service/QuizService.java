package br.com.letscode.interview.answer.resource.service;

import br.com.letscode.interview.answer.resource.domain.Answer;
import br.com.letscode.interview.answer.resource.domain.Question;
import br.com.letscode.interview.answer.resource.domain.Quiz;
import br.com.letscode.interview.answer.resource.domain.Response;
import br.com.letscode.interview.answer.resource.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.security.InvalidParameterException;
import java.util.Date;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionService questionService;

    @Autowired
    MovieCardService movieCardService;

    public void finishQuiz(String username) {
        validateUsername(username);
        Quiz quiz = checkAvailableQuiz(username);
        quiz.setActive(false);
        for(Question question : quiz.getQuestionList()){
            if(question.getAnswer() == null){
                quiz.setMistakes(quiz.getMistakes()+1);
                break;
            }
        }
        quizRepository.save(quiz);
    }

    public Quiz startQuiz(String username) {
        validateUsername(username);
        Quiz quiz = quizRepository.findByUsernameAndActiveIsTrue(username);
        if(quiz == null){
            quiz = new Quiz();
            quiz.setUsername(username);
            quiz.setActive(true);
            quiz.setCreatedAt(new Date());
            quiz.setMistakes(0);
            quizRepository.save(quiz);
            return quiz;
        } else {
            throw new EntityExistsException("Não foi possível iniciar um novo quiz, pois já há um ativo para o usuário: " + username);
        }
    }

    public Question getQuestion(String username, Boolean createIfAbsent) {
        validateUsername(username);
        Quiz quiz = checkAvailableQuiz(username);
        Question question = null;
        for(Question qst : quiz.getQuestionList()){
            if(qst.getAnswer() == null){
                question = qst;
                break;
            }
        }
        if(question == null && createIfAbsent){
            question = questionService.createNewQuestion(quiz);
            quiz.getQuestionList().add(question);
            quizRepository.save(quiz);
        }
        return question;
    }

    public Question createQuestion(String username) {
        validateUsername(username);
        Quiz quiz = checkAvailableQuiz(username);
        Question question = null;
        for(Question qst : quiz.getQuestionList()){
            if(qst.getAnswer() == null){
                question = qst;
                break;
            }
        }
        if(question == null){
            question = questionService.createNewQuestion(quiz);
            quiz.getQuestionList().add(question);
            quizRepository.save(quiz);
        } else {
            throw new EntityExistsException("Não foi possível criar nova questão. Quiz ainda possui uma questão não respondida.");
        }
        return question;
    }

    public Answer checkResponse(String username, Response response) {
        validateUsername(username);
        Quiz quiz = checkAvailableQuiz(username);
        Question openedQuestion = this.getQuestion(username, false);
        if(openedQuestion != null) {
            Answer answer = questionService.evaluateResponse(openedQuestion, response);
            if(answer.getCorrect() == false){
                quiz.setMistakes(quiz.getMistakes()+1);
                if(quiz.getMistakes() == 3){
                    quiz.setActive(false);
                }
                quizRepository.save(quiz);
            }
            return answer;
        } else {
            throw new EntityNotFoundException("Nenhuma rodada a ser respondida foi encontrada para o usuário: " + username);
        }
    }

    public void validateUsername(String username){
        if(username == null || username.isEmpty()){
            throw new InvalidParameterException("É preciso informar o login do usuário.");
        }
    }

    public Quiz checkAvailableQuiz(String username){
        Quiz quiz = quizRepository.findByUsernameAndActiveIsTrue(username);
        if(quiz != null){
            return quiz;
        } else {
            throw new EntityNotFoundException("Nenhum quiz ativo encontrado para o usuário: " + username);
        }
    }
}