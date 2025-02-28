package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.repositories.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Modified by Pierrot, on 26-02-2025.
 */
@Component
public class AuthorDaoImpl implements AuthorDao {

    private final AuthorRepository authorRepo;

    public AuthorDaoImpl(AuthorRepository authorRepo) {
        this.authorRepo = authorRepo;
    }

    @Override
    public Author getById(Long id) {
        return authorRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with ID " + id + " not found"));
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return authorRepo.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return authorRepo.save(author);
    }

    @Override
    public Author updateAuthor(Author author) {
        return authorRepo.findById(author.getId())
                .map(authorToUpdate -> {
                    authorToUpdate.setFirstName(author.getFirstName());
                    authorToUpdate.setLastName(author.getLastName());
                    return authorRepo.save(authorToUpdate);
                }).orElse(null);
    }

    @Override
    public void deleteAuthorById(Long id) {
        authorRepo.deleteById(id);
    }
}
