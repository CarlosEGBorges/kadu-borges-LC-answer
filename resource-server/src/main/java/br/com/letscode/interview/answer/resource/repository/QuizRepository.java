package br.com.letscode.interview.answer.resource.repository;

import br.com.letscode.interview.answer.resource.domain.Quiz;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuizRepository extends CrudRepository<Quiz, Long> {

    Quiz findByUsernameAndActiveIsTrue(String username);
    List<Quiz> findAllByActiveIsFalseOrderByUsername();
}
