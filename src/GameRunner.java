import java.awt.EventQueue;

/**
 * This class is the starting point of the program
 * and adds the main menu to the screen.
 * 
 * @author reardj3
 * @version 1.0.2 (August 9th, 2018)
 */

public class GameRunner {

	public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {                
                Menu game = new Menu();
                game.setVisible(true);                
            }
        });
	}
	
}
