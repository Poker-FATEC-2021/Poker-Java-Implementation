package br.edu.fatecmm.poker.java.implementation.board;

import br.edu.fatecmm.poker.java.implementation.base.Card;

import java.util.Comparator;
import java.util.List;

public class StraightFlushHandPlay implements HandPlay {
    @Override
    public boolean isHandPlay(List<Card> cards) {
        Comparator<Card> comparator = Comparator.comparingInt(o -> o.getValue().getWeight());
        cards.sort(comparator);
        int value = -1;
        for (Card card : cards) {
            int cardWeight = card.getValue().getWeight();
            if (value == -1) {
                value = cardWeight;
                continue;
            }
            if (value >= cardWeight || value + 1 != cardWeight) return false;
            value = card.getValue().getWeight();
        }
        return true;
    }
}


