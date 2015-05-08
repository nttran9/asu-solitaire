package edu.asu.FourRowSolitaire;

import static org.testng.AssertJUnit.*;

import java.util.Arrays;
import java.util.Collections;

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
        Object[][] cards = new Object[52][1];
        
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
    
    @Test(dataProvider = "cardProvider")
    public void testHighlight_HighlightUnhighlighted(Card card)
    {
        if (card.getSuit() == CardSuit.INVALID || card.getNumber() == CardNumber.INVALID)
            assertTrue(true);
        else {
            card.highlight();
            assertTrue(card.isHighlighted());
        }
    }
    
    @Test(dataProvider = "cardProvider")
    public void testUnhighlight_UnhighlightHighlighted(Card card)
    {
        if (card.getSuit() == CardSuit.INVALID || card.getNumber() == CardNumber.INVALID)
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
        if (card.getSuit() == CardSuit.INVALID || card.getNumber() == CardNumber.INVALID)
            assertTrue(true);
        else {
            card.setFaceUp();
            assertTrue(card.isFaceUp());
        }
    }
    
    @Test(dataProvider = "cardProvider")
    public void testSetFaceDown_FaceDown(Card card)
    {
        if(card.getSuit() == CardSuit.INVALID || card.getNumber() == CardNumber.INVALID)
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
        if (card.getSuit() == CardSuit.INVALID)
            assertFalse(Card.isValidSuit(card.getSuit()));
        else {
            assertTrue(Card.isValidSuit(card.getSuit()));
        }
    }
    
    @Test(dataProvider = "cardProvider")
    public void testClone_Clone(Card card)
    {
        if (card.getSuit() == CardSuit.INVALID || card.getNumber() == CardNumber.INVALID)
            assertTrue(true);
        else {
            Card cardNew = card.clone();
            assertTrue(card.getSuit() == cardNew.getSuit() && card.getNumber() == cardNew.getNumber() && card.getFullNumber() == cardNew.getFullNumber());
        }
    }
}