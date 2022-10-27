package management.system.library.business.repository;

import management.system.library.business.repository.model.BookDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<BookDAO, Long> {

}
