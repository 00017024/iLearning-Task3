package org.example;

import de.vandermeer.asciitable.AsciiTable;

import java.security.SecureRandom;
import java.util.*;

public class DiceGame {
    private final List<Dice> diceList;
    private final SecureRandom random = new SecureRandom();
    private final Scanner scanner = new Scanner(System.in);
    private final Table table; // Table class dependency
    private final Probability probability; // Probability class dependency

    public DiceGame(List<Dice> diceList, Table table, Probability probability) {
        this.diceList = diceList;
        this.table = table;
        this.probability = probability;
    }

    public void start() {
        System.out.println("=== Welcome to Dice Game ===");

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("0 - Start Game");
            System.out.println("? - Help");
            System.out.println("P - Show Probability Table");
            System.out.println("X - Exit");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "0" -> playGame();
                case "?" -> displayHelp();
                case "P" -> displayProbabilityTable();
                case "X" -> {
                    System.out.println("Exiting game. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void displayHelp() {
        System.out.println("=== HELP ===");
        // Call Table class method to display the help table
        System.out.println(table.generateTable());
    }

    private void displayProbabilityTable() {
        System.out.println("=== PROBABILITY TABLE ===");
        // Call Table class method to display the probability table
        System.out.println(table.generateProbabilityTable(diceList, probability));
    }

    private void playGame() {
        System.out.println("Starting the game...");

        // Determine the first move
        int computerChoice = random.nextInt(2);
        String hmac = HmacUtils.generateHmac("secret_key", String.valueOf(computerChoice));
        System.out.printf("I selected a random value (HMAC=%s). Try to guess it.%n", hmac);

        System.out.println("0 - 0");
        System.out.println("1 - 1");
        System.out.print("Your choice: ");
        String userGuess = scanner.nextLine().trim();

        int userGuessNumber;
        try {
            userGuessNumber = Integer.parseInt(userGuess);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Restarting game.");
            return;
        }

        String key = "secret_key";
        if (userGuessNumber == computerChoice) {
            System.out.printf("Correct! My selection was %d (KEY=%s). You make the first move.%n", computerChoice, key);
        } else {
            System.out.printf("Wrong! My selection was %d (KEY=%s). I make the first move.%n", computerChoice, key);
        }

        // Add logic for dice throws and results
        // User selects their dice
        System.out.println("Choose your dice:");
        for (int i = 0; i < diceList.size(); i++) {
            System.out.printf("%d - %s%n", i, diceList.get(i).getSidesAsString());
        }
        System.out.print("Your selection: ");
        int userDiceChoice = Integer.parseInt(scanner.nextLine());

        if (userDiceChoice < 0 || userDiceChoice >= diceList.size()) {
            System.out.println("Invalid dice selection!");
            return;
        }

        Dice userDice = diceList.get(userDiceChoice);
        Dice computerDice = diceList.get(computerChoice);

        // User and computer throw their dice
        int userRoll = rollDice(userDice);
        int computerRoll = rollDice(computerDice);

        System.out.printf("You rolled: %d (using dice %s)%n", userRoll, userDice.getSidesAsString());
        System.out.printf("I rolled: %d (using dice %s)%n", computerRoll, computerDice.getSidesAsString());

        // Calculate the result based on the rules
        int result = (userRoll - computerRoll + 6) % 6;

        if (result == 1 || result == 3 || result == 5) {
            System.out.println("You win!");
        } else if (result == 0) {
            System.out.println("It's a draw!");
        } else {
            System.out.println("I win!");
        }
    }

    private int rollDice(Dice dice) {
        int[] sides = dice.getSides();
        return sides[random.nextInt(sides.length)];
    }
}
