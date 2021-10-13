package br.edu.fatecmm.poker.java.implementation;

import br.edu.fatecmm.poker.java.implementation.base.Card;
import br.edu.fatecmm.poker.java.implementation.base.Suit;
import br.edu.fatecmm.poker.java.implementation.base.Value;
import br.edu.fatecmm.poker.java.implementation.board.*;
import br.edu.fatecmm.poker.java.implementation.board.common.HandPlay;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        testHandPlay(new TwoPair());
    }

    public static void testHandPlay(HandPlay handPlay) {
        List<Card> cards = Arrays.asList(
                new Card(Value.TWO, Suit.CLUBS),
                new Card(Value.ACE, Suit.DIAMONDS),
                new Card(Value.THREE, Suit.HEART),
                new Card(Value.FIVE, Suit.HEART),
                new Card(Value.FIVE, Suit.DIAMONDS)
        );
        boolean result = handPlay.isHandPlay(cards);
        System.out.println("Result: " + result);
    }
}
