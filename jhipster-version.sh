#!/bin/bash

if [ "$#" -ne 1 ]; then
    echo "Usage: $0 \"<version>\"" >&2
    exit 1
fi

JHIPSTER_VERSION=$1 travis/scripts/00-replace-version-jhipster.sh
