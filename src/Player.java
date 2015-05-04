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
    private Hand hand;
    private int bankroll;
    public Boolean loser;
    public Dealer dealer;
    private JLabel betLabel;
    private JLabel bankrollLabel;
    private JLabel nameLabel;
    private Boolean isPlayer;

    public static boolean playersDone = false;

    public Player(String name, String filePath, Dealer dealer) throws IOException {
        this.name = name;
        this.bankroll = 1500;
        this.image = ImageIO.read(new File(filePath));
        this.dealer = dealer;
        this.currentBet = 0;
        this.loser = true;
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
        double d = Math.random();
        if (d <.20)
        {
            check();
        } else if(d >= .20 && d < .40)
        {
//            call();
            bet();
        } else if (d >= .40 && d < .60)
        {
            bet();
        }
        else
        {
            fold();
        }
    }

    public void check(){

    }

    public void bet(int amount){
        this.currentBet += amount;
        this.bankroll -= amount;
        this.dealer.setHighestBet(this.currentBet);
        this.dealer.addPot(amount);
        Game.log.add(new JLabel(name + " bets " + amount));
        refreshBankrollText();
    }

    public void bet(){
        int amount = (int)((Math.random()/10) * bankroll) ;
        this.bankroll -= amount;
        this.currentBet += amount;
        this.dealer.setHighestBet(this.currentBet);
        this.dealer.addPot(amount);

        Game.log.add(new JLabel(name + " bets " + amount));
        refreshBankrollText();
    }

    public void call(){
        int amount = this.currentBet - this.dealer.highestBet;
        this.currentBet += amount;
        this.dealer.addPot(amount);
        refreshBankrollText();

        Game.log.add(new JLabel(name + " calls " + amount));
        playersDone = true;
    }

    public void fold(){
        playersDone = true;

        Game.log.add(new JLabel(name + " folds"));
        this.loser = true;
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
}
