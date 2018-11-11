#!/bin/bash

if [ "$#" -ne 1 ]; then
    echo "Usage: $0 \"<version>\"" >&2
    exit 1
fi

JHI_VERSION=$1 test-integration/scripts/10-replace-version-jhipster.sh
