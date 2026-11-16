#!/usr/bin/env bash
# Barcha unit testlarni kompilyatsiya qilib ishga tushiradi (Linux / macOS / Git Bash)
set -e
cd "$(dirname "$0")"
rm -rf out && mkdir out
javac -encoding UTF-8 -d out $(find src/main src/test -name "*.java")
java -cp out testkit.TestRunner
