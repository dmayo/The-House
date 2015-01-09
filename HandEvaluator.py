import sys
from Deck import *
import itertools

sys.path.insert(0, "/pypoker-eval2")
sys.path.insert(0, ".")
sys.path.insert(0, ".libs")
from pokereval import PokerEval


class HandEvaluator:
        def __init__(self):
            self.pokereval = PokerEval()
        
        # hiOrLow : "hi" or "low"
        # hand is list of 5 or more cards
        def bestHandValue(self, hiOrLow, handOfCards):
            handToPrettyPrint = [str(card) for card in handOfCards]
            return self.pokereval.best_hand_value(hiOrLow, handToPrettyPrint)
        
        def handStrength(self, ourCards, boardCards):
            allCards = ourCards+boardCards
            ahead = 0
            tied = 0
            behind = 0
            ourRank = self.bestHandValue("hi", allCards)
            deck = Deck()
            for card in allCards:
                deck.dealCard(card)
            
            for i, j in itertools.product(deck.getCards(), deck.getCards()):
                oppRank = self.bestHandValue("hi",boardCards+[i,j])
                if ourRank > oppRank: ahead +=1
                elif ourRank == oppRank: tied +=1
                else: behind +=1
        
            return (ahead+(tied/2.0)) / (ahead+tied+behind)
            
our = [Card('4','s'), Card('4','h')]
board = [Card('K','d'), Card('K','c'), Card('K','h')]
h = HandEvaluator()
print h.handStrength(our,board)
