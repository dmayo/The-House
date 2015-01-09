package runner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

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
    
    public void run() {
        String input;
        try {
            // Block until engine sends us a packet; read it into input.
            while ((input = inStream.readLine()) != null) {

                // Here is where you should implement code to parse the packets
                // from the engine and act on it.
                System.out.println(input);
                
                String word = input.split(" ")[0];
                if ("GETACTION".compareToIgnoreCase(word) == 0) {
                    // When appropriate, reply to the engine with a legal
                    // action.
                    // The engine will ignore all spurious packets you send.
                    // The engine will also check/fold for you if you return an
                    // illegal action.
                    outStream.println("CHECK");
                } else if ("REQUESTKEYVALUES".compareToIgnoreCase(word) == 0) {
                    // At the end, engine will allow bot to send key/value pairs to store.
                    // FINISH indicates no more to store.
                    outStream.println("FINISH");
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