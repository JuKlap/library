package management.system.library.service;

import management.system.library.business.mappers.AuthorMap;
import management.system.library.business.repository.AuthorRepository;
import management.system.library.business.repository.model.AuthorDAO;
import management.system.library.business.service.impl.AuthorServiceImpl;
import management.system.library.model.Author;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorServiceImplTest {

    @Mock
    private AuthorRepository repository;

    @InjectMocks
    private AuthorServiceImpl service;

    @Mock
    private AuthorMap map;

    private Author author;
    private AuthorDAO authorDAO;
    private List<Author> authorList;
    private List<AuthorDAO> authorDAOList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllAuthors_successful(){
        authorDAOList = createAuthorsDAOList();
        authorList = createAuthorList();

        when(repository.findAll()).thenReturn(authorDAOList);
        List<Author> authors = service.findAllAuthors();
        assertEquals(1, authors.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findAllAuthors_invalid(){
        when(repository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertTrue(service.findAllAuthors().isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findAuthorById_successful(){
        author = createAuthor(1L);
        authorDAO = createAuthorDAO(1L);

        when(repository.findById(anyLong())).thenReturn(Optional.of(authorDAO));
        when(map.authorDAO_TO_author(authorDAO)).thenReturn(author);
        Optional<Author> returnedAuthor = service.findAuthorById(author.getId());
        assertEquals(author.getId(), returnedAuthor.get().getId());
        assertEquals(author.getFirstName(), returnedAuthor.get().getFirstName());
        assertEquals(author.getSurname(), returnedAuthor.get().getSurname());
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void findAuthorById_invalid(){
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertFalse(service.findAuthorById(anyLong()).isPresent());
        verify(repository, times(1)).findById(anyLong());
    }

    private Author createAuthor(Long id){
        Author author = new Author();
        author.setId(id);
        author.setFirstName("Charles");
        author.setSurname("Dickens");
        return author;
    }

    private AuthorDAO createAuthorDAO(Long id){
        AuthorDAO authorDAO = new AuthorDAO();
        authorDAO.setId(id);
        authorDAO.setFirstName("Charles");
        authorDAO.setSurname("Dickens");
        return authorDAO;
    }

    private List<Author> createAuthorList(){
        List<Author> authorList = new ArrayList<>();
        authorList.add(createAuthor(1L));
        return authorList;
    }

    private List<AuthorDAO> createAuthorsDAOList(){
        List<AuthorDAO> authorDAOList = new ArrayList<>();
        authorDAOList.add(createAuthorDAO(1L));
        return authorDAOList;
    }

}

