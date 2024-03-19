
/**
 * 
 * Yorum
 * Mouse değerlerini tutmak için kullanılıyor. Sadece setter ve getter metodları var.
 * 
 * Mouse
 * Used for holding mouse values, only has setter and getter methods.
 * @author (your name)
 * @version (a version number or a date)
 */
public class Mouse
{
    private int mouseX;
    private int mouseY;
    private boolean mouseLeft;
    
    /**
     * Mouse
     * Constructor of the class mouse
     */
    public Mouse() {
        mouseX = 0;
        mouseY = 0;
        mouseLeft = false;
    }
    
    /**
     * setMousePosition
     * Sets both x and y values of the mouse to the given values
     * @param x value
     * @param y value
     */
    public void setMousePosition(int x, int y) {
        mouseX = x;
        mouseY = y;
    }
    /**
     * setMouseLeft
     * Sets the boolean value of left click
     * @param boolean left click
     */
    public void setMouseLeft(boolean click) {
        mouseLeft = click;
    }
    
    /**
     * getX
     * Returns the value of mouse x
     * @return mouse x
     */
    public int getX() {
        return mouseX;
    }
    /**
     * getY
     * Returns the value of mouse y
     * @return mouse y
     */
    public int getY() {
        return mouseY;
    }
    /**
     * getLeftClick
     * Returns the value of mouse left
     * @return boolean mouse left
     */
    public boolean getLeftClick() {
        return mouseLeft;
    }
}
