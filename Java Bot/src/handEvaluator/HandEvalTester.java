package handEvaluator;

import cards.Card;

public class HandEvalTester {
    static public void main(String [] args){
        // seven card test
        Card card1 = new Card("Ah");
        Card card2 = new Card("Kh");
        Card card3 = new Card("Qh");
        Card card4 = new Card("Jh");
        Card card5 = new Card("Th");
        Card card6 = new Card("9h");
        Card card7 = new Card("8h");
        
        Card cards[] = new Card[]{card1,card2,card3,card4,card5,card6,card7};
        long encoded7 = HandEval.encode(cards);
        int rank = HandEval.hand7Eval(encoded7);
        System.out.println("Royal Flush Rank: " + rank);
        
        
    }
}
