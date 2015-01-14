package stats;

import java.util.ArrayList;

import cards.BoardCards;
import cards.Card;
import actions.PerformedAction;
import actions.PerformedActionType;
import actions.Street;

public class Player {
    
    private final String name;
    private final Stats stats;
    private int stackSize;
    private boolean isActive;
    private int seat;
    private BoardCards boardCards;
    private PerformedAction lastAction;
    
    public Player(String name, int stackSize, int seat){
        this.name = name;
        this.stats = new Stats();
        this.stackSize = stackSize;
        this.isActive = true;
        this.seat = seat;
        this.boardCards = new BoardCards(Street.PREFLOP, new ArrayList<Card>());
    }
    
    
    public void setBoardCards(BoardCards newBoardCards){
        this.boardCards = newBoardCards;
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
        this.lastAction = action;
    }
    
    
    public PerformedAction getLastAction(){
        return this.lastAction;
    }
    
    public void setLastAction(PerformedAction action){
        this.lastAction = action;
    }
    
    public boolean limped(){
        return (lastAction.getType() == PerformedActionType.CALL) & isActive;
    }
    
    public boolean raised(){
        return (lastAction.getType() == PerformedActionType.RAISE) & isActive;
    }
}
