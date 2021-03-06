package br.edu.fatecmm.poker.java.implementation.board.common;

import br.edu.fatecmm.poker.java.implementation.base.Card;

import java.util.*;
import java.util.stream.Collectors;

public abstract class OrganizedCards implements HandPlay {
    @Override
    public boolean isHandPlay(List<Card> cards) {
        return isHandPlay(
                cards
                        .stream()
                        .collect(
                                Collectors.groupingBy(card -> card.getValue().getWeight())
                        )
        );
    }

    public abstract boolean isHandPlay(Map<Integer, List<Card>> map);
}
