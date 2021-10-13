package br.edu.fatecmm.poker.java.implementation.board;

import br.edu.fatecmm.poker.java.implementation.board.common.MultiOrganizedCards;

import java.util.Arrays;
import java.util.List;

public class TwoPair extends MultiOrganizedCards {
    @Override
    public List<Integer> mask() {
        return Arrays.asList(2, 2);
    }
}
