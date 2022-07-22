package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
class AuthorDaoJDBCTemplateTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("authorDaoJDBCTemplate")
    AuthorDao authorDao;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAllAuthorsByLastName() {
        List<Author> authors = authorDao.findAllAuthorsByLastName("Smith", PageRequest.of(0, 10));

        assertThat(authors)
                .isNotNull()
                .hasSize(10);

        printAuthorsDebugLog(authors);

    }

    @Test
    void findAllAuthorsByLastNameSortFirstNameDesc() {
        List<Author> authors = authorDao.findAllAuthorsByLastName("Smith",
                PageRequest.of(0, 10, Sort.by(Sort.Order.desc("firstname"))));

        assertThat(authors)
                .isNotNull()
                .hasSize(10);
        assertThat(authors.get(0).getFirstName()).isEqualTo("Yugal");

        printAuthorsDebugLog(authors);
    }

    @Test
    void findAllAuthorsByLastNameSortFirstNameAsc() {
        List<Author> authors = authorDao.findAllAuthorsByLastName("Smith",
                PageRequest.of(0, 10, Sort.by(Sort.Order.asc("firstname"))));

        assertThat(authors)
                .isNotNull()
                .hasSize(10);
        assertThat(authors.get(0).getFirstName()).isEqualTo("Ahmed");

        printAuthorsDebugLog(authors);

    }

    @Test
    void findAllAuthorsByLastNameAllRecs() {
        List<Author> authors = authorDao.findAllAuthorsByLastName("Smith", PageRequest.of(0, 100));

        assertThat(authors)
                .isNotNull()
                .hasSize(40);

        printAuthorsDebugLog(authors);
    }


    private void printAuthorsDebugLog(List<Author> authors) {
        System.out.printf("%n###### following the books in test ######%n");
        authors.forEach(author -> System.out.println(author.getFirstName()+" "+author.getLastName()));
        System.out.println();
    }
}