package bot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import stats.Player;
import actions.LegalAction;
import actions.LegalActionType;
import actions.Street;
import cards.BoardCards;
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
    private  int handId;
    private int seat;
    private int numActivePlayers;
    private int potSize = 0;
    private BoardCards boardCards;
    
    public Bot(String name, int stackSize, int bigBlind, int numHands, double timeBank, List<Player> otherPlayers){
        this.otherPlayers = new ArrayList<Player>(otherPlayers);
        this.name = name;
        this.stackSize = stackSize;
        this.bigBlind = bigBlind;
        this.numHands = numHands;
        this.timeBank = timeBank;
        this.boardCards = new BoardCards(Street.PREFLOP, new ArrayList<Card>());
        this.handId = 1;
        this.numActivePlayers = 3;
    }
    
    public void setBoardCards(BoardCards newBoardCards){
        this.boardCards = newBoardCards;
    }
    
    public void setHand(Hand newHand){
        hand = newHand;
    }
    
    public void setTimeBank(double newTimeBank){
        timeBank = newTimeBank;
    }
    
    public void setStackSize(int newStackSize){
        stackSize = newStackSize;
    }
    
    public void setHandId(int newHandId){
        handId = newHandId;
    }
    
    public void setSeat(int newSeat){
        seat = newSeat;
    }
    
    public int getSeat(){
        return seat;
    }
    
    public void setNumActivePlayers(int newNumActivePlayers){
        numActivePlayers = newNumActivePlayers;
    }
    
    public String getName(){
        return name;
    }
    
    public String getAction(String legalActionsArray[]){
        List<LegalAction> legalActions = determineLegalActions(legalActionsArray);
        
        return "CHECK";
    }
    
    public void setPotSize(int newPotSize){
        potSize = newPotSize;
    }
   
    
    
    
    /**
     * 
     * @param getAction 
     * @return a list of legal actions that the bot can make
     */
    private List<LegalAction> determineLegalActions(String legalActionsArray[]){
        
        final List<LegalAction> legalActions = new ArrayList<LegalAction>();
        
        for(String action : legalActionsArray){
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
    
    
    private void preFlopStrategy(){
        // we are first to act and everyone is still playing
        //
        if(seat == 1 && numActivePlayers == 3){
            
        }
    }
    

}
