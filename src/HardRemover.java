
public class HardRemover implements Removalist {
	
	public HardRemover (Board b){
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
	public boolean removed(Point p) {
		int row = p.getRow();
		int col = p.getCol();
		int removedVal = b.getCellValue(row+1, col+1);
		int[] adjRows = findAdj(row);
		int[] adjCols = findAdj(col);
		boolean inAdjRows, inAdjCols, cellAdjRowsFull, cellAdjColsFull;
		
		inAdjRows = (b.rowHas(adjRows[0]+1, removedVal) && b.rowHas(adjRows[1]+1, removedVal));
		inAdjCols = (b.columnHas(adjCols[0]+1, removedVal) && b.columnHas(adjCols[1]+1, removedVal));
		cellAdjRowsFull = adjFull(adjRows, adjCols, col);
		cellAdjColsFull = adjFull(adjCols, adjRows, row);
		
		if((inAdjRows && inAdjCols) || (inAdjRows && cellAdjColsFull) || (inAdjCols && cellAdjRowsFull)){
			b.setIfInitiallySet(row+1, col+1, false);
			numRemoved++;
			return true;
		} else {
			return false;
		}
	}
	
	
	@Override
	public boolean shouldTerminate() {
		if (numRemoved >= maxRemoved){
			return true;
		}
		return false;
	}
	
	private int[] findAdj(int position){
		int[] adj = new int[2];
		int x = ((int)Math.floor(position/3)*3);
		int y = position%3;
		if(y == 0){
			adj[0] = x+1;
			adj[1] = x+2;
		} else if (y == 1){
			adj[0] = x;
			adj[1] = x + 2;
		} else {
			adj[0] = x;
			adj[1] = x + 1;
		}
		return adj;
	}
	
	private boolean adjFull(int[] dir1, int[] direction2, int position){
		int[] dir2 = new int[] {direction2[0], direction2[1], position};
		for(int i = 0; i < 2; i++){
			for (int j = 0; j < 3; j++){
				if(!b.isInitiallySet(dir1[i]+1, dir2[j]+1)){
					return false;
				}
			}
		}
		
		return true;
	}

	Board b;
	private int maxRemoved;
	private int numRemoved;


	private static final int REMOVE_ON_EASY = 10;
	private static final int REMOVE_ON_MEDIUM = 50;
	private static final int REMOVE_ON_HARD = 20;

}
