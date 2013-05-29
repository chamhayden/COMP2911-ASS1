import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 
 */

/**
 * @author Jerome
 *
 */
public class Listeners {

	public Listeners(SudokuFrame frame){
		f = frame;
	}
	
	
	OptionPanes pane = new OptionPanes();
	
	public WindowAdapter closeEvent(){
		WindowAdapter close = new WindowAdapter() {
		// handle window closing 
			public void windowClosing(WindowEvent we) {
				if(pane.exitMessageInGame())
					System.exit(0);
			}
		};
		return close;
	}
	/*
	public KeyListener inputShortCut(){
		KeyListener key = new KeyListener() {
			
			public void keyTyped(KeyEvent e) {
				int id = e.getID();
		        if (id == KeyEvent.KEY_TYPED) {
		            //char c = e.getKeyChar();
		            //String.valueOf(c);
		        	f.highlightValue(String.valueOf(e.getKeyChar()));
		        }
			}

			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		
		
		};
		
		
		return key;
	}
*/	
	
	SudokuFrame f;
}
