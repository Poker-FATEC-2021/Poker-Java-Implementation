package br.edu.fatecmm.poker.java.implementation.board;

import br.edu.fatecmm.poker.java.implementation.base.Card;
import br.edu.fatecmm.poker.java.implementation.board.common.SequenceCards;

import java.util.List;
import java.util.stream.Collectors;

public class Sequence extends SequenceCards {
    @Override
    public boolean handleSequence(Boolean isSequence, List<Card> cards) {
        return isSequence && cards
                .stream()
                .collect(
                        Collectors.groupingBy(c -> c.getSuit().getValue())
                )
                .keySet()
                .size() > 1;
    }
}
