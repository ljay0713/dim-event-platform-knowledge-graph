#!/usr/bin/env sh

echo "$1"
sed -i -e 's|'\\\\$'|''|g' $1
