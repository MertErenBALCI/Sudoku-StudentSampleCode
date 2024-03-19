import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 * Yorum
 * Ana GUI classı bu class. İçinde sudoku, pencere, metod objeleri var. Gameloop bu classın içinde.
 * 
 * Game
 * The main class of the application. It utilizes window, sudoku and button methods classes. The gameloop is happening in this class.
 * 
 */
public class Game
{
    Window window;
    Sudoku thegame;
    String[][] solution;
    ButtonMethods buttonMethod;
    Mouse mouse = new Mouse();
    
    boolean gameWon = false;
    boolean gameLost = false;
    
    /**
     * 
     * Game
     * Constructor for the class Game
     * 
     */
    public Game() {
        thegame = new Sudoku();
        buttonMethod = new ButtonMethods(thegame);
        window = new Window(this, 600, 675);
        solution = thegame.getSolution();
        
        addActionsToButtons();
        
        startLoop();
    }
    
    /**
     * Yorum
     * Gameloop, sonsuza kadar çalışan bir loop içinde. Update ve çizim panelinin repaint fonksiyonu çağırılıyor.
     * Update oyunla etkileşime geçmek için repaint çizmek için.
     * 
     * startLoop
     * This function starts the gameloop, it updates and draws the game until the game is exited.
     */
    private void startLoop() {
        while(true) {
            update();
            
            window.getDrawPanel().repaint();
        }
    }
    
    /**
     * Yorum
     * Pencereye bağlı olan işlevsiz olan tüm butonları adlarını kullanarak alarak buton metodlarındaki fonksiyonlarla ilişkilendirir.
     * 
     * addActionsToButtons
     * This function adds the actions for the each button in the window.
     * 
     */
    private void addActionsToButtons() {
        Button button;
        
        button = window.getButtonByName("Save");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
            buttonMethod.SaveGame();
          }
        });
        
        button = window.getButtonByName("Load");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
            buttonMethod.LoadGame();
          }
        });
        
        button = window.getButtonByName("Clear");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
            buttonMethod.ClearMoves();
          }
        });
        
        button = window.getButtonByName("Undo");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
            buttonMethod.UndoMove();
          }
        });
        
        button = window.getButtonByName("Quit");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            System.exit(0);
          }
        });
        
    }
    
    /**
     * Yorum
     * Tüm çizim işlemleri burada gerçekleşir. Önce çizim panelinin boyutlarını alır sonra oyun boyutunu alır, bunlarla hücre boyutunu alır 
     * ve bu boyutlara göre drawBoard ile hücreleri çizer. Kazanılmışsa oyun yerine kazanma ekranını çizer.
     * 
     * draw
     * This function draws all the content on the draw panel using the graphics provided.
     * @param graphics context
     */
    public void draw(Graphics g) {
        int width = window.getDrawPanel().getWidth();
        int height = window.getDrawPanel().getHeight();
        int gameSize = thegame.getGameSize();
        int xStepSize = width / gameSize;
        int yStepSize = height / gameSize;
  
        if(gameWon) {
            drawWinMenu((Graphics2D) g, width, height, gameSize, xStepSize, yStepSize);
        }
        else if(gameLost) {
            drawLoseMenu((Graphics2D) g, width, height, gameSize, xStepSize, yStepSize);
        }
        else {
            drawBoard((Graphics2D) g, width, height, gameSize, xStepSize, yStepSize);
            drawGame((Graphics2D) g, width, height, gameSize, xStepSize, yStepSize);
        }
    }
    
    /**
     * Yorum
     * Kullanıcı etkileşimini sağlar. Farenin yerini ve sol tıka basılıp basılmadığını kaydeder. Tıklandığında hamle yapma sekmesini açar.
     * 
     * update
     * This function lets the user interact with the game with mouse inputs.
     */
    public void update() {
        int mouseX = mouse.getX();
        int mouseY = mouse.getY();
        boolean leftClick = mouse.getLeftClick();
        mouse.setMousePosition(mouseX, mouseY);
        
        gameWon = thegame.checkWin();
        gameLost = checkLose();
        
        if(leftClick && !gameWon) {
            playMove(mouseX, mouseY);
            
            mouse.setMouseLeft(false);
        }
    
    }
    
    /**
     * Yorum
     * Hamle yapma fonksiyonu. Farenin x ve y eksenindeki değerlerini alarak hangi row ve col'a tıklandığını hesaplar. Eğer değiştirilebilir bir 
     * hücre ise java option input penceresi açar ve değeri o row ve col'a atar. Değiştirebildiyse true döndürür yoksa false.
     * 
     * playMove
     * This functions makes the move using the mouse position when it was clicked using JOptionPane input dialog.
     * @param x value of mouse
     * @param y value of mouse
     * @return if the change happened
     */
    private boolean playMove(int mouseX, int mouseY) {
        int width = window.getDrawPanel().getWidth();
        int height = window.getDrawPanel().getHeight();
        int gameSize = thegame.getGameSize();
        int xStepSize = width / gameSize;
        int yStepSize = height / gameSize;
        
        int row = mouseY / yStepSize;
        int col = mouseX / xStepSize;
        
        boolean returnVal = false;
        
        Slot s = thegame.getMoves()[row][col];
        
        if(!s.getSelected()) {
            
        }
        
        if((s.getSelected() || s.getState().contains("-")) && s.getFillable()) {
            String enteredNumber = JOptionPane.showInputDialog(window.getWindow(), "Enter value: ", "Number", JOptionPane.QUESTION_MESSAGE);
            if(enteredNumber != null) { 
                buttonMethod.makeMove(String.valueOf(row), String.valueOf(col), enteredNumber);
                returnVal = true;
            }    
        }
        
        String selectedState = s.getState();
        
        for(int rows = 0; rows < gameSize; rows++) {
            for(int cols = 0; cols <  gameSize; cols++) {
                Slot currentSlot = thegame.getMoves()[rows][cols];
                String currentState = currentSlot.getState();
                if(currentState.equals(selectedState)) {
                    currentSlot.setSelected(true);
                }
                else {
                    currentSlot.setSelected(false);
                }
            }
        }
                
            
            
        
        return returnVal;
    }
    
    /**
     * Yorum 
     * Hücreleri çizer. Küçük kareleri görünürlük için daha kalın çizgiyle çizer. 
     * 
     * drawBoard
     * This function draws the cells of the board on the screen.
     * @param graphic context
     * @param panel width
     * @param panel height
     * @param game size
     * @param cell size on x axis
     * @param cell size on y axis
     */
    public void drawBoard(Graphics2D g, int width, int height, int gameSize, int xStepSize, int yStepSize) {
        
        g.setColor(Color.WHITE);
        
        int cellSize = (int) Math.sqrt(gameSize);
        
        BasicStroke bigStroke = new BasicStroke(5);
        BasicStroke smallStroke = new BasicStroke(2);
        
        for(int col = 0; col < gameSize; col++) {
            int x = col * xStepSize;
            
            if(col % cellSize == 0) {
                g.setStroke(bigStroke);
            }
            else {
                g.setStroke(smallStroke);
            }
            
            g.drawLine(x, 0, x, height);
        }
        for(int row = 0; row < gameSize; row++) {
            int y = row * yStepSize;
            
            if(row % cellSize == 0) {
                g.setStroke(bigStroke);
            }
            else {
                g.setStroke(smallStroke);
            }
            
            g.drawLine(0, y, width, y);
        }
    }
    
    /**
     * Yorum
     * Sudokudaki slotların değerlerini drawString ile çizim paneline yazar. Eğer slot değiştirilebilir ise bold değilse düz yazı ile yazar.
     * 
     * drawGame
     * This functions writes the slot states on the screen.
     * @param graphic context
     * @param panel width
     * @param panel height
     * @param game size
     * @param cell size on x axis
     * @param cell size on y axis
     */
    public void drawGame(Graphics g, int width, int height, int gameSize, int xStepSize, int yStepSize) {
        for(int row = 0; row < gameSize; row++) {
            for(int col = 0; col < gameSize; col++) {
                Slot slot = thegame.getMoves()[row][col];
                String slotState = slot.getState();
                
                if(slot.getSelected()) {
                    g.setColor(new Color(140, 180, 60));
                }
                else {
                    g.setColor(Color.WHITE);
                }
                
                if(!solution[row][col].equals(slotState)) {
                    g.setColor(Color.RED);
                }
                
                if(!slotState.contains("-")) {
                    if(slot.getFillable()) {
                        g.setFont(new Font("ariel", Font.PLAIN, 26));
                    }
                    else {
                        g.setFont(new Font("ariel", Font.BOLD, 26));
                    }
                    int winStringWidth = (int) g.getFontMetrics().getStringBounds(slotState, g).getWidth();
                    int winStringHeight = (int) g.getFontMetrics().getStringBounds(slotState, g).getHeight();
                    //g.drawString(slotState, width / 2 - winStringWidth / 2, height / 2 - winStringHeight / 2);
                    g.drawString(slotState, col * xStepSize + xStepSize / 2 - winStringWidth / 2, row * yStepSize + yStepSize / 2);
                }
            }
        }
    }
    
    /**
     * Yorum
     * kazandın yazısını ekrana yazar
     * 
     * drawWinMenu
     * This function draws the win screen on the draw panel.
     * @param graphic context
     * @param panel width
     * @param panel height
     * @param game size
     * @param cell size on x axis
     * @param cell size on y axis
     */
    public void drawWinMenu(Graphics g, int width, int height, int gameSize, int xStepSize, int yStepSize) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("ariel", Font.BOLD, 72));
        String winString = "You won!";
        int winStringWidth = (int) g.getFontMetrics().getStringBounds(winString, g).getWidth();
        int winStringHeight = (int) g.getFontMetrics().getStringBounds(winString, g).getHeight();
        g.drawString("You won!", width / 2 - winStringWidth / 2, height / 2 - winStringHeight / 2);
    }
    /**
     * Yorum
     * kaybettin yazısını ekrana yazar
     * 
     * drawLoseMenu
     * This function draws the lose screen on the draw panel.
     * @param graphic context
     * @param panel width
     * @param panel height
     * @param game size
     * @param cell size on x axis
     * @param cell size on y axis
     */
    public void drawLoseMenu(Graphics g, int width, int height, int gameSize, int xStepSize, int yStepSize) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("ariel", Font.BOLD, 72));
        String loseString = "You lost!";
        int loseStringWidth = (int) g.getFontMetrics().getStringBounds(loseString, g).getWidth();
        int loseStringHeight = (int) g.getFontMetrics().getStringBounds(loseString, g).getHeight();
        g.drawString(loseString, width / 2 - loseStringWidth / 2, height / 2 - loseStringHeight / 2);
    }
    
    
    /**
     * getMouse
     * Returns the mouse object of the game
     * @return mouse object
     */
    public Mouse getMouse() {
        return mouse;
    }
    
    private boolean checkLose() {
        boolean isSudokuFull = true;
        int gameSize = thegame.getGameSize();
        for(int row = 0; row < gameSize; row++) {
            for(int col = 0; col < gameSize; col++) {
                Slot s = thegame.getMoves()[row][col];
                if(s.getState().contains("-")) {
                    isSudokuFull = false;
                    break;
                }
            }
        }
        
        if(isSudokuFull) {
            return !thegame.checkWin();
        }
        else {
            return false;
        }
    }
}
