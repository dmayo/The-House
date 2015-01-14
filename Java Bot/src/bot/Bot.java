package bot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import stats.Player;
import actions.ActionProbability;
import actions.LegalAction;
import actions.LegalActionType;
import actions.PerformedActionType;
import actions.Street;
import cards.BoardCards;
import cards.Card;
import cards.EquitySquaredRanking;
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
        
        if(boardCards.getStreet() == Street.PREFLOP){
            ActionProbability actionProb = preFlopStrategy();
            
        }
        
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
    
    
    private ActionProbability preFlopStrategy(){
        final int initialPot = bigBlind + bigBlind/2;
        //public ActionProbability(double probFold, double probCall, double probRaise, double probBet, double probCheck)
        // we are first to act and everyone is still playing
        // play upto KQ
        if(seat == 1){
            if(EquitySquaredRanking.getRank(hand) <= 20){
                return new ActionProbability(0, 0.1, 0.9, 0, 0);
            } else{
                return new ActionProbability(0.8, 0.1, 0.1, 0, 0);
            }
        }
        
        // we are the second to act with small blind
        else if(seat == 2 && numActivePlayers == 3){
            if(potSize == initialPot){ // we are the first to act - button folds
                if(EquitySquaredRanking.getRank(hand) <= 20){
                    return new ActionProbability(0, 0.1, 0.9, 0, 0);
                } else{
                    return new ActionProbability(0.8, 0.1, 0.1, 0, 0);
                }
            } else if(potSize ==  initialPot + bigBlind){ // button limped in
                if(EquitySquaredRanking.getRank(hand) <= 20){
                    return new ActionProbability(0, 0.1, 0.9, 0, 0);
                } else if(EquitySquaredRanking.getRank(hand) <= 40){
                    return new ActionProbability(0.1, 0.7, 0.2, 0, 0);
                } else{
                    return new ActionProbability(0.9, 0.1, 0.1, 0, 0);
                }
            } else{ // button raised
                if(EquitySquaredRanking.getRank(hand) <= 10){
                    return new ActionProbability(0, 0.1, 0.9, 0, 0);
                }
                else if(EquitySquaredRanking.getRank(hand) <=  20){
                    return new ActionProbability(0, 0.8, 0.2, 0, 0);
                } else{
                    return new ActionProbability(0.9, 0.1, 0.1, 0, 0);
                }
            }
        }
        
        // we are the first to act with small blind
        else if(seat == 2 && numActivePlayers == 2){
            if(EquitySquaredRanking.getRank(hand) <= 20){
                return new ActionProbability(0, 0.1, 0.9, 0, 0);
            } else if(EquitySquaredRanking.getRank(hand) <= 40){
                return new ActionProbability(0.2, 0.3, 0.5, 0, 0);
            } else{
                return new ActionProbability(0.8, 0.1, 0.1, 0, 0);
            }
        }
        
       // we are the last to act with big blind
        else if(seat == 3 && numActivePlayers == 3){
            
            if(otherPlayers.get(0).limped() && otherPlayers.get(1).limped()){ // button limped and small blind limped in
                if(EquitySquaredRanking.getRank(hand) <= 25){
                    return new ActionProbability(0, 0, 0.9, 0, 0.1);
                } else if(EquitySquaredRanking.getRank(hand) <= 40){
                    return new ActionProbability(0.1, 0, 0.3, 0, 0.6);
                } else{
                    return new ActionProbability(0.8, 0, 0.1, 0, 0.1);
                }
            } else{ // someone raised
                if(EquitySquaredRanking.getRank(hand) <= 10){
                    return new ActionProbability(0, 0.1, 0.9, 0, 0);
                } else if(EquitySquaredRanking.getRank(hand) <=  20){
                    return new ActionProbability(0, 0.8, 0.2, 0, 0);
                } else if(EquitySquaredRanking.getRank(hand) <=  40){
                    return new ActionProbability(0.4, 0.6, 0, 0, 0);
                } else{
                    return new ActionProbability(0.9, 0.1,0, 0, 0);
                }
            }
        }
        
        // we are the last to act with big blind
        else if(seat == 3 && numActivePlayers == 2){
            
            if(otherPlayers.get(0).limped() || otherPlayers.get(1).limped()){ // other player limped
                if(EquitySquaredRanking.getRank(hand) <= 30){
                    return new ActionProbability(0, 0, 0.9, 0, 0.1);
                } else if(EquitySquaredRanking.getRank(hand) <= 50){
                    return new ActionProbability(0.1, 0, 0.3, 0, 0.6);
                } else{
                    return new ActionProbability(0.8, 0, 0.1, 0, 0.1);
                }
            } else{ // someone raised
                if(EquitySquaredRanking.getRank(hand) <= 10){
                    return new ActionProbability(0, 0.1, 0.9, 0, 0);
                } else if(EquitySquaredRanking.getRank(hand) <=  30){
                    return new ActionProbability(0, 0.8, 0.2, 0, 0);
                } else if(EquitySquaredRanking.getRank(hand) <=  50){
                    return new ActionProbability(0.4, 0.6, 0, 0, 0);
                } else{
                    return new ActionProbability(0.9, 0.1,0, 0, 0);
                }
            }
        }
        
        else{
            return new ActionProbability(0.9, 0.1,0, 0, 0);
        }
        
        
        
    }
    

}
