
public class SudokuSolver implements UniqueChecker {

	@Override
	public boolean isSudokuSolutionUnique(Board boardToSolve) {
		int board[][] = new int[this.BOARD_SIZE][this.BOARD_SIZE];
		for(int row = 0; row < this.BOARD_SIZE; row++) {
			for(int col = 0; col < this.BOARD_SIZE; col++) {
				if(boardToSolve.isVisibleCellValue(row, col)) {
					board[row][col] = boardToSolve.getCellValue(row, col);	
				} else {
					board[row][col] = this.EMPTY;
				}
			}
		}
		SudokuState initialState = new SudokuState(board);
		initialState.solve();
		return initialState.solved();
	}
	private static final int BOARD_SIZE = 9;
	private static final int EMPTY = -1;
}
