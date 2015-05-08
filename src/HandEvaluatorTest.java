/**
 * Created by Kevin Sun on 5/1/15.
 *
 * A test class that tests the hand evaluator.
 */

public class HandEvaluatorTest
{
    public static void main(String[] args) {

        Hand nothing = new Hand(new Card("♠", "2"), new Card("♠", "3"));
        System.out.println("Nothing " + new HandEvaluator(nothing).calculateHand());

        Hand onePair = new Hand(new Card("♠", "2"), new Card("♥", "2"));
        System.out.println("One pair " + new HandEvaluator(onePair).calculateHand());

        Hand twoPair = new Hand(new Card("♠", "2"), new Card("♥", "2"));
        twoPair.addCard(new Card("♠", "3"));
        twoPair.addCard(new Card("♥", "3"));
        System.out.println("Two pair " + new HandEvaluator(twoPair).calculateHand());

        Hand threeOfAKind = new Hand(new Card("♠", "2"), new Card("♥", "2"));
        threeOfAKind.addCard(new Card("♦", "2"));
        System.out.println("Three of a kind " + new HandEvaluator(threeOfAKind).calculateHand());

        Hand straight = new Hand(new Card("♠", "2"), new Card("♥", "3"));
        straight.addCard(new Card("♦", "4"));
        straight.addCard(new Card("♦", "5"));
        straight.addCard(new Card("♦", "6"));
        System.out.println("Straight " + new HandEvaluator(straight).calculateHand());

        Hand flush = new Hand(new Card("♦", "A"), new Card("♦", "3"));
        flush.addCard(new Card("♦", "4"));
        flush.addCard(new Card("♦", "9"));
        flush.addCard(new Card("♦", "6"));
        System.out.println("Flush " + new HandEvaluator(flush).calculateHand());

        Hand fullhouse = new Hand(new Card("♠", "2"), new Card("♦", "2"));
        fullhouse.addCard(new Card("♠", "3"));
        fullhouse.addCard(new Card("♦", "3"));
        fullhouse.addCard(new Card("♥", "3"));
        System.out.println("Full house " + new HandEvaluator(fullhouse).calculateHand());

        Hand straightFlush = new Hand(new Card("♦", "2"), new Card("♦", "3"));
        straightFlush.addCard(new Card("♦", "4"));
        straightFlush.addCard(new Card("♦", "5"));
        straightFlush.addCard(new Card("♦", "6"));
        System.out.println("Straight flush " + new HandEvaluator(straightFlush).calculateHand());

        Hand fourOfAKind = new Hand(new Card("♠", "2"), new Card("♦", "2"));
        fourOfAKind.addCard(new Card("♥", "2"));
        fourOfAKind.addCard(new Card("♣", "2"));
        System.out.println("Four of a kind " + new HandEvaluator(fourOfAKind).calculateHand());

        Hand royalFlush = new Hand(new Card("♦", "A"), new Card("♦", "K"));
        royalFlush.addCard(new Card("♦", "Q"));
        royalFlush.addCard(new Card("♦", "J"));
        royalFlush.addCard(new Card("♦", "10"));
        System.out.println("Royal flush! " + new HandEvaluator(royalFlush).calculateHand());

    }


}
