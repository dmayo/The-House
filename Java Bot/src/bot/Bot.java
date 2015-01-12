package bot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import stats.Player;
import actions.LegalAction;
import actions.LegalActionType;
import cards.Card;

public class Bot {
    private Card hand[];
    private final List<Player> otherPlayers;
    private final String name;
    private int stackSize;
    
    public Bot(String name, int stackSize, List<Player> otherPlayers){
        this.otherPlayers = new ArrayList<Player>(otherPlayers);
        this.name = name;
        this.stackSize = stackSize;
    }
    
    public void setHand(Card hole1, Card hole2){
        hand = new Card[]{hole1,hole2};
    }
    
    
    public String getAction(Map<String, String> getAction){
        List<LegalAction> legalActions = determineLegalActions(getAction);
        
        return "CHECK";
    }
    
    
    
    /**
     * 
     * @param getAction 
     * @return a list of legal actions that the bot can make
     */
    private List<LegalAction> determineLegalActions(Map<String, String> getAction){
        
        final List<LegalAction> legalActions = new ArrayList<LegalAction>();
        final String legalActionString = getAction.get("legalActions");
        
        for(String action : legalActionString.split(" ")){
            if(action.contains(LegalActionType.BET.toString())){
                final String actionSplit[] = action.split(":");
                final int min = new Integer(actionSplit[1]);
                final int max = new Integer(actionSplit[2]);
                legalActions.add(new LegalAction(LegalActionType.BET,min,max,0));
            }
            if(action.contains(LegalActionType.CALL.toString())){
                final String actionSplit[] = action.split(":");
                final int amount = new Integer(actionSplit[1]);
                legalActions.add(new LegalAction(LegalActionType.BET,0,0,amount));
            }
            if(action.contains(LegalActionType.CHECK.toString())){
                legalActions.add(new LegalAction(LegalActionType.CHECK,0,0,0));
            }
            if(action.contains(LegalActionType.FOLD.toString())){
                legalActions.add(new LegalAction(LegalActionType.FOLD,0,0,0));
            }
            if(action.contains(LegalActionType.RAISE.toString())){
                final String actionSplit[] = action.split(":");
                final int min = new Integer(actionSplit[1]);
                final int max = new Integer(actionSplit[2]);
                legalActions.add(new LegalAction(LegalActionType.RAISE,min,max,0));
            }
        }
        
        return legalActions;
        
    }
    

}
