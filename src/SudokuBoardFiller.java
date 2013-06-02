import java.util.LinkedList;
import java.util.Random;

/**
* Class that is passed a board of type Board and fills
*  the board with numbers that are appropriate for a sudoku puzzle
*  (see SudokuMain for this description)
*  
* @author Hayden Smith, Laura Hodges, Jerome Robins, Steven Falconieri
* 
*/
public class SudokuBoardFiller
{
	
	/**
	 * Construct a BoardFiller
	 * @param b Board to fill
	 */
	public SudokuBoardFiller(Board b) 
	{
		this.board = b;
	}
	
	/**
	 * Fills the board with values
	 */
	public void fillBoard()
	{
		int row, col, square;
		int x = 0;
		Random r = new Random();  
		LinkedList<Integer> l = new LinkedList<Integer>();
		int counter = 0;
		
		row = 0;
		
		while(row < board.getBoardSize())
		{
			counter = 0;
			col = 0;
			while(col < board.getBoardSize())
			{				
				square = squareNo(row, col);
				for(int m = 1; m < 10; m++)
				{
					l.add(m);
				}
				do {
					x = l.remove(r.nextInt(l.size()));
				}
				while(!l.isEmpty() && (board.rowHas(row+1, x) || board.columnHas(col+1, x) || board.squareHas(square+1, x)));
				
				if(l.isEmpty())
				{
					col = reset(row, col);
					counter++;
					if (counter > ATTEMPT_LIMIT)
					{
						board.clear();
						row = -1;
						break;
					}
				}
				else
				{
					board.setCellValue(row+1, col+1, x);
					col++;
				}
			}
			row++;
		}		
	}
	
	/** 
	 * Unsets the values in the cells up to a reset point based on current progress at board filling
	 * @param row row the filler got stuck on
	 * @param col column the filler got stuck on
	 * @return column the filler has reset to
	 */
	private int reset(int row, int col)
	{
		int resetPoint;
		if(row % 3 == 2){
			resetPoint = ((int)Math.floor(col/3)*3);
		}
		else
		{
			resetPoint = 0;
		}
		for (int p = resetPoint; p < board.getBoardSize(); p++)
		{
			board.removeCellValue(row+1, p+1);
		}	
		return resetPoint;
	}
	
	/**
	 * The number of the square the given cell is in
	 * @param row row of cell
	 * @param column column of cell
	 * @return square number
	 */
	private int squareNo(int row, int column)
	{
		return ((int)Math.floor(column / 3) + (int)Math.floor(row / 3) * 3);
	}	
	
	private Board board;
	private static final int ATTEMPT_LIMIT = 20;
}
