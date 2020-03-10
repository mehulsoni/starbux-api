#!/bin/bash

if [ "${1}" = "" ]
then
	echo "Missing File name eg ${0} Initial"
	exit
fi
scriptdir=$(dirname "$0")
post="1"
masterPom="$scriptdir/../../../pom.xml"
projectVersionId=$(grep "data-feed" -A 1 $masterPom | fgrep "<version>" | egrep -o "[1-9]+\.[0-9]+")
currentTime=$(date -u "+%Y%m%d%H%M")
touch $scriptdir/V${projectVersionId}_${post}_${currentTime}__${1}.sql
