package edu.asu.FourRowSolitaire;

import static org.testng.AssertJUnit.*;

import java.util.LinkedList;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DealDeckTest
{
    private DiscardPile discardOne;
    private DiscardPile discardThree;
    
    private DealDeck deckOne;
    private DealDeck deckThree;
    
    @BeforeMethod
    public void setUp()
    {
        discardOne = new DiscardPile(1);
        discardThree = new DiscardPile(3);
        
        deckOne = new DealDeck(discardOne, 1);
        deckThree = new DealDeck(discardThree, 3);
    }
    
    @Test
    public void testPop()
    {
        LinkedList<Card> cards = new LinkedList<Card>();
        for (int i = 0; i < 3; i++)
            cards.add(getDummyCard());
        
        // pop cards from DealDeck with 3 cards in it
        deckOne.setDeck(cards);
        deckThree.setDeck(cards);
        
        deckOne.pop();
        deckThree.pop();
        
        assertEquals(2, deckOne.length());
        assertEquals(0, deckThree.length());
        
        // reset deck
        setUp();
        
        // pop cards from DealDeck with 2 cards in it
        cards.removeLast();
        deckOne.setDeck(cards);
        deckThree.setDeck(cards);
        
        deckOne.pop();
        deckThree.pop();
        
        assertEquals(1, deckOne.length());
        assertEquals(0, deckThree.length());
        
        // reset deck
        setUp();
        
        // pop cards from DealDeck with 1 card in it
        cards.removeLast();
        deckOne.setDeck(cards);
        deckThree.setDeck(cards);
        
        deckOne.pop();
        deckThree.pop();
        
        assertEquals(0, deckOne.length());
        assertEquals(0, deckThree.length());
    }
    
    @Test
    public void testUndoPop()
    {
        LinkedList<Card> cards = new LinkedList<Card>();
        cards.add(new Card(CardSuit.CLUBS, CardNumber.ACE, 0, 0));
        cards.add(new Card(CardSuit.SPADES, CardNumber.ACE, 0, 0));
        cards.add(new Card(CardSuit.DIAMONDS, CardNumber.ACE, 0, 0));
        
        deckOne.setDeck(cards);
        deckThree.setDeck(cards);
        
        deckOne.undoPop();
        deckThree.undoPop();
        
        assertEquals(0, deckOne.length());
        assertEquals(0, deckThree.length());
        assertEquals(3, discardOne.length());
        assertEquals(3, discardThree.length());
    }
    
    @Test
    public void testIsValidMove()
    {
        // pushing a single Card
        assertFalse(deckOne.isValidMove(getDummyCard()));
        assertFalse(deckThree.isValidMove(getDummyCard()));
        
        // pushing a CardStack
        CardStack stack = new CardStack();
        stack.push(new Card(CardSuit.CLUBS, CardNumber.ACE, 0, 0));
        stack.push(new Card(CardSuit.SPADES, CardNumber.ACE, 0, 0));
        stack.push(new Card(CardSuit.DIAMONDS, CardNumber.ACE, 0, 0));
        
        assertFalse(deckOne.isValidMove(stack));
        assertFalse(deckThree.isValidMove(stack));
    }
    
    private Card getDummyCard()
    {
        return new Card(CardSuit.INVALID, CardNumber.INVALID, 1, 0);
    }
}
