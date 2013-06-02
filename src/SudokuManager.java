import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;



public class SudokuManager {
	
	public SudokuManager()
	{
		
		board = new SudokuBoard(BOARD_SIZE);
		
		pane = new OptionPanes();
		difficulty = pane.chooseLevel();
		
		while(difficulty == pane.closedOption()){
			if(pane.confirmationPopUp(pane.getIcon("grumpyIcon"),"So soon? Are you sure?", "Aurevoir Sudoku Fun!")){
				System.exit(0);
			} else difficulty = pane.chooseLevel();
		}

		board.generate(difficulty);
		
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
