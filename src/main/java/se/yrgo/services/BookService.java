package se.yrgo.services;

import se.yrgo.domain.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();
    Book findByIsbn(String isbn);
    List<Book> getBooksByAuthor(String name);
    void deleteBookByIsbn(String isbn);
    void registerNewBook(Book book);
}
