package stats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cards.Card;
import actions.PerformedAction;
import actions.PerformedActionType;
import actions.Street;

public class StatsCalculator {
    
    private List<Player> players;
    
    public StatsCalculator(){
    }
    
    public void setPlayers(List<Player> players){
        this.players = new ArrayList<Player>(players);
    }
    
    
    public void processActions(String[] performedActions){
        List<PerformedAction> actions = determinePerformedActions(performedActions);
        for(PerformedAction action : actions){
            for(Player player : players){
                if(player.getName().equals(action.getActor())){
                    player.processAction(action);
                }
            }
        }
    }
    
    private List<PerformedAction> determinePerformedActions(String[] actions){
        
        final List<PerformedAction> performedActions = new ArrayList<PerformedAction>();
        
        for(String action : actions){
            if(action.contains(PerformedActionType.BET.toString()) || action.contains(PerformedActionType.CALL.toString()) ||
               action.contains(PerformedActionType.POST.toString()) || action.contains(PerformedActionType.RAISE.toString()) ||
               action.contains(PerformedActionType.REFUND.toString()) || action.contains(PerformedActionType.TIE.toString()) ||
               action.contains(PerformedActionType.WIN.toString())){
                final String actionSplit[] = action.split(":");
                final int amount = new Integer(actionSplit[1]);
                final String actor = actionSplit[2];
                performedActions.add(new PerformedAction(actor, PerformedActionType.valueOf(actionSplit[0]), amount, new ArrayList<Card>(), Street.NONE));
            }
            else if(action.contains(PerformedActionType.CHECK.toString()) || action.contains(PerformedActionType.FOLD.toString())){
                final String actionSplit[] = action.split(":");
                final String actor = actionSplit[1];
                performedActions.add(new PerformedAction(actor, PerformedActionType.valueOf(actionSplit[0]), 0, new ArrayList<Card>(), Street.NONE));
            }
            else if(action.contains(PerformedActionType.SHOW.toString())){
                final String actionSplit[] = action.split(":");
                final Card card1 = new Card(actionSplit[1]);
                final Card card2 = new Card(actionSplit[2]);
                final String actor = actionSplit[3];             
                performedActions.add(new PerformedAction(actor, PerformedActionType.SHOW, 0, Arrays.asList(new Card[]{card1,card2}), Street.NONE));
            }
            else if(action.contains(PerformedActionType.DEAL.toString())){
                final String actionSplit[] = action.split(":");
                final Street street = Street.valueOf(actionSplit[1]);
                performedActions.add(new PerformedAction("", PerformedActionType.DEAL, 0, new ArrayList<Card>(), street));
            }
           
        }
        
        return performedActions;
    }
}
