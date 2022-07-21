package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import org.springframework.jdbc.core.RowMapper;

public class BookMapper {
    public static final String SELECT_BOOK_ORDER_BY_TITLE = "SELECT * FROM BOOK ORDER BY title ";
    public static final String SELECT_BOOK_OFFSET_LIMIT = "SELECT * FROM BOOK limit ? offset ?";
    public static final String SELECT_BOOK_BY_TITLE = "SELECT * FROM BOOK WHERE title = ?";
    public static final String SELECT_BOOK_BY_ID = "SELECT * FROM BOOK WHERE id = ?";
    public static final String ALL_BOOKS = "SELECT * FROM BOOK";
    public static final String INSERT_BOOK =
            "INSERT INTO BOOK (isbn, publisher, title, author_id) VALUES (?, ?, ?, ?)";
    public static final String SELECT_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
    public static final String UPDATE_BOOK =
            "UPDATE BOOK SET isbn = ?, publisher = ?, title = ?, author_id = ? WHERE id = ?";
    public static final String DELETE_BOOK = "DELETE FROM BOOK WHERE id = ?";

    public static final RowMapper<Book> bookRowMapper = (rs, rowNum) -> {
        Book book = new Book();
        book.setId(rs.getLong(1));
        book.setIsbn(rs.getString(2));
        book.setPublisher(rs.getString(3));
        book.setTitle(rs.getString(4));
        book.setAuthorId(rs.getLong(5));
        return book;
    };

    private BookMapper() {
    }
}
