/**
* Class that is passed a board of type Board and generates an
*  appropriate sudoku puzzle that is uniquely solveable and has
*  the appropriate number of squares removed
*  
* @author Hayden Smith, Laura Hodges, Jerome Robins, Steven Falconieri
* 
*/
public class SudokuGenerator
{
	/**
	 * Generates a sudoku puzzle that is uniquely solveable and has
	 *  appropriate number of squares removed
	 * @param board
	 */
	public void generateBoard(Board board)
	{
		SudokuBoardFiller filler = new SudokuBoardFiller(board);
		SudokuCellRemover r = new SudokuCellRemover(board);
		filler.fillBoard();

		r.removeValues(new SudokuCellRemoverSimple(board));
		r.removeValues(new SudokuCellRemoverHard(board));
		r.removeValues(new SudokuCellRemoverExhaustive(board));
	}
}
