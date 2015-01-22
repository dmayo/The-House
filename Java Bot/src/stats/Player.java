package stats;

import java.util.ArrayList;

import cards.BoardCards;
import cards.Card;
import actions.PerformedAction;
import actions.PerformedActionType;
import actions.Street;

public class Player {
    
    private final String name;
    private Stats stats;
    private int stackSize;
    private boolean isActive;
    private int seat;
    private Street street;
    private PerformedAction lastAction;
    private boolean setVPIP;
    private boolean setPreFlopRaise;
    private boolean madePreFlopAction;
    private boolean cardsShown;
    private boolean preFlopR3B;
    private boolean flopCBet;
    private boolean countedFlopCbetOppurtunity;
    private int numActivePlayers = 3;
    
    
    public Player(String name, int stackSize, int seat){
        this.name = name;
        this.stackSize = stackSize;
        this.isActive = true;
        this.stats = new Stats();
        this.seat = seat;
        this.street = Street.PREFLOP;
        this.lastAction = new PerformedAction(name, PerformedActionType.NONE, 0, new ArrayList<Card>(), Street.PREFLOP);
        setVPIP = false;
        setPreFlopRaise = false;
        madePreFlopAction = false;
        cardsShown = false;
        preFlopR3B = false;
        flopCBet = false;
        countedFlopCbetOppurtunity = false;
    }
    
    public Stats getStats(){
        return stats;
    }
    
    public void setStats(Stats stats){
        this.stats = stats;
    }
    
    public void setNumActivePlayers(int numActivePlayers){
        this.numActivePlayers = numActivePlayers;
    }
   
    
    public void setStreet(Street street){
        this.street = street;
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
    
    
    public boolean didVPIP(){
        return setVPIP;
    }
    
    public boolean didPFR(){
        return setPreFlopRaise;
    }
    
    public boolean didPFR3B(){
        return preFlopR3B;
    }
    
    public boolean didFlopCBet(){
        return flopCBet;
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
     *  Call when NEWHAND message is received.
     * @param active 
     */
    public void setActive(boolean active){
        isActive = active;
        setVPIP = false;
        setPreFlopRaise = false;
        madePreFlopAction = false;
        cardsShown = false;
        preFlopR3B = false;
        flopCBet = false;
        countedFlopCbetOppurtunity = false;
    }
    
    
    /**
     * update the player stats based on the given action
     * @param action
     */
    public void processAction(PerformedAction action){
        
        PerformedActionType type = action.getType();
        
        if(type == PerformedActionType.DEAL){
            street = action.getStreet();   
            if(lastAction.getType() != PerformedActionType.FOLD){
                stats.sawStreet(street);
            }
        }
        if((type == PerformedActionType.CALL || type == PerformedActionType.RAISE || type == PerformedActionType.BET) 
                && street == Street.PREFLOP && !setVPIP){
            stats.VPIP(getPosition());
            setVPIP = true;
        }
        if(type.isAPlayerAction() && street == Street.PREFLOP && !madePreFlopAction){
            stats.numCouldDoActionPreFlop(getPosition());
            madePreFlopAction = true;
        }
        if(lastAction.getType() == PerformedActionType.RAISE && street == Street.PREFLOP && type.isAPlayerAction()){
            stats.couldR3B();
        }
        if(type == PerformedActionType.RAISE && street == Street.PREFLOP && !setPreFlopRaise){
            stats.PFR(getPosition());
            setPreFlopRaise = true;
        }
        if(type.isAPlayerAction()){
            stats.preformedAction(type);
        }
        if(type == PerformedActionType.SHOW){
            stats.WTSD();
            cardsShown = true;
        }
        if((type == PerformedActionType.WIN || type == PerformedActionType.TIE) && cardsShown){
            stats.W$SD();
            cardsShown = false;
        }   
        if(type == PerformedActionType.RAISE && lastAction.getType() == PerformedActionType.RAISE &&
                street == Street.PREFLOP){
            stats.R3B();
            preFlopR3B = true;
        }
        if(type.isAPlayerAction() && street == Street.FLOP && setPreFlopRaise && !countedFlopCbetOppurtunity){
            stats.couldCBet();
            countedFlopCbetOppurtunity = true;
        }
        if((type == PerformedActionType.BET || type == PerformedActionType.RAISE) && street == Street.FLOP && setPreFlopRaise && !flopCBet){
            stats.CBet();
            flopCBet = true;
        }
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
    
    
    public Position getPosition(){
        if(seat == 1){
            return Position.FIRST;
        } else if(seat == 2 && numActivePlayers == 3){
            return Position.MIDDLE;
        }
        
        return Position.LAST;
    }
    

}
