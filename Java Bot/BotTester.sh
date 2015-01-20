max=$2
bot1="pokerbot.sh"
bot2="../PatBot/pokerbot.sh"
bot3="../TruthBot/pokerbot.sh"


function pause(){
read -p "$*"
}

say "running $max matches"

for i in `seq 1 $max`
do
sleep 2
for j in 0 1 2 3 4 5
do
echo "$i , $j"
say "match $i"
say "triplicate $j"
sh "$bot1" $1 &
sleep 2
sh "$bot2" $(($1+1)) &
sleep 2
sh "$bot3" $(($1+2))
sleep 2
done
done
say "simulation complete"