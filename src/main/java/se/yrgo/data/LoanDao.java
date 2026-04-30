package se.yrgo.data;

import se.yrgo.domain.Loan;

import java.util.List;

public interface LoanDao {
    void save(Loan loan);

    void deleteById(long id);

    List<Loan> getAllLoans();

    List<Loan> getLateLoans();

    Loan findById(long id);
}
