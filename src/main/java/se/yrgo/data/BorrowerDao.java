package se.yrgo.data;

import se.yrgo.domain.Borrower;
import se.yrgo.exception.NotFoundException;

import java.util.List;

public interface BorrowerDao {
    void save(Borrower borrower);

    void deleteById(long id);

    Borrower findById(long id);

    Borrower findByEmail(String email);

    List<Borrower> findAll();
}
