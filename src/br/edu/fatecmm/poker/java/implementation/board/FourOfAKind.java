package br.edu.fatecmm.poker.java.implementation.board;

import br.edu.fatecmm.poker.java.implementation.board.common.CountedCards;

public class FourOfAKind extends CountedCards {
    @Override
    public int isCount() {
        return 4;
    }
}
