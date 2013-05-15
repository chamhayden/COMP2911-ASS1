
public class BoardGenerator {
	
	public BoardGenerator(int difficulty, int boardSize){
		BoardGen b = new BoardGen(difficulty, boardSize);
		Removal r = new Removal(difficulty, b);
		finalValues = b.constructBoard();
		visibilities = r.remove();
		b.printBoard();
	}
	
	public boolean getVisibility(int row, int collumn){
		return visibilities[row][collumn];
	}
	
	public int getValues(int row, int collumn){
		return finalValues[row][collumn];
	}
	
	private boolean[][] visibilities;
	private int[][] finalValues;
}
