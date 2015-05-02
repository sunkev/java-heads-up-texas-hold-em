import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by brookeside on 5/1/15.
 */
public class PlayerOptionPanel extends JPanel {
    JButton checkButton = new JButton("Check");
    JButton betButton = new JButton("Bet");
    JButton callButton = new JButton("Call");
    JButton foldButton = new JButton("Fold");
    JTextField betField = new JTextField(5);
    JPanel buttons = new JPanel();
    JPanel betting = new JPanel();

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


        betting.setLayout(new GridLayout(0,2));
        betting.add(betField);
        betting.add(betButton);

        add(buttons, BorderLayout.NORTH);
        add(new JSeparator(), BorderLayout.CENTER);
        add(betting, BorderLayout.SOUTH);
    }
}
