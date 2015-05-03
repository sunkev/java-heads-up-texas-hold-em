import java.util.*;

/**
 * Created by brookeside on 5/2/15.
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

    public HandEvaluator(Hand hand){
        this.cards = hand.handArray();
        this.values = countValues();
        this.suits = countSuits();
    }

    public Enum calculate(){
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

    public Map<String, Integer> countValues(){
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

    public boolean isPair(){
        return (this.values.containsValue(2));
    }

    public boolean isTwoPair(){
        int count = 0;
        for (Map.Entry<String, Integer> entry: values.entrySet()){
            if (entry.getValue() == 2){
                count++;
            }
        }

        return (count == 2);
    }

    public boolean isThreeOfAKind(){
        return (this.values.containsValue(3));
    }

    public boolean isStraight(){
        // see if each value can be used as a starting point for a straight
        for (Map.Entry<String, Integer> entry: values.entrySet()){
            if (recursiveFindStraight(entry.getKey(), 0)) return true;
        }

        return false;
    }

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

    public boolean isFlush(){
        return (this.suits.containsValue(5));
    }

    public boolean isFullHouse(){
        return (this.values.containsValue(2) && this.values.containsValue(3));
    }

    public boolean isStraightFlush(){
        return(isFlush() && isStraight());
    }

    public boolean isFourOfAKind(){
        return(this.values.containsValue(4));
    }

    public boolean isRoyalFlush(){
        return isStraightFlush() && this.values.containsKey("A") && this.values.containsKey("K") &&
                this.values.containsKey("Q") && this.values.containsKey("J") && this.values.containsKey("10");
    }
}
