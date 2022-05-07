#!/bin/zsh

CLASSPATH=../src/woo-app/po-uuilib.jar:../src/woo-app/woo-app.jar:../src/woo-core/woo-core.jar

for x in auto-tests/*.in; do
    if [ -e $x:r.import ]; then
        java -cp $CLASSPATH -Dimport=$x:r.import -Din=$x -Dout=$x:r.outhyp woo.app.App
    else
        java -cp $CLASSPATH                      -Din=$x -Dout=$x:r.outhyp woo.app.App
    fi

    diff -cBb -w $x:r.out $x:r.outhyp > $x:r.diff
    if [ -s $x:r.diff ]; then
        echo "FAIL: $x. See file $x:r.diff"
    else
        echo -n "."
        rm -f $x:r.diff $x:r.outhyp 
    fi
done

rm -f saved*

echo "Done."

