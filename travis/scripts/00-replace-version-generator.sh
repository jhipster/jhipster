#!/bin/bash

if [[ $JHIPSTER_VERSION == '' ]]; then
    JHIPSTER_VERSION=0.0.0-TRAVIS
fi

# jhipster-framework.version in generated pom.xml or gradle.properties
PREVIOUS_DIR=$(pwd)
cd $HOME/generator-jhipster/generators/server/templates

sed -e 's/<jhipster-framework.version>.*<\/jhipster-framework.version>/<jhipster-framework.version>'$JHIPSTER_VERSION'<\/jhipster-framework.version>/1;' pom.xml.ejs > pom.xml.ejs.sed
mv -f pom.xml.ejs.sed pom.xml.ejs
cat pom.xml.ejs | grep \<jhipster-framework.version\>

sed -e 's/jhipster_framework_version=.*/jhipster_framework_version='$JHIPSTER_VERSION'/1;' gradle.properties.ejs > gradle.properties.ejs.sed
mv -f gradle.properties.ejs.sed gradle.properties.ejs
cat gradle.properties.ejs | grep jhipster_framework_version=

cd "$PREVIOUS_DIR"
