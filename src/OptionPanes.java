import javax.swing.JOptionPane;

/**
 * 
 */

/**
 * @author Jerome
 *
 */
public class OptionPanes {

	/*public OptionPanes(){
		difficulty = 0;
	}*/
	
	public int closedOption(){
		return JOptionPane.CLOSED_OPTION;
	}
	
	public int chooseLevel(){
		
		String[] buttons = { "Easy", "Medium", "Hard", "Exit" };
		difficulty = JOptionPane.showOptionDialog(null, "Choose your difficulty:", "Welcome to Sudoku Fun!",
		        JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[2]);
		return difficulty;
	
	}
	
	public int exitMessageStart(){
		value = JOptionPane.showConfirmDialog(null,"So soon? Are you sure?", "Aurevoir Sudoku Fun!",
		        JOptionPane.YES_NO_OPTION);
		return value;
	}
	
	public boolean exitMessageInGame(){
		value = JOptionPane.showConfirmDialog(null, "Really? Your progress will not be saved.", "Aurevoir Sudoku Fun!",
		        JOptionPane.YES_NO_OPTION);
		if(value == JOptionPane.YES_OPTION)
			return true;
		else return false;
	}
	
	
	int difficulty;
	int value;
}
