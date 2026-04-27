package se.yrgo.domain;
import java.time.LocalDate;
import java.util.*;

public class Book {

    private String isbn;
    private String title;
    private Author author;
    private Set<BookCopy> copies;

    Book(){};

    public Book(String isbn, String title, Author author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.copies = new HashSet<>();
    }

    public BookCopy createCopy(LocalDate purchaseDate){
        BookCopy copy = new BookCopy(this, purchaseDate);
        copies.add(copy);
        return copy;
    }

    public BookCopy createCopy(){
        LocalDate purchaseDate = LocalDate.now();
        return createCopy(purchaseDate);
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public Set<BookCopy> getCopies() {
        return copies;
    }

    public String toString() {
        return String.format("""
                ********************
                Title: %s
                Author: %s
                Isbn: %s
                Number of copies: %d
                ********************""", title, author, isbn, copies.size());
    }
}
