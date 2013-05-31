/**
 * @author Steven Falconieri <br/>
 * <h3> Class Description</h3>
 * Checks if a Sudoku has a unique solution by attempting to solve the puzzle with logical techniques,
 * which will only solve the puzzle if a unique solution exits.This is specifically why backtracking 
 * was not used since it would require comparison of all solutions in order to ensure that only one 
 * solution existed. 
 */
public class SudokuSolver implements UniqueChecker {
	public SudokuSolver() {
		
	}
	@Override
	// Since implementation only uses logical solving techniques, the puzzle
	// can only be solved if there is a unique solution. This is specifically 
	// why backtracking was not used.
	public boolean isSudokuSolutionUnique(Board boardToSolve) {
		int board[][] = new int[this.BOARD_SIZE][this.BOARD_SIZE];
		for(int row = 1; row <= this.BOARD_SIZE; row++) {
			for(int col = 1; col <= this.BOARD_SIZE; col++) {
				if(boardToSolve.hasInput(row, col)) {
					board[row-1][col-1] = boardToSolve.getCellValue(row, col);	
				} else {
					board[row-1][col-1] = this.EMPTY;
				}
			}
		}
		SudokuState initialState = new SudokuState(board);
		initialState.solve();
		return initialState.solved();
	}
	private static final int BOARD_SIZE = 9;
	private static final int EMPTY = SudokuState.EMPTY;
}
