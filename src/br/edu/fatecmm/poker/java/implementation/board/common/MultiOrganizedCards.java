package br.edu.fatecmm.poker.java.implementation.board.common;

import br.edu.fatecmm.poker.java.implementation.base.Card;

import java.util.*;

public abstract class MultiOrganizedCards extends OrganizedCards {
    @Override
    public boolean isHandPlay(Map<Integer, List<Card>> map) {
        List<Integer> mask = mask();
        Map<Integer, Boolean> answers = new HashMap<>();
        card: for (Map.Entry<Integer, List<Card>> cards : map.entrySet()) {
            for (int i = 0; i < mask.size(); i++) {
                if (answers.containsKey(i)) continue;
                if (cards.getValue().size() == mask.get(i)) {
                    answers.put(i, true);
                    continue card;
                }
            }
        }
        return answers.values().size() == mask.size();
    }

    public abstract List<Integer> mask();
}
