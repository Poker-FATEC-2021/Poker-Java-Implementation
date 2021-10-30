package br.edu.fatecmm.poker.java.implementation.board;

import br.edu.fatecmm.poker.java.implementation.board.common.HandPlay;

public enum HandPlayWeight {
    STRAIGHT_FLUSH(new StraightFlush(), 7),
    FOUR_OF_A_KIND(new FourOfAKind(), 6),
    FULL_HOUSE(new FullHouse(), 5),
    FLUSH(new Flush(), 4),
    SEQUENCE(new Sequence(), 3),
    SET(new Set(), 2),
    TWO_PAIR(new TwoPair(), 1),
    ONE_PAIR(new OnePair(), 0);

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
}
