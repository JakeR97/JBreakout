import java.awt.EventQueue;

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
