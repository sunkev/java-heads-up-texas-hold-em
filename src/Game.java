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
    public Player[] players;

    public Dealer dealer;
    public JPanel table;
    private Deck deck;
    private Hand playerHand;
    private Hand computerHand;
    public PlayerOptionPanel playerOptions;
    public Turn turn;
    private static JPanel log = new JPanel();
    private JPanel previousLog;
    private boolean gameOver;

    public enum Turn {
        PREFLOP, FLOP, TURN, RIVER, SUMMARY
    }

    public static void main(String[] args) throws IOException {
        new Game();
    }

    public Game() throws IOException{
        setTitle("Texas Hold'em");
        setSize(485, 750);

        this.table = new JPanel(new GridLayout(3, 3));
        createPlayers();
        startNewRound();

        add(this.table);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void createPlayers() throws IOException {
        dealer = new Dealer("middle.jpg");

        player = new Player("Player", "left_side.jpg", dealer);
        computer = new Player("Computer", "right_side.jpg", dealer);
        players = new Player[]{player, computer};

        playerOptions = new PlayerOptionPanel(new ButtonListener());

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


    public void startNewRound() {
        this.previousLog = log;
        log = new JPanel();
        log.setLayout(new BoxLayout(log, BoxLayout.PAGE_AXIS));
        // reset the deck, turn and dealer community cards
        this.deck = new Deck();
        this.turn = Turn.PREFLOP;
        this.dealer.resetCommunityCards();

        // give players new hands
        playerHand = new Hand(this.deck.pop(), this.deck.pop());
        computerHand = new Hand(this.deck.pop(), this.deck.pop());

        player.setHand(playerHand);
        computer.setHand(computerHand);

        for(int i = 7; i > 2; i--){
            this.table.remove(i);
        }
//        this.table.remove(7);
//        this.table.remove(6);
//        this.table.remove(5);
//        this.table.remove(4);
//        this.table.remove(3);

        this.table.add(playerHand);
        this.table.add(playerOptions);
        this.table.add(computerHand);
        this.table.add(previousLog);
        this.table.add(log);
//        player.turn();
//        computer.turn();

        this.gameOver = false;
        takeAnte();
    }


    static public void addToLog(String text){
        JLabel label = new JLabel(text);
        label.setFont(new Font("Helvetica", Font.PLAIN, 10));
        log.add(label);
    }

    public void resetTurn(){
        Game.addToLog("------------");
        for(Player x: this.players)
        {
            x.resetCurrentBet();
            x.isOutOfGame = false;
            x.refreshBankrollText();
        }
//        this.dealer.resetPot();
        if (this.gameOver){
            this.dealer.resetPot();
        }
        this.dealer.resetHighestBet();
        this.dealer.refreshPotText();
    }

    public void checkGameEnd() {
        // if everyone folded
        if (dealer.potSize == 0){
            int playerCount = this.players.length;
            int playersOut = 0;
            Player winner = this.player;

            for(Player x: this.players)
            {
                if (x.bankrupt())
                {
                    playersOut += 1;
                }
                else {
                    winner = x;
                }
            }

            if (playersOut == playerCount - 1)
            {
                JOptionPane.showMessageDialog(null, winner.name + " has won!");
            }
        }
    }

    public void checkNoShowdownWin(){
        // if everyone folded
        int playerCount = this.players.length;
        int playersOut = 0;
        Player winner = this.player;

        for(Player x: this.players)
        {
            if (x.isOutOfGame)
            {
                playersOut += 1;
            }
            else {
                winner = x;
            }
        }

        if (playersOut == playerCount - 1)
        {
            winner.addWinnings(this.dealer.potSize);
            this.gameOver = true;
            resetTurn();
        }
    }

    public void showDown(){
        int playerScore = player.handScore();
        int computerScore = computer.handScore();

        Game.addToLog("Player has a " + player.evaluatedHand());
        Game.addToLog("Computer has a " + computer.evaluatedHand());

        if (playerScore == computerScore)
        {
            player.addWinnings(this.dealer.potSize/2);
            computer.addWinnings(this.dealer.potSize/2);
            Game.addToLog("Tie");
        }
        else if (playerScore >= computerScore)
        {
            player.addWinnings(this.dealer.potSize);
            Game.addToLog("Player wins");
        }
        else
        {
            computer.addWinnings(this.dealer.potSize);
            Game.addToLog("Computer wins");
        }

        this.gameOver = true;
    }

    public void takeAnte(){
        Game.addToLog("Adding antes");
        Game.addToLog("------------");
        for(Player x: this.players)
        {
            x.bet(50);
        }
    }

    class ButtonListener implements ActionListener
    {
        public void actionPerformed (ActionEvent e)
        {
            if (turn == Turn.SUMMARY){
                startNewRound();
                return;
            }
            resetTurn();

            switch(e.getActionCommand()){
                case "Check":
                    computer.computerTurn();
                    checkNoShowdownWin();
                    break;
                case "Bet":
                    player.bet(playerOptions.betFieldNumber());
                    computer.computerTurn();
                    checkNoShowdownWin();
                    break;
                case "Call":
                    player.call();
                    checkNoShowdownWin();
                    break;
                case "Fold":
                    player.fold();
                    checkNoShowdownWin();
                    break;
                default:
                    System.out.println("Not working");
                    break;
            }

            if (gameOver)
            {
                Game.addToLog("Click any button for new round");
                turn = Turn.SUMMARY;
            }

            // if players done, go to next turn
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
                    break;
                case RIVER:
                    computer.revealHand();
                    showDown();

                    resetTurn();
                    turn = Turn.SUMMARY;
                    break;
            }

            checkGameEnd();
        }
    }
}
