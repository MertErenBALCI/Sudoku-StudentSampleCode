import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
/**
* 
* Yorum
* Çizim panelinin fare girdilerini alması için java MouseListener ve MouseMotionListener interfacelerini kullanıyor.
* Çizim panelinin içindeki oyundan, oyunun içindeki Mouse objesini alıyor. Mouse objesindeki x, y ve sol tık değerlerini set ile değiştiriyor.
* 
* Çizim panelinin adresini içinde tutuyor ve aynı zamanda çizim paneline atanmış.
* Çizim paneli constructorunda addMouseListener ile eklendi.
* 
* 
* MouseInputs
* Used for getting action performed by the user's mice
*
* @author (your name)
* @version (a version number or a date) 
*/

public class MouseInputs implements MouseListener, MouseMotionListener {
    
    private DrawPanel drawPanel;
    
    /**
     * MouseInputs
     * Constructor of the MouseInputs class
     */
    public MouseInputs(DrawPanel drawPanel) {
        this.drawPanel = drawPanel;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
                
    }

    @Override
    public void mousePressed(MouseEvent e) {
        /**
         * Yorum
         * e.BUTTON1 sol tık için,
         * Sol tık basıldığında Mouse objesinin sol tık bool değerini doğru yapar.
         * Mouse objesi çizim paneli içindeki oyun içindeki mouse objesi
         * 
         */
        if(e.getButton() == e.BUTTON1) {
                    drawPanel.getGame().getMouse().setMouseLeft(true);   
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        /**
         * Yorum
         * Sol tık bırakıldığında sol tık bool değerini false yapar.
         * 
         * 
         */
        if(e.getButton() == e.BUTTON1) {
                    drawPanel.getGame().getMouse().setMouseLeft(false); 
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        /**
         * Yorum
         * Mouse objesinin x ve y değerlerini değiştirir.
         * 
         */
        drawPanel.getGame().getMouse().setMousePosition(e.getX(), e.getY());
    }
    
    
}