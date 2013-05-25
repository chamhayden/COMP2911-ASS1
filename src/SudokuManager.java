import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;



public class SudokuManager {
	
	public SudokuManager()
	{
		/*String[] buttons = { "Easy", "Medium", "Hard", "Cancel" };
		difficulty = JOptionPane.showOptionDialog(null, "Choose your difficulty:", "Welcome to Sudoku Fun!",
		        JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[2]);
		*/
		pane = new OptionPanes();
		difficulty = pane.chooseLevel();
		
		while(difficulty == EXIT_VALUE || difficulty == pane.closedOption()){
			if(pane.exitMessageStart()==UNSURE){
				difficulty = pane.chooseLevel();
			} else System.exit(0);
		}
		
		board = new SudokuBoard(BOARD_SIZE);
		
		board.generate(difficulty);
		
		//board.generate(board.difficultyValueEasy());
		
		
		/*for (int i = 0; i < board.getBoardSize(); i++)
		{
			for (int j = 0; j < board.getBoardSize(); j++)
			{
				System.out.print(board.getCellValue(i, j) + " ");
			}
			System.out.println();
		}*/
		//initialises frame with events handler
		EventQueue.invokeLater(new Runnable(){
			public void run()
			{
				SudokuFrame frame = new SudokuFrame(board);
				frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				frame.addWindowListener(new WindowAdapter() {
					// handle window closing 
					public void windowClosing(WindowEvent we) {
				        if(pane.exitMessageInGame())
				        	System.exit(0);
				    }
					});
				frame.setVisible(true);
			}
		});
	}
	
	OptionPanes pane;
	private int difficulty;
	private SudokuBoard board;
	private static final int BOARD_SIZE = 9;
	private final int EXIT_VALUE = 3;
	private final int UNSURE = 1;
}
