'''
this is not actually the deck
it is all possible cards that we don't already know have be dealt out
'''

import random

class Deck:
    
    cards=[]
    
    def __init__(self):
        for suit in range(0,4):
            for faceValue in range(0,13):
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
      
class Card:
    def __init__(self, faceValue, suit):
        self.faceValue=faceValue
        self.suit = suit
    def __str__(self):
        return "face value: " + str(self.faceValue) + ", suit: " + str(self.suit)
        