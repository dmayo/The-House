package stats;
import java.util.HashMap;
import java.util.Map;

import runner.StringEncode;
import actions.*;

public class Stats {
   
    private final Map<Street, Integer> numStreetsSeen;
    private final Map<PerformedActionType, Integer> numActionsDone  = new HashMap<PerformedActionType, Integer>();
    private final Map<Position, Integer> numVPIP;
    private final Map<Position, Integer> numPFR;
    private final Map<Position, Integer> numFoldPreFlop;
    private final Map<Position, Integer> numCouldDoActionPreFlop;
    private int numWTSD = 0;
    private int numW$SD = 0;
    private int numCouldR3B = 0;
    private int numDidR3B = 0;
    private int numDidCBet = 0;
    private int numCouldCBet = 0;
    private int numW$WSF = 0;
   
    
    
    private final Map<Position, Double> currentVPIP;
    private final Map<Position, Double> currentPFR;
    private final Map<Position, Double> currentFoldPreFlop;
    private double currentWTSD;
    private double currentW$SD;
    private double currentOverallVPIP;
    private double currentOverallPFR;
    private double currentR3B;
    private double currentCBet;
    private double currentW$WSF = 0;
    private final double a = 0.02;
    
    public Stats(Map<Position, Double> initialVPIP, Map<Position, Double> initialPFR, Map<Position, Double> initialFoldPreFlop, 
            double initialWTSD, double initialW$SD, double initialOverallVPIP, double initialOverallPFR, 
            double initialR3B, double initialCBet, double initialW$WSF){
    
        Map<Street, Integer> initialStreetCounts = new HashMap<Street,Integer>();
        initialStreetCounts.put(Street.PREFLOP, 0);
        initialStreetCounts.put(Street.FLOP, 0);
        initialStreetCounts.put(Street.TURN, 0);
        initialStreetCounts.put(Street.RIVER, 0);
        numStreetsSeen = new HashMap<Street, Integer>(initialStreetCounts);
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
        numFoldPreFlop = new HashMap<Position, Integer>(initialStats);
        
        currentVPIP = new HashMap<Position, Double>(initialVPIP);
        currentPFR = new HashMap<Position, Double>(initialPFR);
        currentFoldPreFlop = new HashMap<Position, Double>(initialFoldPreFlop);
        currentWTSD = initialWTSD;
        currentW$SD = initialW$SD;     
        currentOverallVPIP = initialOverallVPIP;
        currentOverallPFR = initialOverallPFR;
        currentR3B = initialR3B;
        currentCBet = initialCBet;
        currentW$WSF = initialW$WSF;
    }
    
    
    public Stats(){
        Map<Street, Integer> initialStreetCounts = new HashMap<Street,Integer>();
        initialStreetCounts.put(Street.PREFLOP, 0);
        initialStreetCounts.put(Street.FLOP, 0);
        initialStreetCounts.put(Street.TURN, 0);
        initialStreetCounts.put(Street.RIVER, 0);
        numStreetsSeen = new HashMap<Street, Integer>(initialStreetCounts);
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
        numFoldPreFlop = new HashMap<Position, Integer>(initialStats);
        
        currentVPIP = new HashMap<Position, Double>();
        currentVPIP.put(Position.FIRST, 0.22);
        currentVPIP.put(Position.MIDDLE, 0.22);
        currentVPIP.put(Position.LAST, 0.22);
        
        currentPFR = new HashMap<Position, Double>();
        currentPFR.put(Position.FIRST, 0.16);
        currentPFR.put(Position.MIDDLE, 0.16);
        currentPFR.put(Position.LAST, 0.16);
        
        currentFoldPreFlop = new HashMap<Position, Double>();
        currentFoldPreFlop.put(Position.FIRST, 0.7);
        currentFoldPreFlop.put(Position.MIDDLE, 0.6);
        currentFoldPreFlop.put(Position.LAST, 0.5);
        
        currentWTSD = 0.3;
        currentW$SD = 0.5;     
        currentOverallVPIP = 0.22;
        currentOverallPFR = 0.16;
        currentR3B = 0.12;
        currentCBet = 0.5;
        currentW$WSF = 0.44;
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
     * Call whenever the player voluntarily puts money in the pot on the preflop. 
     * (i.e. blinds do not count) So call when the player bets, calls, or raises preflop.
     */
    public void VPIP(Position position){
        numVPIP.put(position, numVPIP.get(position)+1);
        double newVPIP = numVPIP.get(position) / (double) numCouldDoActionPreFlop.get(position);
        if (newVPIP != Double.NaN && Double.isFinite(newVPIP)){
            currentVPIP.put(position, a*newVPIP + (1-a)*currentVPIP.get(position));
        }
        
        int totalVPIP = 0;
        int totalCouldDoActionPreFlop = 0;
        for(Position somePosition : Position.values()){
           totalVPIP += numVPIP.get(somePosition);
           totalCouldDoActionPreFlop += numCouldDoActionPreFlop.get(somePosition);
        }
        
        double newOveralVPIP =  totalVPIP / (double) totalCouldDoActionPreFlop;
        if (newOveralVPIP != Double.NaN && Double.isFinite(newOveralVPIP)){
            currentOverallVPIP = a*newOveralVPIP + (1-a)*currentOverallVPIP;
        }
    }
    
    
    /**
     * @return the percentage of preflops the player voluntarily put money in the pot from given position
     */
    public double getVPIP(Position position){
        return currentVPIP.get(position);
    }

   
    /**
     * @return the percentage of preflops the player voluntarily put money in the pot
     */
    public double getVPIP(){
        return currentOverallVPIP;
    }
    
    
    /**
     * Call whenever a player raises on preflop
     */
    public void PFR(Position position){
        numPFR.put(position, numPFR.get(position)+1);
        double newPFR = numPFR.get(position) / (double) numCouldDoActionPreFlop.get(position);
        if (newPFR != Double.NaN && Double.isFinite(newPFR)){
            currentPFR.put(position, a*newPFR + (1-a)*currentPFR.get(position));
        }
        
        int totalPFR = 0;
        int totalCouldDoActionPreFlop = 0;
        for(Position somePosition : Position.values()){
           totalPFR += numPFR.get(somePosition);
           totalCouldDoActionPreFlop += numCouldDoActionPreFlop.get(somePosition);
        }
        
        double newOverallPFR =  totalPFR / (double) totalCouldDoActionPreFlop;
        if (newOverallPFR != Double.NaN && Double.isFinite(newOverallPFR)){
            currentOverallPFR = a*newOverallPFR + (1-a)*currentOverallPFR;
        }
    }
    
    
    /**
     * @return the percentage of times a player raise on preflop from given position
     * @param position the position of the player
     */
    public double getPFR(Position position){
        return currentPFR.get(position);
    }
    
    
    /**
     * @return the percentage of times a player raise on preflop
     */
    public double getPFR(){
        return currentOverallPFR;
    }
    
    
    /**
     * Call whenever a player raises 3-Bet
     */
    public void R3B(){
        final double alphaR3B = 0.06;
        numDidR3B++;
        double newR3B = numDidR3B / (double) numCouldR3B;
        if (newR3B != Double.NaN && Double.isFinite(newR3B)){
            currentR3B = alphaR3B*newR3B + (1-alphaR3B)*currentR3B;
        }
    }
    
    
    /**
     * Call whenever a player could raise 3-Bet
     */
    public void couldR3B(){
        numCouldR3B++;
    }
    
    
    public double getR3B(){
        return currentR3B;
    }
    
    
    /**
     * Call whenever a player continuation bets on the flop
     */
    public void CBet(){
        numDidCBet++;
        double newCBet = numDidCBet / (double) numCouldCBet;
        if (newCBet != Double.NaN && Double.isFinite(newCBet)){
            currentCBet = a*newCBet + (1-a)*currentCBet;
        }
    }
    
    
    /**
     * Call whenever a player could continuation bet on the flop
     */
    public void couldCBet(){
        numCouldCBet++;
    }
    
    
    public double getCBet(){
        return currentCBet;
    }
    
    
    

    /**
     * Call whenever the player fold preflop in the given position
     */
    public void foldPreFlop(Position position){
        numFoldPreFlop.put(position, numFoldPreFlop.get(position)+1);
        double newFoldPreFlop = numFoldPreFlop.get(position) / (double) numCouldDoActionPreFlop.get(position);
        if (newFoldPreFlop != Double.NaN && Double.isFinite(newFoldPreFlop)){
            currentFoldPreFlop.put(position, a*newFoldPreFlop + (1-a)*currentFoldPreFlop.get(position));
        }
        
    }
    
    
    /**
     * @return the percentage of hands the player folded in the given position preflop
     */
    public double getFoldPreFlop(Position position){
        return currentFoldPreFlop.get(position);
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
        double newWTSD = numWTSD / (double) numStreetsSeen.get(Street.FLOP);
        if (newWTSD != Double.NaN && Double.isFinite(newWTSD)){
            currentWTSD = a*newWTSD + (1-a)*currentWTSD;
        }
    }
    
    
    /**
     * @return the percentage of hands a player goes to showdown with after seeing the flop
     */
    public double getWTSD(){
        return currentWTSD;
    }
    
    
    /**
     * Call whenever a player wins money at a showdown
     */
    public void W$SD(){
        numW$SD++;
        double newW$SD = numW$SD / (double) numStreetsSeen.get(Street.FLOP);
        if (newW$SD != Double.NaN && Double.isFinite(newW$SD)){
            currentW$SD = a*newW$SD + (1-a)*currentW$SD;
        }
    }
    
    
    /**
     * @return the percentage of hands a player wins money with when going to showdown
     */
    public double getW$SD(){
        return currentW$SD;
    }
    
    
    /**
     * Call whenever a player wins money after seeing the flop
     */
    public void W$WSF(){
        numW$WSF++;
        double newW$WSF = numW$WSF / (double) numStreetsSeen.get(Street.FLOP);
        if (newW$WSF != Double.NaN && Double.isFinite(newW$WSF)){
            currentW$WSF = a*newW$WSF + (1-a)*currentW$WSF;
        }
    }
    
    
    /**
     * @return the percentage of hands a player wins money after seeing the flop
     */
    public double getW$WSF(){
        return currentW$WSF;
    }
    
 
    
    /**
     * Aggression factor = (NumberBets + NumberRaises)/NumberCalls
     * Aggression factor > 1 means the player is aggressive
     * Aggression factor < 1 means the player is passive
     * @return aggression factor
     */
    public double getAF(){
        double AF = (double)(numActionsDone.get(PerformedActionType.BET) + numActionsDone.get(PerformedActionType.RAISE)) / 
                            numActionsDone.get(PerformedActionType.CALL);
        return AF;
    }
    
    
    @Override
    public String toString(){
        String toReturn = "VPIP: " + getVPIP() + "\n" + 
               "PFR: " + getPFR() + "\n" + 
               "AF: " + getAF() + "\n" +
               "WTSD: " + getWTSD() + "\n"+
               "W$SD: " + getW$SD() + "\n" +
               "R3B: " + getR3B() + "\n" +
               "CBet: " + getCBet() + "\n" +
               "W$WSF: " + getW$WSF() + "\n";
        for(Position position: Position.values()){
            toReturn += "VPIP " + position + " " + getVPIP(position) + "\n";
        }
        for(Position position: Position.values()){
            toReturn += "PFR " + position + " " + getPFR(position) + "\n";
        }
        for(Position position: Position.values()){
            toReturn += "Fold " + position + " " + getFoldPreFlop(position) + "\n";
        }
        return toReturn;
    }
    
    
    public String values(){
        //df.format(0.912385);
        String out = "";
        out+=StringEncode.encodeVal((int)(currentVPIP.get(Position.FIRST)*100));
        out+=StringEncode.encodeVal((int)(currentVPIP.get(Position.MIDDLE)*100));
        out+=StringEncode.encodeVal((int)(currentVPIP.get(Position.LAST)*100));
        out+=StringEncode.encodeVal((int)(currentPFR.get(Position.FIRST)*100));
        out+=StringEncode.encodeVal((int)(currentPFR.get(Position.MIDDLE)*100));
        out+=StringEncode.encodeVal((int)(currentPFR.get(Position.LAST)*100));
        out+=StringEncode.encodeVal((int)(currentWTSD*100));
        out+=StringEncode.encodeVal((int)(currentW$SD*100));
        out+=StringEncode.encodeVal((int)(currentOverallVPIP*100));
        out+=StringEncode.encodeVal((int)(currentOverallPFR*100));
        out+=StringEncode.encodeVal((int)(currentR3B*100));
        out+=StringEncode.encodeVal((int)(currentCBet*100));
        out+=StringEncode.encodeVal((int)(currentFoldPreFlop.get(Position.FIRST)*100));
        out+=StringEncode.encodeVal((int)(currentFoldPreFlop.get(Position.MIDDLE)*100));
        out+=StringEncode.encodeVal((int)(currentFoldPreFlop.get(Position.LAST)*100));
        out+=StringEncode.encodeVal((int)(currentW$WSF*100));
        return out;
    }
    
/*
    public String values(){
        String numStreetsSeenData = "S";
        for(Street street : numStreetsSeen.keySet()){
            numStreetsSeenData +=  " ";
            numStreetsSeenData += street.toString().substring(0,1);
            numStreetsSeenData += numStreetsSeen.get(street);
        }
        
        String numActionsDoneData = "A";
        for(PerformedActionType type : numActionsDone.keySet()){
            numActionsDoneData += " ";
            numActionsDoneData += type == PerformedActionType.CHECK ? type.toString().substring(1,2) : type.toString().substring(0,1);
            numActionsDoneData += numActionsDone.get(type);
        }
        
        String numVPIPData = "V";
        for(Position position : numVPIP.keySet()){
            numVPIPData += " ";
            numVPIPData += position.toString().substring(0,1);
            numVPIPData += numVPIP.get(position);
        }
        
        String numPFRData = "R";
        for(Position position : numPFR.keySet()){
            numPFRData += " ";
            numPFRData += position.toString().substring(0,1);
            numPFRData += numPFR.get(position);
        }
        
        
        String numCouldDoActionPreFlopData = "C";
        for(Position position : numCouldDoActionPreFlop.keySet()){
            numCouldDoActionPreFlopData += " ";
            numCouldDoActionPreFlopData += position.toString().substring(0,1);
            numCouldDoActionPreFlopData += numCouldDoActionPreFlop.get(position);
        }
        
        
        String numWTSDData ="D " + numWTSD;
        String numW$SDData = "$ " + numW$SD;
        
        return numStreetsSeenData + " " + numActionsDoneData + " " + numVPIPData + " " + numPFRData
                           + " " + numCouldDoActionPreFlopData + " " + numWTSDData + " " + numW$SDData;
        
    }
    */
}