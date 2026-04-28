package se.yrgo.services;

import se.yrgo.domain.Borrower;

import java.util.List;

public interface BorrowerService {
    void delete(long id);

    void save(Borrower borrower);

    Borrower findById(long id);

    List<Borrower> findAll();

    Borrower findByEmail(String email);

}
