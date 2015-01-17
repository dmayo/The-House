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
    private final Map<Position, Integer> numVPIP;
    private final Map<Position, Integer> numPFR;
    private final Map<Position, Integer> numCouldDoActionPreFlop;
    private int numWTSD = 0;
    private int numW$SD = 0;
    
    public Stats(){
    
        Map<Street, Integer> initialStreetCounts = new HashMap<Street,Integer>();
        initialStreetCounts.put(Street.PREFLOP, 0);
        initialStreetCounts.put(Street.FLOP, 0);
        initialStreetCounts.put(Street.TURN, 0);
        initialStreetCounts.put(Street.RIVER, 0);
        numStreetsSeen = new HashMap<Street, Integer>(initialStreetCounts);
        totalStreets = new HashMap<Street, Integer>(initialStreetCounts);
        numActionsDone.put(PerformedActionType.BET, 0);
        numActionsDone.put(PerformedActionType.CALL, 0);
        numActionsDone.put(PerformedActionType.CHECK, 0);
        numActionsDone.put(PerformedActionType.RAISE, 0);
        numActionsDone.put(PerformedActionType.FOLD, 0);
        
        Map<Position, Integer> initialStats = new HashMap<Position, Integer>();
        initialStats.put(Position.FIRST, 0);
        initialStats.put(Position.MIDDLE, 0);
        initialStats.put(Position.LAST, 0);
        numVPIP = new HashMap<Position, Integer>(initialStats);
        numPFR = new HashMap<Position, Integer>(initialStats);
        numCouldDoActionPreFlop = new HashMap<Position, Integer>(initialStats);
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
     * Call whenever the player voluntarily puts money in the pot on the preflop. 
     * (i.e. blinds do not count) So call when the player bets, calls, or raises preflop.
     */
    public void VPIP(Position position){
        numVPIP.put(position, numVPIP.get(position)+1);
    }
    
    
    /**
     * @return the percentage of preflops the player voluntarily put money in the pot from given position
     */
    public double getVPIP(Position position){
        return numVPIP.get(position) / (double) numCouldDoActionPreFlop.get(position);
    }

   
    /**
     * @return the percentage of preflops the player voluntarily put money in the pot
     */
    public double getVPIP(){
        int totalVPIP = 0;
        int totalCouldDoActionPreFlop = 0;
        for(Position position : Position.values()){
           totalVPIP += numVPIP.get(position);
           totalCouldDoActionPreFlop += numCouldDoActionPreFlop.get(position);
        }
        
        return totalVPIP / (double) totalCouldDoActionPreFlop;
    }
    
    
    /**
     * Call whenever a player raises on preflop
     */
    public void PFR(Position position){
        numPFR.put(position, numPFR.get(position)+1);

    }
    
    
    /**
     * @return the percentage of times a player raise on preflop from given position
     * @param position the position of the player
     */
    public double getPFR(Position position){
        return numPFR.get(position) / (double) numCouldDoActionPreFlop.get(position);
    }
    
    
    /**
     * @return the percentage of times a player raise on preflop
     */
    public double getPFR(){
        int totalPFR = 0;
        int totalCouldDoActionPreFlop = 0;
        for(Position position : Position.values()){
           totalPFR += numPFR.get(position);
           totalCouldDoActionPreFlop += numCouldDoActionPreFlop.get(position);
        }
        
        return totalPFR / (double) totalCouldDoActionPreFlop;
    }
    
    
    /**
     * Call whenever a player has the opportunity to do an action preflop from given position
     */
    public void numCouldDoActionPreFlop(Position position){
        numCouldDoActionPreFlop.put(position, numCouldDoActionPreFlop.get(position)+1);
    }
    
    
    
    /**
     * Call whenever a player goes to showdown
     */
    public void WTSD(){
        numWTSD++;
    }
    
    
    /**
     * Call whenever a player wins money at a showdown
     */
    public void W$SD(){
        numW$SD++;
    }
    
    /**
     * @return the percentage of hands a player goes to showdown with after seeing the flop
     */
    public double getWTSD(){
        return numWTSD / (double) numStreetsSeen.get(Street.FLOP);
    }
    
    /**
     * @return the percentage of hands a player wins money with when going to showdown
     */
    public double getW$SD(){
        return numW$SD / (double) numWTSD;
    }
    
   
    
 
    
    /**
     * Aggression factor = (NumberBets + NumberRaises)/NumberCalls
     * Aggression factor > 1 means the player is aggressive
     * Aggression factor < 1 means the player is passive
     * @return aggression factor
     */
    public double getAF(){
        double AF = (double)(numActionsDone.get(PerformedActionType.BET) + numActionsDone.get(PerformedActionType.RAISE))  / numActionsDone.get(PerformedActionType.CALL);
        return AF;
    }
    
    
    @Override
    public String toString(){
        String toReturn = "VPIP: " + getVPIP() + "\n" + 
               "PFR: " + getPFR() + "\n" + 
               "AF: " + getAF() + "\n" +
               "WTSD: " + getWTSD() + "\n"+
               "W$SD: " + getW$SD() + "\n";
        for(Position position: Position.values()){
            toReturn += "VPIP " + position + " " + getVPIP(position) + "\n";
        }
        for(Position position: Position.values()){
            toReturn += "PFR " + position + " " + getPFR(position) + "\n";
        }
        return toReturn;
    }
    
}