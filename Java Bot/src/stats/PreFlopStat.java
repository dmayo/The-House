package stats;

import java.util.HashMap;
import java.util.Map;

import actions.PerformedActionType;

public class PreFlopStat {
    private Map<Integer, Map<PerformedActionType, Integer>> seatToActions;
    
    public PreFlopStat(){
        seatToActions = new HashMap<Integer, Map<PerformedActionType,Integer>>();
        Map<PerformedActionType,Integer> initial = new HashMap<PerformedActionType, Integer>();
        initial.put(PerformedActionType.CALL, 0);
        initial.put(PerformedActionType.RAISE, 0);
        initial.put(PerformedActionType.CHECK, 0);
        initial.put(PerformedActionType.FOLD, 0);
        seatToActions.put(1, new HashMap<PerformedActionType, Integer>(initial));
        seatToActions.put(2, new HashMap<PerformedActionType, Integer>(initial));
        seatToActions.put(3, new HashMap<PerformedActionType, Integer>(initial));
    }
    
    public void addAction(int seat, PerformedActionType type){
        int newCount = seatToActions.get(seat).get(type) + 1;
        seatToActions.get(seat).put(type, newCount);
    }
}
