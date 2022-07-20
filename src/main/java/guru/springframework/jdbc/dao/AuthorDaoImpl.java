package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.repositories.AuthorRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Modified by Pierrot on 7/20/22.
 */
@Component
public class AuthorDaoImpl implements AuthorDao {
    private final AuthorRepository authorRepo;

    public AuthorDaoImpl(AuthorRepository authorRepo) {
        this.authorRepo = authorRepo;
    }

    @Override
    public Author findAuthorById(Long id) {

        return authorRepo.findById(id).orElseThrow(() ->
                new EmptyResultDataAccessException(1));
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return authorRepo.save(author);
    }

    @Override
    public Author updateAuthor(Author author) {
        Optional<Author> foundAuthorOpt = authorRepo.findById(author.getId());
        return foundAuthorOpt.map(authorRepo::save).orElse(null);
    }

    @Override
    public void deleteAuthorById(Long id) {
        authorRepo.deleteById(id);
    }
}
