import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by brookeside on 4/30/15.
 */
public class Dealer extends JPanel {
    private BufferedImage image;
    public int potSize;
    public int highestBet;
    private JLabel potLabel;
    private JLabel communityCards;

    public Dealer (String filePath) throws IOException {
        this.potSize = 0;
        this.highestBet = 0;
        this.image = ImageIO.read(new File(filePath));

        setLayout(new GridLayout(3,0));

        addLabelsToTable();

    }

    public void addLabelsToTable(){
        add(new JLabel());

        // add pot to panel
        this.potLabel = new JLabel("Pot \n" + this.potSize);
        this.potLabel.setFont(new Font ("Helvetica", Font.BOLD, 20));
        this.potLabel.setForeground(Color.ORANGE);
        this.potLabel.setHorizontalAlignment(JLabel.CENTER);
        add(this.potLabel);

        // add community cards to panel
        this.communityCards = new JLabel();
        this.communityCards.setFont(new Font ("Helvetica", Font.BOLD, 13));
        this.communityCards.setForeground(Color.ORANGE);
        this.communityCards.setHorizontalAlignment(JLabel.CENTER);
        add(this.communityCards);
    }

    public void addPot(int change){
        this.potSize += change;
        this.potLabel.setText("Pot \n" + potSize);
    }

    public void setHighestBet(int bet){
        this.highestBet = bet;
    }

    public void addCommunityCard(Card card){
        String text = this.communityCards.getText();
        this.communityCards.setText(text + " " + card);
    }

    public void resetCommunityCards(){
        this.communityCards.setText("");
    }

    public void resetPot(){
        this.potSize = 0;
        this.potLabel.setText("Pot \n" + potSize);
    }

    public void paintComponent(Graphics g) {
        // create background
        g.drawImage(image, 0, 0, null);
    }
}
