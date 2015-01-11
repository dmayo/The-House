package actions;

public class LegalAction {
    private final LegalActionType type;
    private final int min;
    private final int max;
    private final int amount;
    
    /**
     * 
     * @param type legal action type
     * @param min min bet or min raise
     * @param max max bet or max raise
     * @param amount amount to call
     */
    public LegalAction(LegalActionType type, int min, int max, int amount){
        this.type = type;
        this.min = min;
        this.max = max;
        this.amount = amount;
    }
    
    
    public LegalActionType getType(){
        return type;
    }
    
    
    public int getMin(){
        return min;
    }
    
    
    public int getMax(){
        return max;
    }
    
    public int getAmount(){
        return amount;
    }
    
}
