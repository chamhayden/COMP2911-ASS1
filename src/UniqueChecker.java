/**
* This interface is given a game space, and checks if
*  the board has a single unique solution
*  
* @author Hayden Smith, Laura Hodges, Jerome Robins, Steven Falconieri
* 
*/
public interface UniqueChecker<E> {
	
	/**
	 * For a given board, check if the game space has a unique solution
	 * @param gameSpace game space concerned with
	 * @return Whether the game space has a unique solution
	 */
	public boolean isUniqueSolution(E gameSpace);
}
