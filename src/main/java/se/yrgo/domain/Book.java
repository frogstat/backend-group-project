package se.yrgo.domain;

import java.time.LocalDate;
import java.util.*;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {
    @Id
    private String isbn;
    private String title;
    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_isbn"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;
    private Set<BookCopy> copies;

    public Book() {
    }

    public Book(String isbn, String title, Author author) {
        this.isbn = isbn;
        this.title = title;
        this.authors = new ArrayList<>();
        this.copies = new HashSet<>();
    }

    public BookCopy createCopy(LocalDate purchaseDate) {
        BookCopy copy = new BookCopy(this, purchaseDate);
        copies.add(copy);
        return copy;
    }

    public BookCopy createCopy() {
        LocalDate purchaseDate = LocalDate.now();
        return createCopy(purchaseDate);


    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public Set<BookCopy> getCopies() {
        return copies;
    }

    public void addAuthor(Author author) {
        if (!authors.contains(author)) {
            authors.add(author);
            author.addWrittenBook(this);
        }
    }

    public String toString() {
        return String.format("""
                ********************
                Title: %s
                Author: %s
                Isbn: %s
                Number of copies: %d
                ********************""", title, authors, isbn, copies.size());
    }


}

