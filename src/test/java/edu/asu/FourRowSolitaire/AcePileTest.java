package edu.asu.FourRowSolitaire;

import static org.testng.AssertJUnit.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Nelson Tran (nttran9)
 * @author Stephen Garcia
 */
public class AcePileTest
{
    private AcePile pile;
    
    @BeforeMethod
    public void setUp()
    {
        pile = null;
    }
    
    @Test
    public void testIsValidMove()
    {
        pile = new AcePile(CardSuit.CLUBS);
        
        Card cAce   = new Card(CardSuit.CLUBS, CardNumber.ACE, 0, 0);
        Card cTwo   = new Card(CardSuit.CLUBS, CardNumber.TWO, 0, 0);
        Card cThree = new Card(CardSuit.CLUBS, CardNumber.THREE, 0, 0);
        Card hAce   = new Card(CardSuit.HEARTS, CardNumber.ACE, 0, 0);
        
        // wrong suit, right number
        if (pile.isValidMove(hAce))
            pile.push(hAce);
        
        assertTrue(pile.isEmpty());
        
        // right suit, right number
        if (pile.isValidMove(cAce))
            pile.push(cAce);
        
        assertTrue(!pile.isEmpty());
        
        // right suit, wrong number
        if (pile.isValidMove(cThree))
            pile.push(cAce);
        
        assertEquals(CardNumber.ACE, pile.peek().getNumber());
        assertEquals(CardSuit.CLUBS, pile.peek().getSuit());
        
        // wrong suit, wrong number
        if (pile.isValidMove(hAce))
            pile.push(hAce);
        
        assertEquals(CardNumber.ACE, pile.peek().getNumber());
        assertEquals(CardSuit.CLUBS, pile.peek().getSuit());
        
        // right suit, right number
        if (pile.isValidMove(cTwo))
            pile.push(cTwo);
        if (pile.isValidMove(cThree))
            pile.push(cThree);
        
        assertEquals(CardNumber.THREE, pile.pop().getNumber());
        assertEquals(CardNumber.TWO, pile.pop().getNumber());
        
        // pushing a stack
        Card cFour = new Card(CardSuit.CLUBS, CardNumber.ACE, 0, 0);
        Card cFive = new Card(CardSuit.CLUBS, CardNumber.TWO, 0, 0);
        Card cSix  = new Card(CardSuit.CLUBS, CardNumber.THREE, 0, 0);
        
        CardStack stack = new CardStack();
        stack.push(cFour);
        stack.push(cFive);
        stack.push(cSix);
        
        if (pile.isValidMove(stack))
            pile.push(stack);
        
        assertEquals(CardNumber.ACE, pile.peek().getNumber());
    }
    
    @Test
    public void testGetSuit()
    {
        pile = new AcePile(CardSuit.HEARTS);
        
        assertEquals(CardSuit.HEARTS, pile.getSuit());
        assertFalse(CardSuit.CLUBS == pile.getSuit());
        assertFalse(CardSuit.DIAMONDS == pile.getSuit());
        assertFalse(CardSuit.SPADES == pile.getSuit());
    }
}