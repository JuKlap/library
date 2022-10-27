package management.system.library.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import management.system.library.model.Book;
import management.system.library.business.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@ApiResponses(value = {
        @ApiResponse(code = 200, message = "The request has succeeded", response = Book.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI"),
        @ApiResponse(code = 500, message = "Server error")
})
@Log4j2
@RequestMapping("/library/system/books")
@RestController
@Component
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping
    @ApiOperation(value = "Finds all books",
    notes = "Returns the list of books",
    response = Book.class, responseContainer = "List")
    public ResponseEntity<List<Book>> findAllBooks(){
        log.info("Retrieve list of books");
        List<Book> bookList = bookService.findAllBooks();
        if(bookList.isEmpty()){
            log.info("Books list is empty. {}", bookList);
            return ResponseEntity.notFound().build();
        }
        log.info("Books list is found. Size: {}", bookList::size);
        return ResponseEntity.ok(bookList);
    }

    @ApiOperation(value = "Finds a book by id",
            notes = "Returns a book with specific id",
            response = Book.class)
    @GetMapping("/{id}")
    public ResponseEntity<Book> findBookById(Long id){
        log.info("Find book by passing an ID, where book ID is {}", id);
        Optional<Book> book = (bookService.findBookById(id));
        if(!book.isPresent()){
            log.info("Book with id {} is not found", id);
        } else {
            log.info("Book with id {} is found: {}", id, book);
        }
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes a book by id",
            notes = "Deletes a book with specific id",
            response = Book.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteBookById(Long id){
        log.info("Delete book by passing an ID where ID is {}", id);
        Optional<Book> book = bookService.findBookById(id);
        if(!book.isPresent()){
            log.info("Book for deletion with ID {} is not found", id);
            return ResponseEntity.notFound().build();
        }
        bookService.deleteBookById(id);
        log.info("Book with id {} is deleted", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
