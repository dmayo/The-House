package handEvaluator;

import java.util.*;

import cards.Card;
import cards.CardSet;
import cards.Hand;

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
        
        long boardLong = HandEval.encode(board);
        long ourCardsAndBoard =  HandEval.encode(hole1) | HandEval.encode(hole2) | boardLong;
        int ourRank = HandEval.hand5Eval(ourCardsAndBoard);
        for(int h1 = 0; h1 < 52; h1++){
            for(int h2 = h1+1; h2 < 52; h2++){       
                long oppHand = (0x01L << h1) | (0x01L << h2);
                if((oppHand & ourCardsAndBoard) == 0){
                    int oppRank = HandEval.hand5Eval(oppHand | boardLong);
                    
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
              
            }
        }
        return (ahead+(tied/2.0)) / (ahead+tied+behind);
    }
    
    
    /**
     * Calculates the immediate strength of hand
     * @param hole1 one hole card in player's hand
     * @param hole2 the other hole card in player's hand
     * @param board the cards on the board (3 to 5 cards)
     * @return hand strength from 0.0-1.0
     */
    public static double handStrengthOld(Card hole1, Card hole2, Card board[]){
        int ahead = 0;
        int tied = 0;
        int behind = 0;
        
        Card allCards[] = combineCardsAndBoard(hole1,hole2,board); 
        int ourRank = rankHand(allCards);
        System.out.println("ourrank: " + ourRank);
        
        CardSet cardSet = CardSet.freshDeck();
        cardSet.removeAll(Arrays.asList(allCards));
        
        for(Card oppHole1 : cardSet){
            for(Card oppHole2 : cardSet){
                if(oppHole1.equals(oppHole2)){
                    break;
                }
                                
                Card oppHand[] = combineCardsAndBoard(oppHole1,oppHole2,board);  
                int oppRank = rankHand(oppHand);
                
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
        }
        return (ahead+(tied/2.0)) / (ahead+tied+behind);
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
    
    
    
    static public double monteCarloEquity(int iterations, Hand hand, Card board[]){
        int ahead = 0;
        int tied = 0;
        
        Card[] handCards = hand.getCards();
        long ourHandLong = HandEval.encode(handCards[0]) | HandEval.encode(handCards[1]);
        long boardLong = HandEval.encode(board);
        long originalUsed = ourHandLong | boardLong;
        
        for(int i=0; i < iterations; i++){
            long used = originalUsed;
            long oppHandLong = addNewRandomCard(2,0,used);
            used |= oppHandLong;
            
            long rollOutBoard = addNewRandomCard(5-board.length, boardLong, used);
            int ourRank = HandEval.hand7Eval(rollOutBoard | ourHandLong);
            int oppRank = HandEval.hand7Eval(rollOutBoard | oppHandLong);
            
            if (ourRank > oppRank){
                ahead++;
            }
            else if(ourRank == oppRank){
                tied++;
            }
        }
        
        return (ahead+(tied/2.0)) / (double)(iterations);
    }
    
    
    
    static private long addNewRandomCard(int num, long addTo, long used){
        final long one = 0x1L;
        for(int i=0; i<num; i++){
            int card = rnd.nextInt(52);
            while(((one << card) & used) != 0){
                card = (card+1) % 52;
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
