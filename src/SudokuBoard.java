import java.util.ArrayList;

/**
* Class that allows creation, modification and access to
*  a Sudoku board
* @author Hayden
* @version 0.1
* 
*/

public class SudokuBoard implements Board {

	public SudokuBoard(int size, int difficulty)
	{	
		boardSize = size;
		board = new ArrayList<ArrayList<BoardCell>>();
		
		BoardGenerator generator = new BoardGenerator(difficulty, boardSize);
		
		for (int i = 0; i < boardSize; i++)
		{
			board.add(new ArrayList<BoardCell>());
			ArrayList<BoardCell> row = board.get(i);
			for (int j = 0; j < boardSize; j++)
			{ 
				row.add(new BoardCell(generator.getValues(i, j), generator.getVisibility(i, j), boardSize));
			}
		}
	}
	
	/**
	 * Get the value that is currently held within a particular cell
	 * @param row Row cell is in
	 * @param col Column cell is in
	 * @return Value currently held in the cell
	 */
	public int getCellValue(int row, int col)
	{
		return getCell(row, col).getValue();
	}
	
	/**
	 * Set the value that is currently held within a particular cell
	 * @param row Row cell is in 
	 * @param col Column cell is in
	 * @param number Value currently held in the cell
	 */
	public void setCellValue(int row, int col, int number)
	{
		//getCell(row, col).setValue(number);
	}
	
	/**
	 * Determine whether a cell is currently visible for the current 
	 *  state of the board
	 * @param row Row cell is in
	 * @param col Column cell is in
	 * @return Whether the cell is currently visible at the current
	 *  particular state of the board
	 */
	public boolean isCurrentlyVisibleCell(int row, int col)
	{
		return true;
	}
	
	/**
	 * Determine whether a cell is visible at the initial state 
	 *  of the board
	 * @param row Row cell is in
	 * @param col Column cell is in
	 * @return Whether the cell is visible at the initial state
	 *  of the board
	 */
	public boolean isInitiallyVisibleCell(int row, int col)
	{
		return true;
	}
	
	/**
	 * Set the visiblity of a cell on the board
	 * @param row Row cell is in
	 * @param col Column cell is in
	 * @param visiblity Whether the particular cell is visible or not
	 */
	public void setCellVisiblity(int row, int col, boolean visiblity)
	{
		
	}
		
		
	/**
	 * Check is a particular cell value's temporary value for a 
	 *  particular number is visible
	 * @param row Row cell is in
	 * @param col Column Cell is in
	 * @param number Number of the temporary value that needs to
	 *  be checked if is visible
	 * @return Whether a particular cell value's temporary value for
	 *  a particular number is visible
	 */
	public boolean isVisibleCellTemp(int row, int col, int number)
	{
		return getCell(row, col).issetTemp(number);
	}
	
	/**
	 * Make a particular cell value's temporary value for a 
	 *  particular number either visible or not visible
	 * @param row Row cell is in
	 * @param col Column Cell is in
	 * @param number Number of the temporary value that needs to
	 *  have it's visiblity changed
	 * @param isSet Value to denote whether to have the cell visible
	 *  or not visible
	 */
	public void setCellTempVisibility(int row, int col, int number, boolean isSet)
	{
		getCell(row, col).setTemp(number, isSet);
	}
	
	/**
	 * Return whether the board has been correctly filled out
	 * @return Whether the board has been correctly filled out
	 */
	public boolean isCorrectBoard()
	{
		boolean allCorrect = true;
		for (int i = 0; i < boardSize; i++)
		{
			for (int j = 0; i < boardSize; j++)
			{
				if (!isCorrectCell(i, j))
				{
					allCorrect = false;
				}
			}
		}
		return allCorrect;
	}
	
	/**
	 * Return whether a particular cell in the board has been
	 *  correctly filled out 
	 * @param row Row cell is in
	 * @param col Column cell is in
	 * @return Whether a particular cell in the board has been correctly
	 *  filled out
	 */
	public boolean isCorrectCell(int row, int col)
	{
		return getCell(row, col).isCorrect();
	}
	
	/**
	 * Return the difficulty constant for "Easy"
	 * @return Difficulty constant for "Easy"
	 */
	public int difficultyValueEasy()
	{
		return DIFFICULTY_EASY;
	}
	
	/**
	 * Return the difficulty constant for "Medium"
	 * @return Difficulty constant for "Medium"
	 */
	public int difficultyValueMedium()
	{
		return DIFFICULTY_MEDIUM;
	}
	
	/**
	 * Return the difficulty constant for "Hard"
	 * @return Difficulty constant for "Hard"
	 */
	public int difficultyValueHard()
	{
		return DIFFICULTY_HARD;
	}
	
	/**
	 * Get the size of the board
	 * @return Size of the board
	 */
	public int getBoardSize()
	{
		return this.boardSize;
	}
	
	/**
	 * Reset all values in the board
	 */
	public void clear()
	{
		for (int i = 0; i < getBoardSize(); i++)
		{
			for (int j = 0; j < getBoardSize(); j++)
			{
				setCellValue(i, j, -1);
				
			}
		}
	}
	
	
	
	
	
	
	private BoardCell getCell(int row, int col)
	{
		return board.get(row).get(col);
	}
		
	public void print()
	{
		for (int i = 0; i < boardSize; i++)
		{
			for (int j = 0; j < boardSize; j++)
			{
				System.out.print(this.getCellValue(i, j) + " ");
			}
			System.out.print("\n");
		}		
	}
	
	private ArrayList<ArrayList<BoardCell>> board;
	private int boardSize;
	private static final int DIFFICULTY_EASY = 1;
	private static final int DIFFICULTY_MEDIUM = 2;
	private static final int DIFFICULTY_HARD = 3;
	
	
}