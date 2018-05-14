#!/bin/bash
if [ ! $1 ]; then
    echo Specify name of test!
    return 1
fi
if [ ! -d tests/ ]; then mkdir tests/; fi
vim tests/$1.in
ln -sf tests/$1.in sort.in
java SortSim
cp sort.out tests/$1.out
cat sort.out
echo Wrote test \'$1\'
