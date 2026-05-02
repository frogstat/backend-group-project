package se.yrgo.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.yrgo.domain.Book;
import se.yrgo.domain.BookCopy;
import se.yrgo.domain.Borrower;
import se.yrgo.domain.Loan;
import se.yrgo.services.BookService;
import se.yrgo.services.BorrowerService;
import se.yrgo.services.LoanService;

import java.util.*;

import static se.yrgo.client.Main.scanner;


public class UserScreen {

    BookService bookService;
    LoanService loanService;

    private Borrower currentUser;
    private String prompt;


    public UserScreen(Borrower currentUser) {
        this.bookService = SpringContext.getBean(BookService.class);
        this.loanService = SpringContext.getBean(LoanService.class);

        this.currentUser = currentUser;
        prompt = prompt;
    }

    public void userMenu() {
        menuLoop:
        while (true) {
            ClientUtils.clearScreen();
            ClientUtils.printHeader("User Menu");
            ClientUtils.slowText("""
                    [1] Borrow book
                    [2] Return book
                    [3] User settings
                    [4] Log out
                    """);
            int answer = ClientUtils.inputInt(prompt, List.of(1, 2, 3, 4));

            switch (answer) {
                case 1 -> borrowBook();
                case 2 -> returnBook();
                case 3 -> userSettings();
                case 4 -> {
                    break menuLoop;
                }
            }
        }
    }

    private void userSettings() {
        ClientUtils.clearScreen();
        ClientUtils.printHeader("User Settings");
        ClientUtils.slowText("""
                [1] Change name
                [2] Change email
                [3] Change password
                [4] Return
                """);
        int answer = ClientUtils.inputInt(prompt, List.of(1, 2, 3, 4));

        if (answer == 4) {
            return;
        }

        String userInfo = switch (answer) {
            case 1 -> "name";
            case 2 -> "email";
            case 3 -> "password";
            default -> throw new IllegalStateException("Unexpected value");
        };

        changeUserInfo(userInfo);
    }

    private void changeUserInfo(String userInfo) {
        ClientUtils.clearScreen();
        ClientUtils.printHeader("Change " + userInfo);
        ClientUtils.slowText("Enter new " + userInfo + ": ");
        String input = ClientUtils.inputString("");

        switch (userInfo) {
            case "name" -> currentUser.setBorrowerName(input);
            case "email" -> currentUser.setEmail(input);
            case "password" -> currentUser.SetPassword(input);
            default -> throw new IllegalStateException("Unexpected value: " + userInfo);
        }
        ClientUtils.slowText(userInfo + " changed!\n");
        ClientUtils.slowText("[1] Return\n");
        int answer = ClientUtils.inputInt(prompt, List.of(1));
        if (answer == 1) {
            return;
        }

    }

    private void returnBook() {
        ClientUtils.clearScreen();
        ClientUtils.printHeader("Return Book");

        if (currentUser.getLoans().isEmpty()) {
            showNoLoansMessage();
        } else {
            while (true) {
                int choice = selectLoan();
                if (choice == -1) {
                    return;
                }
                Loan loan = currentUser.getLoans().get(choice);
                System.out.println("Are you sure you want to return " + loan.getBookCopy().getBook().getTitle() + "? (yes)");
                String answer = ClientUtils.inputString(prompt);
                if (answer.equalsIgnoreCase("yes")) {
                    loan.returnBook();
                    ClientUtils.slowText("Book returned!");
                    ClientUtils.inputString("Press Enter to return...");
                    return;
                } else {
                    ClientUtils.clearScreen();
                    ClientUtils.printHeader("Return Book");
                }
            }
        }
    }

    private int selectLoan() {
        List<Loan> loans = currentUser.getLoans();
        List<Integer> validChoices = new ArrayList<>();
        System.out.println("Select book to return:\n[0] Return");
        validChoices.add(0);

        for (int i = 0; i < loans.size(); i++) {
            validChoices.add(i + 1);
            Book book = loans.get(i).getBookCopy().getBook();
            System.out.printf("[%d] %s by %s (%s)%n", i + 1, book.getTitle(), book.getAuthorsAsString(), book.getIsbn());
        }
        return ClientUtils.inputInt(prompt, validChoices) - 1;
    }


    private void showNoLoansMessage() {
        ClientUtils.slowText("You have no loans.");
        ClientUtils.inputString("Press Enter to return...");
    }

    private void borrowBook() {
        ClientUtils.clearScreen();
        ClientUtils.printHeader("Borrow Book");
        ClientUtils.slowText("""
                [1] Find book by title
                [2] Find book by author
                [3] Find book by ISBN
                [4] Return
                """);
        int answer = ClientUtils.inputInt(prompt, List.of(1, 2, 3, 4));

        switch (answer) {
            case 1 -> borrowBookByTitle();
            case 2 -> borrowBookByAuthor();
            case 3 -> borrowBookByISBN();
        }
    }

    private void borrowBookByTitle() {
        ClientUtils.clearScreen();
        ClientUtils.printHeader("Search for book by title");
        ClientUtils.slowText("WIP\n[1] Return\n");
        int answer = ClientUtils.inputInt(prompt, List.of(1));
        if (answer == 1) {
            return;
        }
    }

    private void borrowBookByAuthor() {
        ClientUtils.clearScreen();
        ClientUtils.printHeader("Search for book by author");
        ClientUtils.slowText("WIP\n[1] Return\n");
        int answer = ClientUtils.inputInt(prompt, List.of(1));
        if (answer == 1) {
            return;
        }
    }

    private void borrowBookByISBN() {
        ClientUtils.clearScreen();
        ClientUtils.printHeader("Search for book by ISBN");

        ClientUtils.slowText("Please enter a valid ISBN");
        String isbn = ClientUtils.inputString(prompt);

        Book bookToBorrow = bookService.findByIsbn(isbn);
        BookCopy bookCopyToBorrow = getAvailableBookCopy(bookToBorrow);

        if (bookCopyToBorrow == null) {
            System.out.println("No available book found for " + bookToBorrow.getTitle());
        } else {
            borrowCopy(bookCopyToBorrow);
        }

    }

    private BookCopy getAvailableBookCopy(Book book) {
        for (BookCopy bookCopy : book.getCopies()) {
            for (Loan loan : bookCopy.getLoans()) {
                if (!loan.isActive()) {
                    return bookCopy;
                }
            }
        }
        return null;
    }

    private void borrowCopy(BookCopy bookCopyToBorrow) {
        System.out.println("Do you want to borrow " + bookCopyToBorrow.getBook().getTitle() + "? (yes)");
        String answer = ClientUtils.inputString(prompt);
        if (answer.equalsIgnoreCase("yes")) {
            try {
                loanService.borrowBook(currentUser.getId(), bookCopyToBorrow.getBook().getIsbn());
                ClientUtils.slowText("Book borrowed!");
            } catch (Exception e) {
                System.out.println("Something went wrong when trying to borrow!");
                System.out.println(e.getMessage());
            }
            ClientUtils.inputString("Press Enter to return...");
        }
    }

}
