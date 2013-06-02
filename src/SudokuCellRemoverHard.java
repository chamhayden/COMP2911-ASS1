/**
* This class takes a Board object that has been previously
*  filled and removes values if they cannot be placed anywhere
*  else in the square
*  
* @author Hayden Smith, Laura Hodges, Jerome Robins, Steven Falconieri
* 
*/
public class SudokuCellRemoverHard implements Removalist {
	
	/**
	 * Constructor for RemoverHard
	 * @param b Board to remove from
	 */
	public SudokuCellRemoverHard (Board b)
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
	 * Removes if the value cannot be placed anywhere else in the square
	 * @return Whether the value can or cannot be placed anywhere else in the square
	 */
	public boolean removeIfCan(Position p)
	{
		int row = p.getRow();
		int col = p.getCol();
		int removedVal = board.getCellValue(row+1, col+1);
		int[] adjRows = findAdj(row);
		int[] adjCols = findAdj(col);
		boolean inAdjRows, inAdjCols, cellAdjRowsFull, cellAdjColsFull;
		
		inAdjRows = (board.rowHas(adjRows[0]+1, removedVal) && board.rowHas(adjRows[1]+1, removedVal));
		inAdjCols = (board.columnHas(adjCols[0]+1, removedVal) && board.columnHas(adjCols[1]+1, removedVal));
		cellAdjRowsFull = adjFull(adjRows, adjCols, col);
		cellAdjColsFull = adjFull(adjCols, adjRows, row);
		
		if ((inAdjRows && inAdjCols) || (inAdjRows && cellAdjColsFull) || (inAdjCols && cellAdjRowsFull))
		{
			board.setIfInitiallySet(row+1, col+1, false);
			numRemoved++;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Returns true if a certain number, based on difficulty, have been removed from the board
	 * @return Whether if a certain number, based on difficulty, has been removed from the board 
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
	 * Finds the rows or columns adjacent to the given row or column
	 * @param position row or column number
	 * @return row or column numbers of adjacent rows or columns
	 */
	private int[] findAdj(int position)
	{
		int[] adj = new int[2];
		int x = ((int)Math.floor(position / 3) * 3);
		int y = position % 3;
		if (y == 0)
		{
			adj[0] = x+1;
			adj[1] = x+2;
		}
		else if (y == 1)
		{
			adj[0] = x;
			adj[1] = x + 2;
		}
		else
		{
			adj[0] = x;
			adj[1] = x + 1;
		}
		return adj;
	}
	
	/**
	 * Evaluates whether the adjacent row/column within a square is full
	 * @param dir1 adjacent row/column numbers
	 * @param direction2 adjacent column/row numbers
	 * @param position column/row of cell
	 * @return if adjacent row/columns in square are full
	 */
	private boolean adjFull(int[] dir1, int[] direction2, int position)
	{
		int[] dir2 = new int[] {direction2[0], direction2[1], position};
		for (int i = 0; i < 2; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				if (!board.isInitiallySet(dir1[i]+1, dir2[j]+1))
				{
					return false;
				}
			}
		}
		
		return true;
	}

	Board board;
	private int maxRemoved;
	private int numRemoved;

	private static final int REMOVE_ON_EASY = 10;
	private static final int REMOVE_ON_MEDIUM = 50;
	private static final int REMOVE_ON_HARD = 20;

}
