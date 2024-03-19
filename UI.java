import java.util.Scanner;
import java.util.Stack;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class provides a text based user interface for the player to interact with the game
 * @author Lauren Scott
 * @version student sample code
 */
public class UI {
    private Sudoku thegame;//this is the game model
    private String menuChoice;//this is the users choice from the menu
    private Scanner reader;//this scanner is used to read the terminal
    
    /**
     * Yorum
     * Eski hamleleri tutup geri almak için stack kullanılacak.
     * 
     */
    private Stack<Assign> previousAssigns;
    private String saveFile = "Save/save.txt";
    
    /**
     * Constructor for the class UI
     */
    public UI() {
        thegame = new Sudoku();
        reader = new Scanner(System.in);
        previousAssigns = new Stack<Assign>();
        menuChoice="";
        while(!menuChoice.equalsIgnoreCase("Q")&&!thegame.checkWin()) {
            displayGame();
            menu();
            menuChoice = getChoice();

        }
        if (thegame.checkWin()) {
            winningAnnouncement();
        }
    }

    /**
     * Method that outputs an announcement when the user has won the game
     */
    public void winningAnnouncement() {
        System.out.println("Congratulations, you solved the puzzle");
    }

    /**
     * Method that displays the game for the user to play
     */
    public void displayGame() {
        //boardmoves = thegame.getMoves();
        if (thegame.getGameSize() == 9) {
            System.out.println("Col   0 1 2 3 4 5 6 7 8");
            System.out.println("      - - - - - - - - -");
        } else {
            System.out.println("Col   0 1 2 3 ");
            System.out.println("      - - - - ");
        }

        for (int i = 0; i < thegame.getGameSize(); i++) {
            System.out.print("Row "+i+"|");
            for (int c = 0; c < thegame.getGameSize(); c++) {
                if (thegame.getGameSize() == 9) {
                    if (c == 2 || c == 5 || c == 8) {
                        if (thegame.getIndividualMove(i,c).contains("-") ){
                            System.out.print(" " + "|");
                        } else{
                            System.out.print(thegame.getIndividualMove(i,c) + "|");
                        }
                    } else {
                        if (thegame.getIndividualMove(i,c).contains("-") ){
                            System.out.print(" " + ".");
                        } else{
                            System.out.print(thegame.getIndividualMove(i,c) + ".");
                        }
                    }

                } else if (thegame.getGameSize() == 4) {
                    if (c == 1 || c == 3) {
                        if (thegame.getIndividualMove(i,c).contains("-") ){
                            System.out.print(" " + "|");
                        } else{
                            System.out.print(thegame.getIndividualMove(i,c) + "|");
                        }
                    } else {
                        if (thegame.getIndividualMove(i,c).contains("-") ){
                            System.out.print(" " + ".");
                        } else{
                            System.out.print(thegame.getIndividualMove(i,c) + ".");
                        }
                    }

                
                }
            }if (thegame.getGameSize() == 9 && (i == 2 || i == 5|| i == 8)) {
                System.out.println("\n      - - - - - - - - -");

            } else if (thegame.getGameSize() == 9 ){
                System.out.println("\n      .................");

            } else if (thegame.getGameSize() == 4 && (i==1||i==3) ){
                System.out.println("\n      - - - - ");

            } else {
                System.out.println("\n     .........");
            }
        }
    }

    /**
     * Method that displays the menu to the user
     */
    public void menu() {

        System.out.println("Please select an option: \n"
            + "[M] make move\n"
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
     * 
     */
    public String getChoice() {
        String choice = reader.next();
        if (choice.equalsIgnoreCase("M")) {
            System.out.print("Which row is the cell you wish to fill?  ");
            String row = reader.next();
            System.out.print("Which colum is the cell you wish to fill?  ");
            String col = reader.next();
            System.out.print("Which number do you want to enter?  ");
            String number = reader.next();
            
            int rowNumber = Integer.parseInt(row);
            int colNumber = Integer.parseInt(col);
            
            //Yorum
            //Girilen row ve coldaki sayıyı kaydeder
            String oldState = thegame.getMoves()[rowNumber][colNumber].getState();
            Assign newAssign = null;
            
            if(!thegame.makeMove(row, col, number)) {
                System.out.println("That cell cannot be changed");
                
                //Yorum
                // Doğru girilene kadar girilen row ve coldaki sayıyı kaydeder
                oldState = thegame.getMoves()[rowNumber][colNumber].getState();
                
                while (!thegame.makeMove(row, col, number)) {
                    System.out.print("Which row is the cell you wish to fill?  ");
                    row = reader.next();
                    System.out.print("Which colum is the cell you wish to fill?  ");
                    col = reader.next();
                    System.out.print("Which number do you want to enter?  ");
                    number = reader.next();
                    
                    //Yorum
                    // Doğru girilesiye kadar girilen row ve coldaki sayıyı kaydeder
                    oldState = thegame.getMoves()[rowNumber][colNumber].getState();
                    
                }
                
                //Yorum
                // Girilen değeri tabloya kaydeder, eski veriyi kaydeder, yeni assigni eski hamle stackine kaydeder.
                newAssign = new Assign(thegame, rowNumber, colNumber, number);
                newAssign.setOldState(oldState);
                previousAssigns.push(newAssign);
            } 
            else {
                //Yorum
                // Girilen değeri tabloya kaydeder, eski veriyi kaydeder, yeni assigni eski hamle stackine kaydeder.
                newAssign = new Assign(thegame, rowNumber, colNumber, number);
                newAssign.setOldState(oldState);
                previousAssigns.push(newAssign);
            }

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
     * 
     * Yorum
     * Filewrite objesini kullanarak sudoku tablosunu dosyaya yazdırır.
     * 
     * saveGame 
     * This method saves the current state of the board to a txt file.
     */
    public void saveGame() {
        try {
            FileWriter filewriter = new FileWriter(saveFile);
            int gameSize = thegame.getGameSize();
            filewriter.write(gameSize + "\n");
            
            for(int row = 0; row < gameSize; row++) {
                for(int col = 0; col < gameSize; col++) {
                    Slot slotToSave = thegame.getMoves()[row][col];
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

    /**
     * 
     * Yorum
     * previousAssign stackinden poplayarak son hamleyi alır ve son hamleyi oynar.
     * 
     * undoMove 
     * This method undoes the previous move made in the game, along with the corresponding computer move.
     */
    public void undoMove() {
        if(!previousAssigns.isEmpty()) {
            Assign assignUndo = previousAssigns.pop();
            int row = assignUndo.getRow();
            int col = assignUndo.getCol();
            String oldState = assignUndo.getOldState();
            new Assign(thegame, row, col, oldState);
        }
        else {
            System.out.println("No previous moves!");
        }
    }

    /**
     * Yorum
     * Dosyadan şekli okuyup setState metodu ile oyundaki slotları değiştirir.
     * Fakat fillable olup olmamalarını değiştiremez, çünkü slotta setFillable metodu yok. 
     * Loadladıktan sonra oyun düzgün çalışmayacaktır. Slot classını değiştirecek olsam setFillable metodu eklerdim.
     * 
     * loadGame
     * This method is used for loading a sudoku game from txt file.
     * 
     */
    public void loadGame() {
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
            
            thegame.getMoves()[row][col].setState(move);
            
        }
    }

    /**
     * 
     * Yorum
     * Slot arrayindeki tüm fillable olan alanların değerini - yapar ve tüm eski hamleleri kaldırır.
     * 
     * clearGame
     * This method clears the game board and any record of moves, to reset the game.
     */
    public void clearGame() {
        int gameSize = thegame.getGameSize();
        for(int row = 0; row < gameSize; row++) {
            for(int col = 0; col < gameSize; col++) {
                Slot slot = thegame.getMoves()[row][col];
                if(slot.getFillable()) {
                    thegame.makeMove(String.valueOf(row), String.valueOf(col), "-");
                }
            }
        }
        previousAssigns.removeAllElements();
    }

}//end of class UI