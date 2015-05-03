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
import java.util.Vector;

import javax.swing.JLayeredPane;

/**
 * This class represents a stack of cards. It provides operations for managing 
 * the stack (i.e. adding and removing cards) as well as operations for 
 * displaying the stack of cards on the screen.
 * <p>
 * CardStack subclasses the JLayeredPane allowing cards in the card stack to 
 * appear stacked on the screen by overlaying the cards on top of each other.
 *
 * @author Matt Stephen
 */
@SuppressWarnings("serial")
public class CardStack extends JLayeredPane
{
    /*
     * The number of pixels in the vertical direction between each card. Each
     * card in the CardStack is painted {@code OFFSET_PIXELS} pixels below the
     * card before it.
     */
    private static final int OFFSET_PIXELS = 25;
    
    /*
     * The dimensions of a Card in pixels when it is painted onto the screen.
     */
    private static final int CARD_WIDTH  = 72;
    private static final int CARD_HEIGHT = 96;
    
    protected Vector<Card> cards;
    
    /**
     * Constructs a new CardStack.
     */
    public CardStack()
    {
        cards = new Vector<Card>();
    }
    
    /**
     * Pushes a card to the top of this CardStack.
     * 
     * @param card the card to be pushed onto this CardStack
     * @return the card that was pushed onto this CardStack
     */
    public Card push(Card card)
    {
        cards.add(card);
        card.setBounds(0, 0, CARD_WIDTH, CARD_HEIGHT);
        add(card, 0);
        
        return card;
    }
    
    /**
     * Pushes another CardStack to the top of this CardStack.
     * 
     * @param stack the CardStack to be pushed onto this CardStack
     * @return an empty CardStack
     */
    public CardStack push(CardStack stack)
    {
        while (!stack.isEmpty())
        {
            Card card = stack.pop();
            push(card);
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
    public synchronized Card pop()
    {
        Card card = peek();
        this.remove(card);
        cards.remove(cards.size() - 1);
        
        return card;
    }
    
    /**
     * Looks at the Card at the top of this CardStack without removing it from
     * the CardStack.
     * 
     * @return the object at the top of this CardStack iff this CardStack is not
     * empty; null otherwise
     */
    public synchronized Card peek()
    {
        if (cards.isEmpty())
            return null;
        
        return cards.lastElement();
    }
    
    /**
     * Tests if this CardStack is empty.
     * 
     * @return true iff this CardStack contains no items; false otherwise
     */
    public boolean isEmpty()
    {
        return cards.size() == 0;
    }
    
    /**
     * Returns the number of Cards in this CardStack.
     * 
     * @return the number of Cards in this CardStack.
     */
    public int length()
    {
        return cards.size();
    }
    
    /**
     * Returns the card located at the specified index, or returns null if the
     * specified index is invalid.
     * 
     * @param index the index of the Card to return
     * @return the Card at the specified index; null if the specified index is 
     * invalid
     */
    public Card getCardAtLocation(int index)
    {
        if (index < cards.size())
            return cards.get(index);
        
        return null;
    }
    
    /**
     * Returns the topmost Card located at the specified coordinate point.
     * 
     * @param p the point location of the Card to return
     * @return the Card located at the specified point; null if the specified
     * point is not defined in the area of a card in this CardStack
     */
    public Card getCardAtLocation(Point p)
    {
        if (cards.isEmpty())
            return null;
        
        if (isValidClick(p))
        {
            int index;
            int y = (int) p.getY();
            
            // top card is selected
            if (y > OFFSET_PIXELS * (cards.size() - 1))
                index = cards.size() - 1;
            else  // card below top card selected
                index = y / OFFSET_PIXELS;
            
            if (isValidCard(index))
                return cards.get(index);
        }
        
        return null;
    }
    
    /**
     * Creates and returns a sub-stack of this CardStack containing all of
     * the cards above the specified card (including the specified card). If
     * the specified card does not exist in this CardStack, an empty CardStack
     * is returned. All of the Cards in the new CardStack are cloned.
     * 
     * @param card a card in this CardStack designated to be the bottom of the
     * created sub-stack
     * @return a sub-stack of this CardStack containing all of the cards above
     * the specified card (including the specified card)
     */
    public CardStack getStack(Card card)
    {
        CardStack temp = new CardStack();
        int index = search(card);
        
        for (int i = 0; i < index; i++)
        {
            temp.push(getCardAtLocation(cards.size() - i - 1).clone());
            getCardAtLocation(cards.size() - i - 1).highlight();
        }
        
        return temp;
    }
    
    /**
     * Creates and returns a sub-stack of this CardStack containing the top
     * {@code numCards} cards. If the input value is invalid, an empty CardStack
     * is returned. All of the Cards in the new CardStack are cloned.
     * 
     * @param numCards the number of cards from the top of this CardStack to
     * create a new CardStack with
     * @return a sub-stack of this CardStack containing the top {@code numCards}
     * cards in this CardStack
     */
    public CardStack getStack(int numCards)
    {
        CardStack temp = new CardStack();
        int index = length() - numCards;
        
        // invalid input
        if (index < 0 || numCards > length())
            return temp;
        
        for (int i = length(); i > index; i--)
        {
            temp.push(getCardAtLocation(cards.size() - i - 1).clone());
            getCardAtLocation(cards.size() - i - 1).highlight();
        }
        
        return temp;
    }
    
    /**
     * Tests if adding a specified Card to this CardStack is a valid move.
     * Returns true if adding the Card to this CardStack is valid move; 
     * false otherwise.
     * <p>
     * <b>Note:</b> If this method is not overridden, false will always be 
     * returned.
     * 
     * @param card a Card to add to this CardStack
     * @return false (override this behavior in subclasses of CardStack)
     */
    public boolean isValidMove(Card card)
    {
        return false;
    }
    
    /**
     * Tests is adding a specified CardStack to this CardStack is a valid move.
     * Returns true if adding the CardStack to this CardStack is a valid move; 
     * false otherwise.
     * <p>
     * <b>Note:</b> If this method is not overridden, false will always be 
     * returned.
     * 
     * @param stack a CardStack to add to this CardStack
     * @return false (override this behavior in subclasses of CardStack)
     */
    public boolean isValidMove(CardStack stack)
    {
        return false;
    }
    
    /**
     * Looks at the Card at the bottom of this CardStack without removing it from
     * the CardStack.
     * 
     * @return the object at the bottom of this CardStack iff this CardStack is 
     * not empty; null otherwise
     */
    public Card getBottom()
    {
        return cards.firstElement();
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
    public CardStack getAvailableCards()
    {
        return null;
    }

    /**
     * Returns true if the two specified cards are not the same color.
     * Otherwise, the method returns false.
     * 
     * @param cardOne first card to compare
     * @param cardTwo second card to compare
     * @return true if the two cards are not the same color; false otherwise
     */
    protected final boolean isDifferentColor(Card cardOne, Card cardTwo)
    {
        return cardOne.getColor() != cardTwo.getColor();
    }
    
    /**
     * Returns true if the two specified cards are of the same suit. Otherwise, 
     * the method returns false.
     * 
     * @param cardOne first card to compare
     * @param cardTwo second card to compare
     * @return true if the two cards have the same suit; false otherwise
     */
    protected final boolean isSameSuit(Card cardOne, Card cardTwo)
    {
        return cardOne.getSuit().equals(cardTwo.getSuit());
    }
    
    /**
     * Returns true if {@code cardOne} is one value less than {@code cardTwo}.
     * Otherwise, the method returns false.
     * 
     * @param cardOne first card to compare
     * @param cardTwo second card to compare
     * @return true if the first card is one value less than the second card;
     * false otherwise
     */
    protected final boolean isOneLess(Card cardOne, Card cardTwo)
    {
        return cardOne.getNumber() + 1 == cardTwo.getNumber();
    }
    
    /**
     * Returns the index of the last occurrence of the specified Card in this
     * CardStack, or returns -1 if the Card is not found.
     * 
     * @param card the Card to search for
     * @return the index of the first occurrence of the element in this 
     * CardStack at position <tt>index</tt> or later in the CardStack; 
     * -1 if the element is not found
     */
    private synchronized int search(Card card)
    {
        int i = cards.lastIndexOf(card);
        
        if (i >= 0)
            return cards.size() - i;
        
        return -1;
    }
    
    /**
     * Tests if a Card is a part of a valid CardStack.
     * 
     * @param index the index of the Card to test
     * @return true if the card is a part of a valid stack; false otherwise
     */
    private boolean isValidCard(int index)
    {
        if (index >= cards.size() || index < 0)
            return false;
        
        for (int i = index; i < cards.size() - 1; i++)
            if (!isDifferentColor(cards.get(i), cards.get(i + 1)) ||
                !isOneLess(cards.get(i), cards.get(i + 1)))
                return false;

        return true;
    }
    
    /**
     * Tests if the specified coordinate point is defined on a Card in this
     * CardStack.
     * 
     * @param p the coordinate point relative to the CardStack component
     * @return true iff the point is defined on a Card in this CardStack; false
     * otherwise
     */
    private boolean isValidClick(Point p)
    {
        final double TOP_CARD_HEIGHT    = cards.lastElement().getBounds().getHeight();
        final double OTHER_CARDS_HEIGHT = OFFSET_PIXELS * (cards.size() - 1);
        final double CARD_STACK_HEIGHT  = TOP_CARD_HEIGHT + OTHER_CARDS_HEIGHT;
        
        if (!isEmpty() && p.getY() > CARD_STACK_HEIGHT)
            return false;
        else
            return true;
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        
        if (!isEmpty())
        {
            for (int i = 0; i < cards.size(); i++)
            {
                Image image = cards.get(i).getImage();
                g.drawImage(image, 0, i * OFFSET_PIXELS, null);
            }
        }
    }
}