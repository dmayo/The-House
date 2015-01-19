package actions;

public enum Street {
    PREFLOP,FLOP,TURN,RIVER,NONE;
    
    /**
     * Converts the number of board cards to the corresponding street
     * @param numBoardCards number of cards on the board. Must be 0, 3, 4, or 5.
     * @return corresponding Street
     */
    public static Street fromInt(int numBoardCards){
        switch(numBoardCards){
            case 0:
                return Street.PREFLOP;
            case 3:
                return Street.FLOP;
            case 4:
                return Street.TURN;
            case 5:
                return Street.RIVER;
               
        }
        return Street.NONE;
    }
}
