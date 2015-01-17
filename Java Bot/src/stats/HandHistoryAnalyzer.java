package stats;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import actions.PerformedAction;
import cards.Card;
import cards.EquitySquaredRanking;
import cards.Hand;

public class HandHistoryAnalyzer {
    
    
    public static void main(String args[]) throws IOException{
        File folder = new File("hh");
        File[] listOfFiles = folder.listFiles();
        Map<String, PreFlopStat> playerToPreFlop = new HashMap<String, PreFlopStat>();
        
        for (int i = 0; i < listOfFiles.length; i++) {
          File file = listOfFiles[i];
          if (file.isFile() && file.getName().endsWith(".txt")) {
            String content = FileUtils.readFileToString(file);
            String lines[] = content.split("\n");
            
            for(String line:lines){
                String words[] = line.split(" ");
                boolean dealt = false;
            
                if(words[0].equalsIgnoreCase("dealt")){
                    String name = words[2];
                    Hand hand = new Hand(words[3].substring(1,3),words[4].substring(0,2));
                    int rank = EquitySquaredRanking.getRank(hand);
                    
                    
                }
                if(dealt && line.contains("calls") || line.contains("folds") || line.contains("raises") || line.contains("checks")){
                    String name = words[0];         
                }
                if(line.contains("FLOP")){
                    dealt = false;
                }
            }
            
          } 
        }
    }
}
