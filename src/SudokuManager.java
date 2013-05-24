import java.awt.EventQueue;

import javax.swing.JFrame;

public class SudokuManager {
	
	public SudokuManager()
	{
		board = new SudokuBoard(BOARD_SIZE);
		board.generate(board.difficultyValueEasy());
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
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
	
	private SudokuBoard board;
	private static final int BOARD_SIZE = 9;
}
