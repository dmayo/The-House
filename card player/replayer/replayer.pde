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

void setup() {
  size(900, 720);
  reader = createReader("poker.txt");  
  cardSheet  = loadImage("cardsheet.png");
  while(line != null){
     try {
    
      line = reader.readLine();
      String[] pieces = split(line, ' ');
      if(line != null){
      if(pieces[0].equals("Hand")){
        matchNum = int(pieces[1].substring(1,pieces[1].length()-1));
        Player playerA = new Player(pieces[2], int(pieces[3].substring(1,pieces[3].length()-2)));
        Player playerB = new Player(pieces[4], int(pieces[5].substring(1,pieces[5].length()-2)));
        Player playerC = new Player(pieces[6], int(pieces[7].substring(1,pieces[7].length()-1)));
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

void draw() {
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


void mousePressed(){
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



void drawControlButtons(){
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
  
  fill(0);
  textSize(30);
}


void keyPressed(){
  if(inputFieldActive){
  if( key >= '0' && key <= '9' ){
    input+=char(key);
  } 
  else if( key == BACKSPACE && input.length() > 0 ){
    input = input.substring( 0, input.length()-1);
  } 
  else if (key == RETURN || key == ENTER){
    int finalInput = int(input);
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



