package cards;

public class Card {
    private final char rank;
    private final char suit;
    
    /**
     * 
     * @param rank the rank of the card. Must be '1'-'9','T','J','Q','K','A'
     * @param suit suit of the card. Must be 'h','s','d','c'
     */
    public Card(char rank, char suit){
        this.rank = rank;
        this.suit = suit;
    }
    
    
    /**
     * @return the rank of the card : '1'-'9','T','J','Q','K','A'
     */
    public char getRank(){
        return rank;
    }
    
    
    /**
     * @return the suit of the card : 'h','s','d','c'
     */
    public char getSuit(){
        return suit;
    }
    
    
    /**
     * @return a char array of all of the possible card ranks
     */
    public static char[] getRanks(){
        return new char[]{'1','2','3','4','5','6','7','8','9','T','J','Q','K','A'};
    }
    
    
    /**
     * @return a char array of all of the possible card suits
     */
    public static char[] getSuits(){
        return new char[]{'h','s','d','c'};
    }
    
    
    @Override
    public String toString(){
        return Character.toString(rank)+Character.toString(suit);
    }
    
    
    @Override
    public boolean equals(Object other){
        if(!(other instanceof Card)){
            return false;
        }
        
        Card _other = (Card)other;
        return (rank == _other.getRank() && suit == _other.getSuit());
    }
    
    
    @Override
    public int hashCode(){
        return rank*suit;
    }
}
