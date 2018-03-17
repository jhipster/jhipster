#!/bin/bash

if [[ $JHIPSTER_VERSION == '' ]]; then
    JHIPSTER_VERSION=0.0.0-TRAVIS
fi

# artifact version of jhipster-parent
sed -e '/<artifactId>jhipster-parent<\/artifactId>/{N;s/<version>.*<\/version>/<version>'$JHIPSTER_VERSION'<\/version>/1;}' pom.xml > pom.xml.sed
mv -f pom.xml.sed pom.xml

# artifact version of jhipster-dependencies
sed -e '/<artifactId>jhipster-dependencies<\/artifactId>/{N;s/<version>.*<\/version>/<version>'$JHIPSTER_VERSION'<\/version>/1;}' jhipster-dependencies/pom.xml > jhipster-dependencies/pom.xml.sed
mv -f jhipster-dependencies/pom.xml.sed jhipster-dependencies/pom.xml

# jhipster-server.version property in jhipster-dependencies
sed -e 's/<jhipster-server.version>.*<\/jhipster-server.version>/<jhipster-server.version>'$JHIPSTER_VERSION'<\/jhipster-server.version>/1' jhipster-dependencies/pom.xml > jhipster-dependencies/pom.xml.sed
mv -f jhipster-dependencies/pom.xml.sed jhipster-dependencies/pom.xml

# parent version of jhipster-server
sed -e '/<artifactId>jhipster-dependencies<\/artifactId>/{N;s/<version>.*<\/version>/<version>'$JHIPSTER_VERSION'<\/version>/1;}' jhipster-server/pom.xml > jhipster-server/pom.xml.sed
mv -f jhipster-server/pom.xml.sed jhipster-server/pom.xml

# artifact version of jhipster-server
sed -e '/<artifactId>jhipster<\/artifactId>/{N;s/<version>.*<\/version>/<version>'$JHIPSTER_VERSION'<\/version>/1;}' jhipster-server/pom.xml > jhipster-server/pom.xml.sed
mv -f jhipster-server/pom.xml.sed jhipster-server/pom.xml
