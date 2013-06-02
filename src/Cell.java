/**
 * Class to store and access values associated with an individual
 *  cell in the grid
 * @author Hayden Smith, Laura Hodges, Jerome Bird, Steven Falconieri
 */
public interface Cell {

	/**
	 * Get what the final value of the cell should be
	 * @return Final value of the cell
	 */
	public int getCorrectValue();
	
	/**
	 * Get the value of the cell inputted by the user
	 * @return Value of the cell inputted by the user
	 */
	public int getInputValue();
	
	public void cloneBack(Cell c);
	
	public Cell cloneCell();
	
	/**
	 * Set what the final value of the cell should be
	 * @param number What the final value of the cell should be
	 */
	public void setCorrectValue(int number);
	
	/**
	 * Set what the user input value should be
	 * @param number What the user input value should be
	 */
	public void setInputValue(int number);
	
	/**
	 * Remove/reset the final value of the cell
	 */
	public void removeCorrectValue();
	
	/**
	 * Remove/reset the user input value of the cell
	 */
	public void removeInputValue();
	
	/**
	 * Determine whether the cell is currently a visible one
	 * @return Whether the cell is currently a visible one
	 */
	public boolean isCurrentlyVisible();
	
	/**
	 * Determine whether the cell was initially a visible one
	 * @return Whether the cell was initially a visible one
	 */
	public boolean isInitiallyVisible();
	
	/**
	 * Set whether the cell is a currently visible one
	 * @param isVisible Whether the cell is a currently visible one
	 */
	public void setCurrentlyVisible(boolean isVisible);
	
	/**
	 * Set whether the cell is an initially visible one
	 * @param isVisible Whether the cell is an initially visible one
	 */
	public void setInitiallyVisible(boolean isVisible);
	
	/**
	 * Check if a draft value for a particular cell is set
	 * @param number Number to check if is set
	 * @return Whether a draft value for a particular cell is set
	 */
	public boolean issetDraft(int number);
	
	/**
	 * Set/unset a draft value for a particular cell
	 * @param number Number to set as draft value
	 * @param isSet Whether to set or unset the draft value
	 */
	public void setDraft(int number, boolean isSet);
	
	/**
	 * Reset all cell fields/data to default
	 */
	public void clear();
	
	/**
	 * Reset all cell data to its original values 
	 *  just after generation
	 */
	public void restart();
	
	/**
	 * Check if the final value of this cell is empty
	 * @return If the final value of this cell is empty
	 */
	public boolean isEmptyCorrect();
	
	/**
	 * Check if the user input value of this cell is empty
	 * @return If the user input value of this cell is empty
	 */
	public boolean isEmptyInput();
	
	/**
	 * Determine whether the cell's input value is correct (i.e.
	 *  matches the final)
	 * @return Whether the cell's input value is correct
	 */
	public boolean isCorrect();
	
	public int getBoardSize();
	
}
