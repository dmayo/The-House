f = open('hands.txt','r')
for line in f:
    
    if line[0] == '(':
        print "new Hand(new Card(\"" + line[1] + "h\"), new Card(\"" + line[2] +"d\")),"
    else:
        print "new Hand(new Card(\"" + line[0] + "h\"), new Card(\"" + line[1] +"h\")),"