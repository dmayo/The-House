package cards;

import java.util.*;

import cards.Card.Rank;
import cards.Card.Suit;

public class Deck {
    
    private final List<Card> cards;
    private final Random generator;
    private static final Rank ranks[] = {Rank.TWO,Rank.THREE,Rank.FOUR,Rank.FIVE,Rank.SIX,Rank.SEVEN,
                                Rank.EIGHT,Rank.NINE,Rank.TEN,Rank.JACK,Rank.QUEEN,Rank.KING,Rank.ACE};
    private static final Suit suits [] = {Suit.CLUB,Suit.DIAMOND,Suit.SPADE,Suit.HEART};
    
    public Deck(){
        generator = new Random();
        
        cards = new ArrayList<Card>();
        for(Suit suit :suits){
            for(Rank rank: ranks){
                cards.add(new Card(rank,suit));
            }
        }
    }
    
    
    /**
     * @return a random card from the deck. Removes the card from the deck
     */
    public Card dealCard(){
        int index = generator.nextInt(cards.size());
        return cards.remove(index);
    }
    
    
    /**
     * @param card card to remove from the deck
     * @return true if the card was removed, false otherwise
     */
    public Boolean dealCard(Card card){
        return cards.remove(card);
    }
    
    
    /**
     * @param card card to be put back into the deck. Card must not already be in the deck
     */
    public void putBack(Card card){
        cards.add(card);
    }
    
    
    /**
     * @return all of the cards in this deck
     */
    public List<Card> getCards(){
        return new ArrayList<Card>(cards);
    }
}
