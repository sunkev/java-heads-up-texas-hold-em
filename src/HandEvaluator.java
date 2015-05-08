import java.util.*;

/**
 * Created by Kevin Sun on 5/1/15.
 *
 * Takes a hand as an argument, and calculates the strength of the hand.
 * Determines whether the hand has a straight, full house, royal flush, etc.
 */

public class HandEvaluator {
    private Card[] cards;
    private List<String> deckValues = Arrays.asList(Deck.VALUES);
    private Map<String, Integer> values;
    private Map<String, Integer> suits;

    public enum RESULTS {
        ONE_OF_A_KIND, PAIR, TWO_PAIR, THREE_OF_A_KIND,
        STRAIGHT, FLUSH, FULLHOUSE, STRAIGHT_FLUSH, FOUR_OF_A_KIND,
        ROYAL_FLUSH
    }

    /**
     * Constructor that sets sets the hand and calls methods that determine
     * the frequency of suits and values
     *
     * @param hand  An instance of the Hand class
     */

    public HandEvaluator(Hand hand){
        this.cards = hand.handArray();
        this.values = countValues();
        this.suits = countSuits();
    }

    /**
     * A method that returns what the category the hand falls under
     *
     * @return   An enum of the evaluated hand
     */

    public Enum calculateHand(){
        if(isRoyalFlush())
        {
            return RESULTS.ROYAL_FLUSH;
        }
        else if(isFourOfAKind())
        {
            return RESULTS.FOUR_OF_A_KIND;
        }
        else if(isStraightFlush())
        {
            return RESULTS.STRAIGHT_FLUSH;
        }
        else if(isFullHouse())
        {
            return RESULTS.FULLHOUSE;
        }
        else if(isFlush())
        {
            return RESULTS.FLUSH;
        }
        else if(isStraight())
        {
            return RESULTS.STRAIGHT;
        }
        else if(isThreeOfAKind())
        {
            return RESULTS.THREE_OF_A_KIND;
        }
        else if(isTwoPair())
        {
            return RESULTS.TWO_PAIR;
        }
        else if(isPair())
        {
            return RESULTS.PAIR;
        }
        else
        {
            return RESULTS.ONE_OF_A_KIND;
        }

    }

    /**
     * A method that returns the strength of hand as integer based off index in array
     *
     * @return   int of how strong that hand is
     */

    public int rankOfHand(){
        return RESULTS.valueOf(calculateHand().toString()).ordinal();
    }

    /**
     * A method that creates hashmap of suits to their frequency
     *
     * @return   Hash of suit to frequency
     */

    public Map<String, Integer> countSuits(){

        Map<String, Integer> hash = new HashMap<String, Integer>();

        for (Card c: cards){
            if(hash.containsKey(c.suit))
            {
                hash.put(c.suit, (hash.get(c.suit) + 1));
            }
            else
            {
                hash.put(c.suit, 1);
            }
        }
        return hash;
    }

    /**
     * A method that creates hashmap of card value to their frequency
     *
     * @return   Hash of value to frequency
     */

    public Map<String, Integer> countValues(){
        // creates hashmap of values and their frequency

        Map<String, Integer> hash = new HashMap<String, Integer>();

        for (Card c: cards){
            if(hash.containsKey(c.value))
            {
                hash.put(c.value, (hash.get(c.value) + 1));
            }
            else
            {
                hash.put(c.value, 1);
            }
        }
        return hash;
    }

    /**
     * A method that determines if the hand is a pair
     *
     * @return   True or false boolean
     */

    public boolean isPair(){
        return (this.values.containsValue(2));
    }

    /**
     * A method that determines if the hand is a two pair
     *
     * @return   True or false boolean
     */

    public boolean isTwoPair(){
        int count = 0;
        for (Map.Entry<String, Integer> entry: values.entrySet()){
            if (entry.getValue() == 2){
                count++;
            }
        }

        return (count == 2);
    }

    /**
     * A method that determines if the hand is a three of a kind
     *
     * @return   True or false boolean
     */

    public boolean isThreeOfAKind(){
        return (this.values.containsValue(3));
    }

    /**
     * A method that determines if the hand is a straight
     *
     * @return   True or false boolean
     */

    public boolean isStraight(){
        // see if each value can be used as a starting point for a straight
        for (Map.Entry<String, Integer> entry: values.entrySet()){
            if (recursiveFindStraight(entry.getKey(), 0)) return true;
        }

        return false;
    }

    /**
     * A method that calculates whether the next part of straight exists in the hand
     *
     * @return   True or false boolean
     */

    public boolean recursiveFindStraight(String value, int count){
        int index = deckValues.indexOf(value);
        int nextIndex = index;

        // if ace, start from bottom of loop again
        if (index == 12) {
            nextIndex = 0;
        } else{
            nextIndex++;
        }

        String nextValue = deckValues.get(nextIndex);

        if (count == 4){
            return true;
        }
        else if(this.values.containsKey(nextValue)){
            return recursiveFindStraight(nextValue, count+1);
        } else {
            return false;
        }
    }

    /**
     * A method that determines if the hand is a flush
     *
     * @return   True or false boolean
     */

    public boolean isFlush(){
        return (this.suits.containsValue(5));
    }

    /**
     * A method that determines if the hand is a fullhouse
     *
     * @return   True or false boolean
     */

    public boolean isFullHouse(){
        return (this.values.containsValue(2) && this.values.containsValue(3));
    }

    /**
     * A method that determines if the hand is a straight flush
     *
     * @return   True or false boolean
     */

    public boolean isStraightFlush(){
        return(isFlush() && isStraight());
    }

    /**
     * A method that determines if the hand is a four of a kind
     *
     * @return   True or false boolean
     */

    public boolean isFourOfAKind(){
        return(this.values.containsValue(4));
    }

    /**
     * A method that determines if the hand is a royal flush
     *
     * @return   True or false boolean
     */

    public boolean isRoyalFlush(){
        return isStraightFlush() && this.values.containsKey("A") && this.values.containsKey("K") &&
                this.values.containsKey("Q") && this.values.containsKey("J") && this.values.containsKey("10");
    }
}
