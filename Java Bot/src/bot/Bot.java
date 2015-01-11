package bot;
import java.util.HashMap;
import java.util.Map;

import cards.Card;

public class Bot {
    private Card hand[];
    
    public Bot(){
        
    }
    
    public void setHand(Card hole1, Card hole2){
        hand = new Card[]{hole1,hole2};
    }
    
    
    public String getAction(String[] actionMessage){
        parseAction(actionMessage);
        
        return "CHECK";
    }
    
    public void parseAction(String[] actionMessage){
        //GETACTION potSize numBoardCards [boardCards] [stackSizes] numActivePlayers [activePlayers] numLastActions [lastActions] numLegalActions [legalActions] timebank
        Map<String, String> arg = new HashMap<String, String>();
        int i=1;
        arg.put("potSize", actionMessage[i++]);
        arg.put("numBoardCards", actionMessage[i++]);
        for (int x=0;x<Integer.parseInt(arg.get("numBoardCards"));x++){
            arg.merge("boardCards", actionMessage[i++], (value, newValue) -> value.concat(" ".concat(newValue)));
        }
        //not sure if numActivePlayers is actually the number of actualPlayers parameters
        arg.put("numActivePlayers", actionMessage[i++]);
        for (int x=0;x<3;x++){
            arg.merge("activePlayers", actionMessage[i++], (value, newValue) -> value.concat(" ".concat(newValue)));
        }
        
        arg.put("numLastActions", actionMessage[i++]);
        for (int x=0;x<Integer.parseInt(arg.get("numLastActions"));x++){
            arg.merge("lastActions", actionMessage[i++], (value, newValue) -> value.concat(" ".concat(newValue)));
        }
        
        arg.put("numLegalActionss", actionMessage[i++]);
        for (int x=0;x<Integer.parseInt(arg.get("numLegalActions"));x++){
            arg.merge("legalActions", actionMessage[i++], (value, newValue) -> value.concat(" ".concat(newValue)));
        }
        
        arg.put("timebank", actionMessage[i++]);

    }

}
