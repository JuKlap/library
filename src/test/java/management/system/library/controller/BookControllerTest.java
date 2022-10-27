package management.system.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import management.system.library.business.service.AuthorService;
import management.system.library.business.service.BookService;
import management.system.library.controllers.AuthorController;
import management.system.library.controllers.BookController;
import management.system.library.model.Author;
import management.system.library.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {


    public static String URL = "/library/system/books";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookController controller;

    @MockBean
    private BookService service;

    @Test
    void findAllBooks_successful() throws Exception{
        List<Book> bookList = createBookList();
        when(service.findAllBooks()).thenReturn(bookList);

        ResultActions mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get(URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("A Christmas Carol"))
                .andExpect(status().isOk());
        verify(service, times(1)).findAllBooks();
    }

    @Test
    void findAllBooks_invalid() throws Exception{
        List<Book> bookList = createBookList();
        bookList.clear();
        when(service.findAllBooks()).thenReturn(bookList);

        ResultActions mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get(URL)
                        .content(asJsonString(bookList))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(service, times(1)).findAllBooks();
    }

    @Test
    void findBookById_successful() throws Exception {
        Optional<Book> book = Optional.of(createBook(1L));
        when(service.findBookById(anyLong())).thenReturn(book);

        ResultActions mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("A Christmas Carol"))
                .andExpect(status().isOk());
        verify(service, times(1)).findBookById(anyLong());
    }

    @Test
    void findBookById_invalid() throws Exception{
        Optional<Book> book = Optional.of(createBook(1L));
        book.get().setId(null);
        when(service.findBookById(null)).thenReturn(Optional.empty());

        ResultActions mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + null)
                        .content(asJsonString(book))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(service, times(0)).findBookById(null);
    }

    @Test
    void deleteBookById_successful() throws Exception{
        Optional<Book> book = Optional.of(createBook(1L));
        when(service.findBookById(anyLong())).thenReturn(book);

        ResultActions mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .delete(URL + "/1")
                .content(asJsonString(book))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(service, times(1)).deleteBookById(anyLong());
    }

    @Test
    void deleteBookById_invalid() throws Exception{
        Optional<Book> book = Optional.of(createBook(1L));
        book.get().setId(null);
        when(service.findBookById(null)).thenReturn(book);

        ResultActions mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .delete(URL + null)
                .content(asJsonString(book))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(service, times(0)).deleteBookById(null);
    }



    private Book createBook(Long id){
        Book book = new Book();
        book.setId(id);
        book.setTitle("A Christmas Carol");
        book.setIsbn("155");
        return book;
    }

    private List<Book> createBookList(){
        List<Book> bookList = new ArrayList<>();
        bookList.add(createBook(1L));
        return bookList;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
