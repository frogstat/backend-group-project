package se.yrgo.client;

import se.yrgo.domain.Borrower;
import se.yrgo.exception.UserAlreadyExistsException;
import se.yrgo.services.BorrowerService;

import java.util.List;

import static se.yrgo.client.Main.scanner;

public class UserLoginScreen {

    BorrowerService borrowerService;

    public UserLoginScreen(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    public void loginPage() {
        while (true) {
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

            Borrower user;
            try {
                user = switch (answer) {
                    case 1 -> logIn();
                    case 2 -> register();
                    default -> throw new IllegalStateException("Unexpected value");
                };
            } catch (UserAlreadyExistsException e) {
                System.err.println("User already exists!");
                continue;
            }

            if (user != null) {
                UserScreen userScreen = new UserScreen(user);
                userScreen.userMenu();
            }
        }

    }

    private Borrower logIn() {
        ClientUtils.clearScreen();
        ClientUtils.printHeader("User Login");
        System.out.print("Email: ");
        System.out.print("Password: ");

        //TODO: check if the user exists in the Borrower table and the password matches!
        return new Borrower("Glenn", "Glenn@glenmail.com", "Nanny11");
    }

    private Borrower register() {
        ClientUtils.clearScreen();
        ClientUtils.printHeader("User Register");

        String name = ClientUtils.inputString("Enter your name: ");

        String email;
        while (true) {
            System.out.print("Enter your email: ");
            email = scanner.nextLine();
            if (!email.contains("@") || !email.contains(".")) {
                System.out.println("Invalid email.");
                continue;
            }
            break;
        }

        String password;
        while (true) {
            System.out.print("Enter a new password: ");
            password = scanner.nextLine();
            if (password.length() < 4) {
                System.out.println("Password too short. Password should be at least 4 characters.");
                continue;
            }

            System.out.print("Repeat your password: ");
            String repeatPassword = scanner.nextLine();
            if (!password.equals(repeatPassword)) {
                System.out.println("Passwords don't match. Try again.");
                continue;
            }
            break;
        }

        if (borrowerService.findByEmail(email) != null) {
            throw new UserAlreadyExistsException("User with email " + email + "already exists.");
        }

        Borrower newBorrower = new Borrower(name, email, password);
        borrowerService.save(newBorrower);
        return newBorrower;
    }
}
