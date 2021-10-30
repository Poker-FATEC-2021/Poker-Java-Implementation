package br.edu.fatecmm.poker.java.implementation.base;

import java.security.SecureRandom;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class Board {
    private final List<Player> players;
    private final List<Card> faceDown;
    private final List<Card> faceUp;

    private int round = 0;
    private int dealer = 0;
    private int money = 0;
    private int biggerBet = 0;
    private int biggerBetOwnerPosition = -1;
    private int boardRotate = 0;

    public Board(List<Player> players, List<Card> faceDown, List<Card> faceUp) {
        this.players = players;
        this.faceDown = faceDown;
        this.faceUp = faceUp;
    }

    public Board() {
        this(
                new ArrayList<>(8),
                new ArrayList<>(64),
                new ArrayList<>()
        );
    }

    public void start() {
        initializeCards();
        initializePlayers();
        initializeDealer();

        while (true) {
            Turn turn = Turn.values()[round];

            System.out.println("Turn: " + turn);
            System.out.println("Dealer: " + (dealer + 1));

            switch (turn) {
                case PRE_FLOP:
                    initializePlayersCards();
                    break;
                case FLOP:
                    initializeFlopCards();
                    break;
                case TURN:
                case RIVER:
                    faceUpCard();
                    break;
            }

            players: for (int i = ((dealer + 1) % players.size()); ; i = ((i + 1) % players.size())) {
                if (i == dealer) boardRotate++;
                System.out.println("-----------------------------------------------------------");
                System.out.println("Players count: " + players.size());
                System.out.println("Board money: " + money);
                System.out.println("Board cards: " + faceUp);
                System.out.println("Bigger bet: " + biggerBet);
                System.out.println("Bigger bet owner position: " + biggerBetOwnerPosition);

                PlayAction action = players
                        .get(i)
                        .play(
                                faceUp,
                                getPlayerState(turn, dealer, i, players.size(), boardRotate),
                                biggerBet
                        );

                switch (action.getKind()) {
                    case RUN: {
                        if ((i+1) % players.size() == biggerBetOwnerPosition) {
                            players.remove(i);
                            boardRotate = 0;
                            break players;
                        } else {
                            Player player = players.get(biggerBetOwnerPosition);
                            players.remove(i);
                            biggerBetOwnerPosition = players.indexOf(player);
                        }
                    }
                    case PAY:
                    case RAISE:
                    case ALL_IN: {
                        int oldBiggerBet = biggerBet;
                        money += action.getMoney();
                        biggerBet = action.getBiggerBet();
                        if (biggerBet > oldBiggerBet) {
                            biggerBetOwnerPosition = i;
                        }
                        break;
                    }
                    case CHECK: {
                        boardRotate = 0;
                        break players;
                    }
                }
                System.out.println("-----------------------------------------------------------\n\n\n");
            }

            dealer = ((dealer + 1) % players.size());
            round++;
        }
    }

    private void initializeCards() {
        for (Value value : Value.values())
            for (Suit suit : Suit.values())
                faceDown.add(new Card(value, suit));

        Collections.shuffle(faceDown, new SecureRandom());
    }

    private void initializePlayers() {
        Scanner s = new Scanner(System.in);

        System.out.println("Insira o número de jogadores reais: ");
        int realPlayersQuantity = Integer.parseInt(s.nextLine());

        System.out.println("Insira o número de jogadores mecanizados: ");
        int mechanicalPlayersQuantity = Integer.parseInt(s.nextLine());

        final class QuantityGenerator {
            private final int quantity;
            private final Function<Integer, Player> fn;

            public QuantityGenerator(int quantity, Function<Integer, Player> fn) {
                this.quantity = quantity;
                this.fn = fn;
            }
        }

        List<QuantityGenerator> generators = Arrays.asList(
                new QuantityGenerator(realPlayersQuantity, RealPlayer::new),
                new QuantityGenerator(mechanicalPlayersQuantity, CPUPlayer::new)
        );

        int id = 0;
        for (QuantityGenerator generator : generators) {
            for (int i = 0; i < generator.quantity; i++, id++) {
                players.add(generator.fn.apply(id));
            }
        }
    }

    private void initializePlayersCards() {
        for (Player player : players) {
            Consumer<Card> c = player.getCards()::add;
            c.accept(faceDown.remove(faceDown.size() - 1));
            c.accept(faceDown.remove(faceDown.size() - 1));
        }
    }

    private void initializeFlopCards() {
        Runnable r = () -> faceUp.add(faceDown.remove(faceDown.size() - 1));
        r.run();
        r.run();
        r.run();
    }

    private void faceUpCard() {
        faceUp.add(faceDown.remove(faceDown.size() - 1));
    }

    private PlayerState getPlayerState(
            Turn turn,
            int dealer,
            int player,
            int playersCount,
            int boardRotate
    ) {
        if (turn == Turn.PRE_FLOP && boardRotate <= 0) {
            if ((dealer + 1) % playersCount == player) return PlayerState.SMALL_BLIND;
            if ((dealer + 2) % playersCount == player) return PlayerState.BIG_BLIND;
        }
        return PlayerState.NORMAL;
    }

    private void initializeDealer() {
        SecureRandom sr = new SecureRandom();
        dealer = sr.nextInt(players.size());
    }
}
