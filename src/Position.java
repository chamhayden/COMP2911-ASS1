/**
 * Refers to a position on the board by its row and column
 * @author laura
 *
 */
public class Position {
	
	/**
	 * Constructor
	 * @param row row of cell
	 * @param col column of cell
	 */
	public Position(int row, int col){
		this.row = row;
		this.col = col;
	}
	
	/**
	 * Row acessor
	 * @return row
	 */
	public int getRow(){
		return row;
	}
	
	/**
	 * Column acessor
	 * @return column
	 */
	public int getCol(){
		return col;
	}
	
	
	private int row;
	private int col;
}
