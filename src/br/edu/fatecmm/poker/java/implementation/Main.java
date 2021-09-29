package br.edu.fatecmm.poker.java.implementation;

import br.edu.fatecmm.poker.java.implementation.base.Card;
import br.edu.fatecmm.poker.java.implementation.base.Suit;
import br.edu.fatecmm.poker.java.implementation.base.Value;
import br.edu.fatecmm.poker.java.implementation.board.FourOfAKind;
import br.edu.fatecmm.poker.java.implementation.board.HandPlay;
import br.edu.fatecmm.poker.java.implementation.board.StraightFlushHandPlay;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        //testStraightFlushHandPlay();
        testFourCardsHandPlay();
    }

    public static void testHandPlay(HandPlay handPlay) {
        List<Card> cards = Arrays.asList(
                new Card(Value.NINE, Suit.CLUBS),
                new Card(Value.NINE, Suit.CLUBS),
                new Card(Value.KING, Suit.CLUBS),
                new Card(Value.KING, Suit.CLUBS),
                new Card(Value.NINE, Suit.CLUBS)
        );
        boolean result = handPlay.isHandPlay(cards);
        System.out.println("Result: " + result);
    }

    public static void testStraightFlushHandPlay() {
        testHandPlay(new StraightFlushHandPlay());
    }

    public static void testFourCardsHandPlay() {
        testHandPlay(new FourOfAKind());
    }
}
