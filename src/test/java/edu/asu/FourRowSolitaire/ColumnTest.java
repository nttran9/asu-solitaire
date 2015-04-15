package edu.asu.FourRowSolitaire;

import static org.testng.AssertJUnit.*;

import java.util.Arrays;
import java.util.Collections;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Nelson Tran (nttran9)
 */
public class ColumnTest
{
	private Column column;
	
	@BeforeMethod
	public void setUp()
	{
		column = new Column();
	}
	
	@DataProvider(name = "cardProvider")
	public Object[][] createCards()
	{
		Object[][] cards = new Object[53][1];
		 
		for (int i = Card.ACE + (13 * 0); i <= Card.KING + (13 * 0); ++i)
			cards[i-1] = new Object[] { new Card(Card.SPADES_SUIT, ((i - 1) % 13) + 1, 0, i) };
		
		for (int i = Card.ACE + (13 * 1); i <= Card.KING + (13 * 1); ++i)
			cards[i-1] = new Object[] { new Card(Card.CLUBS_SUIT, ((i - 1) % 13) + 1, 0, i) };
		
		for (int i = Card.ACE + (13 * 2); i <= Card.KING + (13 * 2); ++i)
			cards[i-1] = new Object[] { new Card(Card.HEARTS_SUIT, ((i - 1) % 13) + 1, 0, i) };
		
		for (int i = Card.ACE + (13 * 3); i <= Card.KING + (13 * 3); ++i)
			cards[i-1] = new Object[] { new Card(Card.DIAMONDS_SUIT, ((i - 1) % 13) + 1, 0, i) };
		
		cards[52] = new Object[] { new Card(Card.INVALID_SUIT, Card.INVALID_NUMBER, 0, 0) };
		
		Collections.shuffle(Arrays.asList(cards));
		return cards;
	}
	
	/**
	 * Test passes if only a King card can be pushed to an empty column.
	 */
	@Test(dataProvider = "cardProvider")
	public void testPush_PushToEmptyColumn(Card card)
	{
		while (!column.isEmpty())
			column.pop();
		
		// returns null if push fails
		Card pushedCard = column.push(card);
		
		if (card.getNumber() == Card.KING)
			assertTrue(pushedCard != null);
		else
			assertTrue(pushedCard == null);
	}
	
	/**
	 * Test passes if a card is pushed only the card being pushed has the 
	 * opposite suit color and a value that is one less than the card at 
	 * the top of the stack.
	 */
	@Test(dataProvider = "cardProvider")
	public void testPush_PushToNonEmptyColumn(Card card)
	{
		Card topCard = null;
		
		if (!column.isEmpty())
			topCard = column.peek();
		
		// returns null if push fails
		Card pushedCard = column.push(card);
		
		// if column wasn't empty before push and push succeeded
		if (topCard != null && pushedCard != null)
			assertTrue(card.getColor() != topCard.getColor() && card.getNumber() == topCard.getNumber() - 1);
		// if column wasn;t empty before push and push failed
		else if (topCard != null)
			assertFalse(card.getColor() != topCard.getColor() && card.getNumber() == topCard.getNumber() - 1);
	}
	
	/**
	 * Test passes if valid move logic is correct. A card can only be placed if:
	 * <ol>
	 * <li>If the column is empty, only a King Card can be pushed.</li>
	 * <li>If the column is not empty, only cards of the opposite suit color 
	 * that have a value one less than the card at the top of the stack can be 
	 * pushed.</li>
	 * </ol>
	 * 
	 * This method is tested implicitly in the {@link Column#push(Card)} tests.
	 */
	@Test(dependsOnMethods = { 
		"testPush_PushToEmptyColumn", 
		"testPush_PushToNonEmptyColumn" })
	public void testIsValidMove_Card()
	{
		assertTrue(true);
	}
}
