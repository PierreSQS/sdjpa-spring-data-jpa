package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
class BookDaoJDBCTemplateTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    BookDao bookDao;

    @BeforeEach
    void setUp() {
        bookDao = new BookDaoJDBCTemplate(jdbcTemplate);
    }

    @Test
    void testFindAllBooksWithPaging1() {
        List<Book> allBooks = bookDao.findAllBooks(10,0);
        assertThat(allBooks)
                .isNotEmpty()
                .hasSize(10);

        System.out.printf("%n###### following the books in test ######%n");
        allBooks.forEach(book -> System.out.println(book.getTitle()+" "+book.getPublisher()));
        System.out.println();
    }

    @Test
    void testFindAllBooksWithPaging2() {
        List<Book> allBooks = bookDao.findAllBooks(10,10);
        assertThat(allBooks)
                .isNotNull()
                .isNotEmpty()
                .hasSize(10);

        System.out.printf("%n###### following the books in test ######%n");
        allBooks.forEach(book -> System.out.println(book.getTitle()+" "+book.getPublisher()));
        System.out.println();
    }

    @Test
    void testFindAllBooksWithPagingX() {
        List<Book> allBooks = bookDao.findAllBooks(10,100);
        assertThat(allBooks)
                .isNotNull()
                .isNotEmpty()
                .hasSize(10);

        System.out.printf("%n###### following the books in test ######%n");
        allBooks.forEach(book -> System.out.println(book.getTitle()+" "+book.getPublisher()));
        System.out.println();
    }

    @Test
    void testFindAllBooks() {
        List<Book> allBooks = bookDao.findAllBooks();
        assertThat(allBooks)
                .isNotNull()
                .isNotEmpty()
                .hasSizeGreaterThan(4);

        System.out.printf("%n###### following the books in test ######%n");
        allBooks.forEach(book -> System.out.println(book.getTitle()+" "+book.getPublisher()));
        System.out.println();
    }

    @Test
    void findBookById() {
        Book book = bookDao.findBookById(3L);

        assertThat(book.getId()).isNotNull();
    }

    @Test
    void findBookByTitle() {
        Book book = bookDao.findBookByTitle("Clean Code");

        assertThat(book).isNotNull();
    }

    @Test
    void saveNewBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        book.setAuthorId(1L);

        Book saved = bookDao.saveNewBook(book);

        assertThat(saved).isNotNull();
    }

    @Test
    void updateBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        book.setAuthorId(1L);
        Book saved = bookDao.saveNewBook(book);

        saved.setTitle("New Book");
        bookDao.updateBook(saved);

        Book fetched = bookDao.findBookById(saved.getId());

        assertThat(fetched.getTitle()).isEqualTo("New Book");
    }

    @Test
    void deleteBookById() {

        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        Book saved = bookDao.saveNewBook(book);
        long savedId = saved.getId();

        bookDao.deleteBookById(savedId);

        assertThrows(EmptyResultDataAccessException.class, () ->
                bookDao.findBookById(savedId)
        );
    }
}