package actions;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ActionProbability {
    private final Map<LegalActionType, Double> probabilityMap;
    private final static Random rnd = new Random();
    
    public ActionProbability(double probFold, double probCall, double probRaise, double probBet, double probCheck){
        probabilityMap = new HashMap<LegalActionType, Double>();
        probabilityMap.put(LegalActionType.FOLD, probFold);
        probabilityMap.put(LegalActionType.CALL, probCall);
        probabilityMap.put(LegalActionType.RAISE, probRaise);
        probabilityMap.put(LegalActionType.BET, probBet);
        probabilityMap.put(LegalActionType.CHECK, probCheck);
    }
    
    
    /**
     * @param action
     * @return the probability of taking the given action
     */
    public double getProb(LegalActionType action){
        return probabilityMap.get(action);
    }
    
    
    public LegalActionType randomlyChooseAction(){
        double p = rnd.nextDouble();
        double cumulativeProbability = 0;
        for(LegalActionType action : probabilityMap.keySet()){
            cumulativeProbability += probabilityMap.get(action);
            if(p <= cumulativeProbability){
                return action;
            }
        }
        
        return LegalActionType.CHECK;
    }
    
    @Override
    public String toString(){
        String out = "";
        for(LegalActionType action : probabilityMap.keySet()){
            out += action.name() + ": " + Double.toString(probabilityMap.get(action)) + ", ";
        }
        return out;
    }
    
}
