/**
 * Class to store and access values associated with an individual
 *  cell on a board of cells. It contains extensive information
 *  as to a cell's correct value, user input value, and draft values
 * @author Hayden Smith, Laura Hodges, Jerome Robins, Steven Falconieri
 * @version 1.2 
 */
public class SudokuBoardCell implements Cell {

	/**
	 * Construct a Board Cell
	 * @param boardSize size of the board the 
	 *  cell is contained within
	 */
	public SudokuBoardCell(int boardSize)
	{		
		this.boardSize = boardSize;
		this.tempNumbers = new boolean[boardSize];
		for (int i = 0; i < boardSize; i++)
		{
			this.tempNumbers[i] = false;
		}
	}
	
	public void cloneBack(Cell c)
	{
		this.setCorrectValue(c.getCorrectValue());
		this.setInputValue(c.getInputValue());
		this.setInitiallyVisible(c.isInitiallyVisible());
		this.setCurrentlyVisible(c.isCurrentlyVisible());
		for (int i = 1; i <= boardSize; i++)
		{
			this.setDraft(i, c.issetDraft(i));
		}
	}
	
	public Cell cloneCell()
	{
		Cell newCell = new SudokuBoardCell(boardSize);
		newCell.setCorrectValue(this.getCorrectValue());
		newCell.setInputValue(this.getInputValue());
		newCell.setInitiallyVisible(this.isInitiallyVisible());
		newCell.setCurrentlyVisible(this.isCurrentlyVisible());
		for (int i = 1; i <= boardSize; i++)
		{ 
			System.out.println("Draft! " + i + ", " + this.issetDraft(i));
			newCell.setDraft(i, this.issetDraft(i));
		}
		return newCell;
	}
	
	/**
	 * Get what the final value of the cell should be
	 * @return Final value of the cell
	 */
	public int getCorrectValue()
	{
		return valueFinal;
	}
	
	/**
	 * Get the value of the cell inputted by the user
	 * @return Value of the cell inputted by the user
	 */
	public int getInputValue()
	{
		return valueInput;
	}
		
	/**
	 * Set what the final value of the cell should be
	 * @param number What the final value of the cell should be
	 */
	public void setCorrectValue(int number)
	{
		valueFinal = number;
	}
	
	/**
	 * Set what the user input value should be
	 * @param number What the user input value should be
	 */
	public void setInputValue(int number)
	{
		valueInput = number;
	}
	
	/**
	 * Remove/reset the final value of the cell
	 */
	public void removeCorrectValue()
	{
		this.valueFinal = -1;
	}
	
	/**
	 * Remove/reset the user input value of the cell
	 */
	public void removeInputValue()
	{
		this.valueInput = -1;
	}	
	
	/**
	 * Determine whether the cell is currently a visible one
	 * @return Whether the cell is currently a visible one
	 */
	public boolean isCurrentlyVisible()
	{
		return this.isCurrentlyVisible;
	}
	
	/**
	 * Determine whether the cell was initially a visible one
	 * @return Whether the cell was initially a visible one
	 */
	public boolean isInitiallyVisible()
	{
		return this.isInitiallyVisible;
	}

	/**
	 * Set whether the cell is a currently visible one
	 * @param isVisible Whether the cell is a currently visible one
	 */
	public void setCurrentlyVisible(boolean isVisible)
	{
		this.isCurrentlyVisible = isVisible;
	}
	
	/**
	 * Set whether the cell is an initially visible one
	 * @param isVisible Whether the cell is an initially visible one
	 */
	public void setInitiallyVisible(boolean isVisible)
	{
		this.isInitiallyVisible = isVisible;
	}
	
	/**
	 * Check if a draft value for a particular cell is set
	 * @param number Number to check if is set
	 * @return Whether a draft value for a particular cell is set
	 */
	public boolean issetDraft(int number)
	{
		return tempNumbers[number - 1];
	}

	/**
	 * Set/unset a draft value for a particular cell
	 * @param number Number to set as draft value
	 * @param isSet Whether to set or unset the draft value
	 */
	public void setDraft(int number, boolean isSet)
	{
		tempNumbers[number - 1] = isSet;
	}
	
	/**
	 * Reset all cell fields/data to default
	 */
	public void clear()
	{
		this.valueFinal = -1;
		this.valueInput = -1;
		this.isCurrentlyVisible = true;
		this.isInitiallyVisible = true;
		for (int i = 0; i < boardSize; i++)
		{
			tempNumbers[i] = false;
		}
	}
	
	/**
	 * Reset all cell data to its original values 
	 *  just after generation
	 */
	public void restart()
	{
		removeInputValue();
		this.isCurrentlyVisible = false;
		for (int i = 0; i < boardSize; i++)
		{
			tempNumbers[i] = false;
		}
	}
	
	/**
	 * Check if the final value of this cell is empty
	 * @return If the final value of this cell is empty
	 */
	public boolean isEmptyCorrect()
	{
		if (this.valueFinal == -1)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Check if the user input value of this cell is empty
	 * @return If the user input value of this cell is empty
	 */
	public boolean isEmptyInput()
	{
		if (this.valueInput == -1)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Determine whether the cell's input value is correct (i.e.
	 *  matches the final)
	 * @return Whether the cell's input value is correct
	 */
	public boolean isCorrect()
	{
		if (valueInput == valueFinal)
		{
			return true;
		}
		return false;
	}
	
	public String toString()
	{
		String bs = new String("(");
		for (int i = 0; i < boardSize; i++)
		{
			if (tempNumbers[i])
			{
				bs = bs + (i+1) + ","; 
			}
		}
		bs = bs + ")";
		return new String("(Final: "+valueFinal+", Input: "+valueInput+", Drafts["+bs+"])\n");
	}
	public Object clone() {
		SudokuBoardCell copy = new SudokuBoardCell(this.boardSize);
		copy.isCurrentlyVisible = this.isCurrentlyVisible;
		copy.isInitiallyVisible = this.isInitiallyVisible;
		copy.tempNumbers = this.tempNumbers;
		copy.valueFinal = this.valueFinal;
		copy.valueInput = this.valueInput;
		return copy;
	}
	
	private int boardSize;
	private int valueFinal;
	private int valueInput;
	private boolean isInitiallyVisible;
	private boolean isCurrentlyVisible;
	private boolean tempNumbers[];
	
}
