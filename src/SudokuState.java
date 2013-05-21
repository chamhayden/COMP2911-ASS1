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
				return possibleCellVals.peek();
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
		 *  @return True if any cells were solved.
		 */
		public void solveMediumSteps() {
			boolean cellsFilled = false;
			int sectionRow = 0;
			int sectionCol = 0;
			LinkedList<Integer> possibleCellVal = null;
			LinkedList<Integer> allOtherPossibleCellVals = new LinkedList<Integer>();
			boolean sameCell = false;
			System.out.println("Solving Simple Steps");
			this.solveSimpleSteps();
			System.out.println("Solving Medium Steps");
			// For each row and column
			for(int row = 0; row < BOARD_SIZE; row++) {
				for(int col = 0; col < BOARD_SIZE; col++) {
					possibleCellVal = this.possibleVals(row, col);
					if(this.getTempFinalVal(row, col) == NOT_POSSIBLE) {
						// For each possible number for this cell.
						for(Integer num : possibleCellVal) {
							sectionRow = 3*(row/3);
							sectionCol = 3*(col/3);
							allOtherPossibleCellVals.clear();
							for(int rowTranslate = 0; rowTranslate < SECTION_SIZE; rowTranslate++) {								
								for(int colTranslate = 0; colTranslate < SECTION_SIZE; colTranslate++) {
									int currentRow = sectionRow + rowTranslate;
									int currentCol = sectionCol + colTranslate;
									sameCell = ((currentRow == row) && (currentCol == col));
									if(!sameCell) {
										allOtherPossibleCellVals.addAll(this.possibleVals(currentRow, currentCol));
									}
								}
							}
							this.removeDuplicates(allOtherPossibleCellVals);
							debug("Num = " + num + " (" + row + ", " + col + ") " + allOtherPossibleCellVals + !allOtherPossibleCellVals.contains(num));
							
							if(!allOtherPossibleCellVals.contains(num)) {
								this.board[row][col] = num;
								this.updateCellsFilled();
								this.updateTempVals();
								cellsFilled = true;
							}
						}
					}
				}
			}
			if(cellsFilled) {
				this.solveMediumSteps();
			}
		}
		
		public void solveHardSteps() {
			this.pointingPairsAndTriplesAlgorithm();
			for(int row = 0; row < BOARD_SIZE; row++) {
				for(int col = 0; col < BOARD_SIZE; col++) {
					debug(this.getSectionPossibleVals(row, col));
				}
			}
		}
		
		/**
		 * http://www.sudokuwiki.org/Intersection_Removal 
		 */
		public void pointingPairsAndTriplesAlgorithm() {
			for(int row = 0; row < this.BOARD_SIZE; row++) {
				for(int col = 0; col < this.BOARD_SIZE; col++) {
					int section = this.getSection(row, col);
					int sectionFirstRow = this.getSectionFirstRow(section); 
					int sectionFirstCol = this.getSectionFirstCol(section);
					// Current Cell is First Cell in a Section
					if(row == sectionFirstRow && col == sectionFirstCol) {
						LinkedList sectionRowPossibleVals = this.getSectionRowPossibleVals(row, col);
						LinkedList sectionColPossibleVals = this.getSectionColPossibleVals(row, col);
						// Get all possible values of this section except this cell.
						LinkedList sectionPossibleVals = this.getSectionPossibleVals(row, col);
						// Add the possible values of this cell as well.
						sectionPossibleVals.addAll(this.possibleVals(row, col));
						// A Pair or Triple in a box - if they are aligned on a row, n can be removed from the rest of the row.
						//                           - if they are aligned on a column, n can be removed from the rest of the column.
						for(int num = 1; num <= this.BOARD_SIZE; num++) {
							int numOccurencesInSection = this.numberOccurances(num, sectionPossibleVals);
							int numOccurencesInSectionRow = this.numberOccurances(num, sectionRowPossibleVals);
							int numOccurencesInSectionCol = this.numberOccurances(num, sectionColPossibleVals);
							boolean pairOrTripleInSection = (numOccurencesInSection == 2 || numOccurencesInSection == 3); 
							boolean pairOrTripleInSectionRow = (numOccurencesInSectionRow == 2 || numOccurencesInSectionRow == 3);
							boolean pairOrTripleInSectionCol = (numOccurencesInSectionCol == 2 || numOccurencesInSectionCol == 3);
							// A Pair or Triple in box
							if(pairOrTripleInSection) {
								if(pairOrTripleInSectionRow) {
									
								}
								if(pairOrTripleInSectionCol) {
									
								}
							}
						}
					}
					// 
					// A Pair or Triple on a row - if they are all in the same box, n can be removed from the rest of the box.
					// A Pair or Triple on a column - if they are all in the same box, n can be removed from the rest of the box.
				}
			}
		}
		
		public void removeTempVal(int num, int row, int col) {
			if(this.getTempFinalVal(row, col) == this.NOT_POSSIBLE) {
				this.temp[row][col][num] = this.NOT_POSSIBLE;
			}
		}
		
		/**
		 * Returns the number of occurrences of a single number within a list of integers.
		 * @param number The number to count the occurrences of within the given list.
		 * @param list The list of integers to search through.
		 * @return The number of occurrences of a single number within a list of integers. 
		 */
		public int numberOccurances(int number, LinkedList<Integer> list) {
			int numCounter = 0;
			for(Integer currentNum : list) {
				if(currentNum == number) {
					numCounter++;
				}
			}
			return numCounter;
		}
		
		
		/** Returns the first column for a given section parsed
		 * @param section Square of 3 by 3 cells contained on a Sudoku board labeled from 1-9 as shown below. <br/>
		 * |1|2|3| <br/>
		 * |4|5|6| <br/>
		 * |7|8|9| <br/>
		 * @return First column in a given section.
		 */
		public int getSectionFirstCol(int section) {
			return (3 * (section % 3));
		}

		/** Returns the first row for a given section parsed
		 * @param section Square of 3 by 3 cells contained on a Sudoku board labeled from 1-9 as shown below. <br/>
		 * |1|2|3| <br/>
		 * |4|5|6| <br/>
		 * |7|8|9| <br/>
		 * @return First row in a given section.
		 */
		public int getSectionFirstRow(int section) {
			return (3 * ((section - 1) % 3));
		}
		
		/**
		 * Returns list of all possible values for cells in 
		 * @param row
		 * @param col
		 * @return
		 */
		public LinkedList<Integer> getSectionRowPossibleVals(int row, int col) {
			int sectionCol = 3*(col/3);
			LinkedList<Integer> sectionRowTemps = new LinkedList<Integer>();
			for(int colTranslate = 0; colTranslate < this.SECTION_SIZE; colTranslate++) {
				int currentCol = sectionCol + colTranslate;
				if(this.getTempFinalVal(row, currentCol) == this.NOT_POSSIBLE) {
					sectionRowTemps.addAll(this.possibleVals(row, currentCol));
				}
			}
			return sectionRowTemps;
		}
		
		public LinkedList<Integer> getSectionColPossibleVals(int row, int col) {
			int sectionRow = 3*(row/3);
			LinkedList<Integer> sectionColTemps = new LinkedList<Integer>();
			for(int rowTranslate = 0; rowTranslate < this.SECTION_SIZE; rowTranslate++) {
				int currentRow = sectionRow + rowTranslate;
				if(this.getTempFinalVal(currentRow, col) == this.NOT_POSSIBLE) {
					sectionColTemps.addAll(this.possibleVals(currentRow, col));
				}
			}
			return sectionColTemps;
		}
		
		/**
		 * Returns list of all possible values of cells contained within the section except those for the cell located at row and col.
		 * @param row
		 * @param col
		 * @return
		 */
		public LinkedList<Integer> getSectionPossibleVals(int row, int col) {
			int sectionRow = (3*(row/3));
			int sectionCol = (3*(col/3));
			int currentRow = 0;
			int currentCol = 0;
			LinkedList<Integer> sectionTemps = new LinkedList<Integer>();
			for(int rowTranslate = 0; rowTranslate < this.SECTION_SIZE; rowTranslate++) {
				for(int colTranslate = 0; colTranslate < this.SECTION_SIZE; colTranslate++) {
					currentRow = sectionRow + rowTranslate;
					currentCol = sectionCol + colTranslate;
					// Exclude Final Values
					if(this.getTempFinalVal(currentRow, currentCol) == this.NOT_POSSIBLE) {
						sectionTemps.addAll(this.possibleVals(currentRow, currentCol));
					}
				}
			}
			return sectionTemps;
		}
		
		public void removeDuplicates(LinkedList<Integer> list) {
			LinkedList<Integer> duplicateFreeList = new LinkedList<Integer>();
			while(!list.isEmpty()) {
				Integer currentElement = list.remove();
				if(!duplicateFreeList.contains(currentElement)) {
					duplicateFreeList.add(currentElement);
				}
			}
			while(!duplicateFreeList.isEmpty()) {
				list.add(duplicateFreeList.remove());
			}
		}
		
		public void debug(Object obj) {
			System.out.println(obj.toString());
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
			this.solveMediumSteps();
			this.solveHardSteps();
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
						System.out.print(possibleValues);
					System.out.print("     |      ");
				}
				System.out.println("");
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

