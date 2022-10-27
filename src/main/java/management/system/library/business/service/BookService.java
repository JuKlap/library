package management.system.library.business.service;

import management.system.library.model.Book;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface BookService {

    List<Book> findAllBooks();

    Optional<Book> findBookById(Long id);

    void deleteBookById(Long id);

}
