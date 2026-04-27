package se.yrgo.client;

import java.util.List;

public class AdministratorScreen {

    public static void AdminMenu(){
        ClientUtils.clearScreen();
        ClientUtils.printHeader("Admin Menu");
        ClientUtils.slowText("""
                [1] Manage books
                [2] Manage users
                [3] log out
                """);

        int answer = ClientUtils.inputInt("Your choice: ", List.of(1, 2, 3));
    }

    private static void manageBooks(){
        ClientUtils.clearScreen();
    }

}
