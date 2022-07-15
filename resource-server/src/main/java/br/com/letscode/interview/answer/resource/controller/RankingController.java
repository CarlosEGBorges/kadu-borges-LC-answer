package br.com.letscode.interview.answer.resource.controller;

import br.com.letscode.interview.answer.resource.domain.RankingPosition;
import br.com.letscode.interview.answer.resource.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api")
@CrossOrigin(origins = "*")
public class RankingController {

    @Autowired
    private RankingService rankingService;

    @GetMapping(path = "/ranking", produces = "application/json")
    public List<RankingPosition> getRanking(){
        return rankingService.getRanking();
    }
}