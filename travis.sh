#!/bin/bash
echo "nexusUsername=rahulsom" >> ~/.gradle/gradle.properties
echo "nexusPassword=$SONATYPE_PASSWORD" >> ~/.gradle/gradle.properties

if [ $TRAVIS_PULL_REQUEST = false ]; then
  if [ $(echo $TRAVIS_BRANCH | grep -cE '(master)|(\d+\..+)') = 1 ]; then
    ./gradlew  snapshot
  else
    ./gradlew  check
  fi
fi
