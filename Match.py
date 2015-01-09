import Street

class Match:
    def __init__(self, dealer, listOfPlayers):
        self.dealer = dealer
        self.street = Street.preflop
        self.streetCards = []
        self.currentPot = 0
        self.playersInPlay = listOfPlayers
    
    # update the street we are on
    # street is a Street enum
    # streetCards is a list of all of the cards now on the board
    def setStreet(self, street, streetCards):
        assert(street.value == len(streetCards))
        self.street = street
        self.streetCards = streetCards
    
    # return the current Street we are on
    def getStreet(self):
        return self.street
    
    # return the current cards laid out on the board
    def getStreetCards(self):
        return self.streetCards
        
    # return the current amount of money in the pot
    def getPot(self):
        return self.currentPot
 
    # update the pot   
    def setPot(self,pot):
        self.currentPot = pot
    
    # add an amount to the pot
    def addToPot(self, amountToAdd):
        self.currentPot+=amountToAdd
    
    # return the players still in play at this stage
    def getPlayersInPlay(self):
        return self.playersInPlay
    
    # remove a player from play (they fold)
    def removePlayerFromPlay(self, player):
        self.listOfPlayers.remove(player)
        