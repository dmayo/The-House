class Player:
    
    def __init__(self, name):
        self.name = name
        self.matchHistory = []
    
    def getName(self):
        return self.name
    
    def resetMatchHistory(self):
        self.matchHistory = {}
    
    # action is the action made: BET, CALL, CHECK, FOLD, RAISE
    # amount is the amount of the action - call and check have amount = 0
    # street is the street the action was made on
    def madeAction(self, action, amount, street):
        if street in self.matchHistory:
            self.matchHistory[street].append([(action,amount)])
        else:
            self.matchHistory[street] = [(action,amount)]
    
    # returns a map that maps each street to a list of (action,amount) tuples
    def getAllActions(self):
        return self.matchHistory
    
    # returns a list of (action,amount) tuples for the given street
    def getActionsOfStreet(self, street):
        return self.mathcHistory[street]
        
    