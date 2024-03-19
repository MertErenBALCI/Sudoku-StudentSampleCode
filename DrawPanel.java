import javax.swing.JPanel;
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Yorum
 * 
 * Çizim işlemlerinin ve mouse girdilerinin gerçekleşmesi için kullanılan JPanel childi.
 * 
 * DrawPanel
 * This class is used for drawing and mouse inputs.
 */
public class DrawPanel extends JPanel {
    
    private Color backGroundColor = new Color(120, 120, 120);
    private int width;
    private int height;
    private Game game = null;
    private MouseInputs mouseInputs;
    
    /**
     * 
     * Yorum
     * Oyunu çizmek için içine oyunu alıyor. JComponenti kendini windowdaki anaPanele eklemek için alıyor.
     * 
     * DrawPanel
     * Constructor of the class DrawPanel
     * 
     */
    public DrawPanel(Game game, JComponent component, int panelY, int panelWidth, int panelHeight, Color color) {
        super();
        this.width = panelWidth;
        this.height = panelHeight;
        
        this.game = game;
        
        /**
         * Yorum
         * MouseInputs objesi oluşturularak drawPanele ekleniyor.
         */
        mouseInputs = new MouseInputs(this);
        this.addMouseListener(mouseInputs);
        this.addMouseMotionListener(mouseInputs);
        
        this.backGroundColor = color;
        this.setBounds(0, panelY, panelWidth, panelHeight);
        
        component.add(this);
    }
    
    /**
     * Yorum
     * Çizim yapılması için Graphics contextini oluşturuyor.
     * Game içindeki update metodundaki çizimPaneli.repaint() ile sürekli çağırılıyor.
     * Öncelikle arka planı çizip sonrasında game.draw ile oyun çiziliyor.
     * 
     * 
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(backGroundColor);
        g.fillRect(0, 0, width, height);
        
        game.draw(g);
    }

    
    /**
     * Yorum
     * Getter metodları.
     * 
     * getWidth
     * Returns the width of panel
     * @return width
     */
    public int getWidth() {
        return width;
    }
    /**
     * getHeight
     * Returns the height of panel
     * @return height
     */
    public int getHeight() {
        return height;
    }
    /**
     * getGame
     * Returns the game content
     * @return game object
     */
    public Game getGame() {
        return game;
    }
    
}