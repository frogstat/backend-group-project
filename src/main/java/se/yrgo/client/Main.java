package se.yrgo.client;


import java.util.*;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            ClientUtils.clearScreen();
            ClientUtils.printHeader("Menu");
            ClientUtils.slowText("""
                    [1] Log in as user
                    [2] Log in as administrator
                    [3] Exit
                    """);

            int answer = ClientUtils.inputInt("Your choice: ", List.of(1, 2, 3));

            if (answer == 3) {
                return;
            }

            switch (answer) {
                case 1 -> new UserLoginScreen(null).loginPage();
                case 2 -> AdministratorScreen.AdminMenu();
            }
        }
    }
}
