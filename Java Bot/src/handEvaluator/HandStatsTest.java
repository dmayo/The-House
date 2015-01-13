package handEvaluator;

import cards.Card;

public class HandStatsTest {
    public static void main(String args[]){
        long startTime;
        long endTime;
        long timeDiff;
        Card hole1 = new Card("Ah");
        Card hole2 = new Card("Kh");
        Card board1 = new Card("Qh");
        Card board2 = new Card("Jh");
        Card board3 = new Card("Ts");
        
        Card board[] = new Card[]{board1,board2,board3};
        double strength = HandStats.handStrength(hole1, hole2, board);
        startTime = System.currentTimeMillis();
        int iterations = 10000;
        for(int i=0; i<iterations; i++){
             strength = HandStats.handStrength(hole1, hole2, board);

        }
        endTime = System.currentTimeMillis();
        timeDiff = endTime-startTime;
        System.out.println("time diff: " + timeDiff/1000.0);
        System.out.println("Hand strength of hole: Tc,Jc board:2d,Ts,Kh - strength: "+ strength + " time: " + (timeDiff/1000.0)/iterations);

      
        startTime = System.currentTimeMillis();
        double equity = HandStats.monteCarloEquity(5000,hole1, hole2, new Card[]{});
        endTime = System.currentTimeMillis();
        timeDiff = endTime-startTime;
        System.out.println("Hand potential of hole: Tc,Jc board:2d,Ts,Kh - equity: "+ equity + " time: " + timeDiff);
        
        
        /*
        for(int i=0; i<100; i++){
            long startTime1 = System.nanoTime();
            long encode = HandEval.encode(new Card[]{hole1,hole2,board[0],board[1],board[2]});
            HandEval.hand5Eval(encode);
            long timeDiff1 = System.nanoTime()-startTime1;
            System.out.println("Time to encode: " + timeDiff1);
        }
        */
        
    }
}
