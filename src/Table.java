/**
 * Created by brookeside on 4/30/15.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Table extends JFrame   {
    public Player player;
    public Player computer;
    public Dealer dealer;
    public JPanel grid;
    private Deck deck;
    private Hand playerHand;
    private Hand computerHand;
    private JPanel playerOptions = new PlayerOptionPanel(new ButtonListener());
    public Turn turn;

    public enum Turn {
        PREFLOP, FLOP, TURN, RIVER
    }

    public Table() throws IOException{
        setTitle("Texas Hold'em");
        setSize(485, 540);

        this.grid = new JPanel(new GridLayout(2, 3));
        createPlayers();
        startGame();

        add(this.grid);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void createPlayers() throws IOException {
        dealer = new Dealer("middle.jpg");
        player = new Player("Player", "left_side.jpg", dealer);
        computer = new Player("Computer", "right_side.jpg", dealer);

        this.grid.add(player);
        this.grid.add(dealer);
        this.grid.add(computer);
        this.grid.add(new JPanel());
        this.grid.add(new JPanel());
        this.grid.add(new JPanel());

//        this.grid.add(player.hand);
//        this.grid.add(player.options);
//        this.grid.add(computer.hand);
    }


    public void startGame() {
//        while (!player.bankrupt() && !computer.bankrupt()){
        startRound();
//        }
    }

    public void startRound() {
        this.deck = new Deck();
        this.turn = Turn.PREFLOP;

        playerHand = new Hand(this.deck.pop(), this.deck.pop(), true);
        computerHand = new Hand(this.deck.pop(), this.deck.pop(), false);

        player.setHand(playerHand);
        computer.setHand(computerHand);

        addHandsToTable();
//        player.turn();
//        computer.turn();
    }

    private void addHandsToTable(){
        this.grid.remove(5);
        this.grid.remove(4);
        this.grid.remove(3);

        this.grid.add(playerHand);
        this.grid.add(playerOptions);
        this.grid.add(computerHand);
    }

    class ButtonListener implements ActionListener
    {
        public void actionPerformed (ActionEvent e)
        {
            switch(e.getActionCommand()){
                case "Check":
                    break;
                case "Bet":
                    player.bet(100); computer.computerTurn(); break;
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
                        break;
                    case FLOP:
                        turn = Turn.TURN;
                        break;
                    case TURN:
                        turn = Turn.RIVER;
                        break;
                    case RIVER:
                        startRound();
                        break;
                }
            }

        }
    }
}
