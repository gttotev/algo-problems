#!/bin/bash
for i in {1..9}; do
  echo Testing "0$i"
  java 'process_packages' < tests/"0$i" > java.out
  diff -w -B java.out tests/"0$i".a
done
for i in {10..22}; do
  echo Testing $i
  java 'process_packages' < tests/$i > java.out
  diff -w -B java.out tests/${i}.a
done
