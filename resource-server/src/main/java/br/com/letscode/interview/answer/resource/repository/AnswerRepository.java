package br.com.letscode.interview.answer.resource.repository;

import br.com.letscode.interview.answer.resource.domain.Answer;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer, Long>
{
}
