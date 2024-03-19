/**
 * Assign
 * This class handles the creation of all moves in the game
 * @author Lauren Scott
 * @version Student Sample Code
 */
public class Assign {
    private int col, row;//The row and column being assigned
    private Sudoku game;//The game
    Slot[][] moves;//2D Array to store the game's moves
    
    /**
     * Yorum
     * Değiştirmeden önce slottaki önceki stateyi kaydetmek için.
     */
    private String oldState;
    
    /**
     * Constructor for Assign class.
     * This gets the total number of moves and calls methods to determine the row that will be filled, and to set the state of the slot being assigned.
     * @param game - the game
     * @param col - the column the user has selected
     * @param player - a Boolean value that determines whether it is a player/computer move
     */
    public Assign(Sudoku game, int row,int col, String number){
        this.game = game;
        this.col = col;
        this.row = row;
        
        this.moves = game.getMoves();
        
        /**
         * Yorum
         * moves dizisindeki row ve coldaki eski değeri değiştirmeden kaydeder.
         * 
         */
        this.oldState = moves[row][col].getState();
        
        if(moves[row][col].getFillable()) { 
            assignMove(number);
        }
        
    }
    
    /**
     * assignMove
     * This method assigns the move to the game
     * @param player a Boolean value to determine whether it is a computer/player move
     */
    public void assignMove(String number) {
        moves[row][col].setState(number);

    }
    /**
     * getRow
     * This method returns the current row value for this move. It allows another element of the program to access this move's current row.
     * @return the row value
     */
    public int getRow() {
        return row;
    }
    
    
    /**
     * getCol
     * This method returns the current col value for this move. It allows another element of the program to access this move's current col.
     * @return the col value
     */
    public int getCol() {
        return col;
    }
    
    /**
     * getOldState
     * This method returns the latest state of row'th and col'th slot.
     * @return the old value in slot
     */
    public String getOldState() {
        return oldState;
    }
    
    /**
     * setOldState
     * This method changes the current oldstate of the object.
     * @param the latest state in slot
     * 
     */
    public void setOldState(String oldState) {
        this.oldState = oldState;
    }
}//End of class Assign
