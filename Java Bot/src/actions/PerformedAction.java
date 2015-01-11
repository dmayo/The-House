package actions;
import java.util.ArrayList;
import java.util.List;

import cards.Card;

public class PerformedAction {
    private final String actor;
    private final int amount;
    private final PerformedActionType type;
    private final List<Card> cards;
    
    
    public PerformedAction(String actor, PerformedActionType type, int amount, List<Card> cards){
        this.actor = actor;
        this.type = type;
        this.amount = amount;
        this.cards = new ArrayList<Card>(cards);
    }
    
    public String getActor(){
        return actor;
    }
    
    public PerformedActionType getType(){
        return type;
    }
    
    public int getAmount(){
        return amount;
    }
    
    public List<Card> getCards(){
        return new ArrayList<Card>(cards);
    }
}
