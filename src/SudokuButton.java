import java.awt.event.ActionListener;

import javax.swing.JButton;



/**
 * @author Jerome
 *
 */
public class SudokuButton extends JButton{

	/**
	 * Class constructor
	 */
	public SudokuButton(int rowNumber, int columnNumber, String newLabel, ActionListener newAction){
		row = rowNumber;
		col = columnNumber;
		label = newLabel;
		action = newAction;
		button = createButton();
		button.addActionListener(newAction);
	}
	
	private JButton createButton(){
		JButton b = new JButton(this.label);
		b.addActionListener(this.action);
		return b;
	}
	
	public int getRow(){
		return this.row;
	}
	
	public int getCol(){
		return this.col;
	}
	
	public JButton getButton(){
		return this.button;
	}
	
	JButton button;
	int row;
	int col;
	String label;
	ActionListener action;
	
}
