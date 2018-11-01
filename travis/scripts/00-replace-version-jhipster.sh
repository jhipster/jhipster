#!/bin/bash

if [[ $JHIPSTER_VERSION == '' ]]; then
    JHIPSTER_VERSION=0.0.0-CICD
fi

# artifact version of jhipster-parent
sed -e '/<artifactId>jhipster-parent<\/artifactId>/{N;s/<version>.*<\/version>/<version>'$JHIPSTER_VERSION'<\/version>/1;}' pom.xml > pom.xml.sed
mv -f pom.xml.sed pom.xml

# jhipster-framework.version property in jhipster-parent
sed -e 's/<jhipster-framework.version>.*<\/jhipster-framework.version>/<jhipster-framework.version>'$JHIPSTER_VERSION'<\/jhipster-framework.version>/1' pom.xml > pom.xml.sed
mv -f pom.xml.sed pom.xml

# parent version of jhipster-dependencies
sed -e '/<artifactId>jhipster-parent<\/artifactId>/{N;s/<version>.*<\/version>/<version>'$JHIPSTER_VERSION'<\/version>/1;}' jhipster-dependencies/pom.xml > jhipster-dependencies/pom.xml.sed
mv -f jhipster-dependencies/pom.xml.sed jhipster-dependencies/pom.xml

# parent version of jhipster-framework
sed -e '/<artifactId>jhipster-dependencies<\/artifactId>/{N;s/<version>.*<\/version>/<version>'$JHIPSTER_VERSION'<\/version>/1;}' jhipster-framework/pom.xml > jhipster-framework/pom.xml.sed
mv -f jhipster-framework/pom.xml.sed jhipster-framework/pom.xml

