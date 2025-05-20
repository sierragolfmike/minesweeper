/**
 * The Assign class represents a move assignment in the Minesweeper game.
 * It stores the row and column being assigned, as well as the game and the moves array.
 * 
 * @author Samuel McCalarence
 * @version 1.8
 */

public class Assign {
    private int col, row; // The row and column being assigned
    public Minesweeper game; // The game
    Slot[][] moves; // 2D Array to store the game's moves

    /**
     * Constructor for the Assign class.
     * Initializes the Assign object with the given game, row, column, and number.
     * Calls the assignMove method to set the state of the slot being assigned.
     *
     * @param game   the game
     * @param row    the row the user has selected
     * @param col    the column the user has selected
     * @param number a String value that represents the move to be assigned
     */
    public Assign(Minesweeper game, int row, int col, String number) {
        this.game = game;
        this.col = col;
        this.row = row;
        assignMove(number);
    }

    /**
     * Assigns a move to the specified cell in the moves array.
     *
     * @param number the move to be assigned to the cell
     */
    public void assignMove(String number) {
        moves[row][col].setState(number);
    }

    /**
     * Returns the current row value for this move.
     * Allows another element of the program to access this move's current row.
     *
     * @return the row value
     */
    public int getRow() {
        return row;
    }
}// End of class Assign