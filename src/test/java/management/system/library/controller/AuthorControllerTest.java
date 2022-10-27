package management.system.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import management.system.library.business.service.AuthorService;
import management.system.library.controllers.AuthorController;
import management.system.library.model.Author;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    public static String URL = "/library/system/authors";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorController controller;

    @MockBean
    private AuthorService service;

    @Test
    void findAllAuthors_successful() throws Exception{
        List<Author> authorList = createAuthorList();
        when(service.findAllAuthors()).thenReturn(authorList);

        ResultActions mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get(URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("Charles"))
                .andExpect(status().isOk());
        verify(service, times(1)).findAllAuthors();
    }

    @Test
    void findAllAuthors_invalid() throws Exception{
        List<Author> authorList = createAuthorList();
        authorList.clear();
        when(service.findAllAuthors()).thenReturn(authorList);

        ResultActions mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get(URL)
                .content(asJsonString(authorList))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(service, times(1)).findAllAuthors();
    }

    @Test
    void findAuthorById_successful() throws Exception {
        Optional<Author> author = Optional.of(createAuthor(1L));
        when(service.findAuthorById(anyLong())).thenReturn(author);

        ResultActions mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Charles"))
                .andExpect(status().isOk());
        verify(service, times(1)).findAuthorById(anyLong());
    }

    @Test
    void findAuthorById_invalid() throws Exception{
        Optional<Author> author = Optional.of(createAuthor(1L));
        author.get().setId(null);
        when(service.findAuthorById(null)).thenReturn(Optional.empty());

        ResultActions mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get(URL + null)
                .content(asJsonString(author))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(service, times(0)).findAuthorById(null);
    }

    private Author createAuthor(Long id){
        Author author = new Author();
        author.setId(id);
        author.setFirstName("Charles");
        author.setSurname("Dickens");
        return author;
    }

    private List<Author> createAuthorList() {
        List<Author> authorList = new ArrayList<>();
        authorList.add(createAuthor(1L));
        return authorList;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
