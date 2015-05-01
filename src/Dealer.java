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

    public Dealer (String filePath) throws IOException {
        potSize = 0;
        image = ImageIO.read(new File(filePath));

        setLayout(new GridBagLayout());


        // add pot to panel
        JLabel potLabel = new JLabel("Pot \n" + potSize + "");
        potLabel.setFont(new Font ("Helvetica", Font.BOLD, 20));
        potLabel.setForeground(Color.ORANGE);
        add(potLabel);
    }

    public void paintComponent(Graphics g) {
        // create background
        g.drawImage(image, 0, 0, null);
    }
}
