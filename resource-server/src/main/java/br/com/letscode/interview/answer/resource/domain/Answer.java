package br.com.letscode.interview.answer.resource.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "movie_card_id")
    private MovieCard selectedOption;

    private Boolean correct;
}