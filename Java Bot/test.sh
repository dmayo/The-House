scons
max=25
function pause(){
   read -p "$*"
}
sleep 2
say "starting poker simulation"
say "simulating $max games"
for i in `seq 1 $max`
do
	echo $i
	say $i
    sh pokerbot.sh $1
	#pause 'Press [Enter] key to continue...'
	sleep 2
done
say "simulation complete"