package cards;
import java.util.ArrayList;
import java.util.List;

import actions.Street;

public class BoardCards {
    
    private List<Card> cards;
    private Street street;
    
    public BoardCards(){
        this.cards = new ArrayList<Card>();
        this.street = Street.PREFLOP;
    }
    
    public BoardCards(Street street, List<Card> cards){
        this.cards = new ArrayList<Card>(cards);
        this.street = street;
    }
    

    public void setCards(Street newStreet, List<Card> newCards){
        this.cards = new ArrayList<Card>(cards);
        this.street = newStreet;
    }
    
    public List<Card> getCards(){
        return new ArrayList<Card>(cards);
    }
    
    public Street getStreet(){
        return street;
    }
    
}
