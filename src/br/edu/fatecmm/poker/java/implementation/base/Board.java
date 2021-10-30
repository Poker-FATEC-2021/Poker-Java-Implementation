package br.edu.fatecmm.poker.java.implementation.base;

import br.edu.fatecmm.poker.java.implementation.board.HandPlayWeight;

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
            System.out.println("Dealer: " + dealer);

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
                case COMPARE: {
                    System.out.println("O jogo acabou!");
                    System.out.println("As cartas vão ser comparadas!");

                    Map<Integer, List<Player>> pointPlayers = new HashMap<>();

                    HandPlayWeight[] handPlayWeights = HandPlayWeight.values();

                    players: for (Player player : players) {
                        List<Card> cards = new ArrayList<>(8);
                        cards.addAll(player.getCards());
                        cards.addAll(faceUp);

                        for (HandPlayWeight handPlayWeight : handPlayWeights) {
                            if (handPlayWeight.getHandPlay().isHandPlay(cards)) {
                                int weight = handPlayWeight.getWeight();
                                if (!pointPlayers.containsKey(weight)) {
                                    pointPlayers.put(weight, new ArrayList<>());
                                }
                                pointPlayers.get(weight).add(player);
                                continue players;
                            }
                        }
                        if (!pointPlayers.containsKey(-1)) {
                            pointPlayers.put(-1, new ArrayList<>());
                        }
                        pointPlayers.get(-1).add(player);
                    }

                    int biggerWeight = pointPlayers
                            .keySet()
                            .stream()
                            .max(Comparator.comparingInt(a -> a))
                            .orElse(-1);

                    List<Player> winners = pointPlayers.get(biggerWeight);

                    System.out.println("Cartas da mesa: ");
                    System.out.println(faceUp);
                    System.out.println("Tipo de jogada: " + biggerWeight);

                    if (winners.size() > 1) {
                        System.out.println("Tivemos mais de um ganhador, o 'pote' será dividido!!!");
                        System.out.println("Os ganhadores foram: ");
                        winners.forEach(System.out::println);
                        float total = (float) money / winners.size();
                        System.out.printf("Cada ganhador receberá %.2f", total);
                    } else {
                        System.out.println("Tivemos apenas um ganhador!!!");
                        Player winner = players.get(0);
                        System.out.println(winner);
                    }

                    System.exit(0);
                }
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
                            biggerBetOwnerPosition = -1;
                            break players;
                        } else if (biggerBetOwnerPosition > 0) {
                            Player player = players.get(biggerBetOwnerPosition);
                            players.remove(i);
                            biggerBetOwnerPosition = players.indexOf(player);
                        } else {
                            players.remove(i);
                        }
                        break;
                    }
                    case PAY:
                    case RAISE:
                    case ALL_IN: {
                        int oldBiggerBet = biggerBet;
                        money += action.getMoney();
                        biggerBet = action.getBiggerBet();
                        if (biggerBet > oldBiggerBet) {
                            biggerBetOwnerPosition = i;
                        } else if ((i + 1) % players.size() == biggerBetOwnerPosition) {
                            boardRotate = 0;
                            biggerBetOwnerPosition = -1;
                            break players;
                        }
                        break;
                    }
                    case CHECK: {
                        if (((i + 1) % players.size() == biggerBetOwnerPosition) || (biggerBetOwnerPosition == -1 && boardRotate == 1)) {
                            boardRotate = 0;
                            biggerBetOwnerPosition = -1;
                            break players;
                        }
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

//        System.out.println("Insira o número de jogadores mecanizados: ");
//        int mechanicalPlayersQuantity = Integer.parseInt(s.nextLine());

        final class QuantityGenerator {
            private final int quantity;
            private final Function<Integer, Player> fn;

            public QuantityGenerator(int quantity, Function<Integer, Player> fn) {
                this.quantity = quantity;
                this.fn = fn;
            }
        }

        List<QuantityGenerator> generators = Arrays.asList(
                new QuantityGenerator(realPlayersQuantity, RealPlayer::new)
                //new QuantityGenerator(mechanicalPlayersQuantity, CPUPlayer::new)
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
