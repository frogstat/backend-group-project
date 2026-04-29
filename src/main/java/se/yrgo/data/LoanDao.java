package se.yrgo.data;

import se.yrgo.domain.Loan;

import java.util.List;

public interface LoanDao {

    List<Loan> getAllLoans();

    List<Loan> getLateLoans();

    Loan findById(long id);
}
