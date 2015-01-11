package runner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import bot.Bot;

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

    public Communicator(PrintWriter output, BufferedReader input) {
        this.outStream = output;
        this.inStream = input;
    }
    
    public Map<String, String> parseGetAction(String[] actionMessage){
        //GETACTION potSize numBoardCards [boardCards] [stackSizes] numActivePlayers [activePlayers] numLastActions [lastActions] numLegalActions [legalActions] timebank
        Map<String, String> arg = new HashMap<String, String>();
        int i=1;
        arg.put("potSize", actionMessage[i++]);
        arg.put("numBoardCards", actionMessage[i++]);
        for (int x=0;x<Integer.parseInt(arg.get("numBoardCards"));x++){
            arg.merge("boardCards", actionMessage[i++], (value, newValue) -> value.concat(" ".concat(newValue)));
        }
        //not sure if numActivePlayers is actually the number of actualPlayers parameters
        arg.put("numActivePlayers", actionMessage[i++]);
        for (int x=0;x<3;x++){
            arg.merge("activePlayers", actionMessage[i++], (value, newValue) -> value.concat(" ".concat(newValue)));
        }
        
        arg.put("numLastActions", actionMessage[i++]);
        for (int x=0;x<Integer.parseInt(arg.get("numLastActions"));x++){
            arg.merge("lastActions", actionMessage[i++], (value, newValue) -> value.concat(" ".concat(newValue)));
        }
        
        arg.put("numLegalActionss", actionMessage[i++]);
        for (int x=0;x<Integer.parseInt(arg.get("numLegalActions"));x++){
            arg.merge("legalActions", actionMessage[i++], (value, newValue) -> value.concat(" ".concat(newValue)));
        }
        
        arg.put("timebank", actionMessage[i++]);
        
        return arg;
    }
    
    public Map<String, String> parseNewGame(String[] message){
        //NEWGAME yourName opp1Name opp2Name stackSize bb numHands timeBank
        Map<String, String> arg = new HashMap<String, String>();
        int i=1;
        arg.put("yourName", message[i++]);
        arg.put("opp1Names", message[i++]);
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
        arg.put("handId", message[i++]);
        arg.put("seat", message[i++]);
        arg.put("holeCard1", message[i++]);
        for (int x=0;x<3;x++){
            arg.merge("stackSizes", message[i++], (value, newValue) -> value.concat(" ".concat(newValue)));
        }
        for (int x=0;x<3;x++){
            arg.merge("playerNames", message[i++], (value, newValue) -> value.concat(" ".concat(newValue)));
        }
        arg.put("numActivePlayers", message[i++]);
        for (int x=0;x<3;x++){
            arg.merge("activePlayers", message[i++], (value, newValue) -> value.concat(" ".concat(newValue)));
        }
        arg.put("timeBank", message[i++]);

        return arg;
    }
    
    public Map<String, String> parseHandOver(String[] message){
        //HANDOVER [stackSizes] numBoardCards [boardCards] numLastActions [lastActions] timeBank
        Map<String, String> arg = new HashMap<String, String>();
        int i=1;
        for (int x=0;x<3;x++){
            arg.merge("stackSizes", message[i++], (value, newValue) -> value.concat(" ".concat(newValue)));
        }
        arg.put("numBoardCards", message[i++]);
        for (int x=0;x<Integer.parseInt(arg.get("numBoardCards"));x++){
            arg.merge("boardCards", message[i++], (value, newValue) -> value.concat(" ".concat(newValue)));
        }
        arg.put("numLastActions", message[i++]);
        for (int x=0;x<Integer.parseInt(arg.get("numLastActions"));x++){
            arg.merge("lastActions", message[i++], (value, newValue) -> value.concat(" ".concat(newValue)));
        }
        arg.put("timeBank", message[i++]);

        return arg;
    }
    
    public void run() {
        String input;
        try {
            Bot bot = new Bot();
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
                    
                    String action = bot.getAction(parsed);
                    outStream.println(action);
                } else if ("REQUESTKEYVALUES".compareToIgnoreCase(word) == 0) {
                    // At the end, engine will allow bot to send key/value pairs to store.
                    // FINISH indicates no more to store.
                    outStream.println("FINISH");
                } else if ("NEWGAME".compareToIgnoreCase(word) == 0) {
                    Map<String,String> parsed = parseNewGame(inputWords);
                    
                } else if ("NEWHAND".compareToIgnoreCase(word) == 0) {
                    Map<String,String> parsed = parseNewHand(inputWords);
                    
                } else if ("HANDOVER".compareToIgnoreCase(word) == 0) {
                    Map<String,String> parsed = parseHandOver(inputWords);
                    
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