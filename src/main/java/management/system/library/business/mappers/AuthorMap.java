package management.system.library.business.mappers;


import management.system.library.model.Author;
import management.system.library.business.repository.model.AuthorDAO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMap {

    AuthorDAO author_TO_authorDAO(Author author);
    Author authorDAO_TO_author(AuthorDAO authorDAO);

}
