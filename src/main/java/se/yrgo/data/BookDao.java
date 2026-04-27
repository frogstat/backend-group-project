package se.yrgo.data;

import se.yrgo.domain.Author;
import se.yrgo.domain.Book;

import java.util.List;

public interface BookDao {
    List<Book> getAllBooks();

    void save(Book newBook);

    void deleteById(String isbn);

    Book findById(String isbn);

    List<Book> getBooksByAuthor(String name);
}
