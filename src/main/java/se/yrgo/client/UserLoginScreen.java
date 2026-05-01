package se.yrgo.client;

import org.springframework.context.support.AbstractXmlApplicationContext;
import se.yrgo.domain.Borrower;
import se.yrgo.exception.IncorrectPasswordException;
import se.yrgo.exception.NotFoundException;
import se.yrgo.exception.UserAlreadyExistsException;
import se.yrgo.services.BorrowerService;

import java.util.List;

import static se.yrgo.client.Main.scanner;

public class UserLoginScreen {

    BorrowerService borrowerService;

    public UserLoginScreen() {
        borrowerService = SpringContext.getBean(BorrowerService.class);
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

            UserScreen userScreen = new UserScreen(user);
            userScreen.userMenu();

        }

    }

    private Borrower logIn() {
        ClientUtils.clearScreen();
        ClientUtils.printHeader("User Login");
        String email = ClientUtils.inputString("Email: ");
        String password = ClientUtils.inputString("Password: ");

        Borrower borrower = borrowerService.findByEmail(email);

        if (borrower == null) {
            throw new NotFoundException("User with email " + email + " not found!");
        } else if (!password.equals(borrower.getPassword())) {
            throw new IncorrectPasswordException("Incorrect password!");
        }

        return borrower;
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

        Borrower newBorrower = new Borrower(name, email, password);
        borrowerService.save(newBorrower);
        return newBorrower;
    }
}
