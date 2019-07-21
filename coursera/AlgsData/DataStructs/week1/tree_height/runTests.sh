#!/bin/bash
for i in {1..9}; do
  echo Testing "0$i"
  java 'tree_height' < tests/"0$i" > java.out
  diff -w -B java.out tests/"0$i".a
done
for i in {10..24}; do
  echo Testing $i
  java 'tree_height' < tests/$i > java.out
  diff -w -B java.out tests/${i}.a
done
