package br.com.letscode.interview.answer.resource.service;

import br.com.letscode.interview.answer.resource.domain.MovieCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MovieCardServiceTest {

    @InjectMocks
    private MovieCardService movieCardService = new MovieCardService();

    @Test
    public void shouldThrowExceptionOnCalculateScore_whenMovieCardIsNull() {
        assertThrows(InvalidParameterException.class, () -> movieCardService.calculateScore(null));
    }

    @Test
    public void shouldNotThrowExceptionOnCalculateScore_whenMovieCardIsNotNull() {
        MovieCard movieCard = createMovieCard(1);
        assertDoesNotThrow(() -> movieCardService.calculateScore(movieCard));
    }

    @Test
    public void shouldCalculateTheScoreCorrectly() {
        MovieCard movieCard = createMovieCard(2);
        Double score = movieCard.getImdbRating() * movieCard.getImdbVotes();
        assertEquals(score, movieCardService.calculateScore(movieCard));
    }

    private MovieCard createMovieCard(Integer randomNumber){
        MovieCard newMovieCard = new MovieCard();
        newMovieCard.setImdbVotes(randomNumber);
        newMovieCard.setImdbRating(randomNumber.doubleValue());
        return newMovieCard;
    }
}
