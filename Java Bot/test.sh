max=100
function pause(){
   read -p "$*"
}
sleep 5
for i in `seq 1 $max`
do
	echo $i
    sh pokerbot.sh 3004
	#pause 'Press [Enter] key to continue...'
	sleep 3
done