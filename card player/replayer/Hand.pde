class Hand{
  Card hole1, hole2;
  
  Hand(Card hole1, Card hole2){
    this.hole1 = hole1;
    this.hole2 = hole2;
  }
  
  void drawHand(int x, int y){
    hole1.drawCard(x,y);
    hole2.drawCard(x+hole1.getCardWidth()+2, y);
  }
  
}
