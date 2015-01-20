max=$1
function pause(){
   read -p "$*"
}
for i in `seq 1 $max`
do
	echo $i
    java -jar  engine_1.6.jar
	#pause 'Press [Enter] key to continue...'
    python winsAccurate.py
done