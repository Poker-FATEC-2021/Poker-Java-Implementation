package br.edu.fatecmm.poker.java.implementation.base;

import java.util.List;

public class CPUPlayer extends Player {

    public CPUPlayer(int id) {
        super(id);
    }

    @Override
    public PlayAction play(List<Card> cards, PlayerState playerState, int biggerBet) {
        return null;
    }
}
