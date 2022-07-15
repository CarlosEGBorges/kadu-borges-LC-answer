package br.com.letscode.interview.answer.resource.repository;

import br.com.letscode.interview.answer.resource.domain.MovieCard;
import org.springframework.data.repository.CrudRepository;

public interface MovieCardRepository extends CrudRepository<MovieCard, String>
{
}
