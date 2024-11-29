package org.example;

import java.util.ArrayList;
import java.util.List;

public class Probability {
    public List<Double> calculateProbabilities(Dice userDice, List<Dice> allDice) {
        List<Double> probabilities = new ArrayList<>();

        for (Dice opponentDice : allDice) {
            if (userDice == opponentDice) {
                probabilities.add(null);
            } else {
                double probability = calculateWinProbability(userDice, opponentDice);
                probabilities.add(probability);
            }
        }

        return probabilities;
    }

    private double calculateWinProbability(Dice userDice, Dice opponentDice) {
        int userWins = 0;
        int totalRounds = 0;

        int[] userSides = userDice.getSides();
        int[] opponentSides = opponentDice.getSides();

        // Simulate all outcomes
        for (int userRoll : userSides) {
            for (int opponentRoll : opponentSides) {
                totalRounds++;
                if (userRoll > opponentRoll) {
                    userWins++;
                }
            }
        }
        return (double) userWins / totalRounds; // Probability of user winning
    }
}
