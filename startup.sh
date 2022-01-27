#!/bin/bash -ex
cd chainlink
npm install
nohup node index.js &
cd ..
java -jar wrapper-chainlink.jar