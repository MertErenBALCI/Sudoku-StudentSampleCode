import java.util.Stack;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Observer;
import java.util.Observable;

/**
 * 
 * Yorum
 * İstenen buton fonksiyonlarının hepsi burada gerçekleşiyor. Save, load, clear, undo 
 * UI için de Assign objesi yerine bu class kullanılabilir fakat UI ve Assign tamamen değiştirmemek için kullanmadım.
 * 
 * ButtonMethods
 * This class handles all functions of the game.
 * @author Lauren Scott
 * @version Student Sample Code
 */
public class ButtonMethods implements Observer {
    private Sudoku game;//The game
    private Slot[][] moves;//2D Array to store the game's moves
    private String saveFile = "Save/save.txt";
    
    
    //Yorum
    //Eski hamleleri tutmak için stack kullanılacak
    private Stack<SlotHelper> previousMoves;
    
    /**
     * 
     * Yorum
     * Slot objesinin satır sütun ve içeriğini tutmak için kullanılıyor. Slot classında sütun ve satır için getter olmadığı için yazdım.
     *      Direkt slot kullanmak daha mantıklı.
     * 
     * SlotHelper
     * This class is used for holding row, col and state of a slot object as it doesn't have getter methods for row and col.
     * 
     */
    private class SlotHelper {
        private int row, col;
        private String number;
        
        public SlotHelper(int row, int col, String number) {
            this.row = row;
            this.col = col;
            this.number = number;
        }
        
        public int getRow() {
            return this.row;
        }
        public int getCol() {
            return this.col;
        }
        public String getNumber() {
            return this.number;
        }
        
    }
    
    @Override
    public void update(Observable arg0, Object arg1) {
        Slot slot = (Slot) arg0;
        
        System.out.println(slot.getState());
    }
    
    /**
     * 
     * Yorum
     * Sadece oyunu alıp sudokunun kopyasını alıyor ve eski hamleler stackini oluşturuyor.
     * 
     * ButtonMethods
     * This constructor initializes as Observer and gets game and moves array from given Sudoku then initializes the previous move stack.
     * @param sudoku object
     */
    public ButtonMethods(Sudoku game){
        super();
        this.game = game;
        this.moves = game.getMoves();
        
        // Stack constructor
        this.previousMoves = new Stack<SlotHelper>();
    }
    
    /**
     * 
     * Yorum
     * Satır sütun ve değere göre sudoku içinde hazır olan makeMove metodunu çağırıp satır ve sütundaki eski değeri, eski hamleler stackine pushluyor.
     * 
     * makeMove
     * This method changes the selected slot with given state if it can. It returns true when it could change the value, false if it could not.
     * @param row value as string
     * @param col value as string
     * @param state valuse as string
     * @return boolean value
     * 
     */
    public boolean makeMove(String row, String col, String number) {
        int moveRow = Integer.parseInt(row);
        int moveCol = Integer.parseInt(col);
        
        String oldNumber = moves[moveRow][moveCol].getState();
        SlotHelper previousSlot = new SlotHelper(moveRow, moveCol, oldNumber);
        
        if(game.makeMove(row, col, number)) { 
            previousMoves.push(previousSlot);
            return true;
        }
        else {
            return false;
        }
        
    }
    
    /**
     * 
     * Yorum
     * Eski hamleler stackini pop atıp içindeki değeri sudokunun içindeki değeri sudoku makeMove metoduyla değiştiriyor.
     * 
     * 
     * undoMove 
     * This method undoes the previous move made in the game, along with the corresponding computer move.
     */
    public boolean UndoMove() {
        if(previousMoves.empty()) {
            return false;
        }
        
        SlotHelper previousSlot = previousMoves.pop();
        String row = String.valueOf(previousSlot.getRow());
        String col = String.valueOf(previousSlot.getCol());
        String number = String.valueOf(previousSlot.getNumber());
        System.out.println(row + " " + col + " " + number);
        game.makeMove(row, col, number);
        
        return true;
    }
    
    /**
     * 
     * Yorum
     * Slot arrayindeki tüm fillable olan alanların değerini - yapar ve tüm eski hamleleri kaldırır.
     * 
     * clearGame
     * This method clears the game board and any record of moves, to reset the game.
     */
    public void ClearMoves() {
        int gameSize = game.getGameSize();
        for(int row = 0; row < gameSize; row++) {
            for(int col = 0; col < gameSize; col++) {
                Slot slot = moves[row][col];
                if(slot.getFillable()) {
                    game.makeMove(String.valueOf(row), String.valueOf(col), "-");
                }
            }
        }
        previousMoves.removeAllElements();
        
    }
    
    /**
     * 
     * Yorum
     * Dosyadan şekli okuyup setState metodu ile oyundaki slotları değiştirir.
     * Fakat fillable olup olmamalarını değiştiremez, çünkü slotta setFillable metodu yok. 
     * Loadladıktan sonra oyun düzgün çalışmayacaktır. Slot classını değiştirecek olsam setFillable metodu eklerdim.
     * 
     * 
     * loadGame
     * This method is used for loading a sudoku game from txt file. 
     */
    public void LoadGame() {
        Scanner reader = null;
        try {
            reader = new Scanner(new File(saveFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        int gameSize = Integer.parseInt(reader.next());
        while (reader.hasNext()) {
            int row = Integer.parseInt(reader.next());
            int col = Integer.parseInt(reader.next());
            String move = reader.next();
            String fillable = reader.next();
            
            System.out.println(move);
            
            /*
             * Yorum
            if(fillable.contains("+")) {
                moves[row][col].setFillable(true);
            }
            else {
                moves[row][col].setFillable(false);
            }*/
            
            moves[row][col].setState(move);
            
        }
    }
    
    /**
     * 
     * Yorum
     * Filewrite objesini kullanarak sudoku tablosunu dosyaya yazdırır.
     * 
     * saveGame 
     * This method saves the current state of the board to a txt file.
     * 
     */
    public void SaveGame() {
        try {
            FileWriter filewriter = new FileWriter(saveFile);
            int gameSize = game.getGameSize();
            filewriter.write(gameSize + "\n");
            
            for(int row = 0; row < gameSize; row++) {
                for(int col = 0; col < gameSize; col++) {
                    Slot slotToSave = moves[row][col];
                    String slotState = slotToSave.getState();
                    String slotFillable = "-";
                    if(slotToSave.getFillable()) {
                        slotFillable = "+";
                    }
                    filewriter.write(row + " " + col + " " + slotState + " " + slotFillable + "\n");
                }
            }
            filewriter.close();
        }
        
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}//End of class Assign
