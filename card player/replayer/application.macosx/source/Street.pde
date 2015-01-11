class Street{
  Card cards[];
  
  Street(Card cards[]){
    this.cards = cards;
  }
  
  void drawStreet(int x, int y){
    for(int i=0; i<cards.length; i++){
      cards[i].drawCard(x+(i*(Card.cardWidth+2)),y);
    }
  }
  
}
