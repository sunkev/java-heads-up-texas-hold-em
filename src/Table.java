/**
 * Created by brookeside on 4/30/15.
 */

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Table extends JFrame   {
    public Player player;
    public Player computer;
    public Dealer dealer;
    public JPanel grid;
    private Deck deck;
    private Hand playerHand;
    private Hand computerHand;

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
        player = new Player("Player", "left_side.jpg");
        computer = new Player("Computer", "right_side.jpg");
        dealer = new Dealer("middle.jpg");

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
            preflop();
//        }
    }

    public void preflop() {
        this.deck = new Deck();

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
        this.grid.add(new PlayerOptionPanel());
        this.grid.add(computerHand);
    }
}
