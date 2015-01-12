package cards;
import java.util.ArrayList;
import java.util.List;

import actions.Street;

public class BoardCards {
    
    private final List<Card> cards;
    private final Street street;
    
    public BoardCards(Street street, List<Card> cards){
        this.cards = new ArrayList<Card>(cards);
        this.street = street;
    }
    
    public List<Card> getCards(){
        return new ArrayList<Card>(cards);
    }
    
    public Street getStreet(){
        return street;
    }
    
}
