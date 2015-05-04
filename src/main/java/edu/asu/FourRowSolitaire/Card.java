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

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * This class represents a single card in a standard deck of playing cards.
 * 
 * @author Matt Stephen
 */
@SuppressWarnings("serial")
public class Card extends JComponent implements Cloneable
{
    /*
     * Each card in a deck is given a distinct ordinal number from 1-52.
     */
    private int fullCardNumber;
    
    private CardSuit   cardSuit;
    private CardNumber cardNumber;
    private CardColor  cardColor;
    
    private int deckNumber;
    
    /*
     * BufferedImage representation of the card that is rendered to the output
     * monitor. {@code cardBack}, {@code cardFront}, and {@code cardHighlighted}
     * are String representations of the URL pointing to the image resources.
     */
    private BufferedImage image;
    private String cardBack;
    private String cardFront;
    private String cardHighlighted;
    
    private boolean faceUp = false;
    private boolean highlighted = false;
    
    /*
     * Used to verify whether cards are being moved from the deck to the Discard
     * Pile or not.
     */
    private String location = "";
    
    /**
     * Constructs a new Card.
     * 
     * @param suit the card suit
     * @param number the card number (a value between 1 and 13, where 1, 11, 
     * 12, 13 represent an Ace, Jack, Queen, and King respectively)
     * @param deckNumber number of the deck that this card belongs to
     * @param fullNumber card ordinal (a distinct value between 1 and 52)
     */
    public Card(CardSuit suit, CardNumber number, int deckNumber, int fullNumber)
    {
        if (isValidSuit(suit) && isValidNumber(number))
        {
            this.cardSuit = suit;
            this.cardNumber = number;
            this.deckNumber = deckNumber;
            this.fullCardNumber = fullNumber;
            cardColor = cardSuit.getColor();
            
            // set card back style
            Integer cardStyle = 3;
            
            if (ChangeAppearance.isValidDeckNumber(deckNumber))
                cardStyle = deckNumber;
            
            // initialize image path locations
            cardBack        = "images/cardbacks/cardback" + cardStyle + ".png";
            cardFront       = "images/cardfaces/"         + cardSuit.getPrefix() + cardNumber.getName() + ".png";
            cardHighlighted = "images/highlightedfaces/"  + cardSuit.getPrefix() + cardNumber.getName() + "H.png";
        }
        else
        {
            cardSuit = CardSuit.INVALID;
            cardNumber = CardNumber.INVALID;
            cardFront = "images/invalidcard.png";
        }
        
        setFaceUp();
    }
    
    /**
     * Highlights this card.
     */
    public void highlight()
    {
        try
        {
            URL imageURL = this.getClass().getClassLoader().getResource(cardHighlighted);
            if (imageURL != null) image = ImageIO.read(imageURL);
        }
        catch (IOException ex)
        {
            System.err.println("Error in creating highlighted card face image.");
        }
        
        highlighted = true;
        repaint();
    }
    
    /**
     * Un-highlights this card.
     */
    public void unhighlight()
    {
        highlighted = false;
        setFaceUp();
    }
    
    /**
     * Tests whether this card is highlighted or not.
     * 
     * @return true if this card is highlighted; false otherwise
     */
    public boolean isHighlighted()
    {
        return highlighted;
    }
    
    /**
     * Set this card face up, showing the card's front.
     */
    public void setFaceUp()
    {
        faceUp = true;
        
        try
        {
            URL imageURL = this.getClass().getClassLoader().getResource(cardFront);
            if (imageURL != null) image = ImageIO.read(imageURL);
        }
        catch(IOException ex)
        {
            System.err.println("Error in creating card face image.");
        }
    }
    
    /**
     * Sets this card face down, showing the card's back.
     */
    public void setFaceDown()
    {
        faceUp = false;
        
        try
        {
            URL imageURL = this.getClass().getClassLoader().getResource(cardBack);
            if (imageURL != null) image = ImageIO.read(imageURL);
        }
        catch (IOException ex)
        {
            System.err.println("Error in creating card back image.");
        }
    }
    
    /**
     * Tests whether this card is face up or not.
     * 
     * @return true if this card is face up; false otherwise
     */
    public boolean isFaceUp()
    {
        return faceUp;
    }
    
    /**
     * Tests if a CardSuit is a valid card suit.
     * 
     * @param suit CardSuit to test
     * @return true if the CardSuit is a valid suit; false otherwise
     */
    public static boolean isValidSuit(CardSuit suit)
    {
        return (suit != CardSuit.INVALID) ? true : false;
    }
    
    /**
     * Tests if a CardNumber is a valid card number.
     * 
     * @param number CardNumber to test
     * @return true if the CardNumber is a valid number; false otherwise
     */
    public static boolean isValidNumber(CardNumber number)
    {
        return (number != CardNumber.INVALID) ? true : false;
    }
    
    /**
     * Returns BufferedImage object graphically representing this card.
     * 
     * @return BufferedImage object
     */
    public BufferedImage getImage()
    {
        return image;
    }
    
    /**
     * Returns this card's number.
     * 
     * @return this card's number
     */
    public CardNumber getNumber()
    {
        return cardNumber;
    }
    
    /**
     * Returns this card's suit.
     * 
     * @return this card's suit
     */
    public CardSuit getSuit()
    {
        return cardSuit;
    }
    
    /**
     * Returns this card's color.
     * 
     * @return this card's color
     */
    public CardColor getColor()
    {
        return cardColor;
    }
    
    /**
     * Returns this card's full number (ordinal from 1-52).
     * 
     * @return this card's full number
     */
    public int getFullNumber()
    {
        return fullCardNumber;
    }
    
    /**
     * Returns the source of this card.
     * 
     * @return the source of this card
     */
    public String getSource()
    {
        return location;
    }
    
    /**
     * Set the source of this card.
     * 
     * @param source the source of this card
     */
    public void setSource(String source)
    {
        location = source;
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
    
    @Override
    public Card clone()
    {
        return new Card(cardSuit, cardNumber, deckNumber, fullCardNumber);
    }
}