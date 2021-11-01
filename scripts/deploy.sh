#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'
scp -i ~/.ssh/id_rsa \
    target/SpringBootProject-1.0.jar \
    ubvova@serU:/home/ubvova/asServer/
echo 'Restart server...'

ssh -i ~/.ssh/id_rsa ubvova@serU << EOF

pgrep java | xargs kill -9
nohup java -jar /home/ubvova/asServer/SpringBootProject-1.0.jar > /home/ubvova/asServer/log.txt &

EOF

echo 'Bye'