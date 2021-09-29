package br.edu.fatecmm.poker.java.implementation.board;

import br.edu.fatecmm.poker.java.implementation.base.Card;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FourOfAKind implements HandPlay {
    @Override
    public boolean isHandPlay(List<Card> cards) {
        Map<Integer, Integer> map = new HashMap<>();
        for (Card card : cards) {
            int weight = card.getValue().getWeight();
            if (!map.containsKey(weight)) {
                map.put(weight, 1);
                continue;
            }
            map.put(weight, map.get(weight) + 1);
        }
        for (Integer count : map.values()) if (count >= 4) return true;
        return false;
    }
}
