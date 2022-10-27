package management.system.library.business.service;

import management.system.library.model.Author;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface AuthorService {

    List<Author> findAllAuthors();

    Optional<Author> findAuthorById(Long id);

}
