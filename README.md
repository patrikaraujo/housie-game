
# housie-game
Simple implementation of the Housie game.

## Instructions to execute the game

### Running the pre-built jar
1. Checkout the git repo.
2. Execute the jar file in the root of the project. The program was written using Java 8, and this jar file was built in a Windows 10 machine.
   ```
   $ java -jar housiegame-0.0.1.jar
   ```
3. Follow the instructions in the prompt to play.

### Building from source
1. Checkout the git repo.
2. Build the executable jar file using Apache Maven. This command will compile the code, run the unit tests and build an executable jar file for the game.
   ```
   $ mvn clean package
   ```
3. Execute the jar file in the '/target' folder.
   ```
   $ java -jar target/housiegame-0.0.1.jar
   ```
   
## How to play
The game is setup choosing the range of numbers to be used in the tickets, which is also the range of numbers to be drawn during the game, choosing the number of players for the game (all players are fictitious and simulated by the program), choosing the size of the ticket, and choosing how many numbers should be in each row.

The program will display an error message and allow the user to input new values if the values don't make sense.
For example, if the range of numbers is 10, the ticket size is 3x5, and numbers per row is 5, an error will be generated because there are not enough unique numbers (5 per row, 3 rows, 15 total) in the range 1 - 10.
Another example, if the range of numbers is 20, the ticket size is 3x5, and numbers per row is 6, an error will be generated because the numbers per row is greater the number of columns in the ticket.

After the game setup, the user presses the 'N' key to draw numbers for the game. Holding the 'N' key will generate several numbers in a row, potentially until the end of the game.

The game ends when there is a winner to all matching combinations, or when all numbers in the specified range are drawn.
Each winning combination is only claimed once, but the same player can win in more than one combination.
When the game is over a summary message will display the winners of the game and the winning combinations.
Winning combinations:
| Combination | Description |
|--|--|
| Top Line | All numbers in the first row of a ticket are marked.|
| Early Five | 5 numbers in a ticket are marked.|
| Full House | All numbers in a ticket are marked.|
