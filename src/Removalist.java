/**
 * Interface to evaluate whether a point can be removed based on conditions
 * @author laura
 *
 */
public interface Removalist {
	/**
	 * Determines whether value can be removed from the board based on a specific condition
	 * @param p position to remove from
	 * @return whether the value was removed
	 */
	public boolean removeIfCan(Position p);
	
	/**
	 * Provides a condition upon which the removal should terminate
	 * @return whether the condition is met
	 */
	public boolean shouldTerminate();
	
}
