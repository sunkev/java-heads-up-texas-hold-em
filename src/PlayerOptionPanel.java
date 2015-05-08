import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Kevin Sun on 5/1/15.
 *
 * A JPanel that contains all the buttons the player can use.
 */

public class PlayerOptionPanel extends JPanel {
    private JButton checkButton = new JButton("Check");
    private JButton betButton = new JButton("Bet");
    private JButton callButton = new JButton("Call");
    private JButton foldButton = new JButton("Fold");
    private JTextField betField = new JTextField(5);
    private JPanel buttons = new JPanel();
    private JPanel betting = new JPanel();

    /**
     * This constructor adds the actionlistener to all
     * buttons. And then adds the button to the GUI
     */

    public PlayerOptionPanel(ActionListener buttonListener){
        checkButton.addActionListener(buttonListener);
        foldButton.addActionListener(buttonListener);
        callButton.addActionListener(buttonListener);
        betButton.addActionListener(buttonListener);

        buttons.setLayout(new GridLayout(2, 2));
        buttons.add(checkButton);
        buttons.add(foldButton);
        buttons.add(callButton);
        buttons.setPreferredSize(new Dimension(125, 175));

        betField.setText("0");
        betting.setLayout(new GridLayout(0,2));
        betting.add(betField);
        betting.add(betButton);

        add(buttons, BorderLayout.NORTH);
        add(new JSeparator(), BorderLayout.CENTER);
        add(betting, BorderLayout.SOUTH);
    }

    /**
     * This method get str value from betField
     * and attempts to convert it to an int, or throws error
     *
     * @return          The int value of betField
     */

    public int betFieldNumber(){
        try {
            return Integer.valueOf(betField.getText());
        }
        catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "You need to enter a integer!");
            System.exit(1);
            return 0;
        }
    }
}
