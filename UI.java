import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * The UI class represents the user interface for the text-based  version of the Minesweeper game.
 * It provides methods to display the game board, show the menu, get the user's choice,
 * save the game, undo the previous move, load a saved game, and clear the game.
 * The UI class interacts with the Minesweeper class to play the game.
 * 
 * @author Samuel McCalarence
 * @version 1.8
 */

public class UI {
    private Minesweeper gameInstance; // The game
    private String menuChoice; // The user's choice from the menu
    private Scanner reader; // Scanner object to read user input

    /**
     * Constructor for the class UI
     */
    public UI() {
        gameInstance = new Minesweeper(); // Create a new Minesweeper game
        reader = new Scanner(System.in); // Create a new Scanner object to read user input
        menuChoice=""; // Initialise the menu choice

        while(!menuChoice.equalsIgnoreCase("Q")&&gameInstance.checkWin().equals("continue")) { // Continue to display the game and menu until the user quits or wins
            displayGame(); // Display the game
            menu(); // Display the menu
            menuChoice = getChoice(); // Get the user's choice from the menu

        }
        if (gameInstance.checkWin().equals("won")){ // Check if the user has won the game
            winningAnnouncement(); // Display a winning announcement
        } else if (gameInstance.checkWin().equals("lives") ){ // Check if the user has lost due to lack of lives
            livesAnnouncement(); // Display a lives announcement
        }
    }

    /**
     * Method that outputs an announcement when the user has won the game
     */
    public void winningAnnouncement() {
        System.out.println("\nCongratulations, you solved the level");
    }

    /**
     * Method that outputs an announcement when the user has lost due to lack of lives
     */
    public void livesAnnouncement() {
        System.out.println("\nYou have ran out of lives, the game is over");
    }

    /**
     * Method that displays the game for the user to play
     */
    public void displayGame() {
        //boardmoves = thegame.getMoves();
        
        System.out.print("\n\nCol    ");
        for (int r = 0; r < gameInstance.getGameSize(); r++) {
            System.out.print(r + " ");
        }
        for (int i = 0; i < gameInstance.getGameSize(); i++) {
            System.out.print("\nRow  " + i);
            for (int c = 0; c < gameInstance.getGameSize() ; c++) {
                System.out.print(" "+gameInstance.getCellState(i,c));
            }
        }
        
    }

    /**
     * Method that displays the menu to the user
     */
    public void menu() {

        System.out.println("\nPlease select an option: \n"
            + "[M] Flag a mine\n"
            + "[G] Guess a square\n"
            + "[S] save game\n"
            + "[L] load saved game\n"
            + "[U] undo move\n"
            + "[C] clear game\n"
            + "[Q] quit game\n");

    }

    /**
     * Method that gets the user's choice from the menu and conducts the activities
     * accordingly
     * @return the choice the user has selected
     */
    public String getChoice() {
    String choice;
    choice = reader.next();

    if (choice.equalsIgnoreCase("M") || choice.equalsIgnoreCase("G")) {
        int row = -1;
        int col = -1;

        // Get row input
        while (row < 0 || row >= gameInstance.getGameSize()) {
            System.out.print("Which row is the cell you wish to flag? ");
            if (reader.hasNextInt()) {
                row = reader.nextInt();
                if (row < 0 || row >= gameInstance.getGameSize()) {
                    System.out.println("Invalid input. Please enter a number between 0 and " + (gameInstance.getGameSize() - 1) + ".");
                    row = -1; // reset to invalid state
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                reader.next(); // discard invalid input
            }
        }

        // Get column input
        while (col < 0 || col >= gameInstance.getGameSize()) {
            System.out.print("Which column is the cell you wish to flag? ");
            if (reader.hasNextInt()) {
                col = reader.nextInt();
                if (col < 0 || col >= gameInstance.getGameSize()) {
                    System.out.println("Invalid input. Please enter a number between 0 and " + (gameInstance.getGameSize() - 1) + ".");
                    col = -1; // reset to invalid state
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                reader.next(); // discard invalid input
            }
        }

        System.out.print(gameInstance.makeMove(row, col, choice));

    } else if (choice.equalsIgnoreCase("S")) {
        saveGame();
    } else if (choice.equalsIgnoreCase("U")) {
        undoMove();
    } else if (choice.equalsIgnoreCase("L")) {
        loadGame();
    } else if (choice.equalsIgnoreCase("C")) {
        clearGame();
    } else if (choice.equalsIgnoreCase("Q")) {
        System.exit(0);
    }

    return choice;
}

    /**
     * Saves the current game state with a specified filename.
     */
    public void saveGame() {
        System.out.print("Enter the name of the save: ");
        String filename = reader.next();
        gameInstance.saveGame(filename);
    }

    /**
     * Undoes the last move in the game.
     * If a move is successfully undone, it prints "Move undone".
     * If there are no moves to undo, it prints "No moves to undo".
     */
    public void undoMove() {
        if (gameInstance.undoMove()) {
            System.out.println("Move undone");
        } else {
            System.out.println("No moves to undo");
        }
    }

    /**
     * Loads a game by displaying the most recent saves, prompting the user to enter the name of the save,
     * and then calling the `loadGame` method of the `gameInstance` object with the specified filename.
     */
    public void loadGame() {
    // List files in the 'saves' directory
    File saveDir = new File("saves");
    File[] files = saveDir.listFiles((dir, name) -> name.endsWith(".txt"));

    // Sort files by last modified date, most recent first
    if (files != null) {
        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
    }

    // Print the most recent 5 files
    System.out.println("Recent saves:");
    for (int i = 0; i < files.length && i < 5; i++) {
        System.out.println((i + 1) + ". " + files[i].getName());
    }

    // Prompt the user to enter the name of the save
    System.out.print("Enter the name of the save: ");
    String filename = reader.next();
    gameInstance.loadGame(filename);
}
    
    /**
     * clearGame
     * This method clears the game and resets lives to 3
     */
    public void clearGame() {
        gameInstance.clearGame();
        System.out.println("Game has been cleared, and lives reset to 3");
    }

    /**
     * The main method within the Java application. 
     * It's the core method of the program and calls all others.
     */
    public static void main(String args[]) {
        new UI();
    }

}//end of class UI