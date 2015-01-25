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
    private List<LegalActionType> previousActions = new ArrayList<LegalActionType>();
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
        previousActions = new ArrayList<LegalActionType>();
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
           return preFlopStrategy(legalActions);
        }
            
        else{    
            return postFlopStrategy(legalActions);
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
    
    
    
    
    private String preFlopStrategy(Map<LegalActionType, LegalAction> legalActions){
        //pbots equity calculator
        String hands = hand.toString();

        for(Player player : otherPlayers){
            if(player.isActive() && player.getLastAction().getType() != PerformedActionType.FOLD && 
                    player.getLastAction().getType() != PerformedActionType.NONE){ // player has acted
                if(player.didPFR()){ // player raised so use PFR
                    double precentRangeDouble = player.getStats().getPFR(player.getPosition());
                    precentRangeDouble *= player.didPFR3B() ? player.getStats().getR3B() : 1;
                    int percentRange = (int)(100*precentRangeDouble);
                    String range = HandRange.getRangeFromPercent(percentRange);      
                    preflopRangeMap.put(player.getName(), range);
                    preflopRangePercentMap.put(player.getName(), percentRange);
                    hands += ":" + range;
                } else if(player.didVPIP()){ // player called so use VPIP
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
        
        System.out.println("hands: " + hands);
        Results r = Calculator.calc(hands, boardCards.toString(), "", 5000);
        double equity = new Double(r.getEv().get(0));
        System.out.println("equity preflop: " + equity);        
        System.out.println("seat: " + getSeat());
        
        ActionProbability actionProb;
        double fractionOfPot = 0.75;
        
        if(numActivePlayers == 2 && seat == 1  && previousActions.size()==0){
            Player player = otherPlayers.get(1);
            int percentRange = (int)(100*player.getStats().getVPIP(player.getPosition()));
            String range = HandRange.getRangeFromPercent(percentRange); 
            Results results = Calculator.calc(hand.toString()+":"+range, boardCards.toString(), "", 5000);
            double e = new Double(results.getEv().get(0));
            System.out.println("e: " + e);
            if(e < (4/11.0)){
                double foldPercentage = (4.0 - 11.0*e) / (7.0 - 11.0*e);
                double playerFoldPercentage = player.getStats().getFoldPreFlop(Position.LAST);
                System.out.println("fold percentage: " + foldPercentage);
                System.out.println("player fold percentage: " + playerFoldPercentage);
                if(playerFoldPercentage > foldPercentage){
                    actionProb = new ActionProbability(0, 0, 1, 0, 0);
                } else{
                    actionProb = new ActionProbability(1, 0, 0, 0, 0);
                }
            } 
            else if(equity > 0.4){
                actionProb = new ActionProbability(0, 0.1, 0.9, 0, 0);
            }
            else{
                actionProb = new ActionProbability(0.9, 0.05, 0.05, 0, 0);               
            }
            fractionOfPot = 1;
        }
        else if(previousActions.contains(LegalActionType.RAISE)){
            if(equity > 0.9){
                actionProb = new ActionProbability(0, 0.1, 0.9, 0, 0);
            }
            else if(equity > 0.7){
                actionProb = new ActionProbability(0, 0.6, 0.4, 0, 0);
            } else if(equity > 0.6){
                actionProb = new ActionProbability(0, 0.7, 0.3, 0, 0); 
            }else if(equity > 0.45){
                actionProb = new ActionProbability(0.05, 0.95, 0, 0, 0);
            } else{
                actionProb = new ActionProbability(0.9, 0.05, 0.05, 0, 0);
            }
        } else{
            if(equity > 0.8){
                actionProb = new ActionProbability(0, 0.1, 0.9, 0, 0);
            } else if(equity > 0.6){
                actionProb = new ActionProbability(0, 0.3, 0.7, 0, 0); 
            }else if(equity > 0.4){
                actionProb = new ActionProbability(0, 0.5, 0.5, 0, 0);
            } else{
                actionProb = new ActionProbability(0.9, 0.05, 0.05, 0, 0);               
            }
        }
        
    
        System.out.println(actionProb.toString());
        LegalAction actionToPerform = nextBest(legalActions, actionProb.randomlyChooseAction());
        System.out.println("action to perform: " + actionToPerform.getType());

        int amount = Math.max(actionToPerform.getAmount(), actionToPerform.getMax());
        
        if(actionToPerform.getMax() != 0){
            double amountToRaise = fractionOfPot*potSize;
            amount = (int) clamp(amountToRaise, actionToPerform.getMin(), actionToPerform.getMax());
        }
        
        previousActions.add(actionToPerform.getType());
        
        if(amount != 0){
            return actionToPerform.getType().toString() + ":" + amount;
        } else{
            return actionToPerform.getType().toString();
        }
     
    }




    private String postFlopStrategy(Map<LegalActionType, LegalAction> legalActions){
        // --- equity calculation based on preflop range
        String handsToEvaluate = hand.toString();

        for(Player player : otherPlayers){
            if(player.getLastAction().getType() != PerformedActionType.FOLD && player.isActive()){
                if(preflopRangePercentMap.containsKey(player.getName())){
                    handsToEvaluate += ":" + preflopRangeMap.get(player.getName());
                } 
                else{
                    handsToEvaluate += ":xx";
                }
            }
        }

        System.out.println("hands: " + handsToEvaluate);
        Results r = Calculator.calc(handsToEvaluate, boardCards.toString(), "", 5000);
        double equity = new Double(r.getEv().get(0));
        System.out.println("equity " + equity);  
        // ---------------------------------------------------    

        ActionProbability actionProb;

        if(legalActions.containsKey(LegalActionType.CALL)){
            int callAmount=legalActions.get(LegalActionType.CALL).getAmount();
            double potOdds = callAmount/(double)(callAmount+potSize); //pot odds = call/(call+pot)
            System.out.println("potOdds: " + potOdds);
            //If pot odds are better than your pot equity, call or raise
            //If pot odds are worse, fold
            if(potOdds*1.3 < equity){
                //fold, call, raise, bet, check
                actionProb = new ActionProbability(0, 1-equity, equity, 0, 0);       
            }
            else{
               actionProb = new ActionProbability(0.95, 0, 0.05, 0, 0);
            }
        } else{ // we are first to act or those before us checked
            if(equity > 0.65){
                actionProb = new ActionProbability(0, 0, 0, 1, 0);
            } else{
                actionProb = new ActionProbability(0.95, 0,0, 0.05, 0);
            }
        }        
        
        System.out.println(actionProb.toString());
        LegalAction actionToPerform = nextBest(legalActions, actionProb.randomlyChooseAction());
        System.out.println("action to perform: " + actionToPerform.getType());
      
        int amount = Math.max(actionToPerform.getAmount(), actionToPerform.getMax());
        if(actionToPerform.getMax() != 0){
            double amountToRaiseOrBet = 0.75*potSize;
            amount = (int) clamp(amountToRaiseOrBet, actionToPerform.getMin(), actionToPerform.getMax());
        }

        if(amount != 0){
            return actionToPerform.getType().toString() + ":" + amount;
        } else{
            return actionToPerform.getType().toString();
        }
            
    }
    
    
    
    private String flopStrategy(Map<LegalActionType, LegalAction> legalActions){
        // --- equity calculation based on preflop range
        String handsToEvaluate = hand.toString();

        for(Player player : otherPlayers){
            if(player.getLastAction().getType() != PerformedActionType.FOLD && player.isActive()){
                if(preflopRangePercentMap.containsKey(player.getName())){
                    handsToEvaluate += ":" + preflopRangeMap.get(player.getName());
                } 
                else{
                    handsToEvaluate += ":xx";
                }
            }
        }

        System.out.println("hands: " + handsToEvaluate);
        Results r = Calculator.calc(handsToEvaluate, boardCards.toString(), "", 5000);
        double equity = new Double(r.getEv().get(0));
        System.out.println("equity " + equity);  
        // ---------------------------------------------------
        
        ActionProbability actionProb;
        
        if(legalActions.containsKey(LegalActionType.CALL)){
            int callAmount=legalActions.get(LegalActionType.CALL).getAmount();
            double potOdds = callAmount/(double)(callAmount+potSize); //pot odds = call/(call+pot)
            System.out.println("potOdds: " + potOdds);
            //If pot odds are better than your pot equity, call or raise
            //If pot odds are worse, fold
            if(potOdds*1.3 < equity){
                //fold, call, raise, bet, check
                actionProb = new ActionProbability(0, 1-equity, equity, 0, 0);       
            }
            else{
               actionProb = new ActionProbability(0.95, 0, 0.05, 0, 0);
            }
        } else{ // we are first to act or those before us checked
            if(equity > 0.65){
                actionProb = new ActionProbability(0, 0, 0, 1, 0);
            } else{
                actionProb = new ActionProbability(0.95, 0,0, 0.05, 0);
            }
        }        
        
        System.out.println(actionProb.toString());
        LegalAction actionToPerform = nextBest(legalActions, actionProb.randomlyChooseAction());
        System.out.println("action to perform: " + actionToPerform.getType());
      
        int amount = Math.max(actionToPerform.getAmount(), actionToPerform.getMax());
        if(actionToPerform.getMax() != 0){
            double amountToRaiseOrBet = 0.75*potSize;
            amount = (int) clamp(amountToRaiseOrBet, actionToPerform.getMin(), actionToPerform.getMax());
        }

        if(amount != 0){
            return actionToPerform.getType().toString() + ":" + amount;
        } else{
            return actionToPerform.getType().toString();
        }

    }

}
