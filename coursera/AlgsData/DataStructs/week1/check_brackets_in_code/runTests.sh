#!/bin/bash
for i in {1..9}; do
  echo Testing "0$i"
  java 'check_brackets' < tests/"0$i" > java.out
  diff -w -B java.out tests/"0$i".a
done
for i in {10..54}; do
  echo Testing $i
  java 'check_brackets' < tests/$i > java.out
  diff -w -B java.out tests/${i}.a
done
