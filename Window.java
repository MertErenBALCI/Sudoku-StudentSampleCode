import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Color;

/**
 * 
 * Yorum
 * JFrame objesini oluşturan ana pencere.
 * İki kısımdan oluşuyor, butonPaneli ve çizimPaneli.
 * 
 * Window
 * This class used for the creating the main JFrame content.
 * 
 */
public class Window {

    // Yorum
    JFrame window; //JFrame objesi
    Button[] buttons; // JButton'un child classı buton classı array, 5 buton var.
    DrawPanel drawPanel; // JPanel'in child classı çizim paneli
    
    
    /**
     * 
     * Yorum
     * Window constuctoru, genişlik ve yükseklik alarak pencere oluşturuyor. Çizim paneli ve butonları kendine ekliyor.
     * 
     * Game parametresini çizim panelinin içine göndermek için alıyor.
     * 
     * Window
     * Constructor of the Window class
     * @param game object
     * @param width
     * @param height
     */
    public Window(Game game, int windowWidth, int windowHeight) {
        
        int buttonPanelHeight = 75;
        /**
         * Yorum
         * Pencere oluşturuluyor. Boyutu sabit kalması için setResizable false yapılıyor. Boyutu ayarlanıyor.
         * 
         */
        window = new JFrame("Sudoku");
        window.setPreferredSize(new Dimension(windowWidth + 20, windowHeight + 50));
        window.setResizable(false);
        window.setLayout(null);
        window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
        
        /**
         * Yorum
         * butonPaneli ve çizimPanelinin hizalı şekilde eklenmesi için anaPanel oluşturuluyor.
         * setLayout null ile butonPaneli ve çizimPaneli pikseline göre yerleştiriliyor.
         * 
         */
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, windowWidth, windowHeight);
        mainPanel.setBackground(Color.WHITE);
        
        Color drawPanelBackgroundColor = new Color(130, 130, 130);
        Color buttonPanelBackgroundColor = new Color(100, 100, 100);
        
        /**
         * Yorum
         * butonPaneli oluşturuluyor. FlowLayout ile butonlar yan yana yerleştiriliyor.
         * setBounds ile sol üst köşeye ve 75 yükseklikte anaPanele koyuluyor.
         * 
         */
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        buttonPanel.setBounds(0, 0, windowWidth, buttonPanelHeight);
        buttonPanel.setBackground(buttonPanelBackgroundColor);
        mainPanel.add(buttonPanel);
        
        
        /**
         * Yorum
         * 5 tane buton constructoruna butonPaneli ve ad gönderilerek oluşturuluyor. Buton dizisine ekleniyor.
         * 
         */
        buttons = new Button[5];
        buttons[0] = new Button("Save", buttonPanel);
        buttons[1] = new Button("Load", buttonPanel);
        buttons[2] = new Button("Clear", buttonPanel);
        buttons[3] = new Button("Undo", buttonPanel);
        buttons[4] = new Button("Quit", buttonPanel);
        
        /**
         * Yorum
         * çizimPaneli oluşturuluyor.
         * Game tutması gerektiği için game gönderiliyor, anaPanele bağlanması için anaPanel gönderiliyor, yüksekliği de ana pencereden butonPanelinin
         * yükseliği çıkarılarak gönderliliyor.
         * 
         */
        drawPanel = new DrawPanel(game, mainPanel, buttonPanelHeight, windowWidth, windowHeight - buttonPanelHeight, drawPanelBackgroundColor);
        
        
        /**
         * Yorum
         * Ana panel ana pencereye ekleniyor.
         */
        window.getContentPane().add(mainPanel);
        window.pack();
        window.setVisible(true);
    }
    
    /**
     * Yorum
     * çizimPaneli getter
     * 
     * getDrawPanel
     * Returns the draw panel
     * @return draw panel
     */
    public DrawPanel getDrawPanel() {
        return drawPanel;
    }
    /**
     * getWindow
     * Returns the JFrame window
     * @return window
     */
    public JFrame getWindow() {
        return window;
    }
    /**
     * Yorum
     * Alınan ada göre buton dizisindeki aynı ada sahip buton döndürülüyor.
     * 
     * getButtonByName
     * Finds and returns the button in the buttons array
     * @param button name
     * @return button
     */
    public Button getButtonByName(String name) {
        for(Button button : buttons) {
            if(button.getName().equals(name)) {
                return button;
            }
        }
        return null;
    }
}
