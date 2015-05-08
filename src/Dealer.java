import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Kevin Sun on 5/1/15.
 *
 * Creates many cards. It shuffles the cards and is able to remove and return a card from the top of the deck.
 */

public class Dealer extends JPanel {
    private BufferedImage image;
    public int potSize;
    public int highestBet;
    private JLabel potLabel;
    private JLabel communityCards;

    /**
     * This constructor receives a filepath. Creates a dealer.
     *
     * @param   filePath     Filepath of background image
     */

    public Dealer (String filePath) throws IOException {
        this.potSize = 0;
        this.highestBet = 0;
        this.image = ImageIO.read(new File(filePath));

        setLayout(new GridLayout(3,0));

        addLabelsToTable();
    }

    /**
     * This method adds the JLabels to the table grid.
     */

    public void addLabelsToTable(){
        add(new JLabel());

        // add pot to panel
        this.potLabel = new JLabel("Pot \n" + this.potSize);
        this.potLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
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

    /**
     * This method adds an int to the pot.
     *
     * @param   change    int value to add or subtract from the pot.
     */

    public void addPot(int change){
        this.potSize += change;
        this.potLabel.setText("Pot \n" + potSize);
    }

    /**
     * This method sets the current highest bet on the table.
     *
     * @param   bet    int bet
     */

    public void setHighestBet(int bet){
        this.highestBet = bet;
    }

    /**
     * This method resets the highest bet.
     */

    public void resetHighestBet(){
        this.highestBet = 0;
    }

    /**
     * This method resets the pot label text.
     */

    public void refreshPotText(){
        this.potLabel.setText("Pot \n" + potSize);
    }

    /**
     * This method adds a card to the community cards view.
     * Is not stored.
     *
     *  @param   card    Card
     */

    public void addCommunityCard(Card card){
        String text = this.communityCards.getText();
        this.communityCards.setText(text + " " + card);
    }

    /**
     * This method resets community cards label
     */

    public void resetCommunityCards(){
        this.communityCards.setText("");
    }

    /**
     * This method resets the pot value
     */

    public void resetPot(){
        this.potSize = 0;
    }

    /**
     * This method adds a background to the JPanel
     */

    public void paintComponent(Graphics g) {
        // create background
        g.drawImage(image, 0, 0, null);
    }
}
