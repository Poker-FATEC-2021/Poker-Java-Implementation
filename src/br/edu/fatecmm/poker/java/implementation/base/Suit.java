package br.edu.fatecmm.poker.java.implementation.base;

public enum Suit {
    CLUBS(0),
    DIAMONDS(1),
    HEART(2),
    SPADES(3);

    private final int value;

    Suit(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
