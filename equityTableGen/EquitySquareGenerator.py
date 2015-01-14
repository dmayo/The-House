f = open('hands.txt','r')
for line in f:
    
    if line[0] == '(':
        print "new Hand(\"" + line[1] + "h\",\"" + line[2] +"h\"),"
    else:
        print "new Hand(\"" + line[0] + "h\",\"" + line[1] +"d\"),"