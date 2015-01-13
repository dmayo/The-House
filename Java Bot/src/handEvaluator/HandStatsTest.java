package handEvaluator;

import cards.Card;

public class HandStatsTest {
    public static void main(String args[]){
       // long startTime;
       // long endTime;
       // long timeDiff;
        Card hole1 = new Card("Tc");
        Card hole2 = new Card("Jc");
        Card board1 = new Card("2d");
        Card board2 = new Card("Ts");
        Card board3 = new Card("Kh");
        
        Card board[] = new Card[]{board1,board2,board3};
      //  startTime = System.currentTimeMillis();
        double strength = HandStats.handStrength( hole1, hole2, board);
      //  endTime = System.currentTimeMillis();
      //  timeDiff = endTime-startTime;
      //  System.out.println("Hand strength of hole: Tc,Jc board:2d,Ts,Kh - strength: "+ strength + " time: " + timeDiff);
        /*
        startTime = System.currentTimeMillis();
        double pPot = HandStats.handPotential(hole1, hole2, board)[0];
        endTime = System.currentTimeMillis();
        timeDiff = endTime-startTime;
        System.out.println("Hand potential of hole: Tc,Jc board:2d,Ts,Kh - potential: "+ pPot + " time: " + timeDiff);
        */
        
        long startTime1 = System.nanoTime();
        long encode = HandEval.encode(new Card[]{hole1,hole2,board[0],board[1],board[2]});
        HandEval.hand5Eval(encode);
        long timeDiff1 = System.nanoTime()-startTime1;
        System.out.println("Time to encode: " + timeDiff1);
    }
}
