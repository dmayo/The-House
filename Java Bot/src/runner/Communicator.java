package runner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cards.BoardCards;
import cards.Card;
import cards.Hand;
import stats.Player;
import stats.Position;
import stats.Stats;
import stats.StatsCalculator;
import actions.PerformedAction;
import actions.PerformedActionType;
import actions.Street;
import bot.StatBot;

/**
 * Simple example pokerbot, written in Java.
 * 
 * This is an example of a bare bones, pokerbot. It only sets up the socket
 * necessary to connect with the engine and then always returns the same action.
 * It is meant as an example of how a pokerbot should communicate with the
 * engine.
 * 
 */
public class Communicator {
    
    private final PrintWriter outStream;
    private final BufferedReader inStream;
    private final Map<String, Stats> keyValues = new HashMap<String, Stats>();

    public Communicator(PrintWriter output, BufferedReader input) {
        this.outStream = output;
        this.inStream = input;
    }
    
    public Map<String, String> parseGetAction(String[] actionMessage){
        //GETACTION potSize numBoardCards [boardCards] [stackSizes] numActivePlayers [activePlayers] numLastActions [lastActions] numLegalActions [legalActions] timebank
        Map<String, String> arg = new HashMap<String, String>();
        int i=1;
        String concatTemp = "";
        arg.put("potSize", actionMessage[i++]);
        arg.put("numBoardCards", actionMessage[i++]);
        for (int x=0;x<Integer.parseInt(arg.get("numBoardCards"));x++){
            concatTemp = concatTemp.concat(actionMessage[i++].concat(" "));
        }
        concatTemp = concatTemp.trim();
        arg.put("boardCards", concatTemp);
        concatTemp="";
        for (int x=0;x<3;x++){
            concatTemp = concatTemp.concat(actionMessage[i++].concat(" "));
        }
        concatTemp = concatTemp.trim();
        arg.put("stackSizes", concatTemp);
        concatTemp="";
        //not sure if numActivePlayers is actually the number of actualPlayers parameters
        arg.put("numActivePlayers", actionMessage[i++]);
        for (int x=0;x<3;x++){
            concatTemp = concatTemp.concat(actionMessage[i++].concat(" "));
        }
        concatTemp = concatTemp.trim();
        arg.put("activePlayers", concatTemp);
        concatTemp="";
        arg.put("numLastActions", actionMessage[i++]);
        for (int x=0;x<Integer.parseInt(arg.get("numLastActions"));x++){
            concatTemp = concatTemp.concat(actionMessage[i++].concat(" "));
        }
        concatTemp = concatTemp.trim();
        arg.put("lastActions", concatTemp);
        concatTemp="";
        arg.put("numLegalActions", actionMessage[i++]);
        for (int x=0;x<Integer.parseInt(arg.get("numLegalActions"));x++){
            concatTemp = concatTemp.concat(actionMessage[i++].concat(" "));
        }
        concatTemp = concatTemp.trim();
        arg.put("legalActions", concatTemp);
        concatTemp="";
        arg.put("timeBank", actionMessage[i++]);
        
        return arg;
    }
    
    public Map<String, String> parseNewGame(String[] message){
        //NEWGAME yourName opp1Name opp2Name stackSize bb numHands timeBank
        Map<String, String> arg = new HashMap<String, String>();
        int i=1;
        arg.put("yourName", message[i++]);
        arg.put("opp1Name", message[i++]);
        arg.put("opp2Name", message[i++]);
        arg.put("stackSize", message[i++]);
        arg.put("bb", message[i++]);
        arg.put("numHands", message[i++]);
        arg.put("timeBank", message[i++]);

        return arg;
    }
    
    public Map<String, String> parseNewHand(String[] message){
        //NEWHAND handId seat holeCard1 holeCard2 [stackSizes] [playerNames] numActivePlayers [activePlayers] timeBank
        Map<String, String> arg = new HashMap<String, String>();
        int i=1;
        String concatTemp = "";
        arg.put("handId", message[i++]);
        arg.put("seat", message[i++]);
        arg.put("holeCard1", message[i++]);
        arg.put("holeCard2", message[i++]);
        for (int x=0;x<3;x++){
            concatTemp = concatTemp.concat(message[i++].concat(" "));
        }
        concatTemp = concatTemp.trim();
        arg.put("stackSizes", concatTemp);
        concatTemp="";
        for (int x=0;x<3;x++){
            concatTemp = concatTemp.concat(message[i++].concat(" "));
        }
        concatTemp = concatTemp.trim();
        arg.put("playerNames", concatTemp);
        concatTemp="";
        arg.put("numActivePlayers", message[i++]);
        for (int x=0;x<3;x++){
            concatTemp = concatTemp.concat(message[i++].concat(" "));
        }
        concatTemp = concatTemp.trim();
        arg.put("activePlayers", concatTemp);
        concatTemp="";
        arg.put("timeBank", message[i++]);

        return arg;
    }
    
    public Map<String, String> parseHandOver(String[] message){
        //HANDOVER [stackSizes] numBoardCards [boardCards] numLastActions [lastActions] timeBank
        Map<String, String> arg = new HashMap<String, String>();
        int i=1;
        String concatTemp="";
        for (int x=0;x<3;x++){
            concatTemp = concatTemp.concat(message[i++].concat(" "));
        }
        concatTemp = concatTemp.trim();
        arg.put("stackSizes", concatTemp);
        concatTemp="";
        arg.put("numBoardCards", message[i++]);
        for (int x=0;x<Integer.parseInt(arg.get("numBoardCards"));x++){
            concatTemp = concatTemp.concat(message[i++].concat(" "));
        }
        concatTemp = concatTemp.trim();
        arg.put("boardCards", concatTemp);
        concatTemp="";
        arg.put("numLastActions", message[i++]);
        for (int x=0;x<Integer.parseInt(arg.get("numLastActions"));x++){
            concatTemp = concatTemp.concat(message[i++].concat(" "));
        }
        concatTemp = concatTemp.trim();
        arg.put("performedActions", concatTemp);
        concatTemp="";
        arg.put("timeBank", message[i++]);
        
        return arg;
    }
    
    public void run() {
        String input;
        try {
            StatsCalculator statsCalc = new StatsCalculator();
            StatBot bot =  new StatBot("none", 0,0,0,20, new ArrayList<Player>());
            List<Player> players = new ArrayList<Player>();
            // Block until engine sends us a packet; read it into input.
            while ((input = inStream.readLine()) != null) {

                // Here is where you should implement code to parse the packets
                // from the engine and act on it.
                System.out.println(input);
                
                String inputWords[] = input.split(" ");
                String word = inputWords[0];
                if ("GETACTION".compareToIgnoreCase(word) == 0) {
                    // When appropriate, reply to the engine with a legal
                    // action.
                    // The engine will ignore all spurious packets you send.
                    // The engine will also check/fold for you if you return an
                    // illegal action.
                    Map<String,String> parsed = parseGetAction(inputWords);
                    //GETACTION potSize numBoardCards [boardCards] [stackSizes] numActivePlayers [activePlayers] numLastActions [lastActions] numLegalActions [legalActions] timebank
                    int potSize = new Integer(parsed.get("potSize"));
                    double timeBank = new Double(parsed.get("timeBank"));
                    int numBoardCards = new Integer(parsed.get("numBoardCards"));
                    String[] boardCardsStringArray = parsed.get("boardCards").split(" ");
                    String[] stackSizes = parsed.get("stackSizes").split(" ");
                    String[] activePlayers = parsed.get("activePlayers").split(" ");
                    String[] legalActions = parsed.get("legalActions").split(" ");
                    String lastActions[] = parsed.get("lastActions").split(" ");
                    
                    List<Card> boardCardsList = new ArrayList<Card>();
                    if(numBoardCards > 0){
                        for(String card: boardCardsStringArray){
                            boardCardsList.add(new Card(card));
                        }
                    }

                    for(int i=0; i < stackSizes.length; i++){
                        for(Player player : players){
                            if(player.getSeat() == i+1){
                                //player.setBoardCards(new BoardCards(Street.fromInt(numBoardCards), boardCardsList));
                                //player.setActive(new Boolean(activePlayers[i]));
                                player.setStackSize(new Integer(stackSizes[i])); 
                            }
                        }
                        if(bot.getSeat() == i+1){
                            bot.setStackSize(new Integer(stackSizes[i]));
                        }
                    }
                    
                    bot.setPotSize(potSize);
                    bot.setTimeBank(timeBank);
                    bot.setBoardCards(new BoardCards(Street.fromInt(numBoardCards), boardCardsList));
                    statsCalc.processActions(lastActions);
                    String action = bot.getAction(legalActions);
                    outStream.println(action);
                } else if ("REQUESTKEYVALUES".compareToIgnoreCase(word) == 0) {
                    // At the end, engine will allow bot to send key/value pairs to store.
                    // FINISH indicates no more to store.
                    
                    for(Player player : players){
                        if(!player.getName().equals(bot.getName())){
                            String name = player.getName();
                            System.out.println(name);
                            if(name.equals("RANDOMBOT1")){
                                name = "1";
                            }
                            else if(name.equals("RANDOMBOT2")){
                                name = "1";
                            }
                            else if(name.equals("YOURBOT")){
                                name = "3";
                            }
                            outStream.println("DELETE "+StringEncode.encodeInt(Integer.valueOf(name)));
                            outStream.println("PUT "+StringEncode.encodeInt(Integer.valueOf(name))+" "+player.getStats().values());
                        }
                    }
                    outStream.println("FINISH");
                    
                } else if ("KEYVALUE".compareToIgnoreCase(word) == 0) {
                        // Gets keyvalue pairs
                        String playerName = Integer.toString(StringEncode.decodeInt(inputWords[1]));
                        String stringVal = "";
                        for(int i=2;i<inputWords.length;i++){
                            stringVal += inputWords[i];
                        }
                        char[] values = stringVal.toCharArray();
                        
                        int i=values.length-1;
                        final Map<Position, Double> currentVPIP = new HashMap<Position, Double>();
                        currentVPIP.put(Position.FIRST, (double) StringEncode.decodeInt(String.valueOf(values[i--]))/100);
                        currentVPIP.put(Position.MIDDLE, (double) StringEncode.decodeInt(String.valueOf(values[i--]))/100);
                        currentVPIP.put(Position.LAST, (double) StringEncode.decodeInt(String.valueOf(values[i--]))/100);
                        
                        final Map<Position, Double> currentPFR = new HashMap<Position, Double>();
                        currentPFR.put(Position.FIRST, (double) StringEncode.decodeInt(String.valueOf(values[i--]))/100);
                        currentPFR.put(Position.MIDDLE, (double) StringEncode.decodeInt(String.valueOf(values[i--]))/100);
                        currentPFR.put(Position.LAST, (double) StringEncode.decodeInt(String.valueOf(values[i--]))/100);
                        
                        double currentWTSD = (double) StringEncode.decodeInt(String.valueOf(values[i--]))/100;
                        double currentW$SD = (double) StringEncode.decodeInt(String.valueOf(values[i--]))/100;
                        double currentOverallVPIP = (double) StringEncode.decodeInt(String.valueOf(values[i--]))/100;
                        double currentOverallPFR = (double) StringEncode.decodeInt(String.valueOf(values[i--]))/100;
                        System.out.println("current: "+currentVPIP+" "+currentPFR+" "+currentWTSD+" "+currentW$SD+" "+currentOverallVPIP+" "+currentOverallPFR);
                        Stats playerStats = new Stats(currentVPIP, currentPFR, currentWTSD, currentW$SD, currentOverallVPIP, currentOverallPFR);
                        
                        keyValues.put(playerName, playerStats);
                        
                } else if ("NEWGAME".compareToIgnoreCase(word) == 0) {
                    //NEWGAME yourName opp1Name opp2Name stackSize bb numHands timeBank
                    Map<String,String> parsed = parseNewGame(inputWords);
                    String botName = parsed.get("yourName");
                    
                    int stackSize = new Integer(parsed.get("stackSize"));
                    Player player1 = new Player(parsed.get("opp1Name"), stackSize,0); // dummy seat values must be updated in NEWHAND
                    Player player2 = new Player(parsed.get("opp2Name"), stackSize,0); // dummy seat values must be updated in NEWHAND
                    Player player3 = new Player(botName,stackSize,0);
                    players = Arrays.asList(new Player[]{player1,player2,player3});
                    
                    
                   
                    int numHands = new Integer(parsed.get("numHands"));
                    int bigBlind = new Integer(parsed.get("bb"));
                    double timeBank = new Double(parsed.get("timeBank"));
                    
                    statsCalc.setPlayers(players);
                    bot = new StatBot(botName, stackSize, bigBlind, numHands, timeBank, Arrays.asList(new Player[]{player1,player2}));
                    
                } else if ("NEWHAND".compareToIgnoreCase(word) == 0) {
                    //NEWHAND handId seat holeCard1 holeCard2 [stackSizes] [playerNames] numActivePlayers [activePlayers] timeBank
                    Map<String,String> parsed = parseNewHand(inputWords);
                    
                    int handId = new Integer(parsed.get("handId"));
                    int seat = new Integer(parsed.get("seat"));
                    Card holeCard1 = new Card(parsed.get("holeCard1"));
                    Card holeCard2 = new Card(parsed.get("holeCard2"));
                    double timeBank = new Double(parsed.get("timeBank"));
                    int numActivePlayers = new Integer(parsed.get("numActivePlayers"));
                    
                    bot.setHandId(handId);
                    bot.setSeat(seat);
                    bot.setHand(new Hand(holeCard1, holeCard2));
                    bot.setTimeBank(timeBank);
                    bot.setNumActivePlayers(numActivePlayers);
                    bot.setBoardCards(new BoardCards(Street.PREFLOP, new ArrayList<Card>()));
                    
                    String[] stackSizes = parsed.get("stackSizes").split(" ");
                    String[] playerNames = parsed.get("playerNames").split(" ");
                    String[] activePlayers = parsed.get("activePlayers").split(" ");
                    
                    for(int i=0; i < playerNames.length; i++){
                        String name = playerNames[i];
                        for(Player player : players){
                            if(name.equals(player.getName())){
                                player.setLastAction(new PerformedAction(player.getName(), PerformedActionType.NONE, 0, new ArrayList<Card>(), Street.PREFLOP));
                                player.setStreet(Street.PREFLOP);
                                player.setSeat(i+1);
                                player.setActive(new Boolean(activePlayers[i]));
                                player.setStackSize(new Integer(stackSizes[i]));     
                                player.setNumActivePlayers(numActivePlayers);
                            }
                        }
                        if(name.equals(bot.getName())){
                            bot.setStackSize(new Integer(stackSizes[i]));
                        }
                    }
                    
                    
                    
                } else if ("HANDOVER".compareToIgnoreCase(word) == 0) {
                    //HANDOVER [stackSizes] numBoardCards [boardCards] numLastActions [lastActions] timeBank
                    Map<String,String> parsed = parseHandOver(inputWords);
                    
                    String stackSizes[] = parsed.get("stackSizes").split(" ");
                    int numBoardCards = new Integer(parsed.get("numBoardCards"));
                    String boardCards[] = parsed.get("boardCards").split(" ");
                    int numLastActions = new Integer(parsed.get("numLastActions"));
                    String lastActions[] = parsed.get("performedActions").split(" ");
                    double timeBank = new Double(parsed.get("timeBank"));
                    
                    for(int i=0; i < stackSizes.length; i++){
                        for(Player player : players){
                            if(player.getSeat() == i+1){
                                player.setStackSize(new Integer(stackSizes[i]));
                            }
                        }
                        if(bot.getSeat() == i+1){
                            bot.setStackSize(new Integer(stackSizes[i]));
                        }
                    }
                    bot.setTimeBank(timeBank);       
                    statsCalc.processActions(lastActions);
                    
                    for(Player player : players){
                        System.out.println(player.getName());
                        System.out.println(player.getStats());
                    }
                    System.out.println("");
                    System.out.println("");
                    
                }
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
        
        
        System.out.println("Gameover, engine disconnected");
        
        // Once the server disconnects from us, close our streams and sockets.
        try {
            outStream.close();
            inStream.close();
        } catch (IOException e) {
            System.out.println("Encounterd problem shutting down connections");
            e.printStackTrace();
        }
    }
    
    
    
}