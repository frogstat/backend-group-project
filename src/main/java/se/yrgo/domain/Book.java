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
    private Set<Author> authors;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookCopy> copies;

    public Book() {
    }

    public Book(String isbn, String title, Author author) {
        this.isbn = isbn;
        this.title = title;
        this.authors = new HashSet<>();
        this.authors.add(author);
        this.copies = new HashSet<>();
    }

    public Book(String isbn, String title, Set<Author> authors) {
        this.isbn = isbn;
        this.title = title;
        this.authors = new HashSet<>();
        this.authors.addAll(authors);
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
        if (authors.add(author)) {
            author.addWrittenBook(this);
        }
    }

    public String getAuthorsAsString() {
        StringBuilder authorNames = new StringBuilder();

        for (Author author : authors) {
            authorNames.append(author.getName()).append(", ");
        }

        if (!authorNames.isEmpty()) {
            authorNames.setLength(authorNames.length() - 2);
        }

        return authorNames.toString();
    }

    public String toString() {
        return String.format("""
                ********************
                Title: %s
                Author: %s
                Isbn: %s
                Number of copies: %d
                ********************""", title, getAuthorsAsString(), isbn, copies.size());
    }


}

