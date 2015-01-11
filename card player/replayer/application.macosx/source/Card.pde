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
  
  int getSuitNumber(){
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
  
  
  int getRankNumber(){
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
  
  void drawCard(int x, int y){
    PImage cardImage = cardSheet.get(getRankNumber()*cardWidth, getSuitNumber()*cardHeight, cardWidth, cardHeight);
    image(cardImage, x,y);
  }
  
  
  int getCardWidth(){
    return cardWidth;
  }
  int getCardHeight(){
    return cardHeight;
  }
} 
