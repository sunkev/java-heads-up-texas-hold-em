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
    private BufferedImage image;
    private String name;
    private Hand hand;
    private int bankroll;
    private JLabel bankrollLabel;
    private int currentBet;
    public Boolean loser;
    public Dealer dealer;

    public static boolean playersDone = false;

    public Player(String name, String filePath, Dealer dealer) throws IOException {
        this.name = name;
        this.bankroll = 1500;
        this.image = ImageIO.read(new File(filePath));
        this.dealer = dealer;
        this.currentBet = 0;
        this.loser = true;

        setLayout(new GridBagLayout());

        setDimensions();
        addBankrollLabel();
    }

    private void setDimensions() {
        Dimension size = new Dimension(image.getWidth(null), image.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
    }

    private void addBankrollLabel(){
        bankrollLabel = new JLabel(name + " " + bankroll);
        bankrollLabel.setFont(new Font ("Helvetica", Font.BOLD, 20));
        bankrollLabel.setForeground(Color.ORANGE);
        add(bankrollLabel);
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
        refreshBankrollText();
    }

    public void bet(){
        int amount = (int)((Math.random()/10) * bankroll) ;
        this.bankroll -= amount;
        this.currentBet += amount;
        this.dealer.setHighestBet(this.currentBet);
        this.dealer.addPot(amount);
        refreshBankrollText();
    }

    public void call(){
        int amount = this.currentBet - this.dealer.highestBet;
        this.currentBet += amount;
        this.dealer.addPot(amount);
        refreshBankrollText();
        playersDone = true;
    }

    public void fold(){
        playersDone = true;
        this.loser = true;
    }

    public void paintComponent(Graphics g) {
        // create background
        g.drawImage(image, 0, 0, null);
    }

    public void refreshBankrollText(){
        this.bankrollLabel.setText(name + " " + bankroll);
    }

    public boolean bankrupt(){
        return (bankroll == 0);
    }

    public void setHand(Hand hand){
        this.hand = hand;
    }
}
