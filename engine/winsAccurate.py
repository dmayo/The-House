import glob
import os
import operator

os.chdir("games")
winners={}
numFullGames = 0;
for file in glob.glob("*.txt"):
    if(file!="config.txt"):
        f = open(file, 'r')
        lines = f.readlines()
        winner = ""
        for line in reversed(lines):
            if "wins" in line:
                words = line.split()
                winner = words[0]
            if "Hand" in line:
                words = line.split()
                hand = int(words[1][1:-1])
                if hand == 1001:
                    players = {}
                    players[words[2]] = int(words[3][1:-2])
                    players[words[4]] = int(words[5][1:-2])
                    players[words[6]] = int(words[7][1:-1])
                    winner = max(players.iteritems(), key=operator.itemgetter(1))[0]
                    numFullGames += 1
                break
                
        if winner[-1:].isdigit():
            winner = winner[:-1]
        winners[winner] = winners.get(winner, 0) + 1
        
        
numGames=sum(winners.values())*1.0
print "Number of Games: " + str(numGames)
print "Number of games that reached limit: " + str(numFullGames)
for winner in winners:
    print winner, winners[winner]/numGames
