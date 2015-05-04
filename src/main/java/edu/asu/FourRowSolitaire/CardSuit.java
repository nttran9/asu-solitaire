package edu.asu.FourRowSolitaire;

/**
 * Represents a card's suit.
 * 
 * @author Nelson Tran (nttran9)
 */
public enum CardSuit
{
    SPADES   ("s", "Spades",   CardColor.BLACK, 0),
    CLUBS    ("c", "Clubs",    CardColor.BLACK, 13),
    HEARTS   ("h", "Hearts",   CardColor.RED,   26),
    DIAMONDS ("d", "Diamonds", CardColor.RED,   39),
    INVALID  ("i", "Invalid",  null,           -999);
    
    /*
     * The sum of a card's value and the suit offset yields the card's
     * full number (ordinal value from 1-52).
     */
    private final int offset;
    
    private final CardColor color;
    private final String name;
    private final String prefix;
   
    private CardSuit(String prefix, String name, CardColor color, int offset)
    {
        this.prefix = prefix;
        this.name = name;
        this.color = color;
        this.offset = offset;
    }
    
    public String getPrefix()
    {
        return prefix;
    }
    
    public String getName()
    {
        return name;
    }
    
    public CardColor getColor()
    {
        return color;
    }
    
    public int getOffset()
    {
        return offset;
    }
}