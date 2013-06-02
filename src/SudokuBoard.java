import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

/**
* Class that allows creation, modification and access to
*  a Sudoku board. A board's items have a "correct" value, and user's
*  are able to enter both "input" values and "draft" input values.
*  This also provides extra functionality in terms of whether particular
*  cells or the entire board are correctly filled
*  
* @author Hayden Smith, Laura Hodges, Jerome Robins, Steven Falconieri
* @version 2.2
* */
public class SudokuBoard implements Board {

	/**
	 * Construct a Sudoku Board
	 * @param size Width and height of the board
	 * @param difficulty Arbitrary difficulty of board solving
	 */
	public SudokuBoard(int size)
	{	
		this.undoCellStates = new LinkedList<Cell>();
		this.undoCellPoints = new LinkedList<Point>();
		
		this.boardSize = size;
		board = new ArrayList<ArrayList<Cell>>();
		
		for (int i = 0; i < boardSize; i++)
		{
			board.add(new ArrayList<Cell>());
			ArrayList<Cell> row = board.get(i);
			for (int j = 0; j < boardSize; j++)
			{ 
				Cell c = new SudokuBoardCell(boardSize); 
				row.add(c);
			}
		}
		sudokuGenerator = new SudokuGenerator();
	}
	
	/**
	 * Generate a new board
	 * @param difficulty The difficulty level the board
	 *  should be generated with
	 */
	public void generate(int difficulty)
	{
		clear();
		this.difficulty = difficulty;
		
		this.currentlyGenerating = true;
		sudokuGenerator.generateBoard(this);
		this.currentlyGenerating = false;
	}
	
	/**
	 * Remove the value stored in a cell
	 * @param row Row cell is in
	 * @param col Column cell is in
	 */
	public void removeCellValue(int row, int col)
	{
		if (currentlyGenerating)
		{
			getCell(row, col).removeCorrectValue();
		}
		else
		{
			this.addCellState(row, col);
			getCell(row, col).removeInputValue();
			for (int i = 1; i <= boardSize; i++)
			{
				setCellDraftVisibility(row, col, i, false);
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
		if (currentlyGenerating || isEmptyCell(row, col))
		{
			return getCell(row, col).getCorrectValue();
		}
		else
		{
			return getCell(row, col).getInputValue();
		}
	}
	
	/**
	 * Set the value that is currently held within a particular cell
	 * @param row Row cell is in 
	 * @param col Column cell is in
	 * @param number Value currently held in the cell
	 */
	public void setCellValue(int row, int col, int number)
	{
		if (currentlyGenerating)
		{
			getCell(row, col).setCorrectValue(number);
		}
		else
		{
			if (getCell(row, col).getInputValue() != number)
			{
				this.addCellState(row, col);
				getCell(row, col).setInputValue(number);
				for (int i = 1; i <= boardSize; i++)
				{
					getCell(row, col).setDraft(i, false);
				}
			}
		}
	}
	
	/**
	 * Determine whether a cell is currently visible for the current 
	 *  state of the board
	 * @param row Row cell is in
	 * @param col Column cell is in
	 * @return Whether the cell is currently visible at the current
	 *  particular state of the board
	 */
	public boolean hasInput(int row, int col)
	
	{
		return getCell(row, col).getInputValue()!=-1;
	}
	
	/**
	 * Determine whether a cell is visible at the initial state 
	 *  of the board
	 * @param row Row cell is in
	 * @param col Column cell is in
	 * @return Whether the cell is visible at the initial state
	 *  of the board
	 */
	public boolean isInitiallySet(int row, int col)
	{
		return getCell(row, col).isInitiallyVisible();
	}
	/**
	 * Set the visiblity of a cell on the board
	 * @param row Row cell is in
	 * @param col Column cell is in
	 * @param visiblity Whether the particular cell is visible or not
	 */
	
	public void setIfInitiallySet(int row, int col, boolean visibility)
	{
		if (currentlyGenerating)
		{
			getCell(row, col).setInitiallyVisible(visibility);
		}
		getCell(row, col).setCurrentlyVisible(visibility);
	}
	
	/**
	 * Check if a given cell (for row, column) is empty
	 * @param row Row cell is in
	 * @param col Column cell is in
	 * @return Whether a given cell (for row, column) is empty
	 */
	public boolean isEmptyCell(int row, int col)
	{
		if (currentlyGenerating)
		{
			return getCell(row, col).isEmptyCorrect();
		}
		else
		{
			return getCell(row, col).isEmptyInput();
		}
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
	public boolean isVisibleCellDraft(int row, int col, int number)
	{
		return getCell(row, col).issetDraft(number);
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
	public void setCellDraftVisibility(int row, int col, int number, boolean isSet)
	{
		addCellState(row, col);
		getCell(row, col).setDraft(number, isSet);
		if (isSet)
		{
			getCell(row, col).removeInputValue();
		}
		
	}
			
	/**
	 * Return whether the board has been correctly filled out
	 * @return Whether the board has been correctly filled out
	 */
	public boolean isCorrectBoard()
	{
		boolean allCorrect = true;
		for (int i = 1; i <= boardSize; i++)
		{
			for (int j = 1; j <= boardSize; j++)
			{
				if (!isInitiallySet(i,j) && !isCorrectCell(i, j))
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
	public boolean isDifficultyEasy()
	{
		if (this.difficulty == DIFFICULTY_EASY)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Return the difficulty constant for "Medium"
	 * @return Difficulty constant for "Medium"
	 */
	public boolean isDifficultyMedium()
	{
		if (this.difficulty == DIFFICULTY_MEDIUM)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Return the difficulty constant for "Hard"
	 * @return Difficulty constant for "Hard"
	 */
	public boolean isDifficultyHard()
	{
		if (this.difficulty == DIFFICULTY_HARD)
		{
			return true;
		}
		return false;
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
		for (int i = 1; i <= getBoardSize(); i++)
		{
			for (int j = 1; j <= getBoardSize(); j++)
			{
				clearCell(i, j);				
			}
		}
	}
	
	/**
	 * Reset the board to its original state
	 *  after generation
	 */
	public void restart()
	{
		for (int i = 1; i <= getBoardSize(); i++)
		{
			for (int j = 1; j <= getBoardSize(); j++)
			{
				getCell(i, j).restart();				
			}
		}
	}
	

	/**
	 * For a given row determine if a value exists in it
	 * @param row Row to check if number exists in
	 * @param value Value to check if exists in row
	 * @return Whether a value exists in a particular row
	 */
	public boolean rowHas(int row, int value)
	{
		boolean result = false;
		for (int i = 1; i <= this.boardSize; i++)
		{
			if (getCellValue(row, i) == value)
			{
				if(isInitiallySet(row, i)){
					result = true;
				}
			}
		}
		return result;
	}
	
	/**
	 * For a given column determine if a value exists in it
	 * @param row Column to check if number exists in
	 * @param value Value to check if exists in column
	 * @return Whether a value exists in a particular column
	 */
	public boolean columnHas(int col, int value)
	{
		boolean result = false;
		for (int i = 1; i <= this.boardSize; i++)
		{
			if (getCellValue(i, col) == value)
			{
				if(isInitiallySet(i, col)){
					result = true;
				}
			}
		}
		return result;
	}
	
	/**
	 * Determine whether a square contains a particular value
	 * @param sqr As defined below for a 9x9 board
	 * 
	 * 1 1 1 | 2 2 2 | 3 3 3
	 * 1 1 1 | 2 2 2 | 3 3 3
	 * 1 1 1 | 2 2 2 | 3 3 3
	 * ---------------------
	 * 4 4 4 | 5 5 5 | 6 6 6
	 * 4 4 4 | 5 5 5 | 6 6 6
	 * 4 4 4 | 5 5 5 | 6 6 6
	 * ---------------------
	 * 7 7 7 | 8 8 8 | 9 9 9
	 * 7 7 7 | 8 8 8 | 9 9 9
	 * 7 7 7 | 8 8 8 | 9 9 9
	 * 
	 * @param value Value to check if exists in a square
	 * @return Whether a square contains a particular value
	 */
	public boolean squareHas(int sqrId, int value)
	{
		boolean result = false;
		for (int i = 1; i <= this.boardSize; i++)
		{
			for (int j = 1; j <= this.boardSize; j++)
			{
				if (squareSectionId(i, j) == sqrId)
				{
					if (getCellValue(i, j) == value)
					{
						if(isInitiallySet(i, j)){
							result = true;
						}
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * Return the difficulty of the current board that has
	 *  been generated
	 * @return Difficulty of current board that has been generated
	 */
	public int getDifficulty()
	{
		return this.difficulty;
	}	
	
	/** 
	 * Revert the last change to the board that the user
	 *  made
	 */
	public void undoLast()
	{
		if (undoCellPoints.size() > 0 && undoCellStates.size() > 0)
		{
			Point p = undoCellPoints.pop();
			Cell  c = undoCellStates.pop();
			getCell(p.getX(), p.getY()).cloneBack(c);
		}
	}

	/**
	 * For a given value check if it matches the correct value
	 *  of a cell
	 * @param row Row cell is in
	 * @param col Column cell is in
	 * @param value Value to check if matches the correct
	 *  input of a cell
	 * @return Whether a given value matches the correct value of a cell
	 */
	public boolean isCorrectInputForCell(int row, int col, int value)
	{
		if (value == getCell(row, col).getCorrectValue())
		{
			return true;
		}
		return false;
	}

	/**
	 * Check if all inputs that are capable of being filled on the board
	 *  have in-fact been filled
	 * @return Return whether all inputs that are capable of being filled 
	 *  on the board have in-fact been filled
	 */
	public boolean isFilledBoard()
	{
		for (int i = 1; i <= boardSize; i++)
		{
			for (int j = 1; j <= boardSize; j++)
			{
					if (!isInitiallySet(i,j) && isEmptyCell(i, j))
					{
						return false;
					}
			}
		}
		return true;
	}
	
	/**
	 * Determine the "squareSectionID" (i.e. arbitrary number reflecting
	 *  which segment a cell lies in) of a particular cell
	 * 
	 * 1 1 1 | 2 2 2 | 3 3 3
	 * 1 1 1 | 2 2 2 | 3 3 3
	 * 1 1 1 | 2 2 2 | 3 3 3
	 * ---------------------
	 * 4 4 4 | 5 5 5 | 6 6 6
	 * 4 4 4 | 5 5 5 | 6 6 6
	 * 4 4 4 | 5 5 5 | 6 6 6
	 * ---------------------
	 * 7 7 7 | 8 8 8 | 9 9 9
	 * 7 7 7 | 8 8 8 | 9 9 9
	 * 7 7 7 | 8 8 8 | 9 9 9
	 * 
	 * @param row Row cell is in
	 * @param col Column cell is in
	 * @return The arbitrary square section ID that a cell lies in
	 */
	private int squareSectionId(int row, int col)
	{
		int sectionId = 0;
		if (col >= 1 && col <= 3)
		{
			sectionId += 1;
		}
		else if (col >= 4 && col <= 6)
		{
			sectionId += 2;
		}
		else if (col >= 7 && col <= 9)
		{
			sectionId += 3;
		}
		
		if (row > 3)
		{
			sectionId += 3;
		}
		if (row > 6)
		{
			sectionId += 3;
		}
		return sectionId;
	}

	/**
	 * Take a copy of a cell of a particular row/column and add
	 *  it to the stack of "changed cells" for use in the undo method
	 *  at a later point
	 * @param row Row cell is in
	 * @param col Column cell is in
	 */
	private void addCellState(int row, int col) {
		Cell c = getCell(row, col).cloneCell();
		undoCellStates.addFirst(c);
		undoCellPoints.addFirst(new Point(row, col));
	}
	
	/**
	 * Clear all values in a cell for a given row and column
	 * @param row Row cell is in
	 * @param col Column cell is in
	 */
	private void clearCell(int row, int col)
	{
		getCell(row, col).clear();
	}
	
	/**
	 * Return a Cell object when given a particular row and column
	 * @param row Row cell is in
	 * @param col Column cell is in
	 * @return Cell object for particular row/column
	 */
	private Cell getCell(int row, int col)
	{
		return board.get(row - 1).get(col - 1);
	}	
	
	/**
	 * Return a Cell object when given a particular row and column
	 * @param row Row cell is in
	 * @param col Column cell is in
	 * @return Cell object for particular row/column
	 */
	private Cell getCell(double row, double col)
	{
		return board.get((int)row - 1).get((int)col - 1);
	}	
	
	private SudokuGenerator sudokuGenerator;
	private LinkedList<Cell> undoCellStates;
	private LinkedList<Point> undoCellPoints;
	private ArrayList<ArrayList<Cell>> board;
	private int boardSize;
	private int difficulty;
	public boolean currentlyGenerating;
	
	private static final int DIFFICULTY_EASY = 0;
	private static final int DIFFICULTY_MEDIUM = 1;
	private static final int DIFFICULTY_HARD = 2;
	
}