#!/bin/bash

if [[ $JHIPSTER_VERSION == '' ]]; then
    JHIPSTER_VERSION=0.0.0-TRAVIS
fi

# artifact version of jhipster-parent
sed -i '/<artifactId>jhipster-parent<\/artifactId>/{$!{N;s/<version>.*<\/version>/<version>'$JHIPSTER_VERSION'<\/version>/1}}' pom.xml

# artifact version of jhipster-dependencies
sed -i '/<artifactId>jhipster-dependencies<\/artifactId>/{$!{N;s/<version>.*<\/version>/<version>'$JHIPSTER_VERSION'<\/version>/1}}' jhipster-dependencies/pom.xml

# jhipster-server.version property in jhipster-dependencies
sed -i 's/<jhipster-server.version>.*<\/jhipster-server.version>/<jhipster-server.version>'$JHIPSTER_VERSION'<\/jhipster-server.version>/1' jhipster-dependencies/pom.xml

# parent version of jhipster-server
sed -i '/<artifactId>jhipster-dependencies<\/artifactId>/{$!{N;s/<version>.*<\/version>/<version>'$JHIPSTER_VERSION'<\/version>/1}}' jhipster-server/pom.xml

# artifact version of jhipster-server
sed -i '/<artifactId>jhipster<\/artifactId>/{$!{N;s/<version>.*<\/version>/<version>'$JHIPSTER_VERSION'<\/version>/1}}' jhipster-server/pom.xml
