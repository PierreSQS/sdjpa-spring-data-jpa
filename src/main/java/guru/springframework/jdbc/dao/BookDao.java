package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;

import java.util.List;

/**
 * Modified by Pierrot on 7/20/22.
 */
public interface BookDao {

    List<Book> findAllBooks(int pageSize, int offset);

    List<Book> findAllBooks();

    Book findBookById(Long id);

    Book findBookByTitle(String title);

    Book saveNewBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(Long id);

}
