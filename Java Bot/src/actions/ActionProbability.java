package actions;

public class ActionProbability {
    private final double probFold;
    private final double probCall;
    private final double probRaise;
    private final double probBet;
    private final double probCheck;
    
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
    
    
}
