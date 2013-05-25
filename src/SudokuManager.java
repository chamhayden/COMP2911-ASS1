import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;



public class SudokuManager {
	
	public SudokuManager()
	{
		
		pane = new OptionPanes();
		difficulty = pane.chooseLevel();
		
		while(difficulty == EXIT_VALUE || difficulty == pane.closedOption()){
			if(pane.exitMessageStart()){
				System.exit(0);
			} else difficulty = pane.chooseLevel();
		}
		/*/
		 * Difficulty value has to be offset by +1 in the optionPane as automatic value passed by user input is 0,1,2 (Easy,Med,Hard) and 3 (Exit)
		 * WOuld it be possible to standardise difficulty values to Easy=0,Med=1,Hard=2 throughout to avoid that random offset value?
		 * 
		 * using Board interface methods to get difficulty adds more code as we need to compare which difficulty to fetch and pass
		 * when at this point there is no need to know what we are passing through 
		 */
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
	private final int EXIT_VALUE = 4;
}
