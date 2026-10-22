#!/bin/sh
cd "$(dirname "$0")/TicTacToe" || exit 1
echo "Building the game..."
if ! javac -d out src/main/java/com/mycompany/tictactoe/*.java; then
    echo "Build failed. Make sure the Java JDK 17 or newer is installed. See README.md."
    exit 1
fi
echo "Starting the game..."
java -cp out com.mycompany.tictactoe.GameWindow
