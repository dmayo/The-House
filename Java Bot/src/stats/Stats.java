package stats;
import java.util.HashMap;
import java.util.Map;

import actions.*;

public class Stats {
   
    private final Map<Street, Integer> numStreetsSeen;
    private final Map<Street, Integer> totalStreets;
    private final Map<PerformedActionType, Integer> numActionsDone  = new HashMap<PerformedActionType, Integer>();
    private int actionOpportunities = 0;
    private int potsWon = 0;
    private int numEligibleMatches = 0;
    private int numVPIP = 0;
    private int numPFR = 0;
    private int numCouldDoActionPreFlop = 0;
    
    public Stats(){
    
        Map<Street, Integer> initial = new HashMap<Street,Integer>();
        initial.put(Street.PREFLOP, 0);
        initial.put(Street.FLOP, 0);
        initial.put(Street.TURN, 0);
        initial.put(Street.RIVER, 0);
        numStreetsSeen = new HashMap<Street, Integer>(initial);
        totalStreets = new HashMap<Street, Integer>(initial);
        numActionsDone.put(PerformedActionType.BET, 0);
        numActionsDone.put(PerformedActionType.CALL, 0);
        numActionsDone.put(PerformedActionType.CHECK, 0);
        numActionsDone.put(PerformedActionType.RAISE, 0);
        numActionsDone.put(PerformedActionType.FOLD, 0);
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
    public double percentActionDone(PerformedActionType action){
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
     * Call whenever the player voluntarily puts money in the pot on the preflop. 
     * (i.e. blinds do not count) So call when the player bets, calls, or raises preflop.
     */
    public void VPIP(){
        numVPIP++;
    }
    
    
    /**
     * @return the percentage of preflops the player voluntarily put money in the pot.
     */
    public double getVPIP(){
        return numVPIP / (double) numCouldDoActionPreFlop;
    }
    
    
    /**
     * Call whenever a player raises on preflop
     */
    public void PFR(){
        numPFR++;
    }
    
    
    /**
     * Call whenever a player has the opportunity to do an action preflop
     */
    public void numCouldDoActionPreFlop(){
        numCouldDoActionPreFlop++;
    }
    
    /**
     * @return the percentage of times a player raise on preflop
     */
    public double getPFR(){
        return numPFR / (double) numCouldDoActionPreFlop;
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
    
    
    @Override
    public String toString(){
        return "VPIP: " + getVPIP() + "\n" + 
               "PFR: " + getPFR() + "\n";               
    }
    
}