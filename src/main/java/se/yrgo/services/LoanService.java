package se.yrgo.services;

import se.yrgo.domain.BookCopy;
import se.yrgo.domain.Loan;

import java.util.List;

public interface LoanService {
    void save(Loan loan);

    void deleteById(long id);

    List<Loan> getAllLoans();

    List<Loan> getLateLoans();

    Loan findById(long id);

    void borrowBook(long borrowerId, String isbn);

    //TODO create return book method
}
