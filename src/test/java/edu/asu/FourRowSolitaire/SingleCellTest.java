package edu.asu.FourRowSolitaire;

import static org.testng.AssertJUnit.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SingleCellTest
{
    private SingleCell cell;
    
    @BeforeMethod
    public void setUp()
    {
        cell = new SingleCell();
    }
    
    @Test
    public void testIsValidMove()
    {
        // push a single card onto empty cell
        if (cell.isValidMove(getDummyCard()))
            cell.push(getDummyCard());
        assertEquals(1, cell.length());
        
        // push a single card onto non-empty cell
        if (cell.isValidMove(getDummyCard()))
            cell.push(getDummyCard());
        assertEquals(1, cell.length());
        
        // push a stack onto non-empty cell
        CardStack stack = new CardStack();
        stack.push(getDummyCard());
        stack.push(getDummyCard());
        stack.push(getDummyCard());
        
        if (cell.isValidMove(stack))
            cell.push(stack);
        assertEquals(1, cell.length());
        
        // push a stack onto empty cell
        cell.pop();
        if (cell.isValidMove(stack))
            cell.push(stack);
        assertEquals(0, cell.length());
    }
    
    private Card getDummyCard()
    {
        return new Card(CardSuit.INVALID, CardNumber.INVALID, 0, 0);
    }
}
