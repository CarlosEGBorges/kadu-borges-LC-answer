package br.com.letscode.interview.answer.resource.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class MovieCard {

    @Id
    private String imdbId;
    private String title;
    private String releasedYear;
    private String posterUrl;
    private Double imdbRating;
    private Integer imdbVotes;
}
