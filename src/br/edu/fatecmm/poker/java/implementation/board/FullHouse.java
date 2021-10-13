package br.edu.fatecmm.poker.java.implementation.board;

import br.edu.fatecmm.poker.java.implementation.board.common.MultiOrganizedCards;

import java.util.Arrays;
import java.util.List;

public class FullHouse extends MultiOrganizedCards {
    @Override
    public List<Integer> mask() {
        return Arrays.asList(3, 2);
    }
}
