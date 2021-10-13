package br.edu.fatecmm.poker.java.implementation.board.common;

import br.edu.fatecmm.poker.java.implementation.base.Card;

import java.util.Comparator;
import java.util.List;

public abstract class SequenceCards implements HandPlay {
    @Override
    public boolean isHandPlay(List<Card> cards) {
        if (cards.isEmpty()) return false;
        cards.sort(Comparator.comparingInt(o -> o.getValue().getWeight()));
        int weight = cards.get(0).getValue().getWeight();
        for (int i = 1; i < cards.size(); i++) {
            Card card = cards.get(i);
            int tempWeight = card.getValue().getWeight();
            if (tempWeight == weight + 1) {
                weight = tempWeight;
                continue;
            }
            return isSequence(false, cards);
        }
        return isSequence(true, cards);
    }

    public abstract boolean isSequence(Boolean isSequence, List<Card> cards);
}
