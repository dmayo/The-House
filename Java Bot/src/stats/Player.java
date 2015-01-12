package stats;

import actions.PerformedAction;

public class Player {
    
    private final String name;
    private final Stats stats;
    private int stackSize;
    private boolean isActive;
    private int seat;
    
    
    public Player(String name, int stackSize, int seat){
        this.name = name;
        this.stats = new Stats();
        this.stackSize = stackSize;
        this.isActive = true;
        this.seat = seat;
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
     * 
     * @return seat number of player from 1-3
     */
    public int getSeat(){
        return seat;
    }
    
    
    public void setSeat(int newSeat){
        seat = newSeat;
    }
    
    
    /**
     * Whether or not the player is playing the current hand
     * @return true if playing, false otherwise
     */
    public boolean isActive(){
        return isActive;
    }
    
    
    /**
     * Sets whether or not the player is playing the current hand.
     * @param active 
     */
    public void setActive(boolean active){
        isActive = active;
    }
    
    
    /**
     * update the player stats based on the given action
     * @param action
     */
    public void processAction(PerformedAction action){
        
    }
}
