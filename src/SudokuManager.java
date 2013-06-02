import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;



public class SudokuManager {
	
	public SudokuManager()
	{
		
		board = new SudokuBoard(BOARD_SIZE);
		
		pane = new OptionPanes();
		difficulty = pane.chooseLevel();
		
		while(difficulty == pane.closedOption()){
			if(pane.exitMessageStart()){
				System.exit(0);
			} else difficulty = pane.chooseLevel();
		}

		board.generate(difficulty);
		
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
				//frame.addKeyListener(new Listeners(frame).inputShortCut());
				frame.setMinimumSize(new Dimension(MIN_WIDTH,MIN_HEIGHT));
				frame.pack();
				frame.setVisible(true);
				
			}
		});
	}
	
	private int MIN_WIDTH = 600;
	private int MIN_HEIGHT = 700;
	private OptionPanes pane;
	private int difficulty;
	private SudokuBoard board;
	private static final int BOARD_SIZE = 9;
}
