package edu.asu.FourRowSolitaire;

import static org.testng.AssertJUnit.*;

import java.util.HashSet;
import java.util.LinkedList;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Nelson Tran (nttran9)
 */
public class DeckTest
{
    private Deck deck;
    
    @BeforeMethod
    public void setUp()
    {
        deck = new Deck(0);
    }
    
    @Test
    public void testCreateStandardDeck()
    {
        LinkedList<Card> cards = deck.createStandardDeck(0);
        HashSet<Card> cardSet = new HashSet<Card>();
        
        for (Card card : cards)
            cardSet.add(card);
        
        // test that each card in the deck is distinct and that there are 52 
        // cards in the deck
        assertEquals(52, cardSet.size());
    }
}
