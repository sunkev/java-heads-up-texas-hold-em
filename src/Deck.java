import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by brookeside on 4/30/15.
 */
public class Deck {
    private final String[] SUITS = {"♠", "♣", "♥", "♦"};
    private final String[] VALUES = {"2", "3", "4", "5", "6",
            "7", "8", "9", "10", "J", "Q", "K", "A"};
    private Card[] deck;

    public Deck(){
        List<Card> deckList = new ArrayList<Card>();

        for(String suit: SUITS){
            for(String value: VALUES){
                deckList.add(new Card(suit, value));
            }
        }

        Collections.shuffle(deckList);
        this.deck = deckList.toArray(new Card[deckList.size()]);
    }

    public Card pop(){
        Card firstCard = this.deck[0];
        Card[] newDeck = new Card[this.deck.length-1];

        System.arraycopy(this.deck, 1, newDeck, 0, newDeck.length);

        this.deck = newDeck;
        return firstCard;
    }
}
