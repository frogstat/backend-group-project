package se.yrgo.domain;

import jakarta.persistence.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate loanDate;
    private LocalDate returnDate;
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "borrower_id")
    private Borrower borrower;

    // ?
    @ManyToOne
    private BookCopy bookCopy;

    public Loan() {
    }

    public Loan(BookCopy bookCopy, Borrower borrower) {
        this.loanDate = LocalDate.now();
        this.dueDate = this.loanDate.plusDays(30);
        this.borrower = borrower;
        this.bookCopy = bookCopy;

        this.borrower.getLoans().add(this);
    }

    public long getId() {
        return id;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    // -------Metoder-------

    public void returnBook() {
        if (this.returnDate == null) {
            this.returnDate = LocalDate.now();
        }
    }

    public boolean isActive() {
        return returnDate == null;
    }

    public void extendDueDate() {
        this.dueDate = this.dueDate.plusDays(14);
    }

    public boolean isLate() {
        return isActive() && LocalDate.now().isAfter(dueDate);
    }
}
