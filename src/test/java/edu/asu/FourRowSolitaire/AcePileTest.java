package test;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;

import FourRowSolitaire.AcePile;
import FourRowSolitaire.Card;
import FourRowSolitaire.CardStack;

public class AcePileTest {
	private AcePile pile1;
	private Card card1, card2, card3, card4;
	private CardStack stack;

	@BeforeMethod
	public void setUp() throws Exception {
		pile1 = new AcePile(Card.HEARTS_SUIT);
		stack = new CardStack();
		
		card1 = new Card(Card.HEARTS_SUIT, Card.ACE, 1, 3);
		card2 = new Card(Card.HEARTS_SUIT, Card.EIGHT, 2, 5);
		card3 = new Card(Card.HEARTS_SUIT, Card.JACK, 3, 6);
		card4 = new Card(Card.HEARTS_SUIT, Card.FIVE, 8, 43);
		
		stack.addCard(card1);
		stack.addCard(card2);
		stack.addCard(card3);
	}

	@AfterMethod
	public void tearDown() throws Exception {
		pile1 = null;
		stack = null;
		
		card1 = null;
		card2 = null;
		card3 = null;
		card4 = null;
	}

	@Test
	public void testPushCard() {
		pile1.push(card4);
		
		AssertJUnit.assertEquals(card4, pile1.pop());
		
	}

	@Test
	public void testIsValidMoveCard() {
		pile1.push(stack);
		
		AssertJUnit.assertTrue(pile1.isValidMove(card2));
	}

	@Test
	public void testIsValidMoveCardStack() {
		AssertJUnit.assertTrue(pile1.isValidMove(stack));
	}

}
