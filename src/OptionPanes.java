import javax.swing.JOptionPane;

/**
 * @author Jerome
 *
 */
public class OptionPanes {
	
	public int closedOption(){
		return JOptionPane.CLOSED_OPTION;
	}
	
	public int chooseLevel(){
		
		String[] buttons = {"Easy", "Medium", "Hard", "Exit" };
		difficulty = JOptionPane.showOptionDialog(null, "To start, choose your difficulty:", "Welcome to Sudoku Fun!",
		        JOptionPane.PLAIN_MESSAGE, 0, null, buttons, buttons[3]);
		return difficulty;
	
	}
	
	public boolean exitMessageStart(){
		value = JOptionPane.showConfirmDialog(null,"So soon? Are you sure?", "Aurevoir Sudoku Fun!",
		        JOptionPane.YES_NO_OPTION);
		if(value == JOptionPane.YES_NO_OPTION)
			return true;
		else return false;
	}
	
	public boolean exitMessageInGame(){
		value = JOptionPane.showConfirmDialog(null, "Really? Your progress will not be saved.", "Aurevoir Sudoku Fun!",
		        JOptionPane.YES_NO_OPTION);
		if(value == JOptionPane.YES_OPTION)
			return true;
		else return false;
	}
	
	private int difficulty;
	private int value;
}
