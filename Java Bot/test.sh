max=10
function pause(){
   read -p "$*"
}
for i in `seq 1 $max`
do
	echo $i
    sh pokerbot.sh 3002
	pause 'Press [Enter] key to continue...'
done