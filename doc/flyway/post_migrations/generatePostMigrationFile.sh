#!/bin/bash
if [ "${1}" = "" ]
then
	echo "Missing File name eg ${0} Initial"
	exit
fi
post="1"
buildfile="../../../build.gradle.kts"
projectVersionId=$(fgrep "<version>" $buildfile | head -1 | egrep -o "[1-9]+\.[0-9]+")
currentTime=$(date -u "+%Y%m%d%H%M")
touch V${projectVersionId}_${post}_${currentTime}__${1}.sql
