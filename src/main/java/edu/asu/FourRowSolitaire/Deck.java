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

import java.util.LinkedList;
import java.util.Random;

/**
 * Class: Deck
 *
 * Description: The Deck class holds all the Cards to form a 52 card deck.
 *
 * @author Matt Stephen
 */
public class Deck
{
    private int deckNumber;
    private LinkedList<Card> deck = new LinkedList<Card>();

    public Deck(int deckNumber)
    {
        this.deckNumber = deckNumber;
        shuffle();
    }

    public LinkedList<Card> getDeck()
    {
        return deck;
    }

    public LinkedList<Card> getDeck(LinkedList<Integer> numbers)
    {
        deck = new LinkedList<Card>();
        
        for(int i = 0; i < numbers.size(); i++)
        {
            if(numbers.get(i) > 0)
                createCard(numbers.get(i));
        }
        
        return deck;
    }

    public void shuffle()
    {
        LinkedList<Integer> numberList = new LinkedList<Integer>();

        for (int i = 1; i <= 52; i++)
            numberList.add(i);
        
        Random gen = new Random();
        
        for (int i = 0; i < 52; i++)
        {
            int num = gen.nextInt(numberList.size());
            
            int cardNumber = numberList.get(num);
            numberList.remove(num);
            
            createCard(cardNumber);
        }
    }

    private void createCard(int fullCardNumber)
    {
        CardSuit cardSuit = CardSuit.INVALID;
        CardNumber cardNumber = CardNumber.INVALID;
        
        // get card suit
        for (CardSuit suit : CardSuit.values())
            if (fullCardNumber - suit.getOffset() >= 1 && fullCardNumber - suit.getOffset() <= 13)
                cardSuit = suit;
        
        // get card value
        for (CardNumber number : CardNumber.values())
            if ((fullCardNumber - 1) % 13 + 1 == number.getValue())
                cardNumber = number;
        
        if (fullCardNumber >= 1 && fullCardNumber <= 52 && Card.isValidSuit(cardSuit))
            deck.add(new Card(cardSuit, cardNumber, deckNumber, fullCardNumber));
        else
            deck.add(new Card(CardSuit.INVALID, CardNumber.INVALID, deckNumber, fullCardNumber));
    }
}