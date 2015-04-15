package test;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import FourRowSolitaire.Card;
import FourRowSolitaire.CardStack;

public class CardStackTest {
	private Card card1, card2, card3, card4, card5, card6;
	private CardStack stack1, stack2, stack3;

	@BeforeMethod
	public void setUp() throws Exception {
		card1 = new Card(Card.HEARTS_SUIT, Card.ACE, 1, 3);
		card2 = new Card(Card.DIAMONDS_SUIT, Card.EIGHT, 2, 5);
		card3 = new Card(Card.SPADES_SUIT, Card.JACK, 3, 6);
		card4 = new Card(Card.CLUBS_SUIT, Card.FIVE, 8, 43);
		card5 = new Card(Card.HEARTS_SUIT, Card.SEVEN, 10, 3);
		card6 = new Card(Card.DIAMONDS_SUIT, Card.KING, 7, 7);
		
		stack1 = new CardStack();
		stack2 = new CardStack();
		stack3 = new CardStack();
		
		stack1.addCard(card1);
		stack1.addCard(card2);
		stack1.addCard(card3);
		
		stack2.addCard(card4);
		stack2.addCard(card5);
		stack2.addCard(card6);
		
	}

	@AfterMethod
	public void tearDown() throws Exception {
		card1 = null;
		card2 = null;
		card3 = null;
		card4 = null;
		card5 = null;
		card6 = null;
		
		stack1 = null;
		stack2 = null;
		stack3 = null;
	}

	@Test
	public void testAddStack() {
		stack3.addStack(stack1);
		
		AssertJUnit.assertEquals(stack1, stack3.getAvailableCards());
	}

	@Test
	public void testPop() {
		AssertJUnit.assertEquals(card1, stack1.pop());
		AssertJUnit.assertEquals(card4, stack2.pop());
	}

	@Test
	public void testSearch() {
		AssertJUnit.assertEquals(0, stack1.search(card1));
		AssertJUnit.assertEquals(2, stack2.search(card6));
	}

	@Test
	public void testGetCardAtLocationInt() {
		AssertJUnit.assertEquals(card3, stack1.getCardAtLocation(2));
		AssertJUnit.assertEquals(card5, stack2.getCardAtLocation(1));
	}

	@Test
	public void testGetStackCard() {
		AssertJUnit.assertEquals(stack1 ,stack1.getStack(card1));
		AssertJUnit.assertEquals(stack2 ,stack2.getStack(card4));
	}

	@Test
	public void testGetStackInt() {
		AssertJUnit.assertEquals(stack1 ,stack1.getStack(stack1.length()));
		AssertJUnit.assertEquals(stack2 ,stack2.getStack(stack2.length()));
	}

	@Test
	public void testGetAvailableCards() {
		AssertJUnit.assertEquals(stack1 ,stack1.getAvailableCards());
		AssertJUnit.assertEquals(stack2 ,stack2.getAvailableCards());
	}

}
