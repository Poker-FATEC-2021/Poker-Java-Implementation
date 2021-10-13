package br.edu.fatecmm.poker.java.implementation.board.common;

import br.edu.fatecmm.poker.java.implementation.base.Card;

import java.util.List;
import java.util.Map;

public abstract class CountedCards extends OrganizedCards {
    @Override
    public boolean isHandPlay(Map<Integer, List<Card>> map) {
        int count = isCount();
        for (List<Card> cards : map.values())
            if (cards.size() == count) return true;
        return false;
    }

    public abstract int isCount();
}
