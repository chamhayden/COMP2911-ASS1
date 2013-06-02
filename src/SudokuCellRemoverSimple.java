/**
* This class takes a Board object that has been previously
*  filled and removes values if all other numbers are somewhere in the 
*  same row, column or square
*  
* @author Hayden Smith, Laura Hodges, Jerome Robins, Steven Falconieri
* 
*/
public class SudokuCellRemoverSimple implements Removalist
{

	/** 
	 * Constructor for RemoverSimple
	 * @param b Board to remove from
	 */
	public SudokuCellRemoverSimple (Board b)
	{
		this.board = b;
		if (board.isDifficultyEasy())
		{
			maxRemoved = REMOVE_ON_EASY;
		}
		else if (board.isDifficultyMedium())
		{
			maxRemoved = REMOVE_ON_MEDIUM;
		}
		else if (board.isDifficultyHard())
		{
			maxRemoved = REMOVE_ON_HARD;
		}
		numRemoved = 0;
	}
	
	/**
	 * Removes if all other values are somewhere in the row, column or square
	 * @param p Position object to check if is removeable from the board
	 * @return Whether or not the Position object can be removed from the board
	 */
	public boolean removeIfCan(Position p)
	{
		int row = p.getRow();
		int col = p.getCol();
		for(int i = 1; i < 10; i++)
		{
			if (!(board.rowHas(row+1, i) || board.columnHas(col+1, i) || board.squareHas(squareNo(row,col), i)))
			{
				return false;
			}	
		}
		board.setIfInitiallySet(row+1, col+1, false);
		numRemoved++;
		return true;
	}

	/** 
	 * Checks if the process should terminate once a certain number, based on difficulty, have been removed
	 * @return Whether the process should terminate once a certain number, based on difficulty, have been removed
	 */
	public boolean shouldTerminate()
	{
		if (numRemoved >= maxRemoved)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Return the square ID that a given cell is in
	 * @param row Row that cell is in
	 * @param collumn Column that cell is in
	 * @return The square ID that the cell is in
	 */
	private int squareNo(int row, int column)
	{
		return ((int)Math.floor(column / 3) + (int)Math.floor(row / 3) * 3);
	}
	
	private int maxRemoved;
	private int numRemoved;
	private Board board;
	
	private static final int REMOVE_ON_EASY = 30;
	private static final int REMOVE_ON_MEDIUM = 20;
	private static final int REMOVE_ON_HARD = 10;
}
