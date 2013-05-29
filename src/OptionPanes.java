import javax.swing.JOptionPane;

/**
 * @author Jerome
 *
 */
public class OptionPanes {
	
	public OptionPanes(){
		
	}
	
	public int closedOption(){
		return JOptionPane.CLOSED_OPTION;
	}
	
	public int chooseLevel(){
		String[] buttons = {"Easy", "Medium", "Hard", "Exit" };
		difficulty = JOptionPane.showOptionDialog(null, "To start, choose your difficulty:", "Welcome to Sudoku Fun!",
		        JOptionPane.INFORMATION_MESSAGE, 0, null, buttons, buttons[3]);
		return difficulty;
	}
	
	public int chooseLevelInGame(){
		String[] buttons = {"Easy", "Medium", "Hard"};
		difficulty = JOptionPane.showOptionDialog(null, "Here we go again! Choose your difficulty:", "Welcome to Sudoku Fun!",
		        JOptionPane.INFORMATION_MESSAGE, 0, null, buttons, buttons[2]);
		return difficulty;
	}
	
	public boolean exitMessageStart(){
		value = JOptionPane.showConfirmDialog(null,"So soon? Are you sure?", "Aurevoir Sudoku Fun!",
		        JOptionPane.YES_NO_OPTION);
		if(value == JOptionPane.YES_NO_OPTION)
			return true;
		else return false;
	}
	
	public boolean revealMessage(){
		value = JOptionPane.showConfirmDialog(null,"Give up, eh? Are you sure?", "Sudoku Fun GAME OVER!",
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
	
	public boolean newGameInGame(){
		value = JOptionPane.showConfirmDialog(null, "Start another game?", "New Game",
		        JOptionPane.YES_NO_OPTION);
		if(value == JOptionPane.YES_OPTION)
			return true;
		else return false;
	}
	
	public boolean restartGame(){
		value = JOptionPane.showConfirmDialog(null, "Restart? Are you sure?", "New Game",
		        JOptionPane.YES_NO_OPTION);
		if(value == JOptionPane.YES_OPTION)
			return true;
		else return false;
	}
	
	private int difficulty;
	private int value;
}
