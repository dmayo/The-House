package actions;

public enum LegalActionType {
    BET("BET"), CALL("CALL"), CHECK("CHECK"), FOLD("FOLD"), RAISE("RAISE");
    
    private final String action;
    
    private LegalActionType(String action){
        this.action = action;
    }
    
    public String toString(){
        return action;
    }

}
