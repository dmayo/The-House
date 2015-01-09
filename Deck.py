'''
this is not actually the deck
it is all possible cards that we don't already know have be dealt out
'''

import random

class Deck:
    
    cards=[]
    
    def __init__(self):
        for suit in Card.suits:
            for faceValue in Card.faceValues:
                self.cards.append(Card(faceValue, suit))

                
    def dealRandomCard(self):
        card=random.sample(self.cards,  1)[0]
        self.cards.remove(card)
        return card

    #card must be in the deck    
    def dealCard(self, card):
        self.cards.remove(card)
        return card
    
    #card must not be in the deck
    def putBack(self, Card):
        self.cards.append(Card)
        
    def getCards(self):
        return self.cards
      
      
class Card:
    suits = ['h','c','s','d']
    faceValues = ['2','3','4','5','6','7','8','9','T','J','Q','K','A']
    
    def __init__(self, faceValue, suit):
        self.faceValue=faceValue
        self.suit = suit
    def __str__(self):
        return self.faceValue + self.suit
    def __eq__(self,other):
        return self.faceValue==other.faceValue and self.suit==other.suit