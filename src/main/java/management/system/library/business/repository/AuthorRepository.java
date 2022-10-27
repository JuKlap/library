package management.system.library.business.repository;

import management.system.library.business.repository.model.AuthorDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<AuthorDAO, Long> {
}
