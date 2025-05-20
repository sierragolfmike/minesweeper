import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

/**
 * The GUI class represents the graphical user interface for the Minesweeper game.
 * It extends the JFrame class and provides methods to initialize the GUI, create the game board,
 * add control buttons, refresh the game board, and handle button click events.
 * 
 * @author Samuel McCalarence
 * @version 1.8
 */

public class GUI extends JFrame {
    private Minesweeper thegame;
    private int rows;
    private int cols;
    private JButton[][] buttons;
    private JLabel livesLabel;

    /**
     * Constructs a new GUI object.
     * Initializes the game, sets the number of rows and columns,
     * and creates an array of JButtons for the game board.
     */
    public GUI() {
        thegame = new Minesweeper();
        rows = thegame.getGameSize();
        cols = thegame.getGameSize();
        buttons = new JButton[rows][cols];
        initialiseGUI();
    }

    /**
     * Initialises the GUI for the Minesweeper game.
     * Sets the title, size, layout, and panels for the game board, control buttons, and information display.
     * Displays the GUI to the user.
     */
    public void initialiseGUI() {
        setTitle("Minesweeper");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        /**
         * Creates a JPanel with a grid layout.
         *
         * @param rows the number of rows in the grid
         * @param cols the number of columns in the grid
         * @return a JPanel with a grid layout
         */
        JPanel gridPanel = new JPanel(new GridLayout(rows, cols));
        createGameBoard(gridPanel);

        /**
         * Represents the control panel in the GUI.
         * It is a JPanel with a grid layout of 1 row and 5 columns.
         * Each column contains a control button for the game, facilitating undo, save, load, clear and quit operands.
         */
        JPanel controlPanel = new JPanel(new GridLayout(1, 5));
        addControlButtons(controlPanel);

        /**
         * Represents the information panel in the GUI.
         * It is a JPanel with a label panel to signify the remaining lives.
         */
        JPanel infoPanel = new JPanel();
        livesLabel = new JLabel("Lives left: " + thegame.getLives());
        infoPanel.add(livesLabel);

        add(gridPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        add(infoPanel, BorderLayout.NORTH);

        setVisible(true);
    }

    /**
     * Creates the game board by initialising buttons for each cell in the grid.
     * Each button represents the state of a cell and is added to the grid panel.
     * 
     * @param gridPanel the panel where the buttons will be added
     */
    private void createGameBoard(JPanel gridPanel) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                String state = thegame.getCellState(row, col);
                buttons[row][col] = new JButton(state);
                buttons[row][col].setPreferredSize(new Dimension(40, 40));
                buttons[row][col].addActionListener(new CellButtonListener(row, col));
                gridPanel.add(buttons[row][col]);
            }
        }
    }

    /**
     * Adds control buttons to the specified control panel.
     *
     * @param controlPanel the panel to which the control buttons will be added
     */
    private void addControlButtons(JPanel controlPanel) {
        
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            thegame.clearGame();
            refreshGameBoard();
            JOptionPane.showMessageDialog(this, "Game has been cleared, and lives reset to 3");
        });
        controlPanel.add(clearButton);

        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> {
            if (thegame.undoMove()) {
                JOptionPane.showMessageDialog(this, "Move undone");
                refreshGameBoard();
            } else {
                JOptionPane.showMessageDialog(this, "No moves to undo");
            }
            thegame.undoMove();
        });
        controlPanel.add(undoButton);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            String saveName = JOptionPane.showInputDialog(this, "Enter save name:", "Save Game", JOptionPane.PLAIN_MESSAGE);
            if (saveName != null && !saveName.trim().isEmpty()) {
                thegame.saveGame(saveName);
            }
        });
        controlPanel.add(saveButton);

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> loadGame());
        controlPanel.add(loadButton);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));
        controlPanel.add(quitButton);
    }

    /**
     * Refreshes the game board by updating the text of each button based on the current state of the cells.
     * Also updates the text of the livesLabel to display the number of lives left in the game.
     */
    private void refreshGameBoard() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                String state = thegame.getCellState(row, col);
                buttons[row][col].setText(state); // Update button text based on slot state
            }
        }
        livesLabel.setText("Lives left: " + thegame.getLives());
    }


    /**
     * ActionListener implementation for the cell buttons in the GUI.
     * Handles the user's actions when a cell button is clicked.
     */
    private class CellButtonListener implements ActionListener {
        private final int row;
        private final int col;

        public CellButtonListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Prompt the user for their guess
            String[] options = {"Mark as Mine", "Guess"};
            int choice = JOptionPane.showOptionDialog(
                null, 
                "Choose your action:", 
                "Make a Move", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.INFORMATION_MESSAGE, 
                null, 
                options, 
                options[0]
            );

            String guess = choice == 0 ? "M" : "G";

            // Make the move and get the result
            String result = thegame.makeMove(row, col, guess);

            // Refresh the game board to show the updated state
            refreshGameBoard();

            // Show the result of the move to the user
            JOptionPane.showMessageDialog(null, result);

            // Check if the game is won or lost
            String gameStatus = thegame.checkWin();
                if (gameStatus.equals("won")) {
                    JOptionPane.showMessageDialog(null, "Congratulations! You have won the game!");
                    thegame.clearGame();
                    refreshGameBoard();;
                } else if (gameStatus.equals("lives")) {
                    JOptionPane.showMessageDialog(null, "You have run out of lives! Game over!");
                    thegame.clearGame();
                    refreshGameBoard();
                }
            }
            
        }

    /**
     * Loads a game by displaying a dialog box to select a save file from the "saves" directory.
     * If a save file is selected, the game is loaded using the selected save file and the game board is refreshed.
     * If no save files are found, an error message is displayed.
     */
    private void loadGame() {
    File saveDir = new File("saves");
    File[] files = saveDir.listFiles((dir, name) -> name.endsWith(".txt"));

    if (files == null || files.length == 0) {
        JOptionPane.showMessageDialog(this, "No save files found.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());

    String[] recentSaves = Arrays.stream(files)
                                    .limit(5)
                                    .map(File::getName)
                                    .toArray(String[]::new);

    String selectedSave = (String) JOptionPane.showInputDialog(
            this,
            "Select a save to load:",
            "Load Game",
            JOptionPane.PLAIN_MESSAGE,
            null,
            recentSaves,
            recentSaves[0]);

    if (selectedSave != null && !selectedSave.trim().isEmpty()) {
        thegame.loadGame(selectedSave);
        refreshGameBoard();
    }
}

    /**
     * The entry point of the application.
     * Initializes the GUI by invoking the GUI constructor.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}// End of class GUI