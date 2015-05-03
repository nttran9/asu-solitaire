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

import java.awt.Point;

/**
 * This class represents a Single Cell, which is a stack of cards. It manages
 * an individual cell that can only hold one card.
 * <p>
 * Every Four Row Solitaire board contains four of these Single Cells that can
 * be used for maneuvering space (just like in FreeCell).
 *
 * @author Matt Stephen
 */
@SuppressWarnings("serial")
public class SingleCell extends CardStack
{
    /**
     * Pushes a card to the top of this CardStack.
     * 
     * @param card the card to be pushed onto this CardStack
     * @return the card that was pushed onto this CardStack
     */
    @Override
    public Card push(Card card)
    {
        if (isEmpty())
        {
            super.push(card);
            return card;
        }
        
        return null;
    }
    
    /**
     * Returns the topmost Card located at the specified coordinate point.
     * <p>
     * Because a SingleCell can only contain one card, there is not need 
     * to use logic to deduce which card is directly underneath the point.
     * It will always be the top card.
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
     * For a SingleCell, a card can only be added if the SingleCell currently
     * contains no cards. Any card can be placed in a SingleCell.
     * 
     * @param card a Card to add to this CardStack
     * @return true if move is valid; false otherwise
     */
    @Override
    public boolean isValidMove(Card card)
    {
        return isEmpty() ? true : false;
    }
    
    /**
     * Tests is adding a specified CardStack to this CardStack is a valid move.
     * Returns true if adding the CardStack to this CardStack is a valid move; 
     * false otherwise.
     * <p>
     * <b>Note:</b> Adding a CardStack to a SingleCell is not allowed in Four Row 
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
}