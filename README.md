# Tic Tac Toe (Java Swing)

A Tic Tac Toe game made with Java Swing for the course Java Programming (2IP90) at TU/e. It has three game modes: player vs player, player vs ai, and arcade (POWERUPS).







## Quick start



### 1\. Install Java (skip if you already have it)

You need the Java JDK, version 17 or newer. To check, open a terminal (see step 2) and type `javac -version`. If it shows a version number of 17 or higher, you're set.

If not, download the JDK from https://adoptium.net (choose the latest LTS version) and install it with the default options. On Windows, make sure "Set JAVA\_HOME" and "Add to PATH" are selected during the install.





### 2\. Start the game

**Windows:** open the `TicTacToe-Swing` folder and double-click `run.bat`. (you might have to disable smart app control in windows settings if it does not open)

**macOS / Linux:** open a terminal in the `TicTacToe-Swing` folder and type:

```
sh run.sh
```

To open a terminal in the right folder:

* macOS: open the Terminal app, type `cd ` (with a space), drag the `TicTacToe-Swing` folder into the window, and press Enter.
* Linux: right-click inside the folder and choose "Open in Terminal".

The script compiles the game and opens the game window. The first start takes a few seconds. 

## 

## 

## Building and running by hand

If you prefer to type the commands yourself, open a terminal in the `TicTacToe` folder (the one that contains `pom.xml`).

Windows (Command Prompt):

```
javac -d out src\\main\\java\\com\\mycompany\\tictactoe\\\*.java
java -cp out com.mycompany.tictactoe.GameWindow
```

macOS, Linux and Git Bash:

```
javac -d out src/main/java/com/mycompany/tictactoe/\*.java
java -cp out com.mycompany.tictactoe.GameWindow
```

The first command compiles the game and shows nothing if it worked. The second command opens the game.

With Maven installed, you can also run `mvn compile exec:java` instead. In Apache NetBeans, open the `TicTacToe` folder as a project and press F6.

## 

## 

## How to play

When the game starts, choose a mode in the main menu:

* **Player vs Player**: two players take turns on the same computer. Get 3 in a row to win.
* **Player vs Computer**: you play X against the computer (O), which uses the minimax algorithm and never loses.
* **Arcade**: a 5x5 board where you need 4 in a row. Each player can use every power-up once per game, and one power-up per turn:

  * **Bomb**: remove one of your opponent's marks
  * **Wall**: block an empty square so nobody can use it
  * **Double**: place two marks in one turn

Every turn lasts 10 seconds. If time runs out, the turn passes to the other player. Click **Help** in the main menu for the full rules.

## 

## 

## 

## Project structure

|File|Purpose|
|-|-|
|`GameWindow.java`|Main class. Creates the window and switches between the menu, game and help screens.|
|`MenuPanel.java`|The main menu.|
|`GamePanel.java`|The game screen: turns, timer, power-ups, score and end of game.|
|`HelpPanel.java`|The help screen with the rules.|
|`Board.java`|The game rules: placing marks, finding a winner and detecting a draw.|
|`ComputerPlayer.java`|The computer opponent, using minimax.|
|`GameMode.java`|The three game modes with their board size and win length.|
|`PowerUp.java` / `PowerUpIcon.java`|The Arcade power-ups and their icons.|
|`CellButton.java`|A board square that draws its mark, hover preview and win highlight.|
|`ThemedButton.java` / `Theme.java`|The button style, colours and fonts used throughout the game.|

All source files are in `TicTacToe/src/main/java/com/mycompany/tictactoe/`.

