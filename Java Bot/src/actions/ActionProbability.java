package actions;
import java.util.Random;

public class ActionProbability {
    private final double probFold;
    private final double probCall;
    private final double probRaise;
    private final double probBet;
    private final double probCheck;
    private final static Random rnd = new Random();
    
    public ActionProbability(double probFold, double probCall, double probRaise, double probBet, double probCheck){
        this.probFold = probFold;
        this.probCall = probCall;
        this.probRaise = probRaise;
        this.probBet = probBet;
        this.probCheck = probCheck;
    }
    
    
    /**
     * @param action
     * @return the probability of taking the given action
     */
    public double getProb(LegalActionType action){
        if(action == LegalActionType.FOLD){
            return probFold;
        } else if (action == LegalActionType.CALL){
            return probCall;
        } else if (action == LegalActionType.RAISE){
            return probRaise;
        } else if (action == LegalActionType.BET){
            return probBet;
        } else{
            return probCheck;
        }
    }
    
    
    public LegalActionType randomlyChooseAction(){
        double p = rnd.nextDouble();
        double cumulativeProbability = 0;;
        
        return LegalActionType.BET;
    }
    
    
}
