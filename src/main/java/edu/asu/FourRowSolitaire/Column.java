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

/**
 * This class represents the Column, which is a stack of cards. It inherits all
 * of the operations from {@link CardStack} and overrides the implementations 
 * for a few of them.
 * <p>
 * Every Four Row Solitaire board contains four Columns, each containing five
 * random cards at the start of each game. When a Column is empty, only King
 * cards may be added to the Column. Otherwise, cards can only be added to an
 * AcePile if the card being added is one value less than the card that is 
 * currently at the top of the Column <i>and</i> if the card being added is a
 * different color than the card that is currently at the top of the Column.
 * <p>
 * CardStacks may also be added to a Column if the same conditions mentioned in
 * the paragraph preceding this one are met for the bottom card of the to-be-
 * added CardStack and the card currently at the top of the Column.
 *
 * @author Matt Stephen
 */
@SuppressWarnings("serial")
public class Column extends CardStack
{
    /**
     * Constructs a new Column.
     */
    public Column() { /* Intentionally Empty */ }
    
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
        if (isEmpty() && isKing(card))
            return true;
        else if (!isEmpty() && isDifferentColor(card, peek()) && isOneLess(card, peek()))
            return true;
        else
            return false;
    }

    /**
     * Tests is adding a specified CardStack to this CardStack is a valid move.
     * Returns true if adding the CardStack to this CardStack is a valid move; 
     * false otherwise.
     * 
     * @param stack a CardStack to add to this CardStack
     * @return true if move is valid; false otherwise
     */
    @Override
    public boolean isValidMove(CardStack stack)
    {
        return isValidMove(stack.peek());
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
            boolean cardsMatch = true;
            
            CardStack temp = new CardStack();
            int index = length() - 1;
            temp.push(cards.get(index));
            
            while (cardsMatch && --index >= 0)
            {
                Card card = cards.get(index);
                
                if (isDifferentColor(card, temp.peek()) && isOneLess(card, temp.peek()))
                    temp.push(card);
                else
                    cardsMatch = false;
            }
            
            return temp;
        }
        
        return null;
    }
    
    /**
     * Returns true if the specified card is a King card. Otherwise, false is
     * returned.
     * 
     * @param card card to check
     * @return true if the card is an King card; false otherwise
     */
    private boolean isKing(Card card)
    {
        return card.getNumber() == CardNumber.KING;
    }
}