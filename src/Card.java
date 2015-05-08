/**
 * Created by Kevin Sun on 5/1/15.
 *
 * An instance of this class has knowledge of its suit and value.
 */

public class Card {
    public String suit;
    public String value;

    /**
     * This constructor receives a suit and value
     *
     * @param   suit     The suit received
     * @param   value    The value received
     */

    public Card(String suit, String value){
        this.suit =  suit;
        this.value =  value;
    }

    /**
     * Returns the card suit and value concatenated
     *
     * @return   suit and value concatenated
     */
    public String toString(){
        return suit + " " + value;
    }
}
