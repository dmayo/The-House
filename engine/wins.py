import glob
import os
#os.chdir("/mydir")
winners={}
for file in glob.glob("*.txt"):
    if(file!="config.txt"):
        f = open(file, 'r')
        i=0
        f.seek(-200, os.SEEK_END)
        line = f.readlines()[-2]
        words=line.split()
        winners[words[0]] = winners.get(words[0], 0) + 1
numGames=sum(winners.values())*1.0
print "NumGames: " + str(numGames)
for winner in winners:
    print winner, winners[winner]/numGames
