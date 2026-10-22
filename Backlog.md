# Product Backlog: Tic Tac Toe

Items are listed in order of priority, highest first.

| # | Name | How to demo | Notes |
|---|------|-------------|-------|
| 1 | Create game window | Open program and show tic tac toe board | Use java swing |
| 2 | Create 3x3 board | Show 9 squares that are clickable | Use buttons for each square |
| 3 | Show which player's turn it is | Shows X or O in the corner | with maybe some nice looking bars on the screen but maybe not |
| 4 | Show player moves | Click a square and show X or O depending on player turn | Player take turns |
| 5 | Prevent invalid moves | Click an occupied square and see it doesn't change | Don't allow occupied squares to change |
| 6 | Detect winner | Win by having 3 X's or O's in a row, horizontally, vertically, or diagonally | Check rows, columns, diagonals, and display ''Win'' |
| 7 | Detect draw | Fill the board without a winner | Display "Draw" |
| 8 | Add turn timer | Start a game and show a countdown timer for the current player's turn, starting from 10 seconds | Timer counts down from 10 each turn and resets when the other player starts their turn |
| 9 | Add main menu | Open program and show a menu with buttons for each game mode and Quit | Use a CardLayout to switch between the menu and the game |
| 10 | Play against the computer | Choose Player vs Computer, place an X and see the computer answer with an O | Use the minimax algorithm so the computer never loses. Only on the 3x3 board |
| 11 | Add Arcade mode | Choose Arcade and show a 5x5 board where 4 in a row wins | 4 in a row instead of 5, because 5 in a row almost never happens on 5x5 |
| 12 | Add Bomb power-up | In Arcade, click Bomb and then click an opponent's mark to remove it | Each player can use it once per game |
| 13 | Add Wall power-up | In Arcade, click Wall and then click an empty square to block it | Nobody can place a mark on a wall. Once per game |
| 14 | Add Double move power-up | In Arcade, click Double and place two marks in one turn | Once per game, and only one power-up per turn |
| 15 | Show hover preview | Move the mouse over an empty square and see a faint X or O | GamePanel updates every square after each move so it knows whose turn it is |
| 16 | Highlight winning line | Win a game and see the winning squares turn yellow | Board returns which squares made the winning line |
| 17 | Add Play again button | Finish a game and click Play again to start a new round | Only shows up when the game is over |
| 18 | Add score counter | Win a round, click Play again and see the score for X, O and draws is kept | Resets when you go back to the menu and pick a mode |
| 19 | Improve the look of the game | Open the menu and a game and see the same colours, font and rounded buttons everywhere | Put all colours and fonts in one Theme class |
| 20 | Add Help screen | Click Help in the menu and see the rules and game modes explained | Back button returns to the menu |
