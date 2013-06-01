
public interface BoardGenerator {
	
	/**
	 * Generates a board by filling in the values, and then removing some based on the board difficulty
	 * @param board Board to generate
	 */
	public void generateBoard(Board board);
	
}
