package br.edu.fatecmm.poker.java.implementation.base;

import br.edu.fatecmm.poker.java.implementation.base.PlayAction.Kind;
import java.util.List;
import java.util.Scanner;

import static br.edu.fatecmm.poker.java.implementation.base.PlayAction.Kind.*;

public class RealPlayer extends Player {

    public RealPlayer(int id) {
        super(id);
    }

    @Override
    public PlayAction play(List<Card> cards, PlayerState playerState, int biggerBet) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("----");
        System.out.println("Player state: " + playerState);
        System.out.println(this);
        System.out.println("----");

        if (playerState != PlayerState.NORMAL) {
            System.out.println("Você deve fazer uma aposta mínima de " + (biggerBet + 1));
            int newBet;
            do {
                System.out.println("Insira uma aposta válida!");
                newBet = Integer.parseInt(scanner.nextLine());
            } while (newBet < biggerBet + 1 || newBet > getMoney());
            minusMoney(newBet);
            PlayAction action = PlayAction.of(newBet, RAISE, getId(), newBet);
            addHistory(action);
            return action;
        }

        do {
            System.out.println("Digite o que você deseja fazer: ");

            System.out.println("1 -> RUN");
            System.out.println("2 -> ALL_IN");
            System.out.println("3 -> PAY");
            System.out.println("4 -> RAISE");
            System.out.println("5 -> CHECK");

            int opt;

            do {
                opt = Integer.parseInt(scanner.nextLine());
            } while (opt < 0 || opt > 5);

            switch (opt) {
                case 1: {
                    PlayAction action = PlayAction.of(0, Kind.RUN, getId(), biggerBet);
                    addHistory(action);
                    return action;
                }
                case 2: {
                    System.out.println("Você escolheu apostar tudo!");
                    int money = getMoney();
                    System.out.println("Seu dinheiro era: " + money);
                    sleep();
                    minusMoney(money);
                    PlayAction action = PlayAction.of(money, ALL_IN, getId(), getInitialMoney());
                    addHistory(action);
                    return action;
                }
                case 3: {
                    int myBet = getInitialMoney() - getMoney();
                    int diff = biggerBet - myBet;
                    if (diff <= 0) {
                        System.out.println("Você não pode 'pagar'.");
                        System.out.println("Você já pagou o necessário para manter a maior aposta da mesa");
                        System.out.println("O processo vai recomeçar");
                        sleep();
                    } else {
                        System.out.println("Você vai pagar " + diff);
                        sleep();
                        PlayAction action = PlayAction.of(diff, PAY, getId(), biggerBet);
                        minusMoney(diff);
                        addHistory(action);
                        return action;
                    }
                    break;
                }
                case 4: {
                    int myBet = getInitialMoney() - getMoney();
                    int diff = biggerBet - myBet;
                    System.out.println("Sua aposta mínima deve ser: " + (diff + 1));
                    int newBet;
                    do {
                        System.out.println("Insira uma aposta válida!");
                        newBet = Integer.parseInt(scanner.nextLine());
                    } while (newBet < diff + 1 || newBet > getMoney());
                    minusMoney(newBet);
                    PlayAction action = PlayAction.of(newBet, RAISE, getId(), biggerBet + (newBet - diff));
                    addHistory(action);
                    return action;
                }
                case 5: {
                    int myBet = getInitialMoney() - getMoney();
                    if (myBet == biggerBet) {
                        PlayAction action = PlayAction.of(0, CHECK, getId(), biggerBet);
                        addHistory(action);
                        return action;
                    } else {
                        System.out.println("Você não pode dar o check. O processo vai recomeçar!");
                        sleep();
                    }
                    break;
                }
                default: return null;
            }
        } while(true);
    }

    private void sleep() {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
