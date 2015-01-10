package handEvaluator;

import cards.Card;

public class PerformanceTest {

    /*

     This is the old benchmarking code, and is not used right now.
     
     Look at and use PerformanceTest2 insted.
     
      */

    static String[] ref = {
            "(1) Indiana-1, 2006, http://pokerai.org/pf3",
            "(2) Indiana-3, 2007, http://pokerai.org/pf3",
            "(3) University of Alberta, 2000, http://spaz.ca/poker",
            "(4) Spears port of Kevin Suffecool's C evaluator, http://pokerai.org/pf3",
            "(5) Spears port of 2+2 evaluator, http://pokerai.org/pf3",
            "(6) Steve Brecher HandEval, http://www.stevebrecher.com/Software/software.html"
    };
   
    public static void main(String args[]) {
      //correctnessTest();
      /*
      testIndiana();
      testIndiana3();
      testAlberta();
      testSpears();
      testSpears2p2();
      */
      testSteveBrecher();
    }


   public static void testSteveBrecher() {
      int n = 0;
      int[] hand = new int[7];
      int[] reshand = new int[6];
      Card c = new Card("Th");
      System.out.println("should be 34: " + c.ordinal());
      int[] cards = new int[7];
      //for (int i = 0; i < 7; i++) cards[i] = new pokerai.game.eval.stevebrecher.Card("AsAh");
      //pokerai.game.eval.spears.Card[] deck = pokerai.game.eval.spears.Card.values();
      //CardSet cs = new CardSet();
      long key = 0;
      long time = System.currentTimeMillis();
      long sum = 0;
      for (int h1 = 0; h1 < 13; h1++)
        for (int h2 = 0; h2 < 13; h2++) {
//          System.out.println(h1 + " " + h2);
          for (int h3 = 0; h3 < 13; h3++)
            for (int h4 = 0; h4 < 13; h4++)
              for (int h5 = 0; h5 < 13; h5++)  if (h5 != h2)
                for (int h6 = 0; h6 < 13; h6++) if (h6 != h3)
                  for (int h7 = 0; h7 < 13; h7++) if (h7 != h4) {
              //pokerai.game.eval.spears.Hand h = new pokerai.game.eval.spears.Hand();
                    key = 0;
                    key |= (0x1L << (h1));
                    key |= (0x1L << (13+h2));
                    key |= (0x1L << (26+h3));
                    key |= (0x1L << (39+h4));
                    key |= (0x1L << (13+h5));
                    key |= (0x1L << (26+h6));
                    key |= (0x1L << (39+h7));
                    n++;
                    sum += HandEval.hand7Eval(key);
                    //System.out.println(sum);
                  }
        }
     print(sum, time, n, 5);
    }

    public static void print(long sum, long time, long n, int ind) {
      time = System.currentTimeMillis() - time; // time given is start time
      long handsPerSec = Math.round(1000 / ((time*1.0)/ n));
      System.out.println(ref[ind]);
      System.out.println(" --- Hands per second: [b]" + handsPerSec + "[/b], hands " + n + ", checksum " + sum);
      System.out.println();
    }

  }

