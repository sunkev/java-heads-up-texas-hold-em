/**
 * Created by Kevin Sun on 5/1/15.
 *
 * Runs a heads up texas hold'em poker game.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Game extends JFrame   {
    private Player player;
    private Player computer;
    private Player[] players;
    private Dealer dealer;
    private JPanel table;
    private Deck deck;
    private Hand playerHand;
    private Hand computerHand;
    private PlayerOptionPanel playerOptions;
    private Turn turn;
    private static JPanel log = new JPanel();
    private JPanel previousLog;
    private boolean gameOver;

    public enum Turn {
        PREFLOP, FLOP, TURN, RIVER, SUMMARY
    }

    public static void main(String[] args) throws IOException {
        new Game();
    }

    /**
     * This constructor creates a game class. Controls all aspects of the game.
     * Including creating the players JPanels and starting the turn system.
     */

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


    /**
     * This method creates dealer, player and computer and adds them to table.
     */

    public void createPlayers() throws IOException {
        // create JPanels and add them to table
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


    /**
     * This method creates a new log, previous log, and hands. Then adds them to the table.
     * It also takes an ante from the player's bankroll.
     */

    public void startNewRound() {
        //update the log panels
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

        // remove and re-add all the panels every new set of rounds
        for(int i = 7; i > 2; i--){
            this.table.remove(i);
        }

        this.table.add(playerHand);
        this.table.add(playerOptions);
        this.table.add(computerHand);
        this.table.add(previousLog);
        this.table.add(log);

        this.gameOver = false;
        takeAnte();
    }

    /**
     * Helper method that adds a JLabel to the current log JPanel
     *
     * @param text    A JLabel is created that has this text
     */

    static public void addToLog(String text){
        JLabel label = new JLabel(text);
        label.setFont(new Font("Helvetica", Font.PLAIN, 10));
        log.add(label);
    }

    /**
     * Before each game reset all variables of the player and dealer
     */

    public void resetTurn(){
        Game.addToLog("------------");
        for(Player x: this.players)
        {
            x.resetCurrentBet();
            x.isOutOfGame = false;
            x.refreshBankrollText();
        }
        if (this.gameOver){
            this.dealer.resetPot();
        }
        this.dealer.resetHighestBet();
        this.dealer.refreshPotText();
    }

    /**
     * This method checks if there is a game winner
     */

    public void checkGameEnd() {
        // check if there is a game winner
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

            // if winner exit program
            if (playersOut == playerCount - 1)
            {
                JOptionPane.showMessageDialog(null, winner.name + " has won!");
                System.exit(0);
            }
        }
    }

    /**
     * Check to see if a player has won through making other players fold
     */

    public void checkNoShowdownWin(){
        // check to see if win from folding
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

        // if winner, add to his winnings
        if (playersOut == playerCount - 1)
        {
            winner.addWinnings(this.dealer.potSize);
            this.gameOver = true;
            resetTurn();
        }
    }

    /**
     * Calculates the evaluated poker hands of all the players, and determines the winner.
     */

    public void showDown(){
        // determine which hand is better
        int playerScore = player.handScore();
        int computerScore = computer.handScore();

        // add to log the evaluated hands

        Game.addToLog("Player has a " + player.evaluatedHand());
        Game.addToLog("Computer has a " + computer.evaluatedHand());

        // determine round winner and reward him
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

    /**
     * Method takes 50 chips as an ante from players.
     */

    public void takeAnte(){
        Game.addToLog("Adding antes");
        Game.addToLog("------------");
        for(Player x: this.players)
        {
            x.bet(50);
        }
    }

    /**
     * Dealer deals a community card and the card is added to each player's hand.
     */

    public void dealCard(){

        Card card1 = deck.pop();
        for(Player x: this.players)
        {
            x.addCard(card1);
        }
        dealer.addCommunityCard(card1);
    }

    /**
     * Method determines what happens on which turn.
     */

    public void progressTurn(){
        // if players done, go to next turn
        switch (turn) {
            case PREFLOP:
                turn = Turn.FLOP;
                dealCard();
                dealCard();
                dealCard();
                break;
            case FLOP:
                dealCard();

                turn = Turn.TURN;
                break;
            case TURN:
                dealCard();
                turn = Turn.RIVER;
                break;
            case RIVER:
                computer.revealHand();
                showDown();

                resetTurn();
                turn = Turn.SUMMARY;
                break;
        }
    }

    /**
     * Method that takes what button is pressed, and runs the corresponding code.
     */

    public void buttonClicked(String buttonStr){
        switch(buttonStr){
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
    }

    /**
     * Method passed to the buttons on the PlayerOptionsPanel
     */

    class ButtonListener implements ActionListener
    {
        public void actionPerformed (ActionEvent e)
        {
            // if last turn, restart the game
            if (turn == Turn.SUMMARY){
                startNewRound();
                return;
            }

            resetTurn();

            // give player and computer turns
            buttonClicked(e.getActionCommand());

            // add a summary round
            if (gameOver)
            {
                Game.addToLog("Click any button for new round");
                turn = Turn.SUMMARY;
            }

            // go to next round
            progressTurn();

            //check if game is over
            checkGameEnd();
        }
    }
}
