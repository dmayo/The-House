package bot;

import java.util.Map;

import cards.Card;

public class Bot {
    private Card hand[];
    
    public Bot(){
        
    }
    
    public void setHand(Card hole1, Card hole2){
        hand = new Card[]{hole1,hole2};
    }
    
    
    public String getAction(Map<String, String> gETACTION){
        return "CHECK";
    }
    

}
