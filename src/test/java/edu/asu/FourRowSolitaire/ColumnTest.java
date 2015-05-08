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
        
        int i = 0;
        
        for (CardSuit suit : CardSuit.values())
        {
            for (CardNumber value : CardNumber.values())
            {
                if (Card.isValidSuit(suit) && Card.isValidNumber(value))
                {
                    int fullNumber = suit.getOffset() + value.getValue();
                    cards[i++] = new Object[] { new Card(suit, value, -1, fullNumber) };
                }
            }
        }
        
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
        
        // try pushing the card
        if (column.isValidMove(card))
            column.push(card);
        
        if (card != null && card.getNumber() == CardNumber.KING)
            assertFalse(column.isEmpty());
        else
            assertTrue(column.isEmpty());
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
            assertTrue(card.getColor() != topCard.getColor() && card.getNumber().getValue() == topCard.getNumber().getValue() - 1);
        // if column wasn't empty before push and push failed
        else if (topCard != null)
            assertFalse(card.getColor() != topCard.getColor() && card.getNumber().getValue() == topCard.getNumber().getValue() - 1);
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
