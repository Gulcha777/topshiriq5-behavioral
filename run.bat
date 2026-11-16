@echo off
rem Demo stsenariyni kompilyatsiya qilib ishga tushiradi (Windows)
cd /d "%~dp0"
if exist out rmdir /s /q out
mkdir out
dir /s /b src\main\*.java > sources.txt
javac -encoding UTF-8 -d out @sources.txt
if errorlevel 1 exit /b 1
del sources.txt
java -cp out demo.Main
