from enum import Enum

# value represents the number of cards dealt on the board in the street
class Street(Enum):
    preflop = 0
    flop = 3
    turn = 1
    river = 1