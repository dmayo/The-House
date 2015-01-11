class Player{
  Hand hand;
  int stack;
  String name;
  Action action;
  
  Player(String name, int stack){
    this.name = name;
    this.stack = stack;
    if(stack ==0){
        action = new Action("loser",0);
     }
     else{
       action = new Action("",0);
     }
  }
  
  void setHand(Hand hand){
      this.hand = hand;
  }
  
  void setStack(int stack){
      this.stack = stack;
      if(stack ==0){
        action = new Action("loser",0);
      }
  }
  
  String getPlayerName(){
      return name;
  }
  
  void drawPlayer(int x, int y){
    textSize(30);
    fill(0,102,153);
    text(name + ": $" + stack,x,y-15);
    if(hand != null){
      hand.drawHand(x,y);
    }
    if(action != null){
      action.drawAction(x,y+Card.cardHeight+30);
    }
    
  }
  
  
  void setAction(Action action){
    this.action = action;
    
  }
  
  Action getAction(){
     return action;
  }

}
