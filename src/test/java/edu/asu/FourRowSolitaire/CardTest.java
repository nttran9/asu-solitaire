package edu.asu.FourRowSolitaire;

import static org.testng.AssertJUnit.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author David Henderson (dchende2)
 */
public class CardTest
{
	@DataProvider(name = "cardProvider")
	public Object[][] createCards()
	{
		Object[][] cards = new Object[53][1];

		for (int i = Card.ACE + (13 * 0); i <= Card.KING + (13 * 0); ++i)
			cards[i-1] = new Object[] { new Card  (Card.SPADES_SUIT, ((i-1) % 13) + 1, 0, i) };
		
		for (int i = Card.ACE + (13 * 1); i <= Card.KING + (13 * 1); ++i)
			cards[i-1] = new Object[] { new Card(   Card.CLUBS_SUIT, ((i-1) % 13) + 1, 0, i) };
		
		for (int i = Card.ACE + (13 * 2); i <= Card.KING + (13 * 2); ++i)
			cards[i-1] = new Object[] { new Card(  Card.HEARTS_SUIT, ((i-1) % 13) + 1, 0, i) };
		
		for (int i = Card.ACE + (13 * 3); i <= Card.KING + (13 * 3); ++i)
			cards[i-1] = new Object[] { new Card(Card.DIAMONDS_SUIT, ((i-1) % 13) + 1, 0, i) };
		
		cards[52] = new Object[] { new Card(Card.INVALID_SUIT, Card.INVALID_NUMBER, 0, 0) };

		//Collections.shuffle(Arrays.asList(cards));
		
		return cards;
	}
	
	@Test(dataProvider = "cardProvider")
	public void testHighlight_HighlightUnhighlighted(Card card)
	{
		if(card.getSuit() == Card.INVALID_SUIT || card.getNumber() == Card.INVALID_NUMBER)
			assertTrue(true);
		else {
			card.highlight();
			assertTrue(card.isHighlighted());
		}
	}
	
	@Test(dataProvider = "cardProvider")
	public void testUnhighlight_UnhighlightHighlighted(Card card)
	{
		if(card.getSuit() == Card.INVALID_SUIT || card.getNumber() == Card.INVALID_NUMBER)
			assertTrue(true);
		else {
			card.highlight();
			card.unhighlight();
			assertFalse(card.isHighlighted());
		}
	}
	
	@Test(dependsOnMethods = { 
			"testHighlight_HighlightUnhighlighted", 
			"testUnhighlight_UnhighlightHighlighted" })
	public void test_isHighlighted()
	{
		assertTrue(true);
	}
	
	@Test(dataProvider = "cardProvider")
	public void testSetFaceUp_FaceUp(Card card)
	{
		if(card.getSuit() == Card.INVALID_SUIT || card.getNumber() == Card.INVALID_NUMBER)
			assertTrue(true);
		else {
			card.setFaceUp();
			assertTrue(card.isFaceUp());
		}
	}
	
	@Test(dataProvider = "cardProvider")
	public void testSetFaceDown_FaceDown(Card card)
	{
		if(card.getSuit() == Card.INVALID_SUIT || card.getNumber() == Card.INVALID_NUMBER)
			assertTrue(true);
		else {
			card.setFaceUp();
			card.setFaceDown();
			assertFalse(card.isFaceUp());
		}
	}
	
	@Test(dependsOnMethods = { 
			"testSetFaceUp_FaceUp", 
			"testSetFaceDown_FaceDown" })
	public void test_isFaceUp()
	{
		assertTrue(true);
	}
	
	@Test(dataProvider = "cardProvider")
	public void testIsValidSuit_ValidSuit(Card card)
	{
		if(card.getSuit() == Card.INVALID_SUIT)
			assertFalse(card.isValidSuit(card.getSuit()));
		else {
			assertTrue(card.isValidSuit(card.getSuit()));
		}
	}
	
	@Test(dataProvider = "cardProvider")
	public void testClone_Clone(Card card)
	{
		if(card.getSuit() == Card.INVALID_SUIT || card.getNumber() == Card.INVALID_NUMBER)
			assertTrue(true);
		else {
			Card cardNew = card.clone();
			assertTrue(card.getSuit() == cardNew.getSuit() && card.getNumber() == cardNew.getNumber() && card.getFullNumber() == cardNew.getFullNumber());
		}
	}
}