class Match{
  Player players[];
  Street street;
  int matchNumber;
  int pot = 0;
  int actionIndex = 0;
  ArrayList<String> actions;
  
  Match(Player players[], int matchNumber){
    this.players = players;
    this.matchNumber = matchNumber;
    this.actions = new ArrayList<String>();
  }
  
  
  void reset(){
    actionIndex = 0;
    pot = 0;
    street = null;
    for(Player player: players){
        player.setHand(null);
        if(!player.getAction().getAction().equals("loser")){
          player.setAction(new Action("",0));
        }
    }
  }
  
  
  void drawMatch(int x, int y){
    for(int i=0; i<players.length; i++){
      players[i].drawPlayer(x+(i*300),y);
    }
    if(street != null){
      street.drawStreet(x+250,y+300);
    }
    textSize(30);
    fill(0,0,0);
    text("match #" + matchNumber + " of " +matches.size(),x,y+600);
    textSize(70);
    fill(0,200,0);
    text("Pot: $" + pot,x+300,y+560);
  }
  
  boolean nextAction(){
    if(actionIndex < actions.size()){
      String currAction = actions.get(actionIndex);
      String actionList[] = split(currAction, ' ' );
      
      if(actionList[1].equals("posts")){
        sendActionToPlayer(actionList[0],new Action("posts",int(actionList[actionList.length-1])));
      }
      if(actionList[0].equals("Dealt")){
        Card card1 = new Card(actionList[3].substring(1,3));
        Card card2 = new Card(actionList[4].substring(0,2));
        setPlayerHand(actionList[2],new Hand(card1, card2));
      }
      if(actionList[1].equals("folds")){
        sendActionToPlayer(actionList[0],new Action("folds",0));
      }
      if(actionList[1].equals("bets")){
        sendActionToPlayer(actionList[0],new Action("bets",int(actionList[actionList.length-1])));
      }
      if(actionList[1].equals("calls")){
        sendActionToPlayer(actionList[0],new Action("calls",int(actionList[actionList.length-1])));
      }
      if(actionList[1].equals("raises")){
        sendActionToPlayer(actionList[0],new Action("raises",int(actionList[actionList.length-1])));
      }
      if(actionList[1].equals("checks")){
        sendActionToPlayer(actionList[0],new Action("checks",0));
      }
      if(actionList[1].equals("shows")){
        sendActionToPlayer(actionList[0],new Action("shows",0));
      }
      if(actionList[1].equals("wins")){
        int amount = int(actionList[actionList.length-1].substring(1,actionList[actionList.length-1].length()-1));
        sendActionToPlayer(actionList[0],new Action("wins",amount));
      }
      if(actionList[1].equals("FLOP")){
        pot = int(actionList[3].substring(1,actionList[3].length()-1));
        Card card1 = new Card(actionList[4].substring(1,3));
        Card card2 = new Card(actionList[5].substring(0,2));
        Card card3 = new Card(actionList[6].substring(0,2));
        Card streetCards[] = new Card[]{card1,card2,card3};
        street = new Street(streetCards);
        clearPlayerActions();
      }
      if(actionList[1].equals("TURN")){
        pot = int(actionList[3].substring(1,actionList[3].length()-1));
        Card card1 = new Card(actionList[4].substring(1,3));
        Card card2 = new Card(actionList[5].substring(0,2));
        Card card3 = new Card(actionList[6].substring(0,2));
        Card card4 = new Card(actionList[7].substring(0,2));
        Card streetCards[] = new Card[]{card1,card2,card3,card4};
        street = new Street(streetCards);
        clearPlayerActions();
      }
      if(actionList[1].equals("RIVER")){
        pot = int(actionList[3].substring(1,actionList[3].length()-1));
        Card card1 = new Card(actionList[4].substring(1,3));
        Card card2 = new Card(actionList[5].substring(0,2));
        Card card3 = new Card(actionList[6].substring(0,2));
        Card card4 = new Card(actionList[7].substring(0,2));
        Card card5 = new Card(actionList[8].substring(0,2));
        Card streetCards[] = new Card[]{card1,card2,card3,card4,card5};
        street = new Street(streetCards);
        clearPlayerActions();
      }
      
      actionIndex++;
      return true;
    }
    
    return false;
  }
  
  void addAction(String action){
    actions.add(action);
  }
   
   
  private void sendActionToPlayer(String playerName, Action action){
    for(Player player: players){
      if(player.getPlayerName().equals(playerName)){
        player.setAction(action);
        break;
      }
    }
  }
  
  private void setPlayerHand(String playerName, Hand hand){
    for(Player player: players){
      if(player.getPlayerName().equals(playerName)){
        player.setHand(hand);
      }
    }
  }
  
  private void clearPlayerActions(){
    for(Player player: players){
      if(player.getAction().clearable()){
        player.setAction(new Action("",0));
      }
    }
  }
  
}
