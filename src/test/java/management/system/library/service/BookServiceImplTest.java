package management.system.library.service;

import management.system.library.business.mappers.BookMap;
import management.system.library.business.repository.BookRepository;
import management.system.library.business.repository.model.BookDAO;
import management.system.library.business.service.impl.BookServiceImpl;
import management.system.library.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class BookServiceImplTest {

    @Mock
    private BookRepository repository;

    @InjectMocks
    private BookServiceImpl service;

    @Mock
    private BookMap map;

    private Book book;
    private BookDAO bookDAO;
    private List<Book> bookList;
    private List<BookDAO> bookDAOList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllBooks_successful(){
        bookDAOList = createBookDAOList();
        bookList = createBookList();

        when(repository.findAll()).thenReturn(bookDAOList);
        List<Book> books = service.findAllBooks();
        assertEquals(1, books.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findAllBooks_invalid(){
        when(repository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertTrue(service.findAllBooks().isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findBookById_successful(){
        book = createBook(1L);
        bookDAO = createBookDAO(1L);

        when(repository.findById(anyLong())).thenReturn(Optional.of(bookDAO));
        when(map.bookDAO_TO_book(bookDAO)).thenReturn(book);
        Optional<Book> returnedBook = service.findBookById(book.getId());
        assertEquals(book.getId(), returnedBook.get().getId());
        assertEquals(book.getTitle(), returnedBook.get().getTitle());
        assertEquals(book.getIsbn(), returnedBook.get().getIsbn());
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void findBookById_invalid(){
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertFalse(service.findBookById(anyLong()).isPresent());
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void deleteBookById_successful(){
        service.deleteBookById(anyLong());
        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteBookById_invalid(){
        doThrow(new IllegalArgumentException()).when(repository).deleteById(anyLong());
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.deleteBookById(anyLong()));
    }

    private Book createBook(Long id){
        Book book = new Book();
        book.setId(id);
        book.setTitle("A Christmas Carol");
        book.setIsbn("155");
        return book;
    }

    private BookDAO createBookDAO(Long id){
        BookDAO bookDAO = new BookDAO();
        bookDAO.setId(id);
        bookDAO.setTitle("A Christmas Carol");
        bookDAO.setIsbn("155");
        return bookDAO;
    }

    private List<Book> createBookList(){
        List<Book> bookList = new ArrayList<>();
        bookList.add(createBook(1L));
        return bookList;
    }

    private List<BookDAO> createBookDAOList(){
        List<BookDAO> bookDAOList = new ArrayList<>();
        bookDAOList.add(createBookDAO(1L));
        return bookDAOList;
    }

}
