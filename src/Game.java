/**
 * Created by brookeside on 4/30/15.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Game extends JFrame   {
    public Player player;
    public Player computer;
    public Dealer dealer;
    public JPanel table;
    private Deck deck;
    private Hand playerHand;
    private Hand computerHand;
    public PlayerOptionPanel playerOptions;
    public Turn turn;
    public static JPanel log;

    public enum Turn {
        PREFLOP, FLOP, TURN, RIVER
    }

    public static void main(String[] args) throws IOException {
        new Game();
    }

    public Game() throws IOException{
        setTitle("Texas Hold'em");
        setSize(485, 750);

        this.table = new JPanel(new GridLayout(3, 3));
        createPlayers();
        startGame();

        add(this.table);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void createPlayers() throws IOException {
        dealer = new Dealer("middle.jpg");
        player = new Player("Player", "left_side.jpg", dealer);
        computer = new Player("Computer", "right_side.jpg", dealer);
        log = new JPanel();
        playerOptions = new PlayerOptionPanel(new ButtonListener());
        log.setLayout(new BoxLayout(log, BoxLayout.PAGE_AXIS));

        this.table.add(player);
        this.table.add(dealer);
        this.table.add(computer);

        // add filler panels
        this.table.add(new JPanel());
        this.table.add(new JPanel());
        this.table.add(new JPanel());
        this.table.add(new JPanel());
        this.table.add(new JPanel());
    }


    public void startGame() {
        startRound();
    }

    public void startRound() {
        this.deck = new Deck();
        this.turn = Turn.PREFLOP;
        this.dealer.resetCommunityCards();

        playerHand = new Hand(this.deck.pop(), this.deck.pop());
        computerHand = new Hand(this.deck.pop(), this.deck.pop());

        player.setHand(playerHand);
        computer.setHand(computerHand);

        addHandsToTable();
//        player.turn();
//        computer.turn();
    }

    private void addHandsToTable(){
        this.table.remove(7);
        this.table.remove(6);
        this.table.remove(5);
        this.table.remove(4);
        this.table.remove(3);

        this.table.add(playerHand);
        this.table.add(playerOptions);
        this.table.add(computerHand);
        this.table.add(new JPanel());
        this.table.add(log);

        log.add(new JLabel("Round starts"));

    }

    class ButtonListener implements ActionListener
    {
        public void actionPerformed (ActionEvent e)
        {
            switch(e.getActionCommand()){
                case "Check":
                    break;
                case "Bet":
                    player.bet(playerOptions.betFieldNumber()); computer.computerTurn(); break;
                case "Call":
                    player.call(); computer.computerTurn();
                    break;
                case "Fold":
                    player.fold();
                    break;
                default:
                    System.out.println("YO");
                    break;
            }

            // if players done, go to next turn
            if (Player.playersDone) {
                switch (turn) {
                    case PREFLOP:
                        turn = Turn.FLOP;

                        Card card1 = deck.pop();
                        Card card2 = deck.pop();
                        Card card3 = deck.pop();

                        dealer.addCommunityCard(card1);
                        dealer.addCommunityCard(card2);
                        dealer.addCommunityCard(card3);

                        player.addCard(card1);
                        player.addCard(card2);
                        player.addCard(card3);

                        computer.addCard(card1);
                        computer.addCard(card2);
                        computer.addCard(card3);
                        break;
                    case FLOP:
                        Card card4 = deck.pop();
                        dealer.addCommunityCard(card4);
                        player.addCard(card4);
                        computer.addCard(card4);

                        turn = Turn.TURN;
                        break;
                    case TURN:
                        Card card5 = deck.pop();
                        dealer.addCommunityCard(card5);
                        player.addCard(card5);
                        computer.addCard(card5);

                        turn = Turn.RIVER;
                        computer.revealHand();
                        break;
                    case RIVER:
                        System.out.println("Player " + player.evaluatedHand());
                        System.out.println("Computer " + computer.evaluatedHand());

                        int playerScore = player.handScore();
                        int computerScore = computer.handScore();

                        if (playerScore == computerScore)
                        {
                            System.out.println("Tie");
                        }
                        else if (playerScore >= computerScore)
                        {
                            System.out.println("Player wins");
                        }
                        else
                        {
                            System.out.println("Computer wins");
                        }

                        startRound();
                        break;
                }
            }

        }
    }
}
