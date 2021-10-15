package br.edu.fatecmm.poker.java.implementation.base;

import java.util.List;
import java.util.Scanner;

public class RealPlayer extends Player {

    @Override
    public PlayAction play(List<Card> cards, PlayerState playerState) {
        System.out.println("Board cards: " + cards);
        System.out.println("Your cards: " + getCards());
        System.out.println("Your money: " + getMoney());

        PlayAction playAction;

        switch (playerState) {
            case BLIND:
                playAction = toBet();
                break;
            case NORMAL:
            default:
                playAction = toBet();
                break;
        }
        addHistory(playAction);
        return playAction;
    }

    private PlayAction toBet() {
        Scanner s = new Scanner(System.in);

        System.out.println("Você deve fazer uma aposta. Você tem " + getMoney() + " de dinheiro: ");

        int money;

        do {
            System.out.println("Insira uma aposta válida!");

            money = Integer.parseInt(s.nextLine());

        } while (money <= 0 || money > getMoney());

        minusMoney(money);

        return PlayAction.of(money, PlayAction.Kind.RAISE);
    }
}
