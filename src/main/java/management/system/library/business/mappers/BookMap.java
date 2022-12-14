package management.system.library.business.mappers;

import management.system.library.model.Book;
import management.system.library.business.repository.model.BookDAO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMap {

    BookDAO book_TO_bookDAO(Book book);
    Book bookDAO_TO_book(BookDAO bookDAO);

}
