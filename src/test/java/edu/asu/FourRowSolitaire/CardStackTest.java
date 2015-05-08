package edu.asu.FourRowSolitaire;

import static org.testng.AssertJUnit.*;

import java.util.EmptyStackException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Nelson Tran (nttran9)
 * @author Stephen Garcia
 */
public class CardStackTest
{
    private CardStack stack;
    
    @BeforeMethod
    public void setUp()
    {
        stack = new CardStack();
    }
    
    @Test
    public void testGetStack()
    {
        // add cards to stack
        stack.push(new Card(CardSuit.CLUBS, CardNumber.FOUR, 0, 0));
        stack.push(new Card(CardSuit.HEARTS, CardNumber.THREE, 0, 0));
        stack.push(new Card(CardSuit.CLUBS, CardNumber.TWO, 0, 0));
        stack.push(new Card(CardSuit.HEARTS, CardNumber.ACE, 0, 0));
        
        // get sub-stack containing invalid card
        CardStack temp = stack.getStack(getDummyCard());
        assertTrue(temp.isEmpty());
        
        // get sub-stack containing valid cards and test size and correct order
        temp = stack.getStack(stack.getCardAtLocation(0));
        assertEquals(4, temp.length());
        assertEquals(CardNumber.FOUR, temp.pop().getNumber());
        assertEquals(CardNumber.THREE, temp.pop().getNumber());
        
        temp = stack.getStack(4);
        assertEquals(4, temp.length());
        assertEquals(CardNumber.FOUR, temp.pop().getNumber());
        assertEquals(CardNumber.THREE, temp.pop().getNumber());
    }
    
    @Test
    public void testIsEmpty()
    {
        // empty stack
        assertTrue(stack.isEmpty());
        
        // stack of size 1
        stack.push(getDummyCard());
        assertFalse(stack.isEmpty());
        
        // empty stack after removing an element
        stack.pop();
        assertTrue(stack.isEmpty());
    }
    
    @Test
    public void testPeek()
    {
        // peeking into empty stack should return null
        assertEquals(null, stack.peek());
        
        // add element and peek
        stack.push(new Card(CardSuit.CLUBS, CardNumber.ACE, 0, 0));
        assertEquals(CardNumber.ACE, stack.peek().getNumber());
        assertEquals(CardSuit.CLUBS, stack.peek().getSuit());
        
        // add another element and peek
        stack.push(new Card(CardSuit.HEARTS, CardNumber.SEVEN, 0, 0));
        assertEquals(CardNumber.SEVEN, stack.peek().getNumber());
        assertEquals(CardSuit.HEARTS, stack.peek().getSuit());
    }
    
    @Test(expectedExceptions = EmptyStackException.class)
    public void testSize()
    {
        // empty stack, size = 0
        assertEquals(0, stack.length());
        
        // stack of size 1 (push)
        stack.push(getDummyCard());
        assertEquals(1, stack.length());
        
        // stack of size 2 (push)
        stack.push(getDummyCard());
        assertEquals(2, stack.length());
        
        // stack of size 1 (pop)
        stack.pop();
        assertEquals(1, stack.length());
        
        // stack of size 0 (pop)
        stack.pop();
        assertEquals(0, stack.length());
        
        // still stack of size 0 (pop empty stack)
        stack.pop();
        assertEquals(0, stack.length());
    }
    
    @Test
    public void testIsDifferentColor()
    {
        Card a = new Card(CardSuit.CLUBS, CardNumber.ACE, 0, 0);
        Card b = new Card(CardSuit.SPADES, CardNumber.ACE, 0, 0);
        Card c = new Card(CardSuit.HEARTS, CardNumber.ACE, 0, 0);
        
        assertFalse(stack.isDifferentColor(a, b));
        assertTrue(stack.isDifferentColor(a, c));
    }
    
    @Test
    public void testIsSameSuit()
    {
        Card a = new Card(CardSuit.CLUBS, CardNumber.ACE, 0, 0);
        Card b = new Card(CardSuit.SPADES, CardNumber.ACE, 0, 0);
        Card c = new Card(CardSuit.HEARTS, CardNumber.ACE, 0, 0);
        Card d = new Card(CardSuit.CLUBS, CardNumber.TWO, 0, 0);
        
        assertFalse(stack.isSameSuit(a, b));
        assertFalse(stack.isSameSuit(a, c));
        assertTrue(stack.isSameSuit(a, d));
    }
    
    @Test
    public void testIsOneLess()
    {
        Card a = new Card(CardSuit.CLUBS, CardNumber.ACE, 0, 0);
        Card b = new Card(CardSuit.CLUBS, CardNumber.TWO, 0, 0);
        Card c = new Card(CardSuit.CLUBS, CardNumber.THREE, 0, 0);
        
        assertTrue(stack.isOneLess(a, b));
        assertTrue(stack.isOneLess(b, c));
        assertFalse(stack.isOneLess(b, a));
        assertFalse(stack.isOneLess(c, b));
        assertFalse(stack.isOneLess(a, c));
    }
    
    private Card getDummyCard()
    {
        return new Card(CardSuit.INVALID, CardNumber.INVALID, 0, 0);
    }
}