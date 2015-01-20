package bot;

import handEvaluator.HandStats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.media.jfxmedia.events.PlayerStateEvent.PlayerState;

import pbots_calc.Calculator;
import pbots_calc.Results;
import stats.Player;
import stats.Position;
import actions.ActionProbability;
import actions.LegalAction;
import actions.LegalActionType;
import actions.PerformedActionType;
import actions.Street;
import cards.BoardCards;
import cards.Card;
import cards.HandRange;
import cards.Hand;

public class StatBot {
    private Hand hand;
    private final List<Player> otherPlayers;
    private final String name;
    private int stackSize;
    private double timeBank;
    private final int bigBlind;
    private final int numHands;
    private int handId;
    private int seat;
    private int numActivePlayers;
    private int potSize = 0;
    private BoardCards boardCards;
    private LegalActionType previousAction = LegalActionType.NONE;
    private Map<String, String> preflopRangeMap = new HashMap<String, String>();
    private Map<String, Integer> preflopRangePercentMap = new HashMap<String, Integer>();
    
    public StatBot(String name, int stackSize, int bigBlind, int numHands, double timeBank, List<Player> otherPlayers){
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
        previousAction = LegalActionType.NONE;
        preflopRangeMap = new HashMap<String, String>();
        preflopRangePercentMap = new HashMap<String, Integer>();
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
    
    public Position getPosition(){
        if(seat == 1){
            return Position.FIRST;
        }
        if(seat == 2 && numActivePlayers == 3){
            return Position.MIDDLE;
        }
        return Position.LAST;
    }
    
    public void setNumActivePlayers(int newNumActivePlayers){
        numActivePlayers = newNumActivePlayers;
    }
    
    public String getName(){
        return name;
    }
    
    public String getAction(String legalActionsArray[]){
        Map<LegalActionType, LegalAction> legalActions = determineLegalActions(legalActionsArray);
           
        
        if(boardCards.getStreet() == Street.PREFLOP){
            String hands = hand.toString();

            for(Player player : otherPlayers){
                if(player.isActive() && player.getLastAction().getType() != PerformedActionType.FOLD && 
                        player.getLastAction().getType() != PerformedActionType.NONE){ // player has acted
                    if(player.getLastAction().getType() == PerformedActionType.RAISE){ // player raised so use PFR
                        int percentRange = (int)(100*player.getStats().getPFR(player.getPosition()));
                        String range = HandRange.getRangeFromPercent(percentRange);      
                        preflopRangeMap.put(player.getName(), range);
                        preflopRangePercentMap.put(player.getName(), percentRange);
                        hands += ":" + range;
                    } else if(player.getLastAction().getType() == PerformedActionType.CALL){ // player called so use VPIP
                        int percentRange = (int)(100*player.getStats().getVPIP(player.getPosition()));
                        String range = HandRange.getRangeFromPercent(percentRange);      
                        preflopRangeMap.put(player.getName(), range);
                        preflopRangePercentMap.put(player.getName(), percentRange);
                        hands += ":" + range;
                    } else{ 
                        preflopRangeMap.put(player.getName(), "xx");
                        hands += ":xx";
                    }
                } 
            }
            
            Results r = Calculator.calc(hands, boardCards.toString(), "", 5000);
            double equity = new Double(r.getEv().get(0));
            System.out.println("equity preflop: " + equity);
              
            ActionProbability actionProb = preFlopStrategy(equity);
            System.out.println("seat: " + getSeat());
            
            System.out.println(actionProb.toString());
            LegalAction actionToPerform = nextBest(legalActions, actionProb.randomlyChooseAction());
            System.out.println("action to perform: " + actionToPerform.getType());

            int amount = Math.max(actionToPerform.getAmount(), actionToPerform.getMax());
            if(actionToPerform.getMax() != 0){
                double amountToRaise = 0.7*equity*potSize / (1-equity);
                amount = (int) clamp(amountToRaise, actionToPerform.getMin(), actionToPerform.getMax());
            }
            
            previousAction = actionToPerform.getType();
            
            if(amount != 0){
                return actionToPerform.getType().toString() + ":" + amount;
            } else{
                return actionToPerform.getType().toString();
            }
        }
            
        else{    
            String handsToEvaluate = hand.toString();
            
            for(Player player : otherPlayers){
                if(player.getLastAction().getType() != PerformedActionType.FOLD && player.isActive()){
                    if(preflopRangePercentMap.containsKey(player.getName())){
                        if(boardCards.getStreet() == Street.FLOP){
                            handsToEvaluate += ":" + preflopRangeMap.get(player.getName());
                        }
                        else if(boardCards.getStreet() == Street.TURN){
                           int percent = Math.min(100, (int)(preflopRangePercentMap.get(player.getName())*1.5*player.getStats().getWTSD()));
                           String range = HandRange.getRangeFromPercent(percent);
                           handsToEvaluate += ":"+range;
                           
                        }
                        else if(boardCards.getStreet() == Street.RIVER){
                            int percent = Math.min(100, (int)(preflopRangePercentMap.get(player.getName())*1.2*player.getStats().getWTSD()));
                            String range = HandRange.getRangeFromPercent(percent);
                            handsToEvaluate += ":"+range;
                        }
                    } 
                    else{
                        handsToEvaluate += ":xx";
                    }
                }
            }
            System.out.println(handsToEvaluate);
            Results r = Calculator.calc(handsToEvaluate, boardCards.toString(), "", 5000);
            double equity = new Double(r.getEv().get(0));
            System.out.println("equity " + equity);
            
  
            ActionProbability actionProb = postFlopStrategy(legalActions, equity);
            System.out.println(actionProb.toString());
            LegalActionType actionTypeToPerform = actionProb.randomlyChooseAction();
            LegalAction actionToPerform = nextBest(legalActions, actionTypeToPerform);
            System.out.println("action to perform: " + actionToPerform.getType());
          
            int amount = Math.max(actionToPerform.getAmount(), actionToPerform.getMax());
            if(actionToPerform.getMax() != 0){
                double amountToRaiseOrBet = 0.8*equity*potSize / (1-equity);
                amount = (int) clamp(amountToRaiseOrBet, actionToPerform.getMin(), actionToPerform.getMax());
            }

            if(amount != 0){
                return actionToPerform.getType().toString() + ":" + amount;
            } else{
                return actionToPerform.getType().toString();
            }
        }
        
    }
    
    
    private static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }
    
    
    public void setPotSize(int newPotSize){
        potSize = newPotSize;
    }
   
    
    public LegalAction nextBest(Map<LegalActionType, LegalAction> legalActions, LegalActionType toPerform){
        if(legalActions.containsKey(toPerform)){
            return legalActions.get(toPerform);
        }
        
        if(toPerform == LegalActionType.RAISE){
            if(legalActions.containsKey(LegalActionType.BET)){
                return legalActions.get(LegalActionType.BET);
            }
            else if(legalActions.containsKey(LegalActionType.CALL)){
                return legalActions.get(LegalActionType.CALL);
            }
            else if(legalActions.containsKey(LegalActionType.CHECK)){
                return legalActions.get(LegalActionType.CHECK);
            }
        }
        
        if(toPerform == LegalActionType.BET){
            if(legalActions.containsKey(LegalActionType.RAISE)){
                return legalActions.get(LegalActionType.RAISE);
            }
            else if(legalActions.containsKey(LegalActionType.CALL)){
                return legalActions.get(LegalActionType.CALL);
            }
            else if(legalActions.containsKey(LegalActionType.CHECK)){
                return legalActions.get(LegalActionType.CHECK);
            }
        }
        
        if(toPerform == LegalActionType.CALL){
            if(legalActions.containsKey(LegalActionType.RAISE)){
                return legalActions.get(LegalActionType.RAISE);
            }
            else if(legalActions.containsKey(LegalActionType.BET)){
                return legalActions.get(LegalActionType.BET);
            }
            else if(legalActions.containsKey(LegalActionType.CHECK)){
                return legalActions.get(LegalActionType.CHECK);
            }
        }
        
        return new LegalAction(LegalActionType.FOLD,0,0,0);
    }
    
    
    /**
     * 
     * @param getAction 
     * @return a list of legal actions that the bot can make
     */
    private Map<LegalActionType, LegalAction> determineLegalActions(String legalActionsArray[]){
        
        final Map<LegalActionType, LegalAction> legalActions = new HashMap<LegalActionType, LegalAction>();
        
        for(String action : legalActionsArray){
            //System.out.println("legal action: " + action);
            final String actionSplit[] = action.split(":");
            LegalActionType actionType = LegalActionType.valueOf(actionSplit[0]);
            
            if(actionType == LegalActionType.BET || actionType == LegalActionType.RAISE){    
                final int min = new Integer(actionSplit[1]);
                final int max = new Integer(actionSplit[2]);
                legalActions.put(actionType, new LegalAction(actionType,min,max,0));
            }
            if(actionType == LegalActionType.CALL){
                final int amount = new Integer(actionSplit[1]);
                legalActions.put(actionType, new LegalAction(LegalActionType.CALL,0,0,amount));
            }
            if(actionType == LegalActionType.CHECK || actionType == LegalActionType.FOLD){
                legalActions.put(actionType, new LegalAction(actionType,0,0,0));
            }     
        }
              
        return legalActions;
        
    }
    
    
    private ActionProbability preFlopStrategy(double equity){
     
        if(equity > 0.8){
            return new ActionProbability(0, 0.1, 0.9, 0, 0);
        } else if(equity > 0.6){
            return new ActionProbability(0, 0.3, 0.7, 0, 0); 
        }else if(equity > 0.4){
            return new ActionProbability(0, 0.8, 0.2, 0, 0);
        } else{
            return new ActionProbability(0.9, 0.05, 0.05, 0, 0);
        }
     
    }

    private ActionProbability postFlopStrategy(Map<LegalActionType, LegalAction> legalActions, double equity){
        //get the call amount, call amount is 0 if call is not a legal action
        int callAmount = 0;
        if(legalActions.containsKey(LegalActionType.CALL)){
            callAmount=legalActions.get(LegalActionType.CALL).getAmount();
            double potOdds = callAmount/(double)(callAmount+potSize); //pot odds = call/(call+pot)
            System.out.println("potOdds: " + potOdds);
            //If pot odds are better than your pot equity, call or raise
            //If pot odds are worse, fold
            if(potOdds*1.5 < equity){
                //fold, call, raise, bet, check
                return new ActionProbability(0, 0.9, 0.1, 0, 0);
            }
            else{
                return new ActionProbability(0.9, 0.05, 0.05, 0, 0);
            }
        } else{
            return new ActionProbability(1.0-equity, 0, 0, equity, 0);
        }
        
        
        
    }

}
