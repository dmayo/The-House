package cards;

public class Cards {
    private final char rank;
    private final char suit;
    
    /**
     * 
     * @param rank the rank of the card. Must be '1'-'9','T','J','Q','K'
     * @param suit suit of the card. Must be 'h','s','d','c'
     */
    public Cards(char rank, char suit){
        this.rank = rank;
        this.suit = suit;
    }
    
    
    /**
     * @return the rank of the card : '1'-'9','T','J','Q','K'
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
    
    
    @Override
    public String toString(){
        return Character.toString(rank)+Character.toString(suit);
    }
    
    
    @Override
    public boolean equals(Object other){
        if(!(other instanceof Cards)){
            return false;
        }
        
        Cards _other = (Cards)other;
        return (rank == _other.getRank() && suit == _other.getSuit());
    }
    
    
    @Override
    public int hashCode(){
        return rank*suit;
    }
}
