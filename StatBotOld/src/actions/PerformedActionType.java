package actions;

public enum PerformedActionType {
    BET, CALL, CHECK, DEAL, FOLD, POST, RAISE, REFUND, SHOW, TIE, WIN, NONE;
    
    public boolean isAPlayerAction(){
        return (this == PerformedActionType.BET || this == PerformedActionType.CALL || this == PerformedActionType.CHECK
                || this == PerformedActionType.FOLD || this == PerformedActionType.RAISE);
    }
    
}
