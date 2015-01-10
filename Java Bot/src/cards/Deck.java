package cards;

import java.util.*;

public class Deck {
    
    private final List<Card> cards;
    private final Random generator;
    
    public Deck(){
        generator = new Random();
        cards = new ArrayList<Card>();
        for(char suit : Card.getSuits()){
            for(char rank: Card.getRanks()){
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
