package bot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import stats.Player;
import actions.LegalAction;
import actions.LegalActionType;
import cards.Card;
import cards.Hand;

public class Bot {
    private Hand hand;
    private final List<Player> otherPlayers;
    private final String name;
    private int stackSize;
    private double timeBank;
    private final int bigBlind;
    private final int numHands;
    private  int handId = 1;
    
    public Bot(String name, int stackSize, int bigBlind, int numHands, List<Player> otherPlayers){
        this.otherPlayers = new ArrayList<Player>(otherPlayers);
        this.name = name;
        this.stackSize = stackSize;
        this.bigBlind = bigBlind;
        this.numHands = numHands;
    }
    
    public void setHand(Hand newHand){
        hand = newHand;
    }
    
    public void setTimeBank(double timeBank){
        this.timeBank = timeBank;
    }
    
    public void setStackSize(int stackSize){
        this.stackSize = stackSize;
    }
    
    public void setHandId(int newHandId){
        this.handId = newHandId;
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
