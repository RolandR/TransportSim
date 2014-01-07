#!/bin/bash

clear;
echo "Compiling...";
javac -d ~/Documents/bfh/java/projects/transportSim/bin/ ./*.java;
echo;
echo "Compiled!";
echo "_______________________";
echo;
cd ~/Documents/bfh/java/projects/transportSim/bin;
java TransportSim;
cd ~/Documents/bfh/java/projects/transportSim/src;
echo;
