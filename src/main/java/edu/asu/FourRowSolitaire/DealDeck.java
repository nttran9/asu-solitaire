/*
 * This file is a part of Four Row Solitaire
 *
 * Copyright (C) 2010 by Matt Stephen
 *
 * Four Row Solitaire is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Four Row Solitaire is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FourRowSolitaire.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.asu.FourRowSolitaire;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.LinkedList;

import javax.swing.JOptionPane;

/**
 * This class represents the DealDeck, which is a stack of cards. This deck 
 * manages the cards that are left over after the board initially deals out
 * the deck.
 * <p>
 * Every Four Row Solitaire board contains only one DealDeck. When the player
 * clicks on the DealDeck, either 1 or 3 cards (depending on the the {@code
 * drawCount} are popped to the DiscardPile, where the player can move the 
 * card(s) to the playing field.
 * <p>
 * Depending on the difficulty and the {@code drawCount} of the current game, 
 * the player is allowed to draw through the deck anywhere from 1 (hardest) 
 * to 4 (easiest) times before the game is lost.
 *
 * @author Matt Stephen
 */
@SuppressWarnings("serial")
public class DealDeck extends CardStack
{
    private static final int DRAW_ONE_THROUGH_LIMIT   = 2;
    private static final int DRAW_THREE_THROUGH_LIMIT = 3;
    
    /*
     * The number of deck throughs for each difficulty. Three card drawing adds
     * one more through to each difficulty level.
     */
    private static final int EASY_THROUGH_LIMIT   = 3;
    private static final int MEDIUM_THROUGH_LIMIT = 2;
    private static final int HARD_THROUGH_LIMIT   = 1;
    
    private DiscardPile discardPile;
    private int numTimesThroughDeck;
    private int drawCount;
    
    private int deckThroughLimit;
    private boolean isRedealable;

    /**
     * Constructs a new DealDeck.
     * 
     * @param discard a DiscardPile to deal cards to
     * @param drawCount how cards are drawn at a time from the DealDeck
     */
    public DealDeck(DiscardPile discard, int drawCount)
    {
        discardPile = discard;
        numTimesThroughDeck = 1;
        isRedealable = true;
        
        setDrawCount(drawCount);
        discard.setDrawCount(drawCount);
    }
    
    /**
     * Initially set up the DealDeck.
     * 
     * @param cards the cards that will make up the DealDeck
     */
    public void setDeck(LinkedList<Card> cards)
    {
        for (int i = 0; i < cards.size(); i++)
        {
            cards.get(i).setFaceDown();
            push(cards.get(i));
        }
    }
    
    /**
     * Pop {@code drawCount} number of cards from the DealDeck.
     * 
     * @return the last Card popped from the DealDeck
     */
    @Override
    public synchronized Card pop()
    {
        if (!isEmpty())
        {
            // draw one card
            if (drawCount == 1)
            {
                Card card = super.pop();
                
                card.setFaceUp();
                discardPile.push(card);
                
                this.repaint();
                return card;
            }
            // draw three cards
            else
            {
                int tempDrawCount = drawCount;
                CardStack tempStack = new CardStack();
                
                while (drawCount > 1 && tempDrawCount > 0 && !isEmpty())
                {
                    Card card = super.pop();
                    
                    card.setFaceUp();
                    tempStack.push(card);
                    
                    tempDrawCount--;
                }
                
                // to put the cards back in order because the previous step reversed them
                CardStack tempStack2 = new CardStack();
                
                for (int i = tempStack.length(); i > 0; i--)
                    tempStack2.push(tempStack.pop());
                
                discardPile.push(tempStack2);
                
                this.repaint();
                return discardPile.peek();
            }
        }
        // reset the DealDeck if deckThroughLimit has not been reached yet
        else if (!discardPile.isEmpty() && isRedealable)
        {
            for (int i = discardPile.length(); i > 0; i--)
            {
                Card card = discardPile.pop();
                card.setFaceDown();
                card.setSource("Deck");
                push(card);
            }

            discardPile.repaint();
            numTimesThroughDeck++;
        }
        // otherwise show dialog box informing user that the limit has been reached
        else if (numTimesThroughDeck >= deckThroughLimit)
        {
            isRedealable = false;
            JOptionPane.showMessageDialog(null, "You have reached your deck through limit.");
        }
        
        this.repaint();
        return null;
    }
    
    /**
     * Undo a pop operation.
     */
    public synchronized void undoPop()
    {
        while (!isEmpty())
        {
            Card card = super.pop();
            card.setFaceUp();
            discardPile.push(card);
        }
        
        undone();
        
        if(!isRedealable)
            isRedealable = true;
        
        discardPile.repaint();
        this.repaint();
    }
    
    /**
     * Returns the topmost Card located at the specified coordinate point.
     * <p>
     * Because the cards in an DealDeck directly overlay each other without
     * an offset, there is not need to use logic to deduce which card is 
     * directly underneath the point. It will always be the top card.
     * 
     * @param p the point location of the Card to return
     * @return the Card located at the specified point; null if the specified
     * point is not defined in the area of a card in this CardStack
     */
    @Override
    public Card getCardAtLocation(Point p)
    {
        return peek();
    }
    
    /**
     * Tests if adding a specified Card to this CardStack is a valid move.
     * Returns true if adding the Card to this CardStack is valid move; 
     * false otherwise.
     * <p>
     * <b>Note:</b> Adding a Card to the DealDeck is not allowed in Four Row 
     * Solitaire. As such, this method will always return false.
     * 
     * @param card a Card to add to this CardStack
     * @return false
     */
    @Override
    public boolean isValidMove(Card card)
    {
        return false;
    }
    
    /**
     * Tests is adding a specified CardStack to this CardStack is a valid move.
     * Returns true if adding the CardStack to this CardStack is a valid move; 
     * false otherwise.
     * <p>
     * <b>Note:</b> Adding a CardStack to the DealDeck is not allowed in Four Row 
     * Solitaire. As such, this method will always return false.
     * 
     * @param stack a CardStack to add to this CardStack
     * @return false
     */
    @Override
    public boolean isValidMove(CardStack stack)
    {
        return false;
    }
    
    /**
     * Resets the deck through counter.
     */
    public void reset()
    {
        numTimesThroughDeck = 1;
    }
    
    /**
     * Decrement the deck through counter by 1.
     */
    private void undone()
    {
        numTimesThroughDeck--;
    }

    /**
     * Returns the number of deck throughs so far.
     * 
     * @return the number of deck throughs so far
     */
    public int getDeckThroughs()
    {
        return numTimesThroughDeck;
    }
    
    /**
     * Set the deck through counter.
     * 
     * @param throughs the number of throughs 
     */
    public void setDeckThroughs(int throughs)
    {
        numTimesThroughDeck = throughs;
    }
    
    /**
     * Set the number of cards that are dealt each time from the DealDeck.
     * 
     * @param drawCount number of cards to deal from the DealDeck
     */
    public void setDrawCount(int drawCount)
    {
        this.drawCount = drawCount;
        discardPile.setDrawCount(drawCount);

        if (this.drawCount == 3)
            deckThroughLimit = DRAW_THREE_THROUGH_LIMIT;
        else
            deckThroughLimit = DRAW_ONE_THROUGH_LIMIT;
    }
    
    /**
     * Sets the deck through limit based on the difficulty.
     * 
     * @param difficulty game difficulty (1 - 3)
     */
    public void setDifficulty(int difficulty)
    {
        if (difficulty == 1)
            deckThroughLimit = EASY_THROUGH_LIMIT;
        else if (difficulty == 2)
            deckThroughLimit = MEDIUM_THROUGH_LIMIT;
        else  // if (difficulty == 3)
            deckThroughLimit = HARD_THROUGH_LIMIT;
        
        // draw three has an extra deck through on top of the single card setting
        if (drawCount == 3)
            deckThroughLimit++;
    }
    
    /**
     * Returns true if the DealDeck has deck throughs left; false otherwise.
     * 
     * @return true if the DealDeck has deck throughs left; false otherwise
     */
    public boolean hasDealsLeft()
    {
        return isRedealable;
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        
        if(!isEmpty())
        {
            for(int i = 0; i < length(); i++)
            {
                Image image = getCardAtLocation(i).getImage();
                g.drawImage(image, 0, 0, null);
            }
        }
    }
}