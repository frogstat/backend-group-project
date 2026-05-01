import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.data.BookDao;
import se.yrgo.data.BorrowerDao;
import se.yrgo.domain.Author;
import se.yrgo.domain.BookCopy;
import se.yrgo.domain.Borrower;
import se.yrgo.domain.Book;
import se.yrgo.exception.NoAvailableBookCopiesException;
import se.yrgo.services.LoanService;
import se.yrgo.services.LoanServiceImpl;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:application.xml")
@Transactional
public class LoanIntegrationTest {
    @Autowired
    private BorrowerDao borrowerDao;

    @Autowired
    BookDao bookDao;

    @Autowired
    LoanService loanService;

    @Test
    void shouldCreateLoan() {
        Borrower borrower = createBorrower();

        Book book = new Book("2736352819283", "Gardening for cuties", new Author("Da Gardening Expert"));
        book.createCopy();
        bookDao.save(book);

        loanService.borrowBook(borrower.getId(), book.getIsbn());

        assertEquals(1, loanService.getAllLoans().size());
    }

    @Test
    void cannotBorrowWhenNoCopyAvailable() {
        Borrower borrower = createBorrower();

        Book book = new Book("2736352819283", "Gardening for cuties", new Author("Da Gardening Expert"));
        bookDao.save(book);
        assertThrows(NoAvailableBookCopiesException.class, () -> {
            loanService.borrowBook(borrower.getId(), book.getIsbn());
        });
    }

    private Borrower createBorrower() {
        Borrower borrower = new Borrower("Tulipe", "petitetulipe@mail.com", "SuperSercretShush11!");
        borrowerDao.save(borrower);
        return borrower;
    }
}
