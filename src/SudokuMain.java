/*
 * Interactive Sudoku Solver
 * You are probably familiar with the puzzle of Sudoku. Basically, the puzzle uses a 9×9 grid divided into 9 rows, 9 columns and 9 squares of size 3×3. Given a valid initial position, the problem is to fill in all the grid elements with a number from 1 to 9 such that each row, each column and each square contains all and only the numbers from 1 to 9, so that no number appears more than once in any row, column or square.
 */
public class SudokuMain {

	public static void main (String[] args)
	{
		//System.out.println("Starting game");
		SudokuManager sudoku = new SudokuManager();
	}
}

// Is it a good idea to NOT merge this with SudokuManager, in case people want multiple instances of Sudoku to run on a single process?

/* Only 1 person per team
give cs2911 ass3 ass3.zip
o	All your .java source files
o	The doc directory containing all the Javadoc
o	A .pdf – team design documents (UML class / state / sequence)
2911 classrun -check ass3
*/