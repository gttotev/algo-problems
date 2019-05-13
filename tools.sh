#!/bin/bash
function usaco-run-test {
    local prog=`ls *.java | head -n 1`
    if [ $ALGO_COMPILE ]; then
        javac $prog
        if [ $? != 0 ]; then return; fi
    fi
    prog=${prog%.java}
    #local iof=${prog,,}
    local iof=`echo "$prog" | awk '{print tolower($0)}'`
    local rtests=`ls tests/*.in`
    if [ $1 ]; then rtests=tests/$1.in; fi
    local t
    for t in $rtests; do
        ln -sf $t $iof.in
        t=${t%.in}
        echo "Test '${t#tests/}':"
        java $prog
        if diff $iof.out $t.out &> /dev/null; then
            echo "[32mPASSED[00m"
        else
            cp $iof.out $t.pout
            echo "[31mFAILED![00m (Program out in $t.pout)"
        fi
    done
}

function algo-write-test {
    if [ ! $1 ]; then
        echo Specify name of test!
        return 1
    fi
    if [ ! -d tests/ ]; then mkdir tests/; fi
    touch tests/$1.{in,out}
    if [ ! $ALGO_NO_VIM ]; then
        vim tests/$1.in
        vim tests/$1.out
    fi
    echo Wrote test \'$1\'
}

function algo-run-test {
    local prog=`ls *.java | head -n 1`
    if [ $ALGO_COMPILE ]; then
        javac $prog
        if [ $? != 0 ]; then return; fi
    fi
    prog=$(ls | grep class | grep -v '\$' | head -n 1)
    prog=${prog%.class}
    local rtests=`ls tests/*.in`
    if [ $1 ]; then rtests=tests/$1.in; fi
    local t
    local tmp_out=tests/tmp.out
    for t in $rtests; do
        t=${t%.in}
        echo "Test '${t#tests/}':"
        java $prog < $t.in > $tmp_out
        if diff --strip-trailing-cr $tmp_out $t.out &> /dev/null; then
            rm -f $t.pout
            echo "[32mPASSED[00m"
        else
            echo "[31mFAILED![00m (Program out in $t.pout)"
            cp $tmp_out $t.pout
            diff $t.out $t.pout
        fi
        echo "======================================================="
    done
}
