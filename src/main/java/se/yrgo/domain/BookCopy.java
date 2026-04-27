package se.yrgo.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class BookCopy {

    private long id;
    private Book book;
    private LocalDate purchaseDate;

    BookCopy() {};

    BookCopy(Book book, LocalDate purchaseDate) {
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
                        Author: %s
                        ISBN: %s
                        Purchase Date: %s
                        ********************""",
                book.getTitle(), book.getAuthor(), book.getIsbn(), purchaseDate.toString());
    }

}
