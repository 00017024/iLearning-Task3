package org.example;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import java.util.List;


public class Table {
    public String generateTable() {
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow("#", "Computer", "User");
        table.addRule();
        table.addRow("1", "Generates a random number", "`x ∈ {0,1,2,3,4,5}`");
        table.addRule();
        table.addRow("2", "Generates a secret key", "");
        table.addRule();
        table.addRow("3", "Calculates and displays", "Selects a number");
        table.addRow("", "`HMAC(key).calculate(x)`", "`y ∈ {0,1,2,3,4,5}`");
        table.addRule();
        table.addRow("4", "Calculates the result", "");
        table.addRow("", "`(x + y) % 6`", "");
        table.addRule();
        table.addRow("5", "Shows both the result", "and the key");
        table.addRule();

        return table.render();
    }

    public String generateProbabilityTable(List<Dice> diceList, Probability probability) {
        AsciiTable table = new AsciiTable();
        table.getRenderer().setCWC(new CWC_LongestLine()); // Set column width calculator

        // Header row
        table.addRule();
        table.addRow("User dice \\ v",
                        diceList.get(0).getSidesAsString(),
                        diceList.get(1).getSidesAsString(),
                        diceList.get(2).getSidesAsString())
                .setTextAlignment(TextAlignment.CENTER);
        table.addRule();

        // Probability rows
        for (int i = 0; i < diceList.size(); i++) {
            Dice dice = diceList.get(i);
            List<Double> probabilities = probability.calculateProbabilities(dice, diceList);

            // Create a row: the first column is the dice sides, followed by probabilities
            String[] row = new String[diceList.size() + 1];
            row[0] = dice.getSidesAsString(); // Row header
            for (int j = 0; j < probabilities.size(); j++) {
                row[j + 1] = probabilities.get(j) == null ? "-" : String.format("%.4f", probabilities.get(j));
            }

            table.addRow((Object[]) row).setTextAlignment(TextAlignment.RIGHT);
            table.addRule();
        }

        return table.render();
    }
    private double calculateWinProbability(Dice userDice, Dice opponentDice) {
        int userWins = 0;
        int totalGames = userDice.getSides().length * opponentDice.getSides().length;

        for (int userSide : userDice.getSides()) {
            for (int opponentSide : opponentDice.getSides()) {
                int result = (userSide - opponentSide + 6) % 6;
                if (result == 1 || result == 3 || result == 5) { // Win conditions
                    userWins++;
                }
            }
        }

        return (double) userWins / totalGames;
    }
}
