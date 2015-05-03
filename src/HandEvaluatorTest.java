/**
 * Created by brookeside on 5/3/15.
 */
public class HandEvaluatorTest
{
    public static void main(String[] args) {

        Hand nothing = new Hand(new Card("♠", "2"), new Card("♠", "3"), true);
        System.out.println("Nothing " + new HandEvaluator(nothing).calculate());

        Hand onePair = new Hand(new Card("♠", "2"), new Card("♥", "2"), true);
        System.out.println("One pair " + new HandEvaluator(onePair).calculate());

        Hand twoPair = new Hand(new Card("♠", "2"), new Card("♥", "2"), true);
        twoPair.addCard(new Card("♠", "3"));
        twoPair.addCard(new Card("♥", "3"));
        System.out.println("Two pair " + new HandEvaluator(twoPair).calculate());

        Hand threeOfAKind = new Hand(new Card("♠", "2"), new Card("♥", "2"), true);
        threeOfAKind.addCard(new Card("♦", "2"));
        System.out.println("Three of a kind " + new HandEvaluator(threeOfAKind).calculate());

        Hand straight = new Hand(new Card("♠", "2"), new Card("♥", "3"), true);
        straight.addCard(new Card("♦", "4"));
        straight.addCard(new Card("♦", "5"));
        straight.addCard(new Card("♦", "6"));
        System.out.println("Straight " + new HandEvaluator(straight).calculate());

        Hand flush = new Hand(new Card("♦", "A"), new Card("♦", "3"), true);
        flush.addCard(new Card("♦", "4"));
        flush.addCard(new Card("♦", "9"));
        flush.addCard(new Card("♦", "6"));
        System.out.println("Flush " + new HandEvaluator(flush).calculate());

        Hand fullhouse = new Hand(new Card("♠", "2"), new Card("♦", "2"), true);
        fullhouse.addCard(new Card("♠", "3"));
        fullhouse.addCard(new Card("♦", "3"));
        fullhouse.addCard(new Card("♥", "3"));
        System.out.println("Full house " + new HandEvaluator(fullhouse).calculate());

        Hand straightFlush = new Hand(new Card("♦", "2"), new Card("♦", "3"), true);
        straightFlush.addCard(new Card("♦", "4"));
        straightFlush.addCard(new Card("♦", "5"));
        straightFlush.addCard(new Card("♦", "6"));
        System.out.println("Straight flush " + new HandEvaluator(straightFlush).calculate());

        Hand fourOfAKind = new Hand(new Card("♠", "2"), new Card("♦", "2"), true);
        fourOfAKind.addCard(new Card("♥", "2"));
        fourOfAKind.addCard(new Card("♣", "2"));
        System.out.println("Four of a kind " + new HandEvaluator(fourOfAKind).calculate());

        Hand royalFlush = new Hand(new Card("♦", "A"), new Card("♦", "K"), true);
        royalFlush.addCard(new Card("♦", "Q"));
        royalFlush.addCard(new Card("♦", "J"));
        royalFlush.addCard(new Card("♦", "10"));
        System.out.println("Royal flush! " + new HandEvaluator(royalFlush).calculate());


    }


}
