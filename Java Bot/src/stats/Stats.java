package stats;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import cards.Card;
import cards.CardSet;
import cards.Hand;
import actions.*;

public class Stats {
   
    private Map<Street, Integer> numStreetsSeen = new HashMap<Street, Integer>();
    private Map<Street, Integer> totalStreets = new HashMap<Street, Integer>();
    private Map<PerformedActionType, Integer> numActionsDone  = new HashMap<PerformedActionType, Integer>();
    private int actionOpportunities = 0;
    private int potsWon = 0;
    private int numEligibleMatches = 0;
    private Map<Hand, Double> handProbs = new HashMap<Hand, Double>();
    
    public Stats(){
        //creates uniform probability distribution of every possible hand
        CombinationIterator comb = new CombinationIterator(Arrays.asList(CardSet.freshDeck().toArray()),2);
        while(comb.hasNext()) {
            handProbs.put(new Hand((Card)comb.next().get(0), (Card) comb.next().get(1)), 1.0/1326);
         }
    }
    
    public double getHandProbability(Hand hand){
        return handProbs.get(hand);
    }
    
    
    /**
     * call this method when a player sees a street (i.e. did not fold before hand)
     * @param street street they saw
     */
    public void sawStreet(Street street){
        if(numStreetsSeen.containsKey(street)){
            numStreetsSeen.put(street,numStreetsSeen.get(street)+1);
        } else{
            numStreetsSeen.put(street,1);
        }
    }
   
    
    /**
     * run method when player folds, calls, bets, or raises
     * @param action
     */
    public void preformedAction(PerformedActionType action){
        if(numActionsDone.containsKey(action)){
            numActionsDone.put(action,numActionsDone.get(action)+1);
        } else{
            numActionsDone.put(action,1);
        }
    }
    
    /**
     * Increase the number of streets that have occurred in matches where the player was
     * not eliminated (stack size > 0)
     */
    public void incrementStreetCount(Street street){
        if(totalStreets.containsKey(street)){
            totalStreets.put(street,totalStreets.get(street)+1);
        } else{
            totalStreets.put(street,1);
        }
    }
    
    
    /**
     * 
     * @param street
     * @return the percent of times this player saw the street when he was still playing
     */
    public double percentStreetSeen(Street street){
        return numStreetsSeen.get(street) / ((double)totalStreets.get(street));
    }
    
    
    /**
     * increments the number of opportunities the player had to make an action (fold, bet, etc.).
     * call this method on every street that the player is eligible for.
     */
    public void incrementActionOpportunities(){
        actionOpportunities++;
    }
    
    
    
    /**
     * used to calculate the percent of times a player folds, calls, bets, or raises
     * @param action needs to be fold, call, bet, or raise
     * @return
     */
    public double perecentActionDone(PerformedActionType action){
        return numActionsDone.get(action) / (double) actionOpportunities;
    }
    
    
    /**
     * increments the number of eligible matches that the player
     * has been in
     */
    public void incrementEligibleMatches(){
        numEligibleMatches++;
    }
    
    
    /**
     * 
     * @return the percent of times the player won a pot in
     * matches where they are still playing
     */
    public double percentPotsWon(){
        return potsWon / ((double) numEligibleMatches);
    }
    
    
    /**
     * Call when player wins a pot
     */
    public void wonPot(){
        potsWon++;
    }
    
    /**
     * Aggression factor = (NumberBets + NumberRaises)/NumberCalls
     * Aggression factor > 1 means the player is aggressive
     * Aggression factor < 1 means the player is passive
     * @return aggression factor
     */
    public double getAggressionFactor(){
        double AF = (double)(numActionsDone.get(PerformedActionType.BET) + numActionsDone.get(PerformedActionType.RAISE))  / numActionsDone.get(PerformedActionType.CALL);
        return AF;
    }
    
}