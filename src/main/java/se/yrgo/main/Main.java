package se.yrgo.main;
import se.yrgo.domain.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        Book book = new Book("12345677643", "Gary Trotter", null);

        book.createCopy();
        book.createCopy(LocalDate.of(1992,12,5));

        for(BookCopy bookCopy : book.getCopies()){
            System.out.println(bookCopy);
        }



    }
}
