#!/bin/bash
function usaco-run-tests {
    local prog=`ls *.java`
    #javac $prog
    prog=${prog%.java}
    local iof=${prog,,}
    local t
    for t in `ls tests/*.in`; do
        cp $t $iof.in
        t=${t%.in}
        printf "Test %s - " ${t#tests/}
        java $prog
        if diff $iof.out $t.out &> /dev/null; then
            echo PASSED
        else
            cp $iof.out $t.pout
            echo FAILED! \(Program out in $t.pout\)
        fi
    done
}

function usaco-write-test {
    if [ ! $1 ]; then
        echo Specify name of test!
        return 1
    fi
    vim tests/$1.in
    vim tests/$1.out
    echo Wrote test tests/$1
}
