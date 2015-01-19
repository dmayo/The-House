package stats;

import java.util.HashMap;
import java.util.Map;

import actions.PerformedActionType;

public class PreFlopStat {
    private Map<Integer, Map<PerformedActionType, Integer>> seatToActions;
    private static final PerformedActionType possibleActions[] = new PerformedActionType[]{PerformedActionType.CALL,
                            PerformedActionType.CHECK, PerformedActionType.FOLD, PerformedActionType.RAISE};
    
    public PreFlopStat(){
        seatToActions = new HashMap<Integer, Map<PerformedActionType,Integer>>();
        Map<PerformedActionType,Integer> initial = new HashMap<PerformedActionType, Integer>();
        for(PerformedActionType type : possibleActions){
            initial.put(type, 0);
        }
        
        seatToActions.put(1, new HashMap<PerformedActionType, Integer>(initial));
        seatToActions.put(2, new HashMap<PerformedActionType, Integer>(initial));
        seatToActions.put(3, new HashMap<PerformedActionType, Integer>(initial));
    }
    
    
    
    public void addAction(int seat, PerformedActionType type){
        int newCount = seatToActions.get(seat).get(type) + 1;
        seatToActions.get(seat).put(type, newCount);
    }
    
    
    
    public double getPercentagActionGivenSeat(int seat, PerformedActionType type){
        double total = 0;
        for(PerformedActionType typeOfAction : possibleActions){
            total += seatToActions.get(seat).get(typeOfAction);
        }
        
        return seatToActions.get(seat).get(type) /  total;
    }
    
    
    
    public double getPercentAction(PerformedActionType type){
        double totalActions = 0;
        for(int seat = 1; seat < 4; seat++){
            for(PerformedActionType typeOfAction : possibleActions){
                totalActions += seatToActions.get(seat).get(typeOfAction);
            }
        }
        
        double totalOfType  = 0;
        
        for(int seat = 1; seat < 4; seat++){
            totalOfType += seatToActions.get(seat).get(type);
        }
        
        return totalOfType / totalActions;
    }
    
    
    @Override
    public String toString(){
        String toReturn = "";
        
        for(int seat = 1; seat < 4; seat++){
            for(PerformedActionType type : possibleActions){
                toReturn += "Seat: " + seat + ", " + "Action: " + type + ", " + getPercentagActionGivenSeat(seat, type) + "\n";
            }
        }
        
        return toReturn;
    }
}
