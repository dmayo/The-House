#!/bin/sh
export DYLD_LIBRARY_PATH=/Users/patrickmccabe/Documents/pokerbots/The-House/pbots_calc-master/export/darwin/lib:$LD_LIBRARY_PATH
java -cp /Users/patrickmccabe/Documents/pokerbots/The-House/pbots_calc-master/java/jnaerator-0.11-SNAPSHOT-20121008.jar:/Users/patrickmccabe/Documents/pokerbots/The-House/pbots_calc-master/java/bin pbots_calc.Calculator $@