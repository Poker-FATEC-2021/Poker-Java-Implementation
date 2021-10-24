package br.edu.fatecmm.poker.java.implementation.base;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    private final int id;
    private final List<Card> cards;
    private final List<PlayAction> history;
    private Integer money;
    private final Integer initialMoney;

    public Player(int id, List<Card> cards, List<PlayAction> history, Integer money) {
        this.id = id;
        this.cards = cards;
        this.history = history;
        this.money = money;
        initialMoney = money;
    }

    public Player(int id) {
        this(id, new ArrayList<>(), new ArrayList<>(), 10000);
    }

    public int getId() {
        return id;
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

    public Integer getInitialMoney() {
        return initialMoney;
    }

    public void minusMoney(int money) {
        this.money -= money;
    }

    public abstract PlayAction play(List<Card> cards, PlayerState playerState, int biggerBet);

    @Override
    public String toString() {
        return "Player {" +
                "\n\tid=" + id +
                ",\n\tcards=" + cards +
                ",\n\thistory=" + history +
                ",\n\tmoney=" + money +
                "\n}";
    }
}
