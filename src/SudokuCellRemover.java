import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Comparator;

/**
* This class takes a Board object and removes values from the filled board
*  
* @author Hayden Smith, Laura Hodges, Jerome Robins, Steven Falconieri
* 
*/

public class SudokuCellRemover
{
	
	/**
	 * Constructor for the Removal object
	 * @param b board to remove values from
	 */
	public SudokuCellRemover(Board b)
	{
		this.board = b;
		removableCells = new ArrayList<Position>();
		initialise();
	}
	
	/**
	 * Removes values from the board to produce a puzzle
	 * Iterates through each of the unremoved squares remaining on the board
	 * in random order, removing them if they meet the remover condition
	 * and stopping once the termination condition of the remover is met
	 * @param remover Removalist to be used
	 */
	public void removeValues(Removalist remover)
	{
		int numRemoved = 0;
		int pendingRemoval;
		int size = removableCells.size();
		Random r = new Random();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		PriorityQueue<Integer> indicesRemoved = new PriorityQueue<Integer>(10, new Comparator<Integer>(){

			@Override
			public int compare(Integer o1, Integer o2)
			{
				if (o1 > o2)
				{
					return -1;
				}
				else if (o1 == o2)
				{
					return 0;
				}
				else
				{
					return 1;
				}
			}			
		});

		for (int i = 0; i < size; i++)
		{
			indices.add(i);
		}
		while (!indices.isEmpty() && !remover.shouldTerminate())
		{
			pendingRemoval = indices.remove(r.nextInt(indices.size()));
			if (remover.removeIfCan(removableCells.get(pendingRemoval)))
			{
				indicesRemoved.add(pendingRemoval);
			}
		}
		while (!indicesRemoved.isEmpty())
		{
			int take = indicesRemoved.poll();
			removableCells.remove(take);
			numRemoved++;
		}
	}
	
	/**
	 * Initialises the removableCells ArrayList
	 */
	private void initialise()
	{
		int row, col;
		for(row = 0; row < board.getBoardSize(); row++)
		{
			for(col = 0; col < board.getBoardSize(); col++)
			{
				removableCells.add(new CellLocation(row, col));
			}
		}
	}
	
	private Board board;
	private ArrayList<Position> removableCells;

}
