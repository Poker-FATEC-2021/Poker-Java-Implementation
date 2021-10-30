package br.edu.fatecmm.poker.java.implementation.board;

import br.edu.fatecmm.poker.java.implementation.board.common.HandPlay;

public enum HandPlayWeight {
    ONE_PAIR(new OnePair(), 0),
    TWO_PAIR(new TwoPair(), 1),
    SET(new Set(), 2),
    SEQUENCE(new Sequence(), 3),
    FLUSH(new Flush(), 4),
    FULL_HOUSE(new FullHouse(), 5),
    FOUR_OF_A_KIND(new FourOfAKind(), 6),
    STRAIGHT_FLUSH(new StraightFlush(), 7);

    private final HandPlay handPlay;
    private final int weight;

    HandPlayWeight(HandPlay handPlay, int weight) {
        this.handPlay = handPlay;
        this.weight = weight;
    }

    public HandPlay getHandPlay() {
        return handPlay;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "HandPlayWeight{" +
                "handPlay=" + handPlay +
                ", weight=" + weight +
                '}';
    }
}
