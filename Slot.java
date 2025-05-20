import java.util.Observable;

/**
 * This class represents a slot in the game board.
 * It contains the row and column number of the slot, and the current state of the slot.
 * The state of the slot can be either empty, filled, or flagged.
 * The state of the slot can be changed by the user.
 * The slot can be observed by the game board.
 * 
 * @author Samuel McCalarence
 * @version 1.8
 */

public class Slot extends Observable{
    private String state; // The state of the slot
    private int row, col; // The row and column of the slot
    /**
     * Constructor for the Slot class.
     * Initialises the slot with the given row, column, and status.
     * 
     * @param col    the column number of the slot
     * @param row    the row number of the slot
     * @param status the status of the slot
     */
    public Slot (int col, int row, String status) {
        this.row = row; 
        this.col = col;
        this.state = status;
    }
    /**
     * getState
     * This provides the current state of the slot
     * @return the current state of the slot
     */
    public String getState(){
        return state; // Return the current state of the slot
    }
    /**
     * setState
     * This method sets the state of the slot
     * @param state - the new state of the slot
     */
    public void setState(String state) {
        this.state = state;
        setChanged(); // Notify observers that the state has changed
        notifyObservers(new Slot(col, row, state)); // Notify observers that the state has changed
    }
}//End of class Slot