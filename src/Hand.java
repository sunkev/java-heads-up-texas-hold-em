import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by brookeside on 5/1/15.
 */
public class Hand extends JPanel{
//    public String[] hand;
    private List<Card> hand;
    private JLabel startingHand;
    private Card card1;
    private Card card2;
    private Boolean isPlayer;

    public Hand(Card card1, Card card2, Boolean isPlayer){
        this.hand = new ArrayList<Card>();
        this.card1 = card1;
        this.card2 = card2;
        this.hand.add(card1);
        this.hand.add(card2);
        this.isPlayer = isPlayer;

        setLayout(new GridBagLayout());
        addStartingCardsToTable();
    }

    public String[] addCard(Card card){
        this.hand.add(card);
        return currentHand();
    }

    public String[] currentHand(){
        String[] result = new String[this.hand.size()];

        for (int i = 0; i < this.hand.size(); i++) {
            result[i] = this.hand.get(i).toString();
        }

        return result;
    }

    public void addStartingCardsToTable(){
        if (isPlayer)
        {
            startingHand = new JLabel(card1 + " " + card2);
        }
        else
        {
            startingHand = new JLabel("Hidden Hand");
        }

        startingHand.setFont(new Font ("Helvetica", Font.BOLD, 20));
        startingHand.setForeground(Color.BLACK);
        add(startingHand);
    }
}
