/**
* Class that allows creation, modification and access to
*  a board
* @author Hayden Smith, Laura Hodes, Jerome Bird, Steven Falconieri
* 
*/
public interface Board
{
	/**
	 * Get the value that is currently held within a particular cell
	 * @param row Row cell is in
	 * @param col Column cell is in
	 * @return Value currently held in the cell
	 */
	public int getCellValue(int row, int col);
	
	/**
	 * Set the value that is currently held within a particular cell
	 * @param row Row cell is in 
	 * @param col Column cell is in
	 * @param number Value currently held in the cell
	 */
	public void setCellValue(int row, int col, int number);
	
	/**
	 * Determine whether a cell is currently visible for the current 
	 *  state of the board
	 * @param row Row cell is in
	 * @param col Column cell is in
	 * @return Whether the cell is currently visible at the current
	 *  particular state of the board
	 */
	public boolean isCurrentlyVisibleCell(int row, int col);
	
	/**
	 * Determine whether a cell is visible at the initial state 
	 *  of the board
	 * @param row Row cell is in
	 * @param col Column cell is in
	 * @return Whether the cell is visible at the initial state
	 *  of the board
	 */
	public boolean isInitiallyVisibleCell(int row, int col);
	
	/**
	 * Set the visiblity of a cell on the board
	 * @param row Row cell is in
	 * @param col Column cell is in
	 * @param visiblity Whether the particular cell is visible or not
	 */
	public void setCellVisibility(int row, int col, boolean visibility);
	
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
	public boolean isVisibleCellDraft(int row, int col, int number);
	
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
	public void setCellDraftVisibility(int row, int col, int number, boolean isSet);
	
	/**
	 * Return whether the board has been correctly filled out
	 * @return Whether the board has been correctly filled out
	 */
	public boolean isCorrectBoard();
	
	/**
	 * Remove the value stored in a cell
	 * @param row Row cell is in
	 * @param col Column cell is in
	 */
	public void removeCellValue(int row, int col);
	
	/**
	 * Return whether a particular cell in the board has been
	 *  correctly filled out 
	 * @param row Row cell is in
	 * @param col Column cell is in
	 * @return Whether a particular cell in the board has been correctly
	 *  filled out
	 */
	public boolean isCorrectCell(int row, int col);
	
	/**
	 * Return the difficulty constant for "Easy"
	 * @return Difficulty constant for "Easy"
	 */
	public int difficultyValueEasy();
	
	/**
	 * Return the difficulty constant for "Medium"
	 * @return Difficulty constant for "Medium"
	 */
	public int difficultyValueMedium();
	
	/**
	 * Return the difficulty constant for "Hard"
	 * @return Difficulty constant for "Hard"
	 */
	public int difficultyValueHard();
	
	/**
	 * Get the size of the board
	 * @return Size of the board
	 */
	public int getBoardSize();
	
	/**
	 * Clear all values in the board to empty/clear values
	 */
	public void clear();
	
	/**
	 * Reset the board to its original state
	 *  after generation
	 */
	public void restart();
	
	/**
	 * Generate a new board
	 * @param difficulty The difficulty level the board
	 *  should be generated with
	 */
	public void generate(int difficulty);
	
	/**
	 * For a given row determine if a value exists in it
	 * @param row Row to check if number exists in
	 * @param value Value to check if exists in row
	 * @return Whether a value exists in a particular row
	 */
	public boolean rowHas(int row, int value);
	
	/**
	 * For a given column determine if a value exists in it
	 * @param row Column to check if number exists in
	 * @param value Value to check if exists in column
	 * @return Whether a value exists in a particular column
	 */
	public boolean columnHas(int col, int value);
	
	/**
	 * Determine whether a square (arbitrary collection of
	 *  items that fall into both a set of rows and columns) 
	 *  contains a particular value
	 * @param sqr Arbitrary collection of items that fall into
	 *  both a set of rows and columns
	 * @param value Value to check if exists in a square
	 * @return Whether a square contains a particular value
	 */
	public boolean squareHas(int sqr, int value);
	
	/**
	 * Check if a given cell (for row, column) is empty
	 * @param row Row cell is in
	 * @param col Column cell is in
	 * @return Whether a given cell (for row, column) is empty
	 */
	public boolean isEmptyCell(int row, int col);
	
	/**
	 * Return the difficulty of the current board that has
	 *  been generated
	 * @return Difficulty of current board that has been generated
	 */
	public int getDifficulty();
	
}