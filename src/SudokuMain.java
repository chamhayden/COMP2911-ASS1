public class SudokuMain {

	public static void main (String[] args)
	{
		System.out.println("Starting game");
		SudokuManager sudoku = new SudokuManager();
	}
}

// Is it a good idea to NOT merge this with SudokuManager, in case people want multiple instances of Sudoku to run on a single process?