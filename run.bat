@echo off
cd /d "%~dp0TicTacToe"
echo Building the game...
javac -d out src\main\java\com\mycompany\tictactoe\*.java
if errorlevel 1 (
    echo.
    echo Build failed. Make sure the Java JDK 17 or newer is installed. See README.md.
    pause
    exit /b 1
)
echo Starting the game...
java -cp out com.mycompany.tictactoe.GameWindow
