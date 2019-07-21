#!/bin/bash
shopt -s extglob
#cd tests
for i in $(ls tests/!(*.*)); do
  echo Testing "$i"
  java 'SetRangeSum' < "$i" > java.out
  diff -w -B java.out "$i".a
  if [ $? = "1" ]; then exit; fi
done
