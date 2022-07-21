package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collections;
import java.util.List;

/**
 * Modified by Pierrot on 7/21/22.
 */
public class AuthorDaoJDBCTemplate implements AuthorDao {

    private final JdbcTemplate jdbcTemplate;

    public AuthorDaoJDBCTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Author> findAllAuthorsByLastName(String lastname, Pageable pageable) {
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(AuthorMapper.SELECT_AUTHOR_BY_LAST_NAME);

            if (pageable.getSort().getOrderFor("firstname") != null) {
                sb.append("order by first_name ").append(pageable.getSort()
                        .getOrderFor("firstname").getDirection().name());
            }

            sb.append(" limit ? offset ?");

            return jdbcTemplate.query(sb.toString(), AuthorMapper.authorRowMapper,
                    lastname, pageable.getPageSize(), pageable.getOffset());

        } catch (NullPointerException npe) {
            return Collections.emptyList();
        }

    }

    @Override
    public Author findAuthorById(Long id) {
        return null;
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return null;
    }

    @Override
    public Author updateAuthor(Author author) {
        return null;
    }

    @Override
    public void deleteAuthorById(Long id) {

    }
}
