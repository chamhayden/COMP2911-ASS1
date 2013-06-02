/**
* This class takes a Board object that has been previously
*  filled and removes values that can be whilst still maintaining a unique solution
*  
* @author Hayden Smith, Laura Hodges, Jerome Robins, Steven Falconieri
* 
*/
public class SudokuCellRemoverExhaustive implements Removalist {
	
	/**
	 * Constructor for RemoverExhaustive
	 * @param b board to remove from
	 */
	public SudokuCellRemoverExhaustive (Board b)
	{
		this.board = b;
		checker = new SudokuSolver();
		if (board.isDifficultyEasy()){
			maxRemoved = REMOVE_ON_EASY;
		} else if (board.isDifficultyMedium()){
			maxRemoved = REMOVE_ON_MEDIUM;
		} else if (board.isDifficultyHard()){
			maxRemoved = REMOVE_ON_HARD;
		}
		numRemoved = 0;
	}

	/**
	 * Removes if all other values are somewhere in the row, column or square
	 * @param p Position object to check if is removeable from the board
	 * @return Whether or not the Position object can be removed from the board
	 */
	public boolean removeIfCan(Position p) {
		int row = p.getRow();
		int col = p.getCol();
		
		board.setIfInitiallySet(row+1, col+1, false);
		if(checker.isUniqueSolution(board)){
			numRemoved++;
			return true;
		} else {
			board.setIfInitiallySet(row+1, col+1, true);
			return false;
		}
	}

	/** 
	 * Checks if the process should terminate once a certain number, based on difficulty, have been removed
	 * @return Whether the process should terminate once a certain number, based on difficulty, have been removed
	 */
	public boolean shouldTerminate() {
		if (numRemoved >= maxRemoved){
			return true;
		}
		return false;
	}
	
	Board board;
	SudokuSolver checker;
	int maxRemoved;
	int numRemoved;
	
	private static final int REMOVE_ON_EASY = 0;
	private static final int REMOVE_ON_MEDIUM = 15;
	private static final int REMOVE_ON_HARD = 25;

}
