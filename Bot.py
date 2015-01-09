class Bot:
    # hand is a tuple of cards
    def __init__(self, hand):
        self.hand = hand
                
    def setHand(self, card1, card2):
        self.hand = (card1, card2)
    
    
        