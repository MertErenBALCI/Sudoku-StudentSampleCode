import javax.swing.JButton;
import javax.swing.JComponent;

/**
 * Yorum
 * JButton childi ama ismi var.
 * 
 * Button
 * This class is the same as JButton but it has name as identifier.
 */
public class Button extends JButton {
    String buttonName;
    
    /**
     * Yorum
     * 
     * Component alarak kendini JComponente ekliyor.
     * 
     * Button
     * Constructor of class Button
     */
    public Button(String name, JComponent component) {
        super(name);
        this.buttonName = name;
        
        component.add(this);
    }
    
    /**
     * 
     * getName
     * Returns the name
     * @return the name
     */
    public String getName() {
        return buttonName;
    }

}