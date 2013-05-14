import java.util.LinkedList;
import java.util.Scanner;


/**
 * @author Steven 
 * Holds the current board and evaluation state required to solve a puzzle
 */

public class SudokuState {
		private int board[][];
		private int temp[][][];
		private static final int BOARD_SIZE = 9;
		private static final int SECTION_SIZE = 3;
		private int cellsFilled;
		private static final int EMPTY = -1;
		private static final int NOT_POSSIBLE = -10;
		private static final int POSSIBLE = -100;
		private static final int SATISFIES_ENOUGH = 4;
		
		/** 
		 * Constructs and initializes the board state given a 2D integer array of values with empty cells 
		 * represented by -1.
		 * @param inputBoard 2D integer array of board state.
		 */
		public SudokuState(int inputBoard[][]) {
			// Initialize Board
			this.board = new int[BOARD_SIZE][BOARD_SIZE];
			this.temp = new int[BOARD_SIZE][BOARD_SIZE][BOARD_SIZE];
			this.board = inputBoard;
			this.cellsFilled = 0;
			for(int row = 0; row < BOARD_SIZE; row++) {
				for(int col = 0; col < BOARD_SIZE; col++) {
					// Initialize all possible temp values to be possible;
					for(int i = 0; i < BOARD_SIZE; i++) {
						this.temp[row][col][i] = POSSIBLE;	
					}
					if(this.board[row][col] != EMPTY) {
						this.cellsFilled++;
					}
					System.out.print(this.board[row][col]);
				}
				System.out.println("");
				this.updateTempVals();
				this.updateCellsFilled();
			}
		}
		
		/**
		 * Eliminate the possibility for all temp values except that assigned by number.
		 * @param number
		 * @param row
		 * @param col
		 */
		public void setTempFinalVal(int number, int row, int col) {
			for(int num = 0; num < BOARD_SIZE; num++) {
				if(number != (num + 1)) {
					this.temp[row][col][num] = NOT_POSSIBLE;
				} else {
					this.temp[row][col][num] = POSSIBLE;
				}
				
			}
		}
		
		/**
		 * Return the only possible value for a given cell given all other possibilities have been exhausted. 
		 * @param row
		 * @param col
		 * @return
		 */
		public int getTempFinalVal(int row, int col) {
			LinkedList<Integer> possibleCellVals = this.possibleVals(row, col);
			if(possibleCellVals.size() == 1) {
				return possibleCellVals.remove();
			} 
			return NOT_POSSIBLE;		
		}
		
		/**
		 * Checks all other rows and columns for possible values in order to evaluate if a cell can be filled.
		 * E.g.
		 * ---------------------------------------------
		 * | 1 | - | | | - | 3 | - | - | - | - | - | - |
		 * ---------------------------------------------
		 * | 2 | - | - | - | - | - | - | - | 3 | - | - |
		 * ---------------------------------------------
		 * | x | | | | |   |   |   |   |   |   |   |   |
		 * ---------------------------------------------
		 * |   | 3 | | |
		 * -------------
		 * |   | | | | |
		 * -------------
		 * |   | | | | |
		 * -------------
		 * |   | | | 3 |
		 *  x = 3 since all other rows and columns already have a 3.
		 */
		public void solveMediumSteps() {
			int sectionRow = 0;
			int sectionCol = 0;
			int rowsAndColsSatisfied = 0;
			LinkedList<Integer> possibleCellVal = null;
			// For each row and column
			for(int row = 0; row < BOARD_SIZE; row++) {
				for(int col = 0; col < BOARD_SIZE; col++) {
					possibleCellVal = this.possibleVals(row, col);
					if(this.getTempFinalVal(row, col) == NOT_POSSIBLE) {
						// For each possible number for this cell.
						for(Integer num : possibleCellVal) {
							sectionRow = 3*(row/3);
							sectionCol = 3*(col/3);
							for(int rowTranslate = 0; rowTranslate < SECTION_SIZE; rowTranslate++) {								
								if((sectionRow + rowTranslate) != row && this.inRow(num, sectionRow) > 0) {
									rowsAndColsSatisfied++;
								}
							}
							for(int colTranslate = 0; colTranslate < SECTION_SIZE; colTranslate++) {
								if((sectionCol + colTranslate) != col && this.inColumn(num, sectionCol) > 0) {
									rowsAndColsSatisfied++;
								}
							}
							// TODO is evaluating cases where there is only 3 and not 4 rows and cols satisfied.
							//System.out.println("#################### " + rowsAndColsSatisfied + " num " + num + " row = " + row + " col = " + col + " sectionRow = " + sectionRow + " sectionCol = " + sectionCol);
							if(rowsAndColsSatisfied >= SATISFIES_ENOUGH) {
								System.out.println("####################   " + rowsAndColsSatisfied + " row = " + row + " col = " + col);
								this.setTempFinalVal(num, row, col);
								this.updateBoardVals();
							}
							rowsAndColsSatisfied = 0;
						}
					}
				}
			}
		}
		
		/**
		 * Solves for cells which have all but 1 number missing in a column, row or square.
		 */
		public void solveSimpleSteps() {
			boolean cellsFilled = false;
			for(int row = 0; row < BOARD_SIZE; row++) {
				for(int col = 0; col < BOARD_SIZE; col++) {
					if(this.board[row][col] == EMPTY) {
						int cellVal = this.getTempFinalVal(row, col);
						if(cellVal != NOT_POSSIBLE) {
							this.board[row][col] = cellVal;
							this.updateCellsFilled();
							this.updateTempVals();
							cellsFilled = true;
						} else {
							// **************************
						}
					}
				}
			}
			if(cellsFilled) {
				this.solveSimpleSteps();
			}
		}
		
		
		/**
		 * Provides linked list of possible values for a given cell at (row, col)
		 * @param row Value from 0 to 9 representing the row number from top to bottom
		 * @param col Value from 0 to 9 representing the column number from left to right
		 * @return List of Integers of possible values for this cell.
		 */
		public LinkedList<Integer> possibleVals(int row, int col) {
			LinkedList<Integer> possibleVals = new LinkedList<Integer>();
			for(int number = 0; number < BOARD_SIZE; number++) {
				if(this.temp[row][col][number] == POSSIBLE) {
					possibleVals.add(number+1);
				}
			}
			return possibleVals;
		}
		
		
		/**
		 * Checks if a the current board state (all rows, columns and squares) have valid cell values.
		 * @return
		 */
		public boolean valid() {
			for(int row = 0; row < BOARD_SIZE; row++) {
				for(int col = 0; col < BOARD_SIZE; col++) {
					for(int number = 1; number <= BOARD_SIZE; number++) {
						if(this.inColumn(number, col) > 1 || this.inRow(number, row) > 1 || this.inSection(number, row, col) > 1) {
							return false;
						}
					}
				}
			}
			return true;
		}
		
		
		/**
		 * Checks if current board is solved.
		 */
		public boolean solved() {
			if(this.cellsFilled == BOARD_SIZE * BOARD_SIZE && this.valid()) {
				return true;
			}
			return false;
		}
		
		/**
		 * Solves a given puzzle stored in this board state.
		 */
		public void solve() {
			System.out.println("Updating Temp Vals");
			this.updateTempVals();
			System.out.println("Solving Simple Steps");
			this.solveSimpleSteps();
			System.out.println("Solving Medium Steps");
			this.solveMediumSteps();
			this.updateTempVals();
			this.updateBoardVals();
			this.updateCellsFilled();
		}
		
		/**
		 * Returns the number of filled (non empty) cells on the board
		 */
		public int getNumCellsFilled() {
			return this.cellsFilled;
		}
		
		/**
		 * Sets all temp values in a given cell to be empty
		 * @param row Value from 0 to 9 representing the row number from top to bottom
		 * @param col Value from 0 to 9 representing the column number from left to right
		 */
		public void clearTemp(int row, int col) {
			for(int i = 0; i < BOARD_SIZE; i++) {
				this.temp[row][col][i] = POSSIBLE;
			}
		}
		
		/**
		 * Updates and returns the number of filled cells in the current board state
		 */
		public int updateCellsFilled() {
			int count = 0;
			for(int row = 0; row < BOARD_SIZE; row++) {
				for(int col = 0; col < BOARD_SIZE; col++) {
					if(this.board[row][col] != 0) {
						count++;
					}
				}
			}
			this.cellsFilled = count;
			return count;
		}
		
		/**
		 * Updates the value of temp cells based upon the filled cells in its given row, column and square.
		 */
		public void updateTempVals() {
			for(int row = 0; row < BOARD_SIZE; row++) {
				for(int col = 0; col < BOARD_SIZE; col++) {
					int cellVal = this.board[row][col];
					this.clearTemp(row, col);
					if(cellVal != EMPTY && this.getTempFinalVal(row, col) == NOT_POSSIBLE) {
						this.setTempFinalVal(cellVal, row, col);
					} else {
						for(cellVal = 1; cellVal <= BOARD_SIZE; cellVal++) {
							if(!this.canBe(cellVal, row, col)) {
								// Update possible temp vals
								this.temp[row][col][cellVal-1] = NOT_POSSIBLE;
							}
						}
					}
				}
			}
		}
		public void updateBoardVals() {
			for(int row = 0; row < BOARD_SIZE; row++) {
				for(int col = 0; col < BOARD_SIZE; col++) {
					if(this.getTempFinalVal(row, col) != NOT_POSSIBLE) {
						this.board[row][col] = this.getTempFinalVal(row, col);
					}
				}
			}
		}
		
		/**
		 * Returns the number of occurrences of a number in a given row.
		 * @param number Number looked for in given row.
		 * @param row Value from 0 to 9 representing the row number from top to bottom
		 * @return 
		 */
		public int inRow(int number, int row) {
			int answer = 0;
			for(int tempCol = 0; tempCol < BOARD_SIZE; tempCol++) {
				if(this.board[row][tempCol] == number) {
					answer++;
				}
			}
			return answer;
		}
		/**
		 * Returns the number of occurrences of a number in a given row.
		 * @param number Number looked for in given row.
		 * @param col Value from 0 to 9 representing the column number from left to right
		 * @return 
		 */		
		public int inColumn(int number, int col) {
			int answer = 0;
			for(int tempRow = 0; tempRow < BOARD_SIZE; tempRow++) {
				if(this.board[tempRow][col] == number) {
					answer++;
				}
			}
			return answer;
		}
		
		/**
		 * Returns true if a number can be assigned to a cell without conflict
		 * @param number
		 * @param row Value from 0 to 9 representing the row number from top to bottom
		 * @param col Value from 0 to 9 representing the column number from left to right
		 * @return
		 */
		public boolean canBe(int number, int row, int col) {
			if(this.inColumn(number, col) > 0 || this.inRow(number, row) > 0 || this.inSection(number, row, col) > 0) {
				return false;
			}
			return true;
		}
		
		/**
		 * Returns section number from 1 to 9 as illustrated below:-
		 * -------------
		 * | 1 | 2 | 3 |
		 * -------------
		 * | 4 | 5 | 6 |
		 * -------------
		 * | 7 | 8 | 9 |
		 * -------------
		 * @param row
		 * @param col
		 * @return
		 */
		public int getSection(int row, int col) {
			int sectionRow = 0;
			int sectionCol = 0;
			if(row < 3) {
				sectionRow = 1;
			} else if(row < 6 ) {
				sectionRow = 2; 
			} else if(row < 9) {
				sectionRow = 3;
			}
			if(col < 3) {
				sectionCol = 1;
			} else if(col < 6) {
				sectionCol = 2;
			} else if(col < 9) {
				sectionCol = 3;
			}
			return 3*(sectionRow-1) + sectionCol;
		}
		/**
		 * Returns number of occurrences of number within a section determined by the row 
		 * and column of the number cell.
		 * @param number
		 * @param row Value from 0 to 9 representing the row number from top to bottom
		 * @param col Value from 0 to 9 representing the column number from left to right
		 * @return
		 */
		public int inSection(int number, int row, int col) {
			int sectionRow = 3*(row /3);
			int sectionCol = 3*(col /3);
			int answer = 0;
			for(int i = 0; i < SECTION_SIZE; i++) {
				for(int j = 0; j < SECTION_SIZE; j++) {
					if(this.board[sectionRow+i][sectionCol+j] == number) {
						answer++;
					}
				}
			}
			return answer;
		}
		
		/**
		 * Prints 2D array representation of the board.
		 * @param array
		 */
		public void print2DBoard(int array[][]) {
			for(int row = 0; row < BOARD_SIZE; row++) {
				for(int col = 0; col < BOARD_SIZE; col++) {
					if(array[row][col] != EMPTY) {
						System.out.print(array[row][col] + "|");	
					} else {
						System.out.print(" |");
					}
				}
				System.out.println("");
			}
		}

		/**
		 * Prints temporary values of the board
		 */
		public void printTemps() {
			for(int row = 0; row < BOARD_SIZE; row++) {
				for(int col = 0; col < BOARD_SIZE; col++) {
					LinkedList<Integer> possibleValues = this.possibleVals(row, col);
					for(Integer val : possibleValues) {
						System.out.print(val + "_");
					}
					System.out.print("   |    ");
				}
				System.out.println("");
			}
		}
		
		public static void main(String args[]) {
			int board[][] = new int[BOARD_SIZE][BOARD_SIZE];
			Scanner sc = new Scanner(System.in);
			int i = 0;
			while(i < 9) {
				String input = sc.nextLine();
				String[] vals = input.split(" ");
				int j = 0;
				for(String s : vals) {
					board[i][j++] = Integer.valueOf(s);
				}
				i++;
			}
			
			
			System.out.println("Board");
			
			
			SudokuState s = new SudokuState(board);
			s.print2DBoard(board);
			System.out.println("BoardState");
			s.print2DBoard(s.board);
			
			System.out.println(s.getSection(0,0));
			System.out.println(s.getSection(0,3));
			System.out.println(s.getSection(0,6));
			System.out.println(s.getSection(3,0));
			System.out.println(s.getSection(6,3));
			System.out.println(s.getSection(0,6));
			System.out.println(s.getSection(4,4));
			System.out.println(s.getSection(3,3));
			
			
			System.out.println(s.inSection(5, 1, 1));
			System.out.println(s.inSection(5, 1, 3));
			System.out.println(s.inSection(8, 4, 2));
			System.out.println(s.inSection(2, 4, 4));
			System.out.println(s.inSection(7, 8, 8));
			s.solve();
			s.print2DBoard(s.board);
			s.printTemps();
			s.print2DBoard(s.board);
		}
}

