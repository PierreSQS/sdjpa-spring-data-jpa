package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import org.springframework.jdbc.core.RowMapper;

public class BookMapper {
    public static final String SELECT_BOOK_BY_TITLE = "SELECT * FROM BOOK WHERE title = ?";
    public static final String SELECT_BOOK_BY_ID = "SELECT * FROM BOOK WHERE id = ?";
    public static final String INSERT_BOOK =
            "INSERT INTO BOOK (isbn, publisher, title, author_id) VALUES (?, ?, ?, ?)";
    public static final String SELECT_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
    public static final String UPDATE_BOOK =
            "UPDATE BOOK SET isbn = ?, publisher = ?, title = ?, author_id = ? WHERE id = ?";
    public static final String DELETE_BOOK = "DELETE FROM BOOK WHERE id = ?";

    private BookMapper() {
    }

    public static final RowMapper<Book> bookRowMapper = (rs, rowNum) -> {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setIsbn(rs.getString("isbn"));
        book.setPublisher(rs.getString("publisher"));
        book.setTitle(rs.getString("title"));
        book.setAuthorId(rs.getLong("author_id"));
        return book;
    };
}
