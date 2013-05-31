import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Comparator;
/**
 * This class is used to remove values from the filled in board
 * @author laura
 *
 */

public class Removal {
	
	/**
	 * Constructor for the Removal object
	 * @param b board to remove values from
	 */
	public Removal(Board b){
		this.b = b;
		removable = new ArrayList<Position>();
		initialise();
	}
	/**
	 * Removes values from the board to produce a puzzle
	 * Iterates through each of the unremoved squares remaining on the board
	 * in random order, removing them if they meet the remover condition
	 * and stopping once the termination condition of the remover is met
	 * @param remover
	 */
	public void removeValues(Removalist remover){
		
		//TODO get rid of numRemoved after debugging
		int numRemoved = 0;
		int pendingRemoval;
		int size = removable.size();
		Random r = new Random();
		ArrayList<Integer> indicesOfRemovable = new ArrayList<Integer>();
		PriorityQueue<Integer> removed = new PriorityQueue<Integer>(10, new Comparator<Integer>(){

			@Override
			public int compare(Integer o1, Integer o2) {
				if (o1 > o2){
					return -1;
				} else if (o1 == o2){
					return 0;
				} else {
					return 1;
				}
			}
			
		});

		for(int i = 0; i < size; i++){
			indicesOfRemovable.add(i);
		}
		while(!indicesOfRemovable.isEmpty() && !remover.shouldTerminate()){
			pendingRemoval = indicesOfRemovable.remove(r.nextInt(indicesOfRemovable.size()));
			if(remover.removeIfCan(removable.get(pendingRemoval))){
				removed.add(pendingRemoval);
				numRemoved++;
			}
		}
		while(!removed.isEmpty()){
			removable.remove(removed.poll());
		}
		System.out.println("Removed " + numRemoved);
	}
	
	/**
	 * Initialises the removable ArrayList
	 */
	private void initialise(){
		int row, col;
		for(row = 0; row < 9; row++){
			for(col = 0; col < 9; col++){
				removable.add(new Position(row, col));
			}
		}
	}
	
	//FOR DEBUGGING PURPOSES ONLY DO NOT USE
	//TODO remove after debugging is finished
	public void printBoard(){
		int i,j;
		for(i = 0; i < 9; i++){
			for(j = 0; j < 9; j++){
				//System.out.println("checking if " + i + " " + j + " is initially visible");
				if(b.isInitiallySet(i+1, j+1)){
					System.out.printf("%d" , b.getCellValue(i+1, j+1));
				} else {
					System.out.printf("%d", 0);
				}
			}
			System.out.printf("\n");
		}
	}
	
	private Board b;
	private ArrayList<Position> removable;
	private int difficulty;

	private static final int EASY = 1;
	private static final int MEDIUM = 2;
	private static final int HARD = 3;
}
