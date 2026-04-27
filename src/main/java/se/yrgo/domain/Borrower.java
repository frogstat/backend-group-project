package se.yrgo.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Borrower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String borrowerName;
    private String email;

    @OneToMany(mappedBy = "borrower")
    private List<Loan> loans = new ArrayList<>();

    public Borrower() {
    }

    public Borrower(String borrowerName, String email) {
        this.borrowerName = borrowerName;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    // -------Metoder-------

    public List<Loan> getActiveLoans() {
        return loans.stream()
                .filter(Loan::isActive)
                .toList();
    }

    public List<Loan> getLateLoans() {
        return loans.stream()
                .filter(Loan::isLate)
                .toList();
    }
}
