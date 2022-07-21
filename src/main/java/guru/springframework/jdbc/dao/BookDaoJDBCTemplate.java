package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import java.util.Collections;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Modified by Pierrot on 7/21/22.
 */
public class BookDaoJDBCTemplate implements BookDao {
    private final JdbcTemplate jdbcTemplate;

    public BookDaoJDBCTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> findAllBooksSortByTitle(Pageable pageable) {
        try {
            String sql = BookMapper.SELECT_BOOK_ORDER_BY_TITLE + pageable
                    .getSort().getOrderFor("title").getDirection().name()
                    + " limit ? offset ?";

            System.out.println(sql);

            return jdbcTemplate.query(sql, BookMapper.bookRowMapper,
                    pageable.getPageSize(), pageable.getOffset());
        } catch (NullPointerException npe){
            npe.printStackTrace();
            return Collections.emptyList();
        }

    }

    @Override
    public List<Book> findAllBooks(Pageable pageable) {
        return jdbcTemplate.query(BookMapper.SELECT_BOOK_OFFSET_LIMIT,
                BookMapper.bookRowMapper, pageable.getPageSize(),
                pageable.getOffset());
    }

    @Override
    public List<Book> findAllBooks(int pageSize, int offset) {
        return jdbcTemplate.query(BookMapper.SELECT_BOOK_OFFSET_LIMIT,
                BookMapper.bookRowMapper, pageSize, offset);
    }

    @Override
    public List<Book> findAllBooks() {
        return jdbcTemplate.query(BookMapper.ALL_BOOKS, BookMapper.bookRowMapper);
    }

    @Override
    public Book findBookById(Long id) {
        return jdbcTemplate.queryForObject(BookMapper.SELECT_BOOK_BY_ID, BookMapper.bookRowMapper, id);
    }

    @Override
    public Book findBookByTitle(String title) {
        return jdbcTemplate.queryForObject(BookMapper.SELECT_BOOK_BY_TITLE, BookMapper.bookRowMapper, title);
    }

    @Override
    public Book saveNewBook(Book book) {
        jdbcTemplate.update(BookMapper.INSERT_BOOK,
                book.getIsbn(), book.getPublisher(), book.getTitle(), book.getAuthorId());

        Long createdId = jdbcTemplate.queryForObject(BookMapper.SELECT_LAST_INSERT_ID, Long.class);

        return this.findBookById(createdId);
    }

    @Override
    public Book updateBook(Book book) {
        jdbcTemplate.update(BookMapper.UPDATE_BOOK,
                book.getIsbn(), book.getPublisher(), book.getTitle(), book.getAuthorId(), book.getId());

        return this.findBookById(book.getId());
    }

    @Override
    public void deleteBookById(Long id) {
        jdbcTemplate.update(BookMapper.DELETE_BOOK, id);
    }

}
