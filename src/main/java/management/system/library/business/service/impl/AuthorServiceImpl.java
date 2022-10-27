package management.system.library.business.service.impl;

import lombok.extern.log4j.Log4j2;
import management.system.library.business.mappers.AuthorMap;
import management.system.library.model.Author;
import management.system.library.business.repository.AuthorRepository;
import management.system.library.business.repository.model.AuthorDAO;
import management.system.library.business.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    AuthorMap authorMap;

    @Override
    public List<Author> findAllAuthors(){
        List<AuthorDAO> authorDAOList = (List<AuthorDAO>) authorRepository.findAll();
        log.info("Get authors list. Size is: {}", authorDAOList::size);
        return authorDAOList.stream().map(authorMap::authorDAO_TO_author).collect(Collectors.toList());
    }

    @Override
    public Optional<Author> findAuthorById(Long id){
        Optional<Author> authorById = authorRepository.findById(id)
                .flatMap(author -> Optional.ofNullable(authorMap.authorDAO_TO_author(author)));
        log.info("Author with id {} is {}", id, authorById);
        return authorById;
    }


}
