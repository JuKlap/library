package management.system.library.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import management.system.library.model.Author;
import management.system.library.business.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@ApiResponses(value = {
        @ApiResponse(code = 200, message = "The request has succeeded", response = Author.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI"),
        @ApiResponse(code = 500, message = "Server error")
})
@Log4j2
@RestController
@RequestMapping("/library/system/authors")
@Component
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @ApiOperation(value = "Finds all authors",
            notes = "Returns the list of authors",
            response = Author.class, responseContainer = "List")
    @GetMapping
    public ResponseEntity<List<Author>> findAllAuthors(){
        log.info("Retrieve list of authors");
        List<Author> authorList = authorService.findAllAuthors();
        if(authorList.isEmpty()){
            log.info("Authors list is empty. {}", authorList);
            return ResponseEntity.notFound().build();
        }
        log.info("Author list is found. Size: {}", authorList::size);
        return ResponseEntity.ok(authorList);
    }

    @ApiOperation(value = "Finds an author by id",
            notes = "Returns an author with specific id",
            response = Author.class)
    @GetMapping("/{id}")
    public ResponseEntity<Author> findAuthorById(Long id){
        log.info("Find author by passing an ID, where author ID is {}", id);
        Optional<Author> author = (authorService.findAuthorById(id));
        if(!author.isPresent()){
            log.info("Author with id {} is not found", id);
        } else {
            log.info("Author with id {} is found: {}", id, author);
        }
        return author.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
