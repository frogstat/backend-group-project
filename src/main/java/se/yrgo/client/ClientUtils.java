package se.yrgo.client;

import java.util.*;

public class ClientUtils {

    private static final Scanner scanner = new Scanner(System.in);

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void slowText(String text) {
        try {
            for (char c : text.toCharArray()) {
                System.out.print(c);
                Thread.sleep(25);
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public static int inputInt(String prompt, List<Integer> validChoices) {

        int choice;
        while (true) {
            try {
                System.out.print(prompt);
                choice = Integer.parseInt(scanner.nextLine());
                if (!validChoices.contains(choice)) {
                    continue;
                }
                break;
            } catch (Exception ignored) {
            }
        }
        return choice;
    }

    public static String inputString(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine();
            if (input.isEmpty()) {
                continue;
            }
            break;
        }
        return input;
    }

    public static void printLogo() {
        System.out.println("""
                *******************
                | Library of YRGO |
                *******************""");
    }

    public static void printCurrentMenu(String menu) {
        System.out.println("--- " + menu + " ---");
    }

    public static void printHeader(String menu) {
        printLogo();
        printCurrentMenu(menu);
    }

}
