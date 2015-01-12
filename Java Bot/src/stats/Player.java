package stats;

import actions.PerformedAction;

public class Player {
    
    private final String name;
    private final Stats stats;
    private int stackSize;
    
    public Player(String name, int stackSize){
        this.name = name;
        this.stats = new Stats();
        this.stackSize = stackSize;
    }
    
    
    public String getName(){
        return name;
    }
    
    
    public int getStackSize(){
        return stackSize;
    }
    
    
    public void setStackSize(int newStackSize){
        this.stackSize = newStackSize;
    }
    
    
    /**
     * update the player stats based on the given action
     * @param action
     */
    public void processAction(PerformedAction action){
        
    }
}
