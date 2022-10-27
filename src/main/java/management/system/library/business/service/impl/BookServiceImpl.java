package management.system.library.business.service.impl;

import lombok.extern.log4j.Log4j2;
import management.system.library.business.mappers.BookMap;
import management.system.library.business.service.BookService;
import management.system.library.model.Book;
import management.system.library.business.repository.BookRepository;
import management.system.library.business.repository.model.BookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookMap bookMap;

    @Override
    public List<Book> findAllBooks(){
        List<BookDAO> bookDAOList = (List<BookDAO>) bookRepository.findAll();
        log.info("Get books list. Size is: {}", bookDAOList::size);
        return bookDAOList.stream().map(bookMap::bookDAO_TO_book).collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findBookById(Long id){
        Optional<Book> bookById = bookRepository.findById(id)
                .flatMap(book -> Optional.ofNullable(bookMap.bookDAO_TO_book(book)));
        log.info("Author with id {} is {}", id, bookById);
        return bookById;
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
        log.info("Author with id {} is deleted", id);
    }

}
