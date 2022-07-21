package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import org.springframework.jdbc.core.RowMapper;

/**
 * Modified by Pierrot on 7/21/22.
 */
public class AuthorMapper {

    public static final String SELECT_AUTHOR_BY_LAST_NAME = "SELECT * FROM AUTHOR WHERE last_name = ? ";

    public static final RowMapper<Author> authorRowMapper = (rs, rowNum) -> {
        Author author = new Author();
        author.setId(rs.getLong("id"));
        author.setFirstName(rs.getString("first_name"));
        author.setLastName(rs.getString("last_name"));

        return author;
    };

    private AuthorMapper() {
    }
}
