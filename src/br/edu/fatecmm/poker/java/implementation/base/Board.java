package br.edu.fatecmm.poker.java.implementation.base;

import java.security.SecureRandom;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Board {
    private final List<Player> players;
    private final Deque<Card> faceDown;
    private final Deque<Card> faceUp;
    private int round = 0;
    private int dealer = 0;


    public Board(List<Player> players, Deque<Card> faceDown, Deque<Card> faceUp) {
        this.players = players;
        this.faceDown = faceDown;
        this.faceUp = faceUp;
    }

    public Board() {
        this(new ArrayList<>(), new ArrayDeque<>(), new ArrayDeque<>());
    }

    public void start() {
        initializeCards();
        initializePlayers();
        initializeDealer();

        int playersCount = players.size();

        while (true) {
            System.out.println("Round: " + ++round);
            System.out.println("Dealer: " + dealer);

            for (int i = ((dealer + 1) % playersCount); ; i = ((i + 1) % playersCount)) {
                players.get(i).play(null);
                if (i == dealer) break;
            }

            dealer = ((dealer + 1) % playersCount);
        }
    }

    private void initializeCards() {
        List<Card> cards = new ArrayList<>(64);
        for (Value value : Value.values())
            for (Suit suit : Suit.values())
                cards.add(new Card(value, suit));

        Collections.shuffle(cards, new SecureRandom());
        cards.forEach(faceDown::addLast);
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

        for (Player player : players) {
            Consumer<Card> c = player.getCards()::add;
            c.accept(faceDown.pollLast());
            c.accept(faceDown.pollLast());
        }
    }

    private void initializeDealer() {
        SecureRandom sr = new SecureRandom();
        dealer = sr.nextInt(players.size());
    }
}
