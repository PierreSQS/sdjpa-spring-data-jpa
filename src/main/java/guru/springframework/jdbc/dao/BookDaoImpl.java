package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import guru.springframework.jdbc.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookDaoImpl implements BookDao{
    private final BookRepository bookRepo;

    public BookDaoImpl(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public Book findBookById(Long id) {
        return bookRepo.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    @Override
    public Book findBookByTitle(String title) {
        return bookRepo.findByTitle(title).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Book saveNewBook(Book book) {
        return bookRepo.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        Optional<Book> foundBookOpt = bookRepo.findById(book.getId());
        if (foundBookOpt.isPresent()) {
            Book foundBook1 = foundBookOpt.get();
            foundBook1.setTitle(book.getTitle());
            return foundBook1;
        }
        return null;
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepo.deleteById(id);
    }
}
