/**
 * Removes values if all other numbers are somewhere in the same row, column or square
 * @author laura
 *
 */
public class SimpleRemover implements Removalist {

	/** 
	 * Constructor for SimpleRemoved
	 * @param b Board to remove from
	 */
	public SimpleRemover (Board b){
		this.b = b;
		if (b.isDifficultyEasy()){
			maxRemoved = REMOVE_ON_EASY;
		} else if (b.isDifficultyMedium()){
			maxRemoved = REMOVE_ON_MEDIUM;
		} else if (b.isDifficultyHard()){
			maxRemoved = REMOVE_ON_HARD;
		}
		numRemoved = 0;
	}
	
	@Override
	/**
	 * Removes if all other values are somewhere in the row, column or square
	 */
	public boolean removeIfCan(Position p) {
		int row = p.getRow();
		int col = p.getCol();
		for(int i = 1; i < 10; i++){
			if (!(b.rowHas(row+1, i) || b.columnHas(col+1, i) || b.squareHas(squareNo(row,col), i))){
				return false;
			}	
		}
		b.setIfInitiallySet(row+1, col+1, false);
		numRemoved++;
		return true;
	}

	@Override
	/** 
	 * Terminates once a certain number, based on difficulty, have been removed
	 */
	public boolean shouldTerminate() {
		if (numRemoved >= maxRemoved){
			return true;
		}
		return false;
	}
	
	private int squareNo(int row, int collumn){
		return ((int)Math.floor(collumn/3) + (int)Math.floor(row/3)*3);
	}
	
	private int maxRemoved;
	private int numRemoved;
	private Board b;
	
	private static final int REMOVE_ON_EASY = 30;
	private static final int REMOVE_ON_MEDIUM = 20;
	private static final int REMOVE_ON_HARD = 10;
}