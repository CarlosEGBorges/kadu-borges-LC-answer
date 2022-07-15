package br.com.letscode.interview.answer.resource.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Quiz {

    public static final Integer MAX_MISTAKES = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @OneToMany(mappedBy = "quiz")
    private List<Question> questionList;

    private Integer mistakes;
    private Boolean active;
    private Date createdAt;
    @Version
    private Integer version;
}
