package bot;

import handEvaluator.HandStats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import stats.Player;
import actions.ActionProbability;
import actions.LegalAction;
import actions.LegalActionType;
import actions.PerformedActionType;
import actions.Street;
import cards.BoardCards;
import cards.Card;
import cards.HandRange;
import cards.Hand;

public class TruthBot {
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
    
    public TruthBot(String name, int stackSize, int bigBlind, int numHands, double timeBank, List<Player> otherPlayers){
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
        System.out.println("New Hand -----");
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
        Map<LegalActionType, LegalAction> legalActions = determineLegalActions(legalActionsArray);
        double equity =  HandStats.monteCarloEquity(5000, hand, boardCards);
        equity = numActivePlayers == 3 ? equity*equity : equity;
        //System.out.println("equity " + equity);
        
        if(boardCards.getStreet() == Street.PREFLOP){
            
            ActionProbability actionProb = preFlopStrategy();
            System.out.println(hand.toString());
            System.out.println("seat: " + getSeat());
            System.out.println("equity: " + equity);
            System.out.println("equity squared ranking: " + HandRange.getRank(hand));
            
            System.out.println(actionProb.toString());
            LegalActionType actionTypeToPerform = actionProb.randomlyChooseAction();
            LegalAction actionToPerform = nextBest(legalActions, actionTypeToPerform);

            System.out.println("action to perform: " + actionToPerform.getType());


            int amount = Math.max(actionToPerform.getAmount(), actionToPerform.getMax());
            if(actionToPerform.getMax() != 0){
                amount = (int) (actionToPerform.getMin() + 0.7*equity*(actionToPerform.getMax() - actionToPerform.getMin()));
            }
            
            if(amount != 0){
                return actionToPerform.getType().toString() + ":" + amount;
            } else{
                return actionToPerform.getType().toString();
            }
        }
            
        else{    
            ActionProbability actionProb = postFlopStrategy(legalActions, equity);
            //System.out.println(hand.toString());
            //System.out.println(actionProb.toString());
            LegalActionType actionTypeToPerform = actionProb.randomlyChooseAction();
            LegalAction actionToPerform = nextBest(legalActions, actionTypeToPerform);
            //System.out.println("action to perform: " + actionToPerform.getType());
          
            int amount = Math.max(actionToPerform.getAmount(), actionToPerform.getMax());
            if(actionToPerform.getMax() != 0){
                amount = (int) (actionToPerform.getMin() + 0.7*equity*(actionToPerform.getMax() - actionToPerform.getMin()));
            }

            if(amount != 0){
                return actionToPerform.getType().toString() + ":" + amount;
            } else{
                return actionToPerform.getType().toString();
            }
        }
        
        //System.out.println("Street: "+ boardCards.getStreet());
        //System.out.println("------------------------------------------------");
        //return "CHECK";
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
    
    
    private ActionProbability preFlopStrategy(){
        final int initialPot = bigBlind + bigBlind/2;
        //public ActionProbability(double probFold, double probCall, double probRaise, double probBet, double probCheck)
        // we are first to act and everyone is still playing
        // play upto KQ
        int rank = HandRange.getRank(hand);
        //System.out.println("rank: " + rank);
        //1 is the best ranking
        if(seat == 1){
            //better than AK which is rank 10, equity: 0.43599609
            if(rank <= 10){
                return new ActionProbability(0, 0.3, 0.7, 0, 0);
            }
            //better than JT same suit which is rank 30
            else if(rank <= 30){
                return new ActionProbability(0, 0.4, 0.6, 0, 0);
            }
            else{
                return new ActionProbability(1, 0, 0, 0, 0);
            }
        }
        
        // we are the second to act with small blind
        else if(seat == 2 && numActivePlayers == 3){
            if(potSize == initialPot){ // we are the first to act - button folds
                if(rank <= 10){
                    return new ActionProbability(0, 0.2, 0.8, 0, 0);
                }
                else if(rank <= 30){
                    return new ActionProbability(0, 0.4, 0.6, 0, 0);
                } else{
                    return new ActionProbability(1, 0, 0, 0, 0);
                }
            } else if(potSize ==  initialPot + bigBlind){ // button limped in
                if(rank <= 10){
                    return new ActionProbability(0, 0.2, 0.8, 0, 0);
                }
                else if(rank <= 30){
                    return new ActionProbability(0, 0.4, 0.6, 0, 0);
                } else if(rank <= 50){
                    return new ActionProbability(0.1, 0.7, 0.2, 0, 0);
                } else{
                    return new ActionProbability(1, 0, 0, 0, 0);
                }
            } else{ // button raised
                if(rank <= 10){
                    return new ActionProbability(0, 0.3, 0.7, 0, 0);
                }
                else if(rank <= 20){
                    return new ActionProbability(0, 0.4, 0.6, 0, 0);
                }
                else if(rank <=  40){
                    return new ActionProbability(0, 0.8, 0.2, 0, 0);
                } else{
                    return new ActionProbability(1, 0, 0, 0, 0);
                }
            }
        }
        
        // we are the first to act with big blind
        else if(seat == 2 && numActivePlayers == 2){
            if(rank <= 10){
                return new ActionProbability(0, 0.2, 0.8, 0, 0);
            }
            else if(rank <= 30){
                return new ActionProbability(0, 0.4, 0.6, 0, 0);
            } else if(rank <= 50){
                return new ActionProbability(0.2, 0.4, 0.4, 0, 0);
            } else{
                return new ActionProbability(1, 0, 0, 0, 0);
            }
        }
        
       // we are the last to act with big blind
        else if(seat == 3 && numActivePlayers == 3){
            
            if(otherPlayers.get(0).limped() && otherPlayers.get(1).limped()){ // button limped and small blind limped in
                if(rank <= 10){
                    return new ActionProbability(0, 0, 0.9, 0, 0.1);
                }
                else if(rank <= 30){
                    return new ActionProbability(0, 0, 0.7, 0, 0.3);
                } else if(rank <= 45){
                    return new ActionProbability(0.1, 0, 0.2, 0, 0.7);
                } else{
                    return new ActionProbability(1, 0, 0, 0, 0);
                }
            } else{ // someone raised
                if(rank <= 20){
                    return new ActionProbability(0, 0.4, 0.6, 0, 0);
                } else if(rank <=  30){
                    return new ActionProbability(0, 0.8, 0.2, 0, 0);
                } else if(rank <=  50){
                    return new ActionProbability(0.4, 0.6, 0, 0, 0);
                } else{
                    return new ActionProbability(1, 0, 0, 0, 0);
                }
            }
        }
        
        // we are the last to act with big blind
        else if(seat == 3 && numActivePlayers == 2){
            
            if(otherPlayers.get(0).limped() || otherPlayers.get(1).limped()){ // other player limped
                if(rank <= 20){
                    return new ActionProbability(0, 0, 0.8, 0, 0.2);
                }
                else if(rank <= 40){
                    return new ActionProbability(0, 0, 0.6, 0, 0.4);
                } else if(rank <= 50){
                    return new ActionProbability(0.1, 0, 0.3, 0, 0.6);
                } else{
                    return new ActionProbability(1, 0, 0, 0, 0);
                }
            } else{ // someone raised
                if(rank <= 20){
                    return new ActionProbability(0, 0.4, 0.6, 0, 0);
                } else if(rank <=  40){
                    return new ActionProbability(0, 0.8, 0.2, 0, 0);
                } else if(rank <=  55){
                    return new ActionProbability(0.4, 0.6, 0, 0, 0);
                } else{
                    return new ActionProbability(1, 0, 0, 0, 0);
                }
            }
        }
        
        else{
            return new ActionProbability(1, 0, 0, 0, 0);
        }

    }
    
    private ActionProbability postFlopStrategy(Map<LegalActionType, LegalAction> legalActions, double equity){
        //get the call amount, call amount is 0 if call is not a legal action
        int callAmount = 0;
        if(legalActions.containsKey(LegalActionType.CALL)){
            callAmount=legalActions.get(LegalActionType.CALL).getAmount();
            double potOdds = callAmount/(double)(callAmount+potSize); //pot odds = call/(call+pot)
            //System.out.println("potOdds: " + potOdds);
            //If pot odds are better than your pot equity, call or raise
            //If pot odds are worse, fold
            if(potOdds*1.6 < equity){
                //fold, call, raise, bet, check
                return new ActionProbability(0, 0.8, 0.2, 0, 0);
            }
            else{
                return new ActionProbability(0.8, 0.1, 0.1, 0, 0);
            }
        } else{
            return new ActionProbability(1.0-(equity*equity), 0, 0, equity*equity, 0);
        }
        
        
        
    }

}
