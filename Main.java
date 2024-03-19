import java.util.Scanner;

/**
 * Yorum
 * main metodu.
 * Seçime göre GUI ya da UI başlatıyor.
 *
 * Main
 * Main class that starts GUI or UI based on user choice.
 */
public class Main
{
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        
        int choice = -1;
        while(choice != 1 && choice != 2)   {
            System.out.println("Which mode you want to use? Enter 1 for GUI, 2 for UI");
            choice = userInput.nextInt();
        }
        
        switch(choice) {
            case 1:
                GUI gui = new GUI();
                break;
            case 2:
                UI ui = new UI();
                break;
        }
        
    }
}
