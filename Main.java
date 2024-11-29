package org.example;

import java.util.List;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        // Initialize the dice with sides
        List<Dice> diceList = Arrays.asList(
                new Dice(new int[]{0, 1, 2, 3, 4, 5}),
                new Dice(new int[]{0, 1, 2, 3, 4, 5}),
                new Dice(new int[]{0, 1, 2, 3, 4, 5})
        );

        // Initialize the Table and Probability objects
        Table table = new Table();
        Probability probability = new Probability();

        // Create and start the game
        DiceGame game = new DiceGame(diceList, table, probability);
        game.start(); // Start the game loop

    }
}