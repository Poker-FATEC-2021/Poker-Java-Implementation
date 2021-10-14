package br.edu.fatecmm.poker.java.implementation.base;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    private final List<Card> cards;
    private final Integer money;

    public Player(List<Card> cards, Integer money) {
        this.cards = cards;
        this.money = money;
    }

    public Player() {
        this(new ArrayList<>(), 10000);
    }

    public List<Card> getCards() {
        return cards;
    }

    public Integer getMoney() {
        return money;
    }

    public abstract void play(List<Card> cards);

    @Override
    public String toString() {
        return "Player{" +
                "cards=" + cards +
                '}';
    }
}
