package edu.asu.FourRowSolitaire;

import java.util.LinkedList;

import static org.testng.AssertJUnit.*;
import org.testng.annotations.Test;

/**
 * @author Nelson Tran (nttran9)
 */
public class DeckTest
{
	/**
	 * Test passes if there is exactly one instance of each card in the deck 
	 * after the deck is "shuffled".
	 */
	@Test
	public void testShuffle_VerifyCardSetAfterShuffle()
	{
		Deck deck = new Deck(0);
		deck.shuffle();
		
		int[] cardCount = new int[52];
		LinkedList<Card> cards = deck.getDeck();
		
		for (Card card : cards)
			++cardCount[card.getFullNumber() - 1];
		
		for (int count : cardCount)
			assertEquals(1, count);
	}
	
	/**
	 * Tests passes if no invalid cards exist in the deck after it is 
	 * "shuffled".
	 */
	@Test
	public void testShuffle_NoInvalidCardsAfterShuffle()
	{
		Deck deck = new Deck(0);
		deck.shuffle();
		
		LinkedList<Card> cards = deck.getDeck();
		
		for (Card card : cards)
			assertFalse(card.getNumber() == -1);
	}
}
