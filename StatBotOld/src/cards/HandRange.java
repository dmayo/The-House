package cards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cards.Card.Rank;
import cards.Card.Suit;

public class HandRange {
    static List<String> percentToRange = new ArrayList<String>();

    static{
        percentToRange.add(0,"");
        percentToRange.add(1,"KK+");
        percentToRange.add(2,"JJ+");
        percentToRange.add(3,"88+");
        percentToRange.add(4, "AKs,77+");
        percentToRange.add(5, "AJs+,77+,AKo");
        percentToRange.add(6, "ATs+,77+,AKo");
        percentToRange.add(7, "ATs+,77+,AQo+");
        percentToRange.add(8, "ATs+,KQs,77+,AJo+");
        percentToRange.add(9, "A9s+,KQs,66+,AJo+");
        percentToRange.add(10, "A8s+,KJs+,66+,ATo+");
        percentToRange.add(11, "A8s+,KTs+,66+,ATo+,KQo");
        percentToRange.add(12, "A7s+,KTs+,66+,ATo+,KQo");
        percentToRange.add(13, "A7s+,KTs+,66+,A9o+,KJo+");
        percentToRange.add(14, "A7s+,KTs+,55+,A9o+,KJo+");
        percentToRange.add(15, "A5s+,K9s+,QJs,55+,A9o+,KJo+");
        percentToRange.add(16, "A5s+,K9s+,QJs,55+,A8o+,KJo+");
        percentToRange.add(17, "A5s+,K9s+,QJs,55+,A8o+,KTo+");
        percentToRange.add(18, "A4s+,K9s+,QTs+,55+,A7o+,KTo+");
        percentToRange.add(19, "A3s+,K8s+,QTs+,55+,A7o+,KTo+");
        percentToRange.add(20, "A3s+,K8s+,QTs+,55+,A7o+,KTo+,QJo");
        percentToRange.add(21, "A3s+,K8s+,QTs+,55+,A7o+,K9o+,QJo");
        percentToRange.add(22, "A3s+,K8s+,QTs+,55+,A7o+,A5o,K9o+,QJo");
        percentToRange.add(23, "A3s+,K8s+,Q9s+,55+,A5o+,K9o+,QJo");
        percentToRange.add(24, "A2s+,K7s+,Q9s+,JTs,55+,A5o+,K9o+,QJo");
        percentToRange.add(25, "A2s+,K7s+,Q9s+,JTs,44+,A5o+,K9o+,QTo+");
        percentToRange.add(26, "A2s+,K7s+,Q9s+,JTs,44+,A4o+,K9o+,QTo+");
        percentToRange.add(27, "A2s+,K6s+,Q8s+,JTs,44+,A4o+,K9o+,QTo+");
        percentToRange.add(28, "A2s+,K6s+,Q8s+,JTs,44+,A4o+,K8o+,QTo+");
        percentToRange.add(29, "A2s+,K5s+,Q8s+,J9s+,44+,A3o+,K8o+,QTo+");
        percentToRange.add(30, "A2s+,K5s+,Q8s+,J9s+,44+,A3o+,K8o+,Q9o+");
        percentToRange.add(31, "A2s+,K5s+,Q8s+,J9s+,44+,A3o+,K8o+,Q9o+,JTo");
        percentToRange.add(32, "A2s+,K5s+,Q8s+,J9s+,44+,A3o+,K7o+,Q9o+,JTo");
        percentToRange.add(33, "A2s+,K4s+,Q8s+,J9s+,44+,A2o+,K7o+,Q9o+,JTo");
        percentToRange.add(34, "A2s+,K4s+,Q7s+,J9s+,44+,A2o+,K6o+,Q9o+,JTo");
        percentToRange.add(35, "A2s+,K3s+,Q7s+,J8s+,T9s,44+,A2o+,K6o+,Q9o+,JTo");
        percentToRange.add(36, "A2s+,K3s+,Q6s+,J8s+,T9s,33+,A2o+,K6o+,Q9o+,JTo");
        percentToRange.add(37, "A2s+,K3s+,Q6s+,J8s+,T9s,33+,A2o+,K6o+,Q8o+,JTo");
        percentToRange.add(38, "A2s+,K3s+,Q6s+,J8s+,T9s,33+,A2o+,K5o+,Q8o+,JTo");
        percentToRange.add(39, "A2s+,K2s+,Q6s+,J8s+,T9s,33+,A2o+,K5o+,Q8o+,J9o+");
        percentToRange.add(40, "A2s+,K2s+,Q5s+,J7s+,T8s+,33+,A2o+,K5o+,Q8o+,J9o+");
        percentToRange.add(41, "A2s+,K2s+,Q4s+,J7s+,T8s+,33+,A2o+,K4o+,Q8o+,J9o+");
        percentToRange.add(42, "A2s+,K2s+,Q4s+,J7s+,T8s+,33+,A2o+,K4o+,Q7o+,J9o+");
        percentToRange.add(43, "A2s+,K2s+,Q4s+,J7s+,T8s+,33+,A2o+,K4o+,Q7o+,J9o+,T9o");
        percentToRange.add(44, "A2s+,K2s+,Q4s+,J7s+,T8s+,33+,A2o+,K4o+,Q7o+,J8o+,T9o");
        percentToRange.add(45, "A2s+,K2s+,Q3s+,J7s+,T8s+,33+,A2o+,K3o+,Q7o+,J8o+,T9o");
        percentToRange.add(46, "A2s+,K2s+,Q3s+,J7s+,T8s+,33+,A2o+,K3o+,Q6o+,J8o+,T9o");
        percentToRange.add(47, "A2s+,K2s+,Q3s+,J6s+,T7s+,98s,33+,A2o+,K3o+,Q6o+,J8o+,T9o");
        percentToRange.add(48, "A2s+,K2s+,Q3s+,J6s+,T7s+,98s,33+,A2o+,K2o+,Q6o+,J8o+,T9o");
        percentToRange.add(49, "A2s+,K2s+,Q3s+,J6s+,T7s+,98s,22+,A2o+,K2o+,Q6o+,J7o+,T9o");
        percentToRange.add(50, "A2s+,K2s+,Q2s+,J6s+,T7s+,98s,22+,A2o+,K2o+,Q5o+,J7o+,T9o");
        percentToRange.add(51, "A2s+,K2s+,Q2s+,J5s+,T7s+,98s,22+,A2o+,K2o+,Q5o+,J7o+,T8o+");
        percentToRange.add(52, "A2s+,K2s+,Q2s+,J5s+,T7s+,98s,22+,A2o+,K2o+,Q4o+,J7o+,T8o+");
        percentToRange.add(53, "A2s+,K2s+,Q2s+,J4s+,T7s+,97s+,22+,A2o+,K2o+,Q4o+,J7o+,T8o+");
        percentToRange.add(54, "A2s+,K2s+,Q2s+,J3s+,T6s+,97s+,22+,A2o+,K2o+,Q3o+,J7o+,T8o+");
        percentToRange.add(55, "A2s+,K2s+,Q2s+,J3s+,T6s+,97s+,22+,A2o+,K2o+,Q3o+,J7o+,T8o+,98o");
        percentToRange.add(56, "A2s+,K2s+,Q2s+,J3s+,T6s+,97s+,87s,22+,A2o+,K2o+,Q3o+,J7o+,T8o+,98o");
        percentToRange.add(57, "A2s+,K2s+,Q2s+,J3s+,T6s+,97s+,87s,22+,A2o+,K2o+,Q3o+,J7o+,T7o+,98o");
        percentToRange.add(58, "A2s+,K2s+,Q2s+,J2s+,T6s+,96s+,87s,22+,A2o+,K2o+,Q3o+,J6o+,T7o+,98o");
        percentToRange.add(59, "A2s+,K2s+,Q2s+,J2s+,T6s+,96s+,87s,22+,A2o+,K2o+,Q2o+,J6o+,T7o+,98o");
        percentToRange.add(60, "A2s+,K2s+,Q2s+,J2s+,T5s+,96s+,87s,22+,A2o+,K2o+,Q2o+,J5o+,T7o+,98o");
        percentToRange.add(61, "A2s+,K2s+,Q2s+,J2s+,T4s+,96s+,87s,22+,A2o+,K2o+,Q2o+,J5o+,T7o+,97o+");
        percentToRange.add(62, "A2s+,K2s+,Q2s+,J2s+,T4s+,96s+,86s+,22+,A2o+,K2o+,Q2o+,J5o+,T7o+,97o+");
        percentToRange.add(63, "A2s+,K2s+,Q2s+,J2s+,T4s+,96s+,86s+,22+,A2o+,K2o+,Q2o+,J4o+,T7o+,97o+");
        percentToRange.add(64, "A2s+,K2s+,Q2s+,J2s+,T3s+,95s+,86s+,22+,A2o+,K2o+,Q2o+,J4o+,T6o+,97o+");
        percentToRange.add(65, "A2s+,K2s+,Q2s+,J2s+,T3s+,95s+,86s+,76s,22+,A2o+,K2o+,Q2o+,J3o+,T6o+,97o+");
        percentToRange.add(66, "A2s+,K2s+,Q2s+,J2s+,T3s+,95s+,86s+,76s,22+,A2o+,K2o+,Q2o+,J3o+,T6o+,97o+,87o");
        percentToRange.add(67, "A2s+,K2s+,Q2s+,J2s+,T2s+,95s+,85s+,76s,22+,A2o+,K2o+,Q2o+,J3o+,T6o+,97o+,87o");
        percentToRange.add(68, "A2s+,K2s+,Q2s+,J2s+,T2s+,95s+,85s+,76s,22+,A2o+,K2o+,Q2o+,J3o+,T6o+,96o+,87o");
        percentToRange.add(69, "A2s+,K2s+,Q2s+,J2s+,T2s+,95s+,85s+,76s,22+,A2o+,K2o+,Q2o+,J2o+,T6o+,96o+,87o");
        percentToRange.add(70, "A2s+,K2s+,Q2s+,J2s+,T2s+,94s+,85s+,75s+,22+,A2o+,K2o+,Q2o+,J2o+,T5o+,96o+,87o");
        percentToRange.add(71, "A2s+,K2s+,Q2s+,J2s+,T2s+,94s+,85s+,75s+,22+,A2o+,K2o+,Q2o+,J2o+,T4o+,96o+,87o");
        percentToRange.add(72, "A2s+,K2s+,Q2s+,J2s+,T2s+,93s+,85s+,75s+,22+,A2o+,K2o+,Q2o+,J2o+,T4o+,96o+,86o+");
        percentToRange.add(73, "A2s+,K2s+,Q2s+,J2s+,T2s+,93s+,84s+,75s+,65s,22+,A2o+,K2o+,Q2o+,J2o+,T4o+,96o+,86o+");
        percentToRange.add(74, "A2s+,K2s+,Q2s+,J2s+,T2s+,93s+,84s+,75s+,65s,22+,A2o+,K2o+,Q2o+,J2o+,T4o+,95o+,86o+");
        percentToRange.add(75, "A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,84s+,75s+,65s,22+,A2o+,K2o+,Q2o+,J2o+,T3o+,95o+,86o+");
        percentToRange.add(76, "A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,84s+,75s+,65s,22+,A2o+,K2o+,Q2o+,J2o+,T3o+,95o+,86o+,76o");
        percentToRange.add(77, "A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,84s+,74s+,65s,22+,A2o+,K2o+,Q2o+,J2o+,T2o+,95o+,86o+,76o");
        percentToRange.add(78, "A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,84s+,74s+,65s,54s,22+,A2o+,K2o+,Q2o+,J2o+,T2o+,95o+,85o+,76o");
        percentToRange.add(79, "A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,83s+,74s+,64s+,54s,22+,A2o+,K2o+,Q2o+,J2o+,T2o+,95o+,85o+,76o");
        percentToRange.add(80, "A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,83s+,74s+,64s+,54s,22+,A2o+,K2o+,Q2o+,J2o+,T2o+,94o+,85o+,76o");
        percentToRange.add(81, "A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,82s+,74s+,64s+,54s,22+,A2o+,K2o+,Q2o+,J2o+,T2o+,94o+,85o+,75o+");
        percentToRange.add(82, "A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,82s+,73s+,64s+,54s,22+,A2o+,K2o+,Q2o+,J2o+,T2o+,93o+,85o+,75o+");
        percentToRange.add(83, "A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,82s+,73s+,64s+,54s,22+,A2o+,K2o+,Q2o+,J2o+,T2o+,93o+,85o+,75o+,65o");
        percentToRange.add(84, "A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,82s+,73s+,63s+,53s+,22+,A2o+,K2o+,Q2o+,J2o+,T2o+,93o+,85o+,75o+,65o");
        percentToRange.add(85, "A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,82s+,73s+,63s+,53s+,22+,A2o+,K2o+,Q2o+,J2o+,T2o+,93o+,84o+,75o+,65o");
        percentToRange.add(86, "A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,82s+,73s+,63s+,53s+,43s,22+,A2o+,K2o+,Q2o+,J2o+,T2o+,92o+,84o+,75o+,65o");
        percentToRange.add(87, "A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,82s+,73s+,63s+,53s+,43s,22+,A2o+,K2o+,Q2o+,J2o+,T2o+,92o+,84o+,74o+,65o");
        percentToRange.add(88, "A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,82s+,72s+,63s+,53s+,43s,22+,A2o+,K2o+,Q2o+,J2o+,T2o+,92o+,84o+,74o+,65o,54o");
        percentToRange.add(89, "A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,82s+,72s+,63s+,52s+,43s,22+,A2o+,K2o+,Q2o+,J2o+,T2o+,92o+,84o+,74o+,64o+,54o");
        percentToRange.add(90, "A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,82s+,72s+,62s+,52s+,43s,22+,A2o+,K2o+,Q2o+,J2o+,T2o+,92o+,83o+,74o+,64o+,54o");
        percentToRange.add(91, "A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,82s+,72s+,62s+,52s+,42s+,22+,A2o+,K2o+,Q2o+,J2o+,T2o+,92o+,83o+,74o+,64o+,54o");
        percentToRange.add(92, "A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,82s+,72s+,62s+,52s+,42s+,22+,A2o+,K2o+,Q2o+,J2o+,T2o+,92o+,82o+,74o+,64o+,54o");
        percentToRange.add(93, "A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,82s+,72s+,62s+,52s+,42s+,22+,A2o+,K2o+,Q2o+,J2o+,T2o+,92o+,82o+,73o+,64o+,53o+");
        percentToRange.add(94, "A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,82s+,72s+,62s+,52s+,42s+,22+,A2o+,K2o+,Q2o+,J2o+,T2o+,92o+,82o+,73o+,63o+,53o+");
        percentToRange.add(95, "A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,82s+,72s+,62s+,52s+,42s+,32s,22+,A2o+,K2o+,Q2o+,J2o+,T2o+,92o+,82o+,73o+,63o+,53o+");
        percentToRange.add(96, "A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,82s+,72s+,62s+,52s+,42s+,32s,22+,A2o+,K2o+,Q2o+,J2o+,T2o+,92o+,82o+,72o+,63o+,53o+,43o");
        percentToRange.add(97, "A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,82s+,72s+,62s+,52s+,42s+,32s,22+,A2o+,K2o+,Q2o+,J2o+,T2o+,92o+,82o+,72o+,63o+,52o+,43o");
        percentToRange.add(98, "A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,82s+,72s+,62s+,52s+,42s+,32s,22+,A2o+,K2o+,Q2o+,J2o+,T2o+,92o+,82o+,72o+,62o+,52o+,43o");
        percentToRange.add(99, "A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,82s+,72s+,62s+,52s+,42s+,32s,22+,A2o+,K2o+,Q2o+,J2o+,T2o+,92o+,82o+,72o+,62o+,52o+,42o+");
        percentToRange.add(100, "A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,82s+,72s+,62s+,52s+,42s+,32s,22+,A2o+,K2o+,Q2o+,J2o+,T2o+,92o+,82o+,72o+,62o+,52o+,42o+,32o");
    }
    
    
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
    
    
    public static List<Hand> getHandsFromRange(String range){
        List<Hand> hands = new ArrayList<Hand>();
        String handRanges[] = range.split(",");
        
        for(String handRange : handRanges){
            if(handRange.length() == 2){
                String card = handRange.substring(0,1);
                hands.add(new Hand(card+"h",card+"d"));   
            }
            else if(handRange.length()==3 && handRange.contains("+")){
                String card = handRange.substring(0,1);
                hands.add(new Hand(card+"h",card+"d"));         

                Rank rankCard = Rank.fromChar(handRange.charAt(1));
                Rank allRanks[] = Rank.values();
                for(int i = rankCard.ordinal()+1; i<allRanks.length; i++){
                    Rank rank = allRanks[i];
                    hands.add(new Hand(new Card(rank,Suit.fromChar('h')), new Card(rank, Suit.fromChar('d'))));
                }
            }
            else if(handRange.length() == 3){
                boolean suited = handRange.charAt(2) == 's';
                String card1 = handRange.substring(0,1) + "h";
                String card2 = handRange.substring(1,2) + (suited ? "h" : "d");
                hands.add(new Hand(card1,card2));          
            }
            else if(handRange.length() == 4){
                boolean suited = handRange.charAt(2) == 's';
                String card1 = handRange.substring(0,1) + "h";
                String card2 = handRange.substring(1,2) + (suited ? "h" : "d");
                hands.add(new Hand(card1,card2));         
                
                Rank rankCard2 = Rank.fromChar(handRange.charAt(1));
                Rank allRanks[] = Rank.values();
                for(int i = rankCard2.ordinal()+1; i<allRanks.length; i++){
                    Rank rank = allRanks[i];
                    Suit suit = Suit.fromChar(card2.charAt(1));
                    hands.add(new Hand(new Card(card1), new Card(rank, suit)));
                }
            }  
        }
        
        return hands;
    }
    
    
    /**
     * 
     * @param percent 0-100
     * @return the range that corresponds to the given percent
     */
    public static String getRangeFromPercent(int percent){
        return percentToRange.get(percent);
    }
    
    
    /**
     * 
     * @param hand
     * @param range
     * @return true if the given hand is in the given range, false otherwise
     */
    public static boolean handIsInRange(Hand hand, String range){
        List<Hand> handsInRange = getHandsFromRange(range);
        for(Hand otherHand : handsInRange){
            if(hand.isEqualIgnoringSuit(otherHand) && (hand.isSuited() == otherHand.isSuited())){
                return true;
            }
        }
        return false;
    }
    
}
