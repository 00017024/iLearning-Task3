package org.example;

import java.util.Arrays;

public class Dice {
    private final int[] sides;

    public Dice(int[] sides) {
        this.sides = sides;
    }

    public int[] getSides() {
        return sides;
    }

    public String getSidesAsString() {
        return Arrays.toString(sides);
    }
}
