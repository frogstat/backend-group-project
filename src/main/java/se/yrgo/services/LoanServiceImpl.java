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
    public void borrowBook(long borrowerId, String isbn) {
        Borrower borrower = borrowerDao.findById(borrowerId);

        BookCopy availableCopy = loanDao.getAvailableCopies(isbn)
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new NoAvailableBookCopiesException("No copy  with ISBN  " + isbn + " currently available.")
                );

        Loan loan = new Loan(availableCopy, borrower);

        loanDao.save(loan);
    }

    @Override
    public void returnBook(long loanId) {
        Loan loan = loanDao.findById(loanId);

        if (loan.getReturnDate() != null) {
            throw new IllegalStateException("Book already returned");
        }

        loan.returnBook();

        loanDao.save(loan);
    }
}
