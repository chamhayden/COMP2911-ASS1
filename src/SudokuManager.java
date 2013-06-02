import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
/**
* Class that manages the construction of both the graphical interface and board generation.
* This class partially implements the controller component of the Model, View, Controller (MVC) design pattern.  
* @author Hayden Smith, Laura Hodges, Jerome Robins, Steven Falconieri
*/
public class SudokuManager {
	
	/**
	 * Constructs SudokuManager which calls the 
	 */
	public SudokuManager()
	{
		// Creates the SudokuBoard to store the current model(state) of the game.
		board = new SudokuBoard(BOARD_SIZE);
		// Creates the difficulty option panes.
		pane = new OptionPanes();
		difficulty = pane.chooseLevel();
		while(difficulty == pane.closedOption()){
			if(pane.confirmationPopUp(pane.getIcon("grumpyIcon"),"So soon? Are you sure?", "Aurevoir Sudoku Fun!")){
				System.exit(0);
			} else difficulty = pane.chooseLevel();
		}
		// Generates a board based upon the users selected difficulty level.
		board.generate(difficulty);
		// Constructs the main GUI interface.
		EventQueue.invokeLater(new Runnable(){
			public void run()
			{
				SudokuFrame frame = new SudokuFrame(board);
				frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				frame.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent we) {
						if(pane.confirmationPopUp(pane.getIcon("grumpyIcon"),"So soon? Are you sure?", "Aurevoir Sudoku Fun!"))
							System.exit(0);
					}
				});
				frame.setMinimumSize(new Dimension(MIN_WIDTH,MIN_HEIGHT));
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
	
	private int MIN_WIDTH = 820;
	private int MIN_HEIGHT = 700;
	private OptionPanes pane;
	private int difficulty;
	private SudokuBoard board;
	private static final int BOARD_SIZE = 9;
}
