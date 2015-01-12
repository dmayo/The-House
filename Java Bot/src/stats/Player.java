package stats;

import actions.PerformedAction;

public class Player {
    
    private final String name;
    private final Stats stats;
    
    public Player(String name, Stats stats){
        this.name = name;
        this.stats = stats;
    }
    
    public String getName(){
        return name;
    }
    
    
    /**
     * update the player stats based on the given action
     * @param action
     */
    public void processAction(PerformedAction action){
        
    }
}
