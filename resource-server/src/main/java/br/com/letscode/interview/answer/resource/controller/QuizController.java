package br.com.letscode.interview.answer.resource.controller;

import br.com.letscode.interview.answer.resource.domain.Answer;
import br.com.letscode.interview.answer.resource.domain.Question;
import br.com.letscode.interview.answer.resource.domain.Quiz;
import br.com.letscode.interview.answer.resource.domain.Response;
import br.com.letscode.interview.answer.resource.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class QuizController {

    @Autowired
    QuizService quizService;

    @GetMapping(path = "/quizzes/questions", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Question getQuestion(OAuth2Authentication authentication){
        try {
            return quizService.getQuestion(authentication.getPrincipal().toString(), false);
        }
        catch (Exception exception) {
            throw new ResponseStatusException (
                    HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @PostMapping(path = "/quizzes/questions", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Question createQuestion(OAuth2Authentication authentication){
        try {
            return quizService.createQuestion(authentication.getPrincipal().toString());
        }
        catch (Exception exception) {
            throw new ResponseStatusException (
                    HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @PostMapping(path = "/quizzes/questions/answer", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Answer answerQuestion(@RequestBody Response response, OAuth2Authentication authentication){
        try {
            return quizService.checkResponse(authentication.getPrincipal().toString(), response);
        }
        catch (Exception exception) {
            throw new ResponseStatusException (
                    HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @PostMapping(path = "/quizzes/start", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Quiz startQuiz(OAuth2Authentication authentication){
        try {
            return quizService.startQuiz(authentication.getPrincipal().toString());
        }
        catch (Exception exception) {
            throw new ResponseStatusException (
                    HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @PostMapping(path = "/quizzes/finish", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void finishQuiz(OAuth2Authentication authentication){
        try {
            quizService.finishQuiz(authentication.getPrincipal().toString());
        }
        catch (Exception exception) {
            throw new ResponseStatusException (
                    HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }
}
