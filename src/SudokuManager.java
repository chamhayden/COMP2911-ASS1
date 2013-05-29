import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;



public class SudokuManager {
	
	public SudokuManager()
	{
		
		/*try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }*/
        // Turn off metal's use of bold fonts 
        //UIManager.put("swing.boldMetal", Boolean.FALSE);
		
		board = new SudokuBoard(BOARD_SIZE);
		
		pane = new OptionPanes();
		difficulty = pane.chooseLevel();
		
		while(difficulty == EXIT_VALUE || difficulty == pane.closedOption()){
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
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
	
	private OptionPanes pane;
	private int difficulty;
	private SudokuBoard board;
	private static final int BOARD_SIZE = 9;
	private final int EXIT_VALUE = 3;
}
