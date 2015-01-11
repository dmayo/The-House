class Action{
  String action;
  int amount;
  
  Action(String action, int amount){
    this.action = action;
    this.amount = amount;
  }
  
  void drawAction(int x, int y){
    if(action.equals("folds") || action.equals("loser")){
       textSize(80);
       fill(255,0,0);
       text(action,x+10,y-70);
    }
    else if(action.equals("wins")){
       textSize(80);
       fill(0,255,0);
       text(action,x+10,y-70);
       textSize(30);
        text(action + ": $" + amount,x,y);
    }else if(!action.equals("")){
       textSize(30);
      fill(0,102,153);
      text(action + ": $" + amount,x,y);
    }
  }
  
  String getAction(){
    return action;
  }
  
  boolean clearable(){
    return (!(action.equals("folds")|| action.equals("loser")));
  }
  
}
