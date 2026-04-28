package se.yrgo.data;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import se.yrgo.domain.Book;
import se.yrgo.exception.NotFoundException;

import java.util.List;

@Repository
public class BookDaoJpaImpl implements BookDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Book> getAllBooks() {
        return em.createQuery("select b from Book b", Book.class)
                .getResultList();
    }

    @Override
    public void save(Book newBook) {
        em.merge(newBook);
    }

    @Override
    public void deleteById(String isbn) {
        Book book = em.find(Book.class, isbn);

        if (book == null) {
            throw new NotFoundException("No book found with ISBN: " + isbn);
        }

        em.remove(book);
    }

    @Override
    public Book findById(String isbn) {
        Book book = em.find(Book.class, isbn);

        if (book == null) {
            throw new NotFoundException("No book found with ISBN: " + isbn);
        }

        return book;
    }

    @Override
    public List<Book> getBooksByAuthor(String name) {
        return em.createQuery("select b from Book b join b.authors a where a.name = :name", Book.class)
                .setParameter("name", name)
                .getResultList();
    }
}
