package handEvaluator;

import java.util.*;

import cards.Card;
import cards.CardSet;

public class HandStats {
    private static final Random rnd = new Random();
    /**
     * Calculates the immediate strength of hand
     * @param hole1 one hole card in player's hand
     * @param hole2 the other hole card in player's hand
     * @param board the cards on the board (3 to 5 cards)
     * @return hand strength from 0.0-1.0
     */
    public static double handStrength(Card hole1, Card hole2, Card board[]){
        int ahead = 0;
        int tied = 0;
        int behind = 0;
        
        long startTime1 = System.nanoTime();
        long encode = HandEval.encode(new Card[]{hole1,hole2,board[0],board[1],board[2]});
        HandEval.hand5Eval(encode);
        long timeDiff1 = System.nanoTime()-startTime1;
        System.out.println("Time to encode: " + timeDiff1);
        
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
//            System.out.println(h1 + " " + h2);
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
        return n;//(ahead+(tied/2.0)) / (ahead+tied+behind);
    }
    
    
    
    public static double[] handPotential(Card hole1, Card hole2, Card board[]){
        final int ahead = 0;
        final int behind = 1;
        final int tied = 2;
        int HP[][] = new int[3][3];
        int HPTotal[] = new int[3];
        
        Card allCards[] = combineCardsAndBoard(hole1,hole2,board);
        int ourRank = rankHand(allCards);
        
        CardSet cardSet = CardSet.freshDeck();
        cardSet.removeAll(Arrays.asList(allCards));
        
        for(Card oppHole1 : cardSet){
            for(Card oppHole2 : cardSet){
                if(oppHole1.equals(oppHole2)){
                    break;
                }
                                
                Card oppHand[] = combineCardsAndBoard(oppHole1,oppHole2,board);  
                int oppRank = rankHand(oppHand);
                int index;
                
                if (ourRank > oppRank){
                    index = ahead;
                }
                else if(ourRank < oppRank){
                    index = behind;
                }
                else{
                    index = tied;
                }
                
                HPTotal[index]++;
                
                if(board.length == 3){
                    for(Card turn : cardSet.toArray()){
                        for(Card river: cardSet.toArray()){
                            if(turn.equals(river) || turn.equals(oppHole1) || turn.equals(oppHole2) 
                                    || river.equals(oppHole1) || river.equals(oppHole2)){
                                break;
                            }
                            //System.out.println(river);
                            Card newBoard[] = combineCardsAndBoard(turn,river,board);
                            int ourbest = rankHand(combineCardsAndBoard(hole1,hole2,newBoard));
                            int oppbest = rankHand(combineCardsAndBoard(oppHole1,oppHole2,newBoard));
                            if(ourbest > oppbest) HP[index][ahead]++;
                            else if(ourbest == oppbest) HP[index][tied]++;
                            else HP[index][behind]++;
                        }
                    }
                }
                else if(board.length == 4){
                    for(Card river: cardSet){
                        if(river.equals(oppHole1) || river.equals(oppHole2)){
                            break;
                        }
                        Card newBoard[] = combineCardsAndBoard(river,board);
                        int ourbest = rankHand(combineCardsAndBoard(hole1,hole2,newBoard));
                        int oppbest = rankHand(combineCardsAndBoard(oppHole1,oppHole2,newBoard));
                        if(ourbest > oppbest) HP[index][ahead]++;
                        else if(ourbest == oppbest) HP[index][tied]++;
                        else HP[index][behind]++;
                    }
                }
            }
        }
        
        double Ppot = (HP[behind][ahead]+HP[behind][tied]/2.0+HP[tied][ahead]/2.0)/(HPTotal[behind]+HPTotal[tied]);
        double Npot = (HP[ahead][behind]+HP[tied][behind]/2.0+HP[ahead][tied]/2.0)/(HPTotal[ahead]+HPTotal[tied]);
        return new double[]{Ppot,Npot};
    }
    
    
    
    static public double monteCarloEquity(int iterations, Card hole1, Card hole2, Card board[]){
        int ahead = 0;
        int tied = 0;
        int behind = 0;
        long ourHandLong = 0x01L << hole1.ordinal();
        ourHandLong |= 0x01L << hole2.ordinal();
        long boardLong = HandEval.encode(board);
        long originalUsed = ourHandLong | boardLong;
        
        for(int i=0; i < iterations; i++){
            long used = originalUsed;
            long oppHandLong = addNewRandomCard(2,0,used);
            used |= oppHandLong;
            
            long rollOutBoard = addNewRandomCard(5-board.length, boardLong,used);
            //System.out.println(Long.toBinaryString(rollOutBoard));
            int ourRank = HandEval.hand7Eval(rollOutBoard | ourHandLong);
            int oppRank = HandEval.hand7Eval(rollOutBoard | oppHandLong);
            
            if (ourRank > oppRank){
                ahead++;
            }
            else if(ourRank < oppRank){
                behind++;
            }
            else{
                tied ++;
            }
        }
        
        return (ahead+(tied/2.0)) / (ahead+tied+behind);
    }
    
    
    
    static private long addNewRandomCard(int num, long addTo, long used){
        final long one = 0x01L;
        for(int i=0; i<num; i++){
            int card = rnd.nextInt(52);
            while(((one << card) & used) == 1){
                card = (card+1)%52;
            }
            long cardLong = one << card;         
            addTo |=  cardLong;
            used |= cardLong;           
        }
        
        return addTo;
    }

    
    
    private static Card[] combineCardsAndBoard(Card card1, Card card2, Card board[]){
        Card allCards[] = new Card[board.length + 2];
        allCards[0] = card1;
        allCards[1] = card2;
        for(int i=2; i<allCards.length; i++){
            allCards[i] = board[i-2];
        }
        return allCards;
    }
    
    
    private static Card[] combineCardsAndBoard(Card card1, Card board[]){
        Card allCards[] = new Card[board.length + 1];
        allCards[0] = card1;
        for(int i=1; i<allCards.length; i++){
            allCards[i] = board[i-1];
        }
        return allCards;
    }
    
    
    
    private static int rankHand(Card cards[]){
        long handLong = HandEval.encode(cards);
        int rank = 0;
        
        if(cards.length == 5){
            System.out.println("length five");
            rank = HandEval.hand5Eval(handLong);
        }
        else if(cards.length==6){
            rank = HandEval.hand6Eval(handLong);
        }
        else if(cards.length==7){
            rank = HandEval.hand7Eval(handLong);
        }
        
        return rank;
    }
}
