package se.yrgo.client;

import se.yrgo.domain.Borrower;

import java.util.*;

public class UserScreen {

    Borrower currentUser;


    public static void loginPage() {
        ClientUtils.clearScreen();
        ClientUtils.printHeader("User Login");
        ClientUtils.slowText("""
                [1] Log in
                [2] Register
                [3] Return
                """);

        int answer = ClientUtils.inputInt("Your choice: ", List.of(1, 2, 3));

        if (answer == 3) {
            return;
        }

        Borrower user = switch (answer) {
            case 1 -> logIn();
            case 2 -> register();
            default -> throw new IllegalStateException("Unexpected value");
        };

        if (user != null) {
            UserScreen userScreen = new UserScreen(user);
            userScreen.userMenu();
        }

    }

    private static Borrower logIn() {
        ClientUtils.clearScreen();
        ClientUtils.printHeader("User Login");
        System.out.print("Email: ");
        System.out.println("Password: ");

        //TODO: check if the user exists in the Borrower table and the password matches!
        return new Borrower("Glenn", "Glenn@glenmail.com", "Nanny11");
    }

    private static Borrower register() {
        ClientUtils.clearScreen();
        ClientUtils.printHeader("User Register");
        System.out.print("Enter your email: ");
        System.out.print("Enter a password: ");

        //TODO: Create a new borrower in the borrower table
        return new Borrower("Glenn", "Glenn@glenmail.com", "Nanny11");
    }

    private UserScreen(Borrower currentUser) {
        this.currentUser = currentUser;
    }

    private void userMenu() {
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
            int answer = ClientUtils.inputInt(this.currentUser.getBorrowerName() + "> ", List.of(1, 2, 3, 4));

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
        int answer = ClientUtils.inputInt(this.currentUser.getBorrowerName() + "> ", List.of(1, 2, 3, 4));

        if(answer == 4) {
            return;
        }

        String userInfo = switch (answer){
            case 1 -> "name";
            case 2 -> "email";
            case 3 -> "password";
            default -> throw new IllegalStateException("Unexpected value");
        };

        changeUserInfo(userInfo);
    }

    private void changeUserInfo(String userInfo){
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
        int answer = ClientUtils.inputInt(this.currentUser.getBorrowerName() + "> ", List.of(1));
        if (answer == 1) {
            return;
        }

    }

    private void returnBook() {
        ClientUtils.clearScreen();
        ClientUtils.printHeader("Return Book");
        if (currentUser.getLoans().isEmpty()) {
            ClientUtils.slowText("You have no loans\n[1] Return\n");
            int answer = ClientUtils.inputInt(this.currentUser.getBorrowerName() + "> ", List.of(1));
            if (answer == 1) {
                return;
            }
        }


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
        int answer = ClientUtils.inputInt(this.currentUser.getBorrowerName() + "> ", List.of(1, 2, 3, 4));

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
        int answer = ClientUtils.inputInt(this.currentUser.getBorrowerName() + "> ", List.of(1));
        if (answer == 1) {
            return;
        }
    }

    private void borrowBookByAuthor() {
        ClientUtils.clearScreen();
        ClientUtils.printHeader("Search for book by author");
        ClientUtils.slowText("WIP\n[1] Return\n");
        int answer = ClientUtils.inputInt(this.currentUser.getBorrowerName() + "> ", List.of(1));
        if (answer == 1) {
            return;
        }
    }

    private void borrowBookByISBN() {
        ClientUtils.clearScreen();
        ClientUtils.printHeader("Search for book by ISBN");
        ClientUtils.slowText("WIP\n[1] Return\n");
        int answer = ClientUtils.inputInt(this.currentUser.getBorrowerName() + "> ", List.of(1));
        if (answer == 1) {
            return;
        }
    }

}
