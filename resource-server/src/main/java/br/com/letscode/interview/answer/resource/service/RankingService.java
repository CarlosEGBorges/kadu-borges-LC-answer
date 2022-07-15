package br.com.letscode.interview.answer.resource.service;

import br.com.letscode.interview.answer.resource.domain.Quiz;
import br.com.letscode.interview.answer.resource.domain.RankingPosition;
import br.com.letscode.interview.answer.resource.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RankingService {

    @Autowired
    QuizRepository quizRepository;

    public List<RankingPosition> getRanking(){
        List<RankingPosition> ranking = new ArrayList<>();
        List<Quiz> quizList = quizRepository.findAllByActiveIsFalseOrderByUsername();
        String player = null;
        double questions = 0;
        double points = 0;
        for(Quiz q : quizList){
            if(player != null && !player.equals(q.getUsername())){
                ranking.add(new RankingPosition(player, points*100/questions));
                questions = 0;
                points = 0;
                player = q.getUsername();
            } else {
                player = q.getUsername();
                questions += q.getQuestionList().size();
                points += (q.getQuestionList().size() - q.getMistakes());
            }
        }
        Collections.sort(ranking);
        return ranking;
    }
}
