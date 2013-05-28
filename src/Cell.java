/**
 * Class to store and access values associated with an individual
 *  cell in the grid
 * @author Hayden, Laura, Jerome, Steven
 * @version 0.1
 * 
 */
public interface Cell {

	/**
	 * Get what the final value of the cell should be
	 * @return Final value of the cell
	 */
	public int getFinalValue();
	
	/**
	 * Get the value of the cell inputted by the user
	 * @return Value of the cell inputted by the user
	 */
	public int getInputValue();
	
	/**
	 * Set what the final value of the cell should be
	 * @param number What the final value of the cell should be
	 */
	public void setFinalValue(int number);
	
	/**
	 * Set what the user input value should be
	 * @param number What the user input value should be
	 */
	public void setInputValue(int number);
	
	/**
	 * Remove/reset the final value of the cell
	 */
	public void removeFinalValue();
	
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
	 * 
	 * @param number
	 * @return
	 */
	public boolean issetDraft(int number);
	
	/**
	 * 
	 * @param number
	 * @param isSet
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
	 * 
	 * @return
	 */
	public boolean isEmptyFinal();
	
	/**
	 * 
	 * @return
	 */
	public boolean isEmptyInput();
	
	/**
	 * Determine whether the cell's input value is correct (i.e.
	 *  matches the final)
	 * @return Whether the cell's input value is correct
	 */
	public boolean isCorrect();
	
}
