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

import java.util.Collections;
import java.util.LinkedList;

/**
 * This class represents a deck of {@link Card} objects.
 *
 * @author Matt Stephen
 */
public class Deck
{
    /*
     * The {@link Card}s in the deck are internally stored as a linked list.
     */
    private LinkedList<Card> cards;
    
    /*
     * Denotes which graphical image to be displayed when a card is face down.
     * This integer value is used to determine the filename of the image file.
     */
    private int deckStyle;
    
    /**
     * Constructs a new deck.
     * 
     * @param cards linked list of {@link Card}s that will make up the deck
     */
    public Deck(int deckStyle)
    {
        this.deckStyle = deckStyle;
        
        cards = createStandardDeck(deckStyle);
        Collections.shuffle(cards);
    }
    
    /**
     * Create a list of 52 playing cards resembling a standard deck.
     */
    public LinkedList<Card> createStandardDeck(int deckStyle)
    {
        LinkedList<Card> cards = new LinkedList<Card>();
        
        for (CardSuit suit : CardSuit.values())
        {
            for (CardNumber value : CardNumber.values())
            {
                if (Card.isValidSuit(suit) && Card.isValidNumber(value))
                {
                    int fullNumber = suit.getOffset() + value.getValue();
                    cards.add(new Card(suit, value, deckStyle, fullNumber));
                }
            }
        }
        
        return cards;
    }
    
    /**
     * This method accepts a linked list of Integer objects -- each of them 
     * representing a {@link Card} ordinal number -- and converts the linked 
     * list into another linked list of corresponding Card objects.
     * 
     * @param numbers linked list of integers representing the cards
     * @return corresponding linked list of {@link Card}s
     */
    public LinkedList<Card> createCustomDeck(LinkedList<Integer> ordinals)
    {
        cards = new LinkedList<Card>();
        
        for (int i = 0; i < ordinals.size(); i++)
            if (ordinals.get(i) > 0)
                cards.add(createCard(ordinals.get(i)));
        
        return cards;
    }
    
    /**
     * Creates a new card from a card's ordinal number.
     * 
     * @param fullCardNumber ordinal number of the card to create
     */
    private Card createCard(int fullCardNumber)
    {
        // initially assume card is invalid
        CardSuit   cardSuit = CardSuit.INVALID;
        CardNumber cardNumber = CardNumber.INVALID;
        
        for (CardSuit suit : CardSuit.values())
        {
            int value = fullCardNumber - suit.getOffset();
            if (value >= 1 && value <= 13)
                cardSuit = suit;
        }
        
        for (CardNumber number : CardNumber.values())
            if ((fullCardNumber - 1) % 13 + 1 == number.getValue())
                cardNumber = number;
        
        return new Card(cardSuit, cardNumber, deckStyle, fullCardNumber);
    }
    
    /**
     * Returns a linked list of cards contained in this deck.
     * 
     * @return a linked list containing all of the cards in this deck
     */
    public LinkedList<Card> getDeck()
    {
        return cards;
    }
    
    /**
     * Sets the cards in this deck.
     * 
     * @param cards a linked list containing all of the cards to set
     */
    public void setDeck(LinkedList<Card> cards)
    {
        this.cards = cards;
    }
}