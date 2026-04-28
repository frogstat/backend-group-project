package se.yrgo.services;

import org.springframework.stereotype.Service;
import se.yrgo.data.BookDao;
import se.yrgo.domain.Book;
import se.yrgo.exception.AlreadyExistsException;
import se.yrgo.exception.NotFoundException;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }


    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    @Override
    public Book findByIsbn(String isbn) {
        return bookDao.findById(isbn);
    }

    @Override
    public List<Book> getBooksByAuthor(String name) {
        return bookDao.getBooksByAuthor(name);
    }

    @Override
    public void deleteBookByIsbn(String isbn) {
        Book book = bookDao.findById(isbn);

        if (!book.getCopies().isEmpty()) {
            throw new AlreadyExistsException("Cannot remove book with existing copies from library.");
        }
        bookDao.deleteById(isbn);
    }

    @Override
    public void registerNewBook(Book book) {
        try {
            bookDao.findById(book.getIsbn());
            throw new AlreadyExistsException("Book already exists in the library.");
        } catch (NotFoundException e){
            bookDao.save(book);
        }
    }
}
