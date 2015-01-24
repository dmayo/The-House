max=$2
bot1="pokerbot.sh"


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
sh "$bot1" $1
sleep 2
done
done
say "simulation complete"