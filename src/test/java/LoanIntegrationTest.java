import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.data.BookDao;
import se.yrgo.data.BorrowerDao;
import se.yrgo.domain.*;
import se.yrgo.exception.NoAvailableBookCopiesException;
import se.yrgo.services.LoanService;
import se.yrgo.services.LoanServiceImpl;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

        List<Loan> loans = loanService.getAllLoans();
        assertEquals(1, loans.size());

        Loan loan = loans.get(0);
        assertEquals(borrower.getId(), loan.getBorrower().getId());
        assertEquals(book.getIsbn(), loan.getBookCopy().getBook().getIsbn());
        assertTrue(loan.isActive());
    }

    @Test
    void cannotBorrowWhenNoCopyAvailable() {
        Borrower borrower = createBorrower();

        Book book = new Book("2736352819283", "Gardening for cuties", new Author("Da Gardening Expert"));

        bookDao.save(book);

        assertThrows(NoAvailableBookCopiesException.class, () -> {
            loanService.borrowBook(borrower.getId(), book.getIsbn());
        });

        assertEquals(0, loanService.getAllLoans().size());
    }

    private Borrower createBorrower() {
        Borrower borrower = new Borrower("Tulipe", "petitetulipe@mail.com", "SuperSercretShush11!");
        borrowerDao.save(borrower);
        return borrower;
    }
}
