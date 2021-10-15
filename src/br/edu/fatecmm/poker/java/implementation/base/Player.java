package br.edu.fatecmm.poker.java.implementation.base;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    private final List<Card> cards;
    private final List<PlayAction> history;
    private Integer money;

    public Player(List<Card> cards, List<PlayAction> history, Integer money) {
        this.cards = cards;
        this.history = history;
        this.money = money;
    }

    public Player() {
        this(new ArrayList<>(), new ArrayList<>(), 10000);
    }

    public List<Card> getCards() {
        return cards;
    }

    public List<PlayAction> getHistory() {
        return history;
    }

    public void addHistory(PlayAction playAction) {
        history.add(playAction);
    }

    public Integer getMoney() {
        return money;
    }

    public void minusMoney(int money) {
        this.money -= money;
    }

    public abstract PlayAction play(List<Card> cards, PlayerState playerState);

    @Override
    public String toString() {
        return "Player{" +
                "cards=" + cards +
                '}';
    }
}
