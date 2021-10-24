package br.edu.fatecmm.poker.java.implementation.base;

public class PlayAction {
    private final int money;
    private final Kind kind;
    private final int playerId;
    private final int biggerBet;

    private PlayAction(int money, Kind kind, int playerId, int biggerBet) {
        this.money = money;
        this.kind = kind;
        this.playerId = playerId;
        this.biggerBet = biggerBet;
    }

    public static PlayAction of(int money, Kind kind, int playerId, int biggerBet) {
        return new PlayAction(money, kind, playerId, biggerBet);
    }

    public int getMoney() {
        return money;
    }

    public Kind getKind() {
        return kind;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getBiggerBet() {
        return biggerBet;
    }

    @Override
    public String toString() {
        return "PlayAction{" +
                "money=" + money +
                ", kind=" + kind +
                ", playerId=" + playerId +
                ", biggerBet=" + biggerBet +
                '}';
    }

    public enum Kind {
        RUN,
        PAY,
        RAISE,
        ALL_IN,
        CHECK
    }
}
