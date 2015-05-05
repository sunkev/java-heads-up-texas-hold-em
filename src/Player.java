import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by brookeside on 4/30/15.
 */
public class Player extends JPanel {
    private int currentBet;
    private BufferedImage image;
    private String name;
    public Hand hand;
    private int bankroll;
    public Dealer dealer;
    private JLabel betLabel;
    private JLabel bankrollLabel;
    private JLabel nameLabel;
    private Boolean isPlayer;


    public boolean isOutOfGame = false;


    public Player(String name, String filePath, Dealer dealer) throws IOException {
        this.name = name;
        this.bankroll = 1500;
        this.image = ImageIO.read(new File(filePath));
        this.dealer = dealer;
        this.currentBet = 0;
        this.isPlayer = (this.name.equals("Player"));

        setLayout(new GridLayout(3,0));

        setDimensions();
        initLabels();
    }

    private void setDimensions() {
        Dimension size = new Dimension(image.getWidth(null), image.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
    }

    private void initLabels(){
        nameLabel = new JLabel(name);
        bankrollLabel = new JLabel();
        betLabel = new JLabel();

        JLabel[] labels = {nameLabel, bankrollLabel, betLabel};

        for (JLabel label: labels)
        {
            label.setFont(new Font ("Helvetica", Font.BOLD, 15));
            label.setForeground(Color.ORANGE);
            label.setHorizontalAlignment(JLabel.CENTER);
            add(label);
        }

        refreshBankrollText();
    }

    public void computerTurn(){
        double rand = Math.random();
        double strengthOfHand = (rand*(handScore() + 1))/2;

        if (bankrupt()){
            check();
        }

        if(canCheck()){
            if (strengthOfHand <.50)
            {
                check();
            }
            else if(strengthOfHand >= .50)
            {
                bet();
            }
        }
        else
        {
            if (strengthOfHand <.30)
            {
                fold();
            }
            else if(strengthOfHand >= .30 && strengthOfHand < 2.0)
            {
                call();
            }
            else
            {
                bet((int) (rand * this.dealer.highestBet));
            }
        }
    }

    public boolean canCheck(){
        return this.currentBet == this.dealer.highestBet;
    }

    public void check(){
        // only computer can check
        Game.addToLog(name + " checks ");
    }

    public void bet(int amount){
        if (amount > bankroll)
        {
            amount = bankroll;
        }

        this.currentBet += amount;
        this.bankroll -= amount;
        this.dealer.setHighestBet(this.currentBet);
        this.dealer.addPot(amount);

        Game.addToLog(name + " bets " + amount);
        refreshBankrollText();
    }

    public void bet(){
        int amount = (int)(Math.random() * bankroll);
        this.bet(amount);
    }

    public void call(){
        int amount = this.dealer.highestBet - this.currentBet;
        this.currentBet = this.dealer.highestBet;
        this.bankroll -= amount;
        this.dealer.addPot(amount);
        refreshBankrollText();

        Game.addToLog(name + " calls " + amount);
    }

    public void fold(){
        this.isOutOfGame = true;

        Game.addToLog(name + " folds");
    }

    public void paintComponent(Graphics g) {
        // create background
        g.drawImage(image, 0, 0, null);
    }

    public void refreshBankrollText(){
        this.bankrollLabel.setText("Bankroll: " + bankroll);
        this.betLabel.setText("Bet: " + currentBet);
    }

    public boolean bankrupt(){
        return (bankroll == 0);
    }

    public void setHand(Hand hand){
        this.hand = hand;
        this.hand.addStartingCardsToTable(this.isPlayer);
    }

    public void addCard(Card card){
        this.hand.addCard(card);
    }

    public Enum evaluatedHand()
    {
        return new HandEvaluator(this.hand).calculateHand();
    }

    public int handScore()
    {
        return new HandEvaluator(this.hand).rankOfHand();
    }

    public void revealHand(){
        this.hand.revealHand();
    }

    public int addWinnings(int winnings){
        this.bankroll += winnings;
        refreshBankrollText();
        return this.bankroll;
    }

    public void resetCurrentBet(){
        this.currentBet = 0;
    }
}
