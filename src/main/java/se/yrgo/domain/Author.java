package se.yrgo.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToMany(mappedBy = "authors")
    private List<Book> books;

    public Author(String name) {
        this.name = name;
        books = new ArrayList<>();
    }

    public Author() {

    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void addWrittenBook(Book book) {
        if (!books.contains(book)) {
            books.add(book);
            book.addAuthor(this);
        }
    }

    @Override
    public String toString() {
        return String.format("""
                ********************
                Name: %s
                ********************""", name);
    }
}
