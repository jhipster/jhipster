#!/bin/bash

if [[ $JHIPSTER_VERSION == '' ]]; then
    JHIPSTER_VERSION=0.0.0-TRAVIS
fi

# jhipster-dependencies.version in generated pom.xml or gradle.properties
PREVIOUS_DIR=$(pwd)
cd $HOME/generator-jhipster/generators/server/templates

sed -e 's/<jhipster-dependencies.version>.*<\/jhipster-dependencies.version>/<jhipster-dependencies.version>'$JHIPSTER_VERSION'<\/jhipster-dependencies.version>/1;' pom.xml.ejs > pom.xml.ejs.sed
mv -f pom.xml.ejs.sed pom.xml.ejs
cat pom.xml.ejs | grep \<jhipster-dependencies.version\>

sed -e 's/jhipster_dependencies_version=.*/jhipster_dependencies_version='$JHIPSTER_VERSION'/1;' gradle.properties.ejs > gradle.properties.ejs.sed
mv -f gradle.properties.ejs.sed gradle.properties.ejs
cat gradle.properties.ejs | grep jhipster_dependencies_version=

cd "$PREVIOUS_DIR"
