package br.edu.fatecmm.poker.java.implementation.board;

import br.edu.fatecmm.poker.java.implementation.board.common.CountedCards;

public class OnePair extends CountedCards {
    @Override
    public int getCount() {
        return 2;
    }
}
