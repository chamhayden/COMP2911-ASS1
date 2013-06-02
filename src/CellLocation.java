/**
* Refers to a position on the board by its row and column
* @author Hayden Smith, Laura Hodges, Jerome Robins, Steven Falconieri
*/
public class CellLocation implements Position
{
	
	/**
	 * Constructor
	 * @param row Row of cell
	 * @param col Column of cell
	 */
	public CellLocation(int row, int col)
	{
		this.row = row;
		this.col = col;
	}
	
	/**
	 * Row accessor
	 * @return The row
	 */
	public int getRow()
	{
		return row;
	}
	
	/**
	 * Column accessor
	 * @return The column
	 */
	public int getCol()
	{
		return col;
	}
		
	private int row;
	private int col;
}
