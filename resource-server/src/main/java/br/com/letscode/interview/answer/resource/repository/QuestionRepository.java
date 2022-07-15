package br.com.letscode.interview.answer.resource.repository;

import br.com.letscode.interview.answer.resource.domain.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {
}