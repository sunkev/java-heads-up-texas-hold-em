import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin Sun on 5/1/15.
 *
 * Class that stores two face down cards dealt. Can accommodate additional community cards.
 */

public class Hand extends JPanel{
//    public String[] hand;
    private List<Card> hand;
    private JLabel startingHand;
    private Card card1;
    private Card card2;

    /**
     * Constructor that adds two cards to the hand
     */

    public Hand(Card card1, Card card2){
        this.hand = new ArrayList<Card>();
        this.card1 = card1;
        this.card2 = card2;
        this.hand.add(card1);
        this.hand.add(card2);

        setLayout(new GridBagLayout());
    }

    /**
     * Adds a card to the hand.
     *
     * @param card   The card to add to hand
     * @return    A string array consisting of the cards
     */

    public String[] addCard(Card card){
        this.hand.add(card);
        return currentHand();
    }

    /**
     * Converts the hand to a readable array of card strings (suit and value)
     *
     * * @return    A string array consisting of the cards converted to string
     */

    public String[] currentHand(){
        String[] result = new String[this.hand.size()];

        for (int i = 0; i < this.hand.size(); i++) {
            result[i] = this.hand.get(i).toString();
        }

        return result;
    }


    /**
     *  Adds the two starting cards to the JPanel. Hides the cards if hand is held by the computer.
     *
     * * @return    A string array consisting of the cards converted to string
     */

     public void addStartingCardsToTable(boolean isPlayer){
        // add hand to panel
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

    /**
     * Reveals the hand by adding the starting cards to the JPanel.
     */

    public void revealHand(){
        startingHand.setText(card1 + " " + card2);
    }

    /**
     * A method that returns the hand as an array of cards
     *
     * * @return    The hand as an array of cards
     */

    public Card[] handArray(){
        return hand.toArray(new Card[hand.size()]);
    }
}
