/**
 * Created by brookeside on 5/1/15.
 */
public class Card {
    public String suit;
    public String value;

    public Card(String suit, String value){
        this.suit =  suit;
        this.value =  value;
    }

    public String toString(){
        return suit + " " + value;
    }
}
