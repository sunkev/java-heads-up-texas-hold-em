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

    public Player(String name, String filePath) throws IOException {
        this.name = name;
        this.bankroll = 1500;
        this.image = ImageIO.read(new File(filePath));

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
        bankrollLabel = new JLabel(name + " " + bankroll+"");
        bankrollLabel.setFont(new Font ("Helvetica", Font.BOLD, 20));
        bankrollLabel.setForeground(Color.ORANGE);
        add(bankrollLabel);
    }

    public void paintComponent(Graphics g) {
        // create background
        g.drawImage(image, 0, 0, null);
    }

    public boolean bankrupt(){
        return (bankroll == 0);
    }

    public void setHand(Hand hand){
        this.hand = hand;
    }
}
