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

/**
 * This class represents the DealDeck, which is a stack of cards. This deck 
 * manages the cards dealt and discarded from the DealDeck.
 * <p>
 * Every Four Row Solitaire board contains only one DiscardPile. When the 
 * player clicks on the DealDeck, either 1 or 3 cards (depending on the the 
 * {@code drawCount} are popped to the DiscardPile, where the player can move 
 * the card(s) to the playing field.
 * <p>
 * If the DealDeck has dealt all of its cards to the DiscardPile and the 
 * DealDeck is redealable (it hasn't reached its deck through limit), then all
 * of the cards from the DiscardPile will be popped and pushed back onto the
 * DealDeck.
 *
 * @author Matt Stephen
 */
@SuppressWarnings("serial")
public class DiscardPile extends CardStack
{
    /*
     * The number of pixels in the horizontal direction between each card. Each
     * card in the DiscardPile is painted {@code OFFSET_PIXELS} pixels to the 
     * right of the card before it.
     */
    private static final int OFFSET_PIXELS = 15;
    
    private int cardsLeftFromDraw;
    private int drawCount;
    
    /**
     * Constructs a new DiscardPile.
     * 
     * @param drawCount how cards are drawn at a time from the DealDeck and 
     * pushed to the DiscardPile being constructed
     */
    public DiscardPile(int drawCount)
    {
        this.cardsLeftFromDraw = 0;
        this.drawCount = drawCount;
    }
    
    /**
     * Set the number of cards drawn at a time from the DealDeck and pushed
     * to this DiscardPile.
     * 
     * @param drawCount how cards are drawn at a time from the DealDeck and 
     * pushed to the DiscardPile being constructed
     */
    public void setDrawCount(int drawCount)
    {
        this.drawCount = drawCount;
    }
    
    /**
     * Returns the number of viewable cards in this DiscardPile.
     * 
     * @return number of viewable cards in this DiscardPile
     */
    public int getNumViewableCards()
    {
        return cardsLeftFromDraw;
    }
    
    /**
     * Set the number of viewable cards in this DiscardPile.
     * 
     * @param numViewableCards number of viewable cards in this DiscardPile
     */
    public void setView(int numViewableCards)
    {
        cardsLeftFromDraw = numViewableCards;
    }
    
    /**
     * Add a stack of cards to this DiscardPile.
     * 
     * @param stack CardStack of cards to add to this DiscardPile
     */
    public void addStack(CardStack stack)
    {
        for (int i = stack.length(); i > 0; i--)
        {
            Card card = stack.pop();
            addCard(card);
        }
    }
    
    /**
     * Pushes a card to the top of this DiscardPile.
     * 
     * @param card the card to be pushed onto this DiscardPile
     * @return the card that was pushed onto this DiscardPile
     */
    @Override
    public Card push(Card card)
    {
        if (drawCount == 1)
            cardsLeftFromDraw = 0;
        
        addCard(card);
        card.setSource("");
        return card;
    }
    
    /**
     * Pushes another CardStack to the top of this DiscardPile.
     * 
     * @param stack the CardStack to be pushed onto this DiscardPile
     * @return an empty CardStack
     */
    @Override
    public CardStack push(CardStack stack)
    {
        if (drawCount != 1 || (drawCount == 1 && stack.length() == 1))
        {
            cardsLeftFromDraw = 0;
            
            while (!stack.isEmpty())
                push(stack.pop());
        }
        
        return stack;
    }
    
    /**
     * Removes the Card at the top of this CardStack and returns that Card
     * as the value of this method.
     * 
     * @return the card at the top of this CardStack iff this CardStack is not
     * empty; null otherwise
     */
    @Override
    public synchronized Card pop()
    {
        Card card = super.pop();
        
        /* 
         * Display multiple cards correctly. After a player removes the top card
         * of draw 3, the top 3 cards should be displayed.
         */
        if (cardsLeftFromDraw > 0)
            cardsLeftFromDraw--;
        else
            cardsLeftFromDraw = 0;

        return card;
    }
    
    /**
     * Undo a pop operation.
     * 
     * @return the popped Card
     */
    public synchronized Card undoPop()
    {
        Card card = super.pop();
        return card;
    }
    
    /**
     * Returns the topmost Card located at the specified coordinate point.
     * <p>
     * Because only the top card of the DiscardPile can be selected, there 
     * is not need to use logic to deduce which card is directly underneath 
     * the point. It will always be the top card.
     * 
     * @param p the point location of the Card to return
     * @return the Card located at the specified point; null if the specified
     * point is not defined in the area of a card in this CardStack
     */
    public Card getCardAtLocation(Point p)
    {
        return peek();
    }
    
    /**
     * Tests if adding a specified Card to this CardStack is a valid move.
     * Returns true if adding the Card to this CardStack is valid move; 
     * false otherwise.
     * <p>
     * For a DiscardPile, cards can only be added if the cards are coming
     * from the DealDeck.
     * 
     * @param card a Card to add to this CardStack
     * @return true if move is valid; false otherwise
     */
    public boolean isValidMove(Card card)
    {
        if (card.getSource().equals("Deck"))
           return true;
        else
            return false;
    }
    
    /**
     * Tests is adding a specified CardStack to this CardStack is a valid move.
     * Returns true if adding the CardStack to this CardStack is a valid move; 
     * false otherwise.
     * <p>
     * <b>Note:</b> Adding a CardStack to the DiscardPile is not allowed in Four Row 
     * Solitaire. As such, this method will always return false.
     * 
     * @param stack a CardStack to add to this CardStack
     * @return false
     */
    public boolean isValidMove(CardStack stack)
    {
        return false;
    }
    
    /**
     * Returns the largest valid sub-stack of this CardStack that contains the
     * top Card of this CardStack. If this CardStack is empty, then null will be 
     * returned. A sub-stack is valid if:
     * <ol>
     * <li>Each Card in the stack is one greater in value the the Card directly
     * beneath it if there is a Card directly beneath the Card.</li>
     * <li>Each Card in the stack has a different color than the Card directly
     * beneath it if there is a Card directly beneath the Card.</li>
     * </ol>
     * 
     * @return the largest valid sub-stack of this CardStack or null if this
     * CardStack is empty
     */
    @Override
    public CardStack getAvailableCards()
    {
        if (!isEmpty())
        {
            CardStack temp = new CardStack();
            temp.push(peek());
            
            return temp;
        }
        else
            return null;
    }
    
    /**
     * Add card to this DiscardPile.
     * 
     * @param card card to be added
     */
    private void addCard(Card card)
    {
        cardsLeftFromDraw++;
        super.push(card);
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        
        // draw one card
        if (!isEmpty() && drawCount == 1)
        {
            for (int i = 0; i < length(); i++)
            {
                Image image = getCardAtLocation(i).getImage();
                g.drawImage(image, 0, 0, null);
            }
        }
        // draw three cards
        else if (!isEmpty() && drawCount == 3)
        {
            if (cardsLeftFromDraw > 0)
            {
                for (int i = 0; i < length() - cardsLeftFromDraw + 1; i++)
                {
                    Image image = getCardAtLocation(i).getImage();
                    g.drawImage(image, 0, 0, null);
                }
                
                for (int i = length() - cardsLeftFromDraw + 1; i < length(); i++)
                {
                    Image image = getCardAtLocation(i).getImage();
                    
                    if ((cardsLeftFromDraw == 3 && i == length() - 2) || (cardsLeftFromDraw == 2 && i == length() - 1))
                        g.drawImage(image, OFFSET_PIXELS, 0, null);
                    else if (cardsLeftFromDraw == 3 && i == length() - 1)
                        g.drawImage(image, OFFSET_PIXELS * 2, 0, null);
                }
            }
            else
            {
                for (int i = 0; i < length(); i++)
                {
                    Image image = getCardAtLocation(i).getImage();
                    g.drawImage(image, 0, 0, null);
                }
            }
        }
    }
}