#!/usr/bin/env bash
# Demo stsenariyni kompilyatsiya qilib ishga tushiradi (Linux / macOS / Git Bash)
set -e
cd "$(dirname "$0")"
rm -rf out && mkdir out
javac -encoding UTF-8 -d out $(find src/main -name "*.java")
java -cp out demo.Main
