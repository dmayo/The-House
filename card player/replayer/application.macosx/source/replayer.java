import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class replayer extends PApplet {

// The next line is needed if running in JavaScript Mode with Processing.js
/* @pjs preload="moonwalk.jpg"; */ 

BufferedReader reader;
String line = "";
String lines[];
int wait = 1000;
long lastStep = 0;
Match currentMatch;
ArrayList<Match> matches = new ArrayList<Match>();
int matchNum = 0;
boolean playing = false;
PImage cardSheet;
String input ="";
boolean inputFieldActive = false;

public void setup() {
  size(900, 720);
  reader = createReader("poker.txt");  
  cardSheet  = loadImage("cardsheet.png");
  while(line != null){
     try {
    
      line = reader.readLine();
      String[] pieces = split(line, ' ');
      if(line != null){
      if(pieces[0].equals("Hand")){
        matchNum = PApplet.parseInt(pieces[1].substring(1,pieces[1].length()-1));
        Player playerA = new Player(pieces[2], PApplet.parseInt(pieces[3].substring(1,pieces[3].length()-2)));
        Player playerB = new Player(pieces[4], PApplet.parseInt(pieces[5].substring(1,pieces[5].length()-2)));
        Player playerC = new Player(pieces[6], PApplet.parseInt(pieces[7].substring(1,pieces[7].length()-1)));
        Player players[] = new Player[]{playerA, playerB, playerC};
        matches.add(new Match(players, matchNum));
      }
      else if(!pieces[0].equals("6.176") && !pieces[0].equals("")){
        matches.get(matchNum-1).addAction(line);
      }
      
      }
      
      
    }
    catch (IOException e) {
      e.printStackTrace();
      line = null;
    }

  }
  
  
  matchNum = 0;
  drawControlButtons();
  
}

public void draw() {
  if(millis() - lastStep > wait && playing && matchNum < matches.size()){
    Match currMatch = matches.get(matchNum);
    if(currMatch.nextAction()){
      fill(200);
      rect(0,0,width,height);
      drawControlButtons();
      currMatch.drawMatch(50,50);
    } else{
      matchNum++;
    }
    lastStep = millis();
  }
  
  else if(matchNum >= matches.size()){
    playing = false;
    fill(200);
    rect(0,0,width,height);
    drawControlButtons();
    matches.get(matches.size()-1).drawMatch(50,50);
  }
}


public void mousePressed(){
  if(mouseX >= 50 && mouseX <= 50+100 
    && mouseY >= 660 && mouseY <= 660+30){
      //back
      matchNum = max(matchNum-1, 0);
      matches.get(matchNum).reset();
      fill(200);
      rect(0,0,width,height);
      drawControlButtons();
      matches.get(matchNum).drawMatch(50,50);
    }
  
  if(mouseX >= 170 && mouseX <= 170+100 
    && mouseY >= 660 && mouseY <= 660+30){
      //forward
      matchNum = min(matchNum+1,matches.size()-1);
      matches.get(matchNum).reset();
      fill(200);
      rect(0,0,width,height);
      drawControlButtons();
      matches.get(matchNum).drawMatch(50,50);
    }
    
    if(mouseX >= 400 && mouseX <= 400+100 
    && mouseY >= 660 && mouseY <= 660+30){
      //play/pause
      playing = !playing;
      fill(200);
      rect(0,0,width,height);
      drawControlButtons();
      matches.get(min(matchNum,matches.size()-1)).drawMatch(50,50);
    }
    
    if(mouseX >= 770 && mouseX <= 770+100 
    && mouseY >= 660 && mouseY <= 660+30 ){
      playing = false;
      Match currMatch = matches.get(min(matchNum,matches.size()-1));
      if(currMatch.nextAction()){
        fill(200);
        rect(0,0,width,height);
        drawControlButtons();
        currMatch.drawMatch(50,50);
      } else{
        matchNum=min(matchNum+1,matches.size()-1);
      }
    }
    
    if(mouseX >= 600 && mouseX <= 600+100 
    && mouseY >= 660 && mouseY <= 660+30){
      // input text field
      inputFieldActive = true;
      playing = false;
    }
    
}



public void drawControlButtons(){
  fill(0,0,200);
  // back
  rect(50,660,100,30);
  //forward
  rect(170,660,100,30);
  //step
  rect(770,660,100,30);
  
  fill(255,255,255);
  // text
  rect(600,660,100,30);
  textSize(25);
  text("back",60,685);
  text("forward",175,685);
  text("step",785,685);
  
  // play/pause
  if(playing){
    fill(200,0,0);
  }
  else{
    fill(0,200,0);
  }
  rect(400,660,100,30);
  fill(255,255,255);
  if(playing){
    text("pause",420,685);
  }
  else{
    text("play",420,685);
  }
}


public void keyPressed(){
  if(inputFieldActive){
  if( key >= '0' && key <= '9' ){
    input+=PApplet.parseChar(key);
  } 
  else if( key == BACKSPACE && input.length() > 0 ){
    input = input.substring( 0, input.length()-1);
  } 
  else if (key == RETURN || key == ENTER){
    int finalInput = PApplet.parseInt(input);
    matchNum = max(min(finalInput-1,matches.size()-1),0);
     matches.get(matchNum).reset();
     fill(200);
     rect(0,0,width,height);
     drawControlButtons();
     matches.get(matchNum).drawMatch(50,50);
    inputFieldActive = false;
    input = "";
    
  }
  textSize(20);
  fill(0);
  text(input,620,685);
  }
}



class Action{
  String action;
  int amount;
  
  Action(String action, int amount){
    this.action = action;
    this.amount = amount;
  }
  
  public void drawAction(int x, int y){
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
  
  public String getAction(){
    return action;
  }
  
  public boolean clearable(){
    return (!(action.equals("folds")|| action.equals("loser")));
  }
  
}
class Card { 
  String rank, suit;
  final static int cardWidth = 92;
  final static int cardHeight = 133;  
  
  Card (String rank, String suit) {  
    this.rank = rank;
    this.suit = suit;
  } 
  
  Card (String card) {  
    this.rank = card.substring(0,1);
    this.suit = card.substring(1,2);
  } 
  
  public int getSuitNumber(){
    if(suit.equals("c")){
      return 0;
    }
    else if(suit.equals("h")){
      return 1;
    }
    else if(suit.equals("d")){
      return 2;
    }
    else{
      return 3;
    }
  }
  
  
  public int getRankNumber(){
    if(rank.equals("A")){
      return 0;
    }
    else if(rank.equals("2")){
      return 1;
    }
    else if(rank.equals("3")){
      return 2;
    }
    else if(rank.equals("4")){
      return 3;
    }
    else if(rank.equals("5")){
      return 4;
    }
    else if(rank.equals("6")){
      return 5;
    }
    else if(rank.equals("7")){
      return 6;
    }
    else if(rank.equals("8")){
      return 7;
    }
    else if(rank.equals("9")){
      return 8;
    }
    else if(rank.equals("T")){
      return 9;
    }
    else if(rank.equals("J")){
      return 10;
    }
    else if(rank.equals("Q")){
      return 11;
    }
    else{
      return 12;
    }
  }
  
  public void drawCard(int x, int y){
    PImage cardImage = cardSheet.get(getRankNumber()*cardWidth, getSuitNumber()*cardHeight, cardWidth, cardHeight);
    image(cardImage, x,y);
  }
  
  
  public int getCardWidth(){
    return cardWidth;
  }
  public int getCardHeight(){
    return cardHeight;
  }
} 
class Hand{
  Card hole1, hole2;
  
  Hand(Card hole1, Card hole2){
    this.hole1 = hole1;
    this.hole2 = hole2;
  }
  
  public void drawHand(int x, int y){
    hole1.drawCard(x,y);
    hole2.drawCard(x+hole1.getCardWidth()+2, y);
  }
  
}
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
  
  
  public void reset(){
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
  
  
  public void drawMatch(int x, int y){
    for(int i=0; i<players.length; i++){
      players[i].drawPlayer(x+(i*300),y);
    }
    if(street != null){
      street.drawStreet(x+250,y+300);
    }
    textSize(30);
    fill(0,0,0);
    text("match #" + matchNumber,x,y+600);
    textSize(70);
    fill(0,200,0);
    text("Pot: $" + pot,x+300,y+560);
  }
  
  public boolean nextAction(){
    if(actionIndex < actions.size()){
      String currAction = actions.get(actionIndex);
      String actionList[] = split(currAction, ' ' );
      
      if(actionList[1].equals("posts")){
        sendActionToPlayer(actionList[0],new Action("posts",PApplet.parseInt(actionList[actionList.length-1])));
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
        sendActionToPlayer(actionList[0],new Action("bets",PApplet.parseInt(actionList[actionList.length-1])));
      }
      if(actionList[1].equals("calls")){
        sendActionToPlayer(actionList[0],new Action("calls",PApplet.parseInt(actionList[actionList.length-1])));
      }
      if(actionList[1].equals("raises")){
        sendActionToPlayer(actionList[0],new Action("raises",PApplet.parseInt(actionList[actionList.length-1])));
      }
      if(actionList[1].equals("checks")){
        sendActionToPlayer(actionList[0],new Action("checks",0));
      }
      if(actionList[1].equals("shows")){
        sendActionToPlayer(actionList[0],new Action("shows",0));
      }
      if(actionList[1].equals("wins")){
        int amount = PApplet.parseInt(actionList[actionList.length-1].substring(1,actionList[actionList.length-1].length()-1));
        sendActionToPlayer(actionList[0],new Action("wins",amount));
      }
      if(actionList[1].equals("FLOP")){
        pot = PApplet.parseInt(actionList[3].substring(1,actionList[3].length()-1));
        Card card1 = new Card(actionList[4].substring(1,3));
        Card card2 = new Card(actionList[5].substring(0,2));
        Card card3 = new Card(actionList[6].substring(0,2));
        Card streetCards[] = new Card[]{card1,card2,card3};
        street = new Street(streetCards);
        clearPlayerActions();
      }
      if(actionList[1].equals("TURN")){
        pot = PApplet.parseInt(actionList[3].substring(1,actionList[3].length()-1));
        Card card1 = new Card(actionList[4].substring(1,3));
        Card card2 = new Card(actionList[5].substring(0,2));
        Card card3 = new Card(actionList[6].substring(0,2));
        Card card4 = new Card(actionList[7].substring(0,2));
        Card streetCards[] = new Card[]{card1,card2,card3,card4};
        street = new Street(streetCards);
        clearPlayerActions();
      }
      if(actionList[1].equals("RIVER")){
        pot = PApplet.parseInt(actionList[3].substring(1,actionList[3].length()-1));
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
  
  public void addAction(String action){
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
      if(!player.getAction().clearable()){
        player.setAction(new Action("",0));
      }
    }
  }
  
}
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
  
  public void setHand(Hand hand){
      this.hand = hand;
  }
  
  public void setStack(int stack){
      this.stack = stack;
      if(stack ==0){
        action = new Action("loser",0);
      }
  }
  
  public String getPlayerName(){
      return name;
  }
  
  public void drawPlayer(int x, int y){
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
  
  
  public void setAction(Action action){
    this.action = action;
    
  }
  
  public Action getAction(){
     return action;
  }

}
class Preflop{
  
  Preflop(){
    
  }
  
}
class Street{
  Card cards[];
  
  Street(Card cards[]){
    this.cards = cards;
  }
  
  public void drawStreet(int x, int y){
    for(int i=0; i<cards.length; i++){
      cards[i].drawCard(x+(i*(Card.cardWidth+2)),y);
    }
  }
  
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "replayer" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
