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
import java.awt.image.BufferedImage;
import java.io.IOException;
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
    
    /*
     * The face value and suit of the card.
     */
    private CardSuit   cardSuit;
    private CardNumber cardNumber;
    
    /*
     * Denotes which graphical image to be displayed when the card is face down.
     * This integer value is used to determine the filename of the image file.
     * Defaults to style #3.
     */
    private int cardBackStyle = 3;
    
    /*
     * BufferedImage representation of the card that is rendered to the output
     * monitor. {@code cardBack}, {@code cardFront}, and {@code cardHighlighted}
     * are String representations of the file path to the image resources.
     */
    private BufferedImage image;
    private String cardBack;
    private String cardFront;
    private String cardHighlighted;
    
    /*
     * Card states.
     */
    private boolean faceUp = false;
    private boolean highlighted = false;
    
    /*
     * Used to determine whether cards are being moved from the deck to the 
     * Discard Pile.
     */
    private String location = "";
    
    /**
     * Constructs a new Card.
     * 
     * @param suit          the suit of the card
     * @param number        the face value of the card
     * @param cardBackStyle integer denoting the image that is to be displayed 
     *                      when a card is face down
     * @param fullNumber    an ordinal number between 1 and 52
     */
    public Card(CardSuit suit, CardNumber number, int cardBackStyle, int fullNumber)
    {
        if (isValidSuit(suit) && isValidNumber(number))
        {
            this.cardSuit = suit;
            this.cardNumber = number;
            this.fullCardNumber = fullNumber;
            
            // set image displayed when card is face down
            if (ChangeAppearance.isValidDeckNumber(cardBackStyle))
                this.cardBackStyle = cardBackStyle;
            
            // initialize image path locations
            cardBack        = "images/cardbacks/cardback" + cardBackStyle + ".png";
            cardFront       = "images/cardfaces/"         + cardSuit.getPrefix() + cardNumber.getName() + ".png";
            cardHighlighted = "images/highlightedfaces/"  + cardSuit.getPrefix() + cardNumber.getName() + "H.png";
        }
        else
        {
            cardSuit   = CardSuit.INVALID;
            cardNumber = CardNumber.INVALID;
            cardFront  = "images/invalidcard.png";
            cardBack   = "images/cardbacks/cardback3.png";
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
            System.err.println("Error occurred when creating highlighted card face image.");
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
     * Determines whether this card is highlighted or not.
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
            System.err.println("Error occurred when creating card face image.");
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
            System.err.println("Error occurred when creating card back image.");
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
        return cardSuit.getColor();
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
        return new Card(cardSuit, cardNumber, cardBackStyle, fullCardNumber);
    }
    
    public boolean equals(Card other)
    {
        if (other == null)
            return false;
        
        return (getSuit() == ((Card) other).getSuit() &&
                getNumber() == ((Card) other).getNumber());
    }
}