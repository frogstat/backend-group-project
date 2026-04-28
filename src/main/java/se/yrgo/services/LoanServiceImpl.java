package se.yrgo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.data.BookDao;
import se.yrgo.data.BorrowerDao;
import se.yrgo.data.LoanDao;
import se.yrgo.domain.Book;
import se.yrgo.domain.BookCopy;
import se.yrgo.domain.Borrower;
import se.yrgo.domain.Loan;
import se.yrgo.exception.NoAvailableBookCopiesException;

import java.util.List;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {
    private final LoanDao loanDao;
    private final BookDao bookDao;
    private final BorrowerDao borrowerDao;

    public LoanServiceImpl(LoanDao loanDao, BookDao bookDao, BorrowerDao borrowerDao) {
        this.loanDao = loanDao;
        this.bookDao = bookDao;
        this.borrowerDao = borrowerDao;
    }

    @Override
    public void save(Loan loan) {
        loanDao.save(loan);
    }

    @Override
    public void deleteById(long id) {
        loanDao.deleteById(id);
    }

    @Override
    public List<Loan> getAllLoans() {
        return loanDao.getAllLoans();
    }

    @Override
    public List<Loan> getLateLoans() {
        return loanDao.getLateLoans();
    }

    @Override
    public Loan findById(long id) {
        return loanDao.findById(id);
    }

    @Override
    public void borrowBook(long id, String isbn) {
        Borrower borrower = borrowerDao.findById(id);

        Book book = bookDao.findById(isbn);
        BookCopy availableCopy = book.getCopies().stream()
                .filter(copy -> isCopyAvailable(copy))
                .findFirst()
                .orElseThrow(() -> new NoAvailableBookCopiesException("No copy currently available."));

        Loan loan = new Loan(availableCopy, borrower);

        loanDao.save(loan);
    }

    private boolean isCopyAvailable(BookCopy copy) {
        return loanDao.getAllLoans().stream()
                .noneMatch(loan -> loan.getBookCopy().equals(copy) && loan.isActive());
    }
}
