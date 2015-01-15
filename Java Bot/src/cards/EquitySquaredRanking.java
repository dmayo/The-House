package cards;

public class EquitySquaredRanking {
    private static Hand ranking[] = new Hand[]{
        new Hand("Ah","Ad"), //1
        new Hand("Kh","Kd"), //2
        new Hand("Qh","Qd"), //3
        new Hand("Jh","Jd"), //4
        new Hand("Th","Td"), //5
        new Hand("9h","9d"), //6
        new Hand("Ah","Kh"), //7
        new Hand("8h","8d"), //8
        new Hand("Ah","Qh"), //9
        new Hand("Ah","Kd"), //10
        new Hand("Ah","Jh"), //11
        new Hand("Ah","Th"), //12
        new Hand("Kh","Qh"), //13
        new Hand("Ah","Qd"), //14
        new Hand("7h","7d"), //15
        new Hand("Kh","Jh"), //16
        new Hand("Ah","Jd"), //17
        new Hand("Kh","Th"), //18
        new Hand("Ah","9h"), //19
        new Hand("Kh","Qd"), //20
        new Hand("Ah","Td"), //21
        new Hand("Qh","Jh"), //22
        new Hand("Ah","8h"), //23
        new Hand("6h","6d"), //24
        new Hand("Kh","Jd"), //25
        new Hand("Qh","Th"), //26
        new Hand("Ah","7h"), //27
        new Hand("Kh","9h"), //28
        new Hand("Kh","Td"), //29
        new Hand("Jh","Th"), //30
        new Hand("Ah","9d"), //31
        new Hand("Ah","5h"), //32
        new Hand("Qh","Jd"), //33
        new Hand("Ah","6h"), //34
        new Hand("Qh","9h"), //35
        new Hand("Ah","4h"), //36
        new Hand("Ah","8d"), //37
        new Hand("5h","5d"), //38
        new Hand("Qh","Td"), //39
        new Hand("Kh","8h"), //40
        new Hand("Ah","3h"), //41
        new Hand("Jh","9h"), //42
        new Hand("Ah","7d"), //43
        new Hand("Kh","7h"), //44
        new Hand("Kh","9d"), //45
        new Hand("Jh","Td"), //46
        new Hand("Ah","2h"), //47
        new Hand("Th","9h"), //48
        new Hand("Qh","8h"), //49
        new Hand("Kh","6h"), //50
        new Hand("Ah","5d"), //51
        new Hand("Ah","6d"), //52
        new Hand("Qh","9d"), //53
        new Hand("Kh","5h"), //54
        new Hand("Jh","8h"), //55
        new Hand("Ah","4d"), //56
        new Hand("Kh","8d"), //57
        new Hand("4h","4d"), //58
        new Hand("Th","8h"), //59
        new Hand("Kh","4h"), //60
        new Hand("Qh","7h"), //61
        new Hand("Ah","3d"), //62
        new Hand("Jh","9d"), //63
        new Hand("Kh","7d"), //64
        new Hand("9h","8h"), //65
        new Hand("Kh","3h"), //66
        new Hand("Qh","6h"), //67
        new Hand("Th","9d"), //68
        new Hand("Ah","2d"), //69
        new Hand("Jh","7h"), //70
        new Hand("Qh","8d"), //71
        new Hand("Kh","6d"), //72
        new Hand("Kh","2h"), //73
        new Hand("Qh","5h"), //74
        new Hand("Th","7h"), //75
        new Hand("Jh","8d"), //76
        new Hand("Qh","4h"), //77
        new Hand("Kh","5d"), //78
        new Hand("9h","7h"), //79
        new Hand("3h","3d"), //80
        new Hand("8h","7h"), //81
        new Hand("Th","8d"), //82
        new Hand("Jh","6h"), //83
        new Hand("Qh","3h"), //84
        new Hand("Qh","7d"), //85
        new Hand("Kh","4d"), //86
        new Hand("Jh","5h"), //87
        new Hand("9h","8d"), //88
        new Hand("Th","6h"), //89
        new Hand("Qh","2h"), //90
        new Hand("Qh","6d"), //91
        new Hand("Kh","3d"), //92
        new Hand("9h","6h"), //93
        new Hand("Jh","7d"), //94
        new Hand("Jh","4h"), //95
        new Hand("8h","6h"), //96
        new Hand("7h","6h"), //97
        new Hand("Qh","5d"), //98
        new Hand("Kh","2d"), //99
        new Hand("Th","7d"), //100
        new Hand("Jh","3h"), //101
        new Hand("2h","2d"), //102
        new Hand("Th","5h"), //103
        new Hand("9h","7d"), //104
        new Hand("Qh","4d"), //105
        new Hand("Jh","2h"), //106
        new Hand("8h","7d"), //107
        new Hand("9h","5h"), //108
        new Hand("Th","4h"), //109
        new Hand("6h","5h"), //110
        new Hand("7h","5h"), //111
        new Hand("8h","5h"), //112
        new Hand("Jh","6d"), //113
        new Hand("Qh","3d"), //114
        new Hand("Th","3h"), //115
        new Hand("Jh","5d"), //116
        new Hand("Th","6d"), //117
        new Hand("5h","4h"), //118
        new Hand("Qh","2d"), //119
        new Hand("Th","2h"), //120
        new Hand("9h","6d"), //121
        new Hand("6h","4h"), //122
        new Hand("8h","6d"), //123
        new Hand("7h","6d"), //124
        new Hand("9h","4h"), //125
        new Hand("Jh","4d"), //126
        new Hand("7h","4h"), //127
        new Hand("8h","4h"), //128
        new Hand("9h","3h"), //129
        new Hand("Jh","3d"), //130
        new Hand("5h","3h"), //131
        new Hand("Th","5d"), //132
        new Hand("9h","2h"), //133
        new Hand("6h","5d"), //134
        new Hand("6h","3h"), //135
        new Hand("Jh","2d"), //136
        new Hand("9h","5d"), //137
        new Hand("Th","4d"), //138
        new Hand("7h","5d"), //139
        new Hand("8h","5d"), //140
        new Hand("7h","3h"), //141
        new Hand("4h","3h"), //142
        new Hand("8h","3h"), //143
        new Hand("8h","2h"), //144
        new Hand("Th","3d"), //145
        new Hand("5h","2h"), //146
        new Hand("5h","4d"), //147
        new Hand("Th","2d"), //148
        new Hand("6h","2h"), //149
        new Hand("6h","4d"), //150
        new Hand("4h","2h"), //151
        new Hand("9h","4d"), //152
        new Hand("7h","2h"), //153
        new Hand("7h","4d"), //154
        new Hand("8h","4d"), //155
        new Hand("9h","3d"), //156
        new Hand("3h","2h"), //157
        new Hand("5h","3d"), //158
        new Hand("9h","2d"), //159
        new Hand("6h","3d"), //160
        new Hand("4h","3d"), //161
        new Hand("7h","3d"), //162
        new Hand("8h","3d"), //163
        new Hand("8h","2d"), //164
        new Hand("5h","2d"), //165
        new Hand("6h","2d"), //166
        new Hand("4h","2d"), //167
        new Hand("7h","2d"), //168
        new Hand("3h","2d")  //169    
    };
    
    
    /**
     * returns the ranking of a given hand in the equity squared table
     * 1 is the best, 
     * @param handToRank
     * @return
     */
    public static int getRank(Hand handToRank){
        int rank = 1;
        for(Hand hand : ranking){
            if(handToRank.isEqualIgnoringSuit(hand) && (hand.isSuited() == handToRank.isSuited())){
                return rank;
            }
            rank++;
        }
        
        return 0;
    }
}
