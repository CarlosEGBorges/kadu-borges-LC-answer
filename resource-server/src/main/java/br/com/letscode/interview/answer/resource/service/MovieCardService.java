package br.com.letscode.interview.answer.resource.service;

import br.com.letscode.interview.answer.resource.domain.MovieCard;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;

@Service
public class MovieCardService {

    public Double calculateScore(MovieCard movieCard){
        if(movieCard == null){
            throw new InvalidParameterException("MovieCard não pode ser Null");
        }
        Double score = 0.0;
        score = movieCard.getImdbRating() * movieCard.getImdbVotes();
        return score;
    }
}
