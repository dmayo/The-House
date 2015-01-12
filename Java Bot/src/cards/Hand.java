package cards;

public class Hand {
    private Card card1;
    private Card card2;
    
    public Hand(Card card1, Card card2){
        this.card1 = card1;
        this.card2 = card2;
    }
    
    
    /**
     * @return true if cards are same suit, false otherwise
     */
    public boolean isSuited(){
        return (card1.suitOf() == card2.suitOf());
    }
    
    
    @Override
    public boolean equals(Object other){
        if(!(other instanceof Hand)){
            return false;
        }
        
        Hand _other = (Hand) other;
        return ((_other.card1.equals(card1) && _other.card2.equals(card2)) ||
                (_other.card2.equals(card1) && _other.card1.equals(card2)));
    }
    
    @Override
    public int hashCode(){
        return card1.hashCode()*card2.hashCode();
    }
}
