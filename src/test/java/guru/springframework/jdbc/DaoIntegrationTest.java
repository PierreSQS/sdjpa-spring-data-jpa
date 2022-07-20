package guru.springframework.jdbc;

import guru.springframework.jdbc.dao.AuthorDao;
import guru.springframework.jdbc.dao.BookDao;
import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.domain.Book;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Modified by Pierrot on 7/20/22.
 */
@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DaoIntegrationTest {
    @Autowired
    AuthorDao authorDao;

    @Autowired
    BookDao bookDao;

    @Test
    void testDeleteBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        Book saved = bookDao.saveNewBook(book);

        bookDao.deleteBookById(saved.getId());

        Book deleted = bookDao.findBookById(saved.getId());

        assertThat(deleted).isNull();
    }

    @Test
    void updateBookTest() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");

        Author author = new Author();
        author.setId(3L);

        book.setAuthor(author);
        Book saved = bookDao.saveNewBook(book);

        saved.setTitle("New Book");
        Book updateBook = bookDao.updateBook(saved);

        Book fetched = bookDao.findBookById(updateBook.getId());

        assertThat(fetched.getTitle()).isEqualTo("New Book");
    }

    @Test
    void testSaveBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");

        Author author = new Author();
        author.setId(3L);

        book.setAuthor(author);
        Book saved = bookDao.saveNewBook(book);

        assertThat(saved).isNotNull();
    }

    @Test
    void testGetBookByTitle() {
        Book book = bookDao.findBookByTitle("Clean Code");

        assertThat(book).isNotNull();
    }

    @Test
    void testGetBookByTitleNotFound() {
        assertThrows(EntityNotFoundException.class,() ->
                bookDao.findBookByTitle("Book Not Existing"));
    }

    @Test
    void testGetBook() {
        Book book = bookDao.findBookById(3L);

        assertThat(book.getId()).isNotNull();
    }

    @Test
    void testDeleteAuthor() {
        Author author = new Author();
        author.setFirstName("john");
        author.setLastName("t");

        System.out.printf("%n###### the author to delete: %s %s ######%n%n"
                , author.getFirstName(), author.getLastName());

        Author saved = authorDao.saveNewAuthor(author);
        Long id = saved.getId();

        authorDao.deleteAuthorById(id);

        assertThrows(EmptyResultDataAccessException.class, () ->
                authorDao.findAuthorById(id));

    }

    @Test
    void testUpdateAuthor() {
        Author author = new Author();
        author.setFirstName("john");
        author.setLastName("t");

        System.out.printf("%n###### the author to update: %s %s ######%n%n"
                , author.getFirstName(), author.getLastName());

        Author saved = authorDao.saveNewAuthor(author);

        saved.setLastName("Thompson");
        Author updated = authorDao.updateAuthor(saved);

        assertThat(updated.getLastName()).isEqualTo("Thompson");
        // Made the test more accurate
        assertThat(saved.getId()).isEqualTo(updated.getId());

        System.out.printf("%n###### the updated author name: %s %s -ID: %d ######%n%n"
                , updated.getFirstName(), updated.getLastName(),updated.getId());
    }

    @Test
    void testInsertAuthor() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Thompson");
        Author saved = authorDao.saveNewAuthor(author);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();

        System.out.printf("%n###### the saved author name: %s %s -ID: %s ######%n%n"
                , author.getFirstName(), author.getLastName(), author.getId());
    }

    @Test
    void testGetAuthorByName() {
        Author author = authorDao.findAuthorByName("Craig", "Walls");

        assertThat(author).isNotNull();
        System.out.printf("%n###### the found author name: %s ######%n%n", author.getLastName());
    }

    @Test
    void testGetAuthorByNameNotFound() {
        assertThrows(EntityNotFoundException.class, () -> authorDao.findAuthorByName("foo", "bar"));
    }

    @Test
    void testGetAuthorById() {
        Author author = authorDao.findAuthorById(2L);
        assertThat(author.getId()).isNotNull();
        System.out.printf("%n###### the found author name: %s ######%n%n", author.getLastName());

    }
}
