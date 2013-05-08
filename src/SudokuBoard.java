import java.util.ArrayList;

/**
* Class that allows creation, modification and access to
*  a Sudoku board
* @author Hayden, Laura, Jerome, Steven
* @version 0.1
* 
*/
public class SudokuBoard implements Board {

	// TODO Javadoc this
	public SudokuBoard(int size, int difficulty)
	{
		boardSize = size;
		board = new ArrayList<ArrayList<BoardCell>>();
		Cell generation[][] = generateBoard(difficulty);
	}
	
	// TODO Javadoc this
	public int getCellActualFinal(int row, int col)
	{
		return getCell(row, col).getActualFinal();
	}

	// TODO Javadoc this
	public int getCellInputFinal(int row, int col)
	{
		return getCell(row, col).getInputFinal();
	}	

	// TODO Javadoc this
	public boolean isPresetCell(int row, int col)
	{
		return getCell(row, col).isPreset();
	}
	
	// TODO Javadoc this
	public boolean issetCellInputTemp(int row, int col, int number)
	{
		return getCell(row, col).issetInputTemp(number);
	}
	
	// TODO Javadoc this
	public void setCellInputFinal(int row, int col, int number)
	{
		getCell(row, col).setInputFinal(number);
	}
	
	// TODO Javadoc this
	public void setCellInputTemp(int row, int col, int number, boolean isSet)
	{
		getCell(row, col).setInputTemp(number, isSet);
	}
	
	// TODO Javadoc this
	public boolean isCorrectCell(int row, int col)
	{
		return getCell(row, col).isCorrect();
	}
	
	// TODO Javadoc this
	private BoardCell getCell(int row, int col)
	{
		return board.get(row).get(col);
	}
	
	// TODO Write this properly
	private ArrayList<ArrayList<Cell[][] generateBoard(int difficulty)
	{
		int c = 0;
		for (int i = 0; i < boardSize; i++)
		{
			board.add(new ArrayList<BoardCell>());
			ArrayList<BoardCell> row = board.get(i);
			for (int j = 0; j < boardSize; j++)
			{
				row.add(new BoardCell(c++, true, boardSize));
			}
		}
		
	}	
		
	private ArrayList<ArrayList<BoardCell>> board;
	private static int boardSize;
	
}