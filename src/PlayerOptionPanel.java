import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by brookeside on 5/1/15.
 */
public class PlayerOptionPanel extends JPanel {
    private JButton checkButton = new JButton("Check");
    private JButton betButton = new JButton("Bet");
    private JButton callButton = new JButton("Call");
    private JButton foldButton = new JButton("Fold");
    private JTextField betField = new JTextField(5);
    private JPanel buttons = new JPanel();
    private JPanel betting = new JPanel();

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

    public int betFieldNumber(){
        return Integer.valueOf(betField.getText());
    }
}
