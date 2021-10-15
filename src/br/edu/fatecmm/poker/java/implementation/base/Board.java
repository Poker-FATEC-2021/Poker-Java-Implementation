package br.edu.fatecmm.poker.java.implementation.base;

import java.security.SecureRandom;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Board {
    private final List<Player> players;
    private final List<Card> faceDown;
    private final List<Card> faceUp;
    private final List<Integer> removedPlayers;

    private int round = 0;
    private int dealer = 0;
    private int money = 0;

    public Board(List<Player> players, List<Card> faceDown, List<Card> faceUp, List<Integer> removedPlayers) {
        this.players = players;
        this.faceDown = faceDown;
        this.faceUp = faceUp;
        this.removedPlayers = removedPlayers;
    }

    public Board() {
        this(
                new ArrayList<>(8),
                new ArrayList<>(64),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    public void start() {
        initializeCards();
        initializePlayers();
        initializeDealer();

        while (true) {
            System.out.println("Round: " + round + 1);
            System.out.println("Dealer: " + dealer);

            Turn turn = Turn.values()[round];

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

            int playersCount = players.size();

            players: for (int i = ((dealer + 1) % playersCount); ; i = ((i + 1) % playersCount)) {
                System.out.println("Player " + i + 1);

                PlayAction action = players
                        .get(i)
                        .play(faceUp, generatePlayerState(turn, dealer, i));

                switch (action.getKind()) {
                    case RUN:
                        removedPlayers.add(i);
                        break;
                    case PAY:
                        break;
                    case RAISE:
                    case ALL_IN:
                        money += action.getMoney();
                        break;
                    case CHECK: break players;
                }
            }
            for (Integer removed : removedPlayers) players.remove((int) removed);
            removedPlayers.clear();

            dealer = ((dealer + 1) % playersCount);
            round++;

            if (turn == Turn.RIVER) break;
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
        int realPlayers = Integer.parseInt(s.nextLine());

        System.out.println("Insira o número de jogadores mecanizados: ");
        int mechanicalPlayers = Integer.parseInt(s.nextLine());

        final class QuantityGenerator {
            private final int quantity;
            private final Supplier<Player> supplier;

            public QuantityGenerator(int quantity, Supplier<Player> supplier) {
                this.quantity = quantity;
                this.supplier = supplier;
            }
        }

        BiConsumer<QuantityGenerator, Consumer<Player>> fn = (g, c) -> {
            for (int i = 0; i < g.quantity; i++) c.accept(g.supplier.get());
        };

        fn.accept(new QuantityGenerator(realPlayers, RealPlayer::new), players::add);
        fn.accept(new QuantityGenerator(mechanicalPlayers, CPUPlayer::new), players::add);
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

    private PlayerState generatePlayerState(Turn turn, int dealer, int player) {
        boolean isNextDealer = dealer + 1 == player || dealer + 2 == player;
        if (turn == Turn.PRE_FLOP && isNextDealer) return PlayerState.BLIND;
        return PlayerState.NORMAL;
    }

    private void initializeDealer() {
        SecureRandom sr = new SecureRandom();
        dealer = sr.nextInt(players.size());
    }
}
