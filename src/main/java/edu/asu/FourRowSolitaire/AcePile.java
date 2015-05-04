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
 * This class represents the AcePile, which is a stack of cards. It inherits all
 * of the operations from {@link CardStack} and overrides the implementations 
 * for a few of them.
 * <p>
 * There are four AcePiles per {@link SolitaireBoard}, one for each suit. If 
 * an AcePile is empty, only Ace cards may be added to the AcePile. Otherwise,
 * cards can only be added to an AcePile if the card being added is one value 
 * less than the card that is currently at the top of the AcePile <i>and</i> 
 * if the card being added is a different color than the card that is currently
 * at the top of the AcePile.
 * <p>
 * The AcePile is where the player tries to put all of the cards in. Once all of
 * the cards on the board are placed in the AcePile, the game is won. In fact, 
 * this is the <i>only</i> way that the player can win.
 * 
 * @author Matt Stephen
 */
@SuppressWarnings("serial")
public class AcePile extends CardStack
{
    private CardSuit suit;
    
    /**
     * Constructs a new AcePile with a specified suit.
     * 
     * @param spades suit type
     */
    public AcePile(CardSuit spades)
    {
        this.suit = spades;
    }

    /**
     * Returns the suit of the AcePile.
     * 
     * @return suit of the AcePile
     */
    public CardSuit getSuit()
    {
        return suit;
    }
    
    /**
     * Pushes a card to the top of the CardStack.
     * 
     * @param card the card to be pushed onto this CardStack
     * @return the card that was pushed onto this CardStack
     */
    @Override
    public Card push(Card card)
    {
        if (isValidMove(card))
        {
            super.push(card);
            return card;
        }
        
        return null;
    }
    
    /**
     * Returns the topmost Card located at the specified coordinate point.
     * <p>
     * Because the cards in an AcePile directly overlay each other without
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
     * 
     * @param card a Card to add to this CardStack
     * @return true if move is valid; false otherwise
     */
    @Override
    public boolean isValidMove(Card card)
    {
        if (isEmpty() && isSameSuit(card) && isAce(card))
            return true;
        else if (!isEmpty() && isSameSuit(card) && isOneLess(peek(), card))
            return true;
        else
            return false;
    }
    
    /**
     * Tests is adding a specified CardStack to this CardStack is a valid move.
     * Returns true if adding the CardStack to this CardStack is a valid move; 
     * false otherwise.
     * <p>
     * <b>Note:</b> Adding a CardStack to an AcePile is not allowed in Four Row 
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
     * Returns true if the specified card is an Ace card. Otherwise, false is
     * returned.
     * 
     * @param card card to check
     * @return true if the card is an Ace card; false otherwise
     */
    private boolean isAce(Card card)
    {
        return card.getNumber() == CardNumber.ACE;
    }
    
    /**
     * Returns true if the specified card's suit matches the suit of this 
     * AcePile.
     * 
     * @param card card to check
     * @return true if the specified card's suit matches the suit of this 
     * AcePile; false otherwise
     */
    private boolean isSameSuit(Card card)
    {
        return card.getSuit().equals(getSuit());
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        
        for (int i = 0; i < length(); i++)
        {
            Image image = getCardAtLocation(i).getImage();
            g.drawImage(image, 0, 0, null);
        }
    }
}