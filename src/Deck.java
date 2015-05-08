import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Kevin Sun on 5/1/15.
 *
 * Creates many cards. It shuffles the cards and is able to remove and return a card from the top of the deck.
 */

public class Deck {
    private static final String[] SUITS = {"♠", "♣", "♥", "♦"};
    public static final String[] VALUES = {"2", "3", "4", "5", "6",
            "7", "8", "9", "10", "J", "Q", "K", "A"};
    private Card[] deck;

    /**
     * This constructor creates a deck consisting of cards of different suit and value combinations.
     * Also shuffles the deck.
     */

    public Deck(){
        List<Card> deckList = new ArrayList<Card>();

        // create cards and add to deck
        for(String suit: SUITS){
            for(String value: VALUES){
                deckList.add(new Card(suit, value));
            }
        }

        // shuffle deck
        Collections.shuffle(deckList);
        this.deck = deckList.toArray(new Card[deckList.size()]);
    }

    /**
     * This method removes a card from deck and returns it
     *
     * @return     A random card removed from the deck
     */

    public Card pop(){

        Card firstCard = this.deck[0];
        Card[] newDeck = new Card[this.deck.length-1];

        System.arraycopy(this.deck, 1, newDeck, 0, newDeck.length);

        this.deck = newDeck;
        return firstCard;
    }
}
