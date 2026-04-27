package se.yrgo.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "book_isbn")
    private Book book;
    private LocalDate purchaseDate;

    public BookCopy() {
    }

    public BookCopy(Book book, LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    public String toString() {
        return String.format("""
                        ********************
                        Title: %s
                        ISBN: %s
                        Purchase Date: %s
                        ********************""",
                book.getTitle(), book.getIsbn(), purchaseDate.toString());
    }
}
