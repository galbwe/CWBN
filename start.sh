#!/bin/sh

cat >> /dev-config.edn << EOF
{:database-url "jdbc:h2:./cwbn_dev.db"}
EOF

redis-server --daemonize yes
java -jar -Dconf=/usr/src/app/dev-config.edn /usr/src/app/cwbn.jar
