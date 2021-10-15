package br.edu.fatecmm.poker.java.implementation.base;

public class PlayAction {
    private final int money;
    private final Kind kind;

    private PlayAction(int money, Kind kind) {
        this.money = money;
        this.kind = kind;
    }

    private PlayAction(Kind kind) {
        this(-1, kind);
    }

    public static PlayAction fromKind(Kind kind) {
        return new PlayAction(kind);
    }

    public static PlayAction of(int money, Kind kind) {
        return new PlayAction(money, kind);
    }

    public int getMoney() {
        return money;
    }

    public Kind getKind() {
        return kind;
    }

    @Override
    public String toString() {
        return "PlayAction{" +
                "money=" + money +
                ", kind=" + kind +
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
