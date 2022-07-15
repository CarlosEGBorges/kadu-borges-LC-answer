package br.com.letscode.interview.answer.resource.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    @JsonIgnore
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "card_one")
    private MovieCard cardOne;

    @ManyToOne
    @JoinColumn(name = "card_two")
    private MovieCard cardTwo;

    @ManyToOne
    @JoinColumn(name = "answer_id", nullable = true)
    private Answer answer;
}
