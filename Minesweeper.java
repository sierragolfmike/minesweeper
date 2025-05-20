import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The Minesweeper class represents a game of Minesweeper.
 * It manages the game board, player board, game size, lives, move history, and game state.
 * The class provides methods for initializing a new game, making moves, checking for a win or loss, and saving/loading the game state.
 * 
 * @author Samuel McCalarence
 * @version 1.8
 */

public class Minesweeper{
    private String[][] gameBoard; // The game board
    private Slot[][] playerBoard; // The player board
    private Scanner reader; // The scanner for reading the level file
    private int gameSize; // The size of the game
    private String level = "Levels/em1.txt"; // The level file
    private int lives = 3;  // The number of lives the player has
    private ArrayList<String> moveHistory; // The move history
    private boolean newGame; // Whether a new game is being initialised

    /**
     * The Minesweeper class represents a game of Minesweeper.
     * It initialises a new game and keeps track of the move history.
     */
    public Minesweeper() {
        moveHistory = new ArrayList<>(); // Initialise the move history array
        newGame = true; // Set newGame to true

        if(newGame){
            initialiseNewGame(); // Initialise a new game if newGame is true
        } else {
            return; // Otherwise, return
        }
    }

    /**
     * ========================================================================================================
     * Original Constructor and Methods provided in the Assignment Brief
     * ========================================================================================================
     */

    /**
     * Initialises a new game of Minesweeper.
     * Reads the level file, calculates the game size, and initialises the game board and player board.
     * 
     * @throws FileNotFoundException if the level file is not found
     * @throws Exception if there is an error initialising the new game
     */
    public void initialiseNewGame() {
        try { // Try to initialise a new game
            reader = new Scanner(new File(level)); // Create a new scanner for the level file
            gameSize = calculateGameSize(); // Calculate the game size
            gameBoard = new String[gameSize][gameSize]; // Initialise the game board array
            playerBoard = new Slot[gameSize][gameSize]; // Initialise the player board array
            readLevelFile(); // Read the level file
        } catch (FileNotFoundException e) { // Catch a FileNotFoundException
            System.err.println("Level file not found: " + level); // Print an error message
            e.printStackTrace(); // Print the stack trace
        } catch (Exception e) { // Catch any other exceptions
            System.err.println("Error initialising the new game."); // Print an error message
            e.printStackTrace(); // Print the stack trace
        } finally {
            if (reader != null) { // If the scanner is not null
                reader.close(); // Close the scanner
            }
        }
    }
    
    /**
     * Returns the current state of the player's board.
     *
     * @return a 2D array representing the player's board
     */
    public Slot[][] getMoves() {
        return playerBoard; // Return the player board
    }

    /**
     * Retrieves the state of an individual cell on the player board at the specified row and column.
     * 
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @return the state of the cell at the specified row and column
     * @throws IndexOutOfBoundsException if the row or column index is invalid
     */
    public String getIndividualMove(int row, int col) {
        try {
            if (row < 0 || col < 0 || row >= gameSize || col >= gameSize) { // If the row or column index is invalid
                throw new IndexOutOfBoundsException("Invalid row or column index."); // Throw an IndexOutOfBoundsException
            }
            return playerBoard[row][col].getState(); // Return the state of the cell at the specified row and column
        } catch (IndexOutOfBoundsException e) { // Catch an IndexOutOfBoundsException
            System.err.println("Error accessing cell at (" + row + ", " + col + ")"); // Print an error message
            e.printStackTrace(); // Print the stack trace
            return null;  // Return null
        }
    }    

    /**
     * Calculates the game size based on the input file.
     * 
     * @return the game size as an integer
     */
    public int calculateGameSize() {
        try {
            return Integer.parseInt(reader.next()); // Parse the game size as an integer
        } catch (NumberFormatException e) { // Catch a NumberFormatException
            System.err.println("Error parsing game size."); // Print an error message
            e.printStackTrace(); // Print the stack trace
            return -1; // Return -1
        } catch (NoSuchElementException e) { // Catch a NoSuchElementException
            System.err.println("No more elements in the input file."); // Print an error message
            e.printStackTrace(); // Print the stack trace
            return -1; // Return -1
        }
    }

    /**
     * Returns the size of the game.
     *
     * @return the size of the game
     */
    public int getGameSize() {
        return gameSize; // Return the size of the game
    }

    /**
     * Reads the level file and populates the game board and player board arrays.
     * 
     * @return The game board array after reading the level file.
     */
    public String[][] readLevelFile() {
        try { // Try to read the level file
            while (reader.hasNext()) { // While there is more input in the level file
                int row = Integer.parseInt(reader.next()); // Parse the row index as an integer
                int col = Integer.parseInt(reader.next()); // Parse the column index as an integer
                String move = reader.next(); // Read the move
    
                if (row < 0 || col < 0 || row >= gameSize || col >= gameSize) { // If the row or column index is invalid
                    System.err.println("Invalid row or column index: (" + row + ", " + col + ")"); // Print an error message
                    continue; // Continue to the next iteration
                }
    
                gameBoard[row][col] = move; // Set the move on the game board
                playerBoard[row][col] = new Slot(row, col, "?"); // Set the state of the cell on the player board
            }
        } catch (NumberFormatException e) { // Catch a NumberFormatException
            System.err.println("Error parsing row or column index."); // Print an error message
            e.printStackTrace(); // Print the stack trace
        } catch (NoSuchElementException e) { // Catch a NoSuchElementException
            System.err.println("Invalid input format in level file."); // Print an error message
            e.printStackTrace(); // Print the stack trace
        } catch (Exception e) { // Catch any other exceptions
            System.err.println("Error reading the level file."); // Print an error message
            e.printStackTrace(); // Print the stack trace
        }
        return gameBoard; // Return the game board array
    }
    
    /**
     * Checks if the player has won the game.
     * 
     * @return "continue" if the game is still ongoing,
     *         "won" if the player has won the game,
     *         "lives" if the player has lost all lives.
     */
    public String checkWin(){ // Check if the player has won the game
        if (lives !=0 ) { // If the player has lives remaining
        for (int i = 0; i<gameSize; i++) { // Iterate over the rows of the game board
            for (int c = 0; c <gameSize; c++) { // Iterate over the columns of the game board
                if (gameBoard[i][c].equals("M") && !playerBoard[i][c].getState().equals("M")) { // If a mine is not flagged
                    return "continue"; // Return "continue"
                }
            }
        }
            return "won"; // Return "won"
        } else {
            return "lives"; // Return "lives"
        }
    }

    /**
     * Makes a move in the Minesweeper game at the specified row and column with the given guess.
     * Updates the move history and player board accordingly.
     * 
     * @param row   the row index of the move
     * @param col   the column index of the move
     * @param guess the guess made by the player ("M" for mine, "G" for guess)
     * @return a message indicating the result of the move
     */
    public String makeMove(int row, int col, String guess){ // Make a move in the Minesweeper game
    
        moveHistory.add(String.valueOf(row) + String.valueOf(col) + guess); // Add the move to the move history
    
        if (guess.equalsIgnoreCase("M") && gameBoard[row][col].equalsIgnoreCase("M")){ // If the guess is a mine and the cell is a mine
            playerBoard[row][col].setState("M"); // Set the state of the cell to "M"
        } else if (guess.equalsIgnoreCase("G")) { // If the guess is a guess
            if (gameBoard[row][col].equals("M") ) { // If the cell is a mine
                lives -= 1; // Decrement the number of lives
                playerBoard[row][col].setState("M"); // Set the state of the cell to "M"
                return "You have lost one life. \nNew life total: " + lives; // Return a message indicating the result of the move
            } else if (gameBoard[row][col].equals("-")){ // If the cell is not a mine
                playerBoard[row][col].setState("-"); // Set the state of the cell to "-"
                
            } else { // If the cell is a number
                playerBoard[row][col].setState(gameBoard[row][col]); // Set the state of the cell to the number
            }
            return "You have survived this time"; // Return a message indicating the result of the move
        } else {
            return "Unsuccessful - this was not a mine"; // Return a message indicating the result of the move
        }
        return "You have successfully found a mine"; // Return a message indicating the result of the move
    }

    /**
     * Retrieves the state of the cell at the specified row and column.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @return the state of the cell at the specified row and column
     */
    public String getCellState(int row, int col) {
        return playerBoard[row][col].getState(); // Return the state of the cell at the specified row and column
    }

    /**
     * ========================================================================================================
     * Added Functionality for the Minesweeper Game by Samuel McCalarence (Student)
     * ========================================================================================================
     */
    
    /**
     * Clears the game by reinitializing it, resetting the number of lives, and clearing the move history.
     */
    public void clearGame() { // Clear the game
        initialiseNewGame(); // Reinitialise the game
        lives = 3; // Reset the number of lives
        moveHistory.clear(); // Clear the move history
    }

    /**
     * Undoes the last move made by the player.
     * If there are moves in the move history, the last move is removed and the state of the board at the specified row and column is set to "?".
     * If the move history is empty, no move is undone.
     * 
     * @return true if a move was undone, false otherwise
     */
    public boolean undoMove() { // Undo the last move
        if (!moveHistory.isEmpty()) { // If the move history is not empty
            String lastMove = moveHistory.remove(moveHistory.size() - 1); // Remove the last move from the move history
            
            int row = Character.getNumericValue(lastMove.charAt(0)); // Get the row index of the last move
            int col = Character.getNumericValue(lastMove.charAt(1)); // Get the column index of the last move
            
            playerBoard[row][col].setState("?"); // Set the state of the cell at the specified row and column to "?"
    
            return true; // Return true
        } else {
           return false; // Return false as the game is already in the initial state
        }
    }

    /**
     * Saves the current game state to a file.
     * 
     * @param filename the name of the file to save the game to
     */
    public void saveGame(String filename) { // Save the current game state to a file
        try {
            if (!filename.endsWith(".txt")) { // If the filename does not end with ".txt"
                filename += ".txt"; // Append ".txt" to the filename
            }

            File saveDir = new File("saves"); // Create a new file object for the 'saves' directory
            if (!saveDir.exists()) { // If the 'saves' directory does not exist
                saveDir.mkdirs(); // Create the 'saves' directory
            }

            File file = new File(saveDir, filename); // Create a new file object for the save file
            FileWriter writer = new FileWriter(file); // Create a new file writer for the save file
    
            writer.write(gameSize + "\n"); // Write the game size to the file
            writer.write(lives + "\n"); // Write the number of lives to the file

            for (int i = 0; i < gameSize; i++) { // Iterate over the rows of the game board
                for (int j = 0; j < gameSize; j++) { // Iterate over the columns of the game board
                    writer.write(i + " " + j + " " + gameBoard[i][j] + "\n"); // Write the row index, column index, and value of the cell to the file
                }
            }
    
            for (int i = 0; i < gameSize; i++) { // Iterate over the rows of the player board
                for (int j = 0; j < gameSize; j++) { // Iterate over the columns of the player board
                    writer.write(i + " " + j + " " + playerBoard[i][j].getState() + "\n"); // Write the row index, column index, and state of the cell to the file
                }
            }
            writer.close();

        } catch (IOException e) { // Catch an IOException
            e.printStackTrace(); // Print the stack trace
        }
    }

    /**
     * Loads a game from a specified file.
     *
     * @param filename the name of the file to load the game from
     */
    public void loadGame(String filename) {
        try {
            File file = new File("saves", filename); // Create a new file object for the save file
            Scanner reader = new Scanner(file); // Create a new scanner for the save file
    
            gameSize = Integer.parseInt(reader.nextLine()); // Parse the game size as an integer
            lives = Integer.parseInt(reader.nextLine()); // Parse the number of lives as an integer
    
            gameBoard = new String[gameSize][gameSize]; // Initialise the game board array
            playerBoard = new Slot[gameSize][gameSize]; // Initialise the player board array
    
            for (int i = 0; i < gameSize; i++) { // Iterate over the rows of the game board
                for (int j = 0; j < gameSize; j++) { // Iterate over the columns of the game board
                    int row = reader.nextInt(); // Parse the row index as an integer
                    int col = reader.nextInt(); // Parse the column index as an integer
                    String value = reader.next(); // Read the value of the cell
                    gameBoard[row][col] = value; // Set the value of the cell on the game board
                }
            }
    
            for (int i = 0; i < gameSize; i++) { // Iterate over the rows of the player board
                for (int j = 0; j < gameSize; j++) { // Iterate over the columns of the player board
                    int row = reader.nextInt(); // Parse the row index as an integer
                    int col = reader.nextInt(); // Parse the column index as an integer
                    String state = reader.next(); // Read the state of the cell
                    playerBoard[row][col] = new Slot(row, col, state); // Set the state of the cell on the player board
                }
            }
    
            reader.close(); // Close the scanner
        } catch (FileNotFoundException e) { // Catch a FileNotFoundException
            e.printStackTrace(); // Print the stack trace
        }
    }

    /**
     * Returns the number of lives remaining.
     *
     * @return the number of lives remaining
     */
    public int getLives() {
        return lives; // Return the number of lives
    }
}