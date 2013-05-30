import java.util.ArrayList;
import java.util.Random;


public class Removal {
	
	public Removal(Board b){
		this.b = b;
		if(b.isDifficultyEasy()){
			difficulty = EASY;
		} else if (b.isDifficultyMedium()){
			difficulty = MEDIUM;
		} else {
			difficulty = HARD;
		}
		checker = new SudokuSolver();
		removable = new ArrayList<Point>();
		initialise();
		selector = new CellSelector(removable, this.b);

	}
	
	public void remove(){
		simpleRemove();
		
		if (difficulty > 1){
			harderRemove();
			if (difficulty > 2){
				complexRemove();
			}
		}
	}
	
	private void simpleRemove(){
		Random r = new Random();
		int toRemove;
		int hasRemoved = 0;
		int row, col;
		int z;
		Point removed;
		int count = 0;
		boolean can;
		if(difficulty <= 1){
			toRemove = 40;
		} else if (difficulty <= 2){
			toRemove = 25;
		} else {
			toRemove = 20;
		}
		while(toRemove > 0 && count < 1000){
			removed = removeRandom();
			row = removed.getRow();
			col = removed.getCol();
			z = b.getCellValue(row+1, col+1);
			can = true;

			for(int i = 1; i < 10; i++){
				if(i != z){
					if (!(b.rowHas(row+1, i) || b.columnHas(col+1, i) || b.squareHas(squareNo(row,col), i))){
						can = false;
						count++;
						break;
					}
				}
			}
			if(can){
				toRemove--;
				hasRemoved++;
				//System.out.println("");
				//System.out.println(row + " " + col);
				//printBoard();
				//System.out.println("");
			} else {
				putBack(removed);
			}
		}
		System.out.println("Simple remove got " + hasRemoved + " values");
	}
	
	private void harderRemove(){
		
		assert(findAdj(0)[0] == 1);
		assert(findAdj(0)[1] == 2);
		assert(findAdj(1)[0] == 0);
		assert(findAdj(1)[1] == 2);
		assert(findAdj(2)[0] == 0);
		assert(findAdj(2)[1] == 1);
		assert(findAdj(3)[0] == 4);
		assert(findAdj(3)[1] == 5);
		assert(findAdj(4)[0] == 3);
		assert(findAdj(4)[1] == 5);
		assert(findAdj(5)[0] == 3);
		assert(findAdj(5)[1] == 4);
		assert(findAdj(6)[0] == 7);
		assert(findAdj(6)[1] == 8);
		assert(findAdj(7)[0] == 6);
		assert(findAdj(7)[1] == 8);
		assert(findAdj(8)[0] == 6);
		assert(findAdj(8)[1] == 7);
		//TODO test findAdjSquares
		
		
		int count = 0;
		int sucess = 0;
		while(count < 10000){
			Point removed = removeRandom();
			int row = removed.getRow();
			int col = removed.getCol();
			int removedVal = b.getCellValue(row+1, col+1);
			int[] adjRows = findAdj(row);
			int[] adjCols = findAdj(col);
			int[] adjSquares = findAdjSquares(row, col);
	
			
			boolean inAdjRows = (b.rowHas(adjRows[0]+1, removedVal) && b.rowHas(adjRows[1]+1, removedVal));
			boolean inAdjCols = (b.columnHas(adjCols[0]+1, removedVal) && b.columnHas(adjCols[1]+1, removedVal));
			boolean cellAdjRowsFull = adjFull(adjRows, adjCols, col);
			boolean cellAdjColsFull = adjFull(adjCols, adjRows, row);

			
			if(inAdjRows && inAdjCols){
				sucess++;
				System.out.println("");
				System.out.println("1");
				System.out.println(row + " " + col);
				printBoard();
				System.out.println("");
			} else if (inAdjRows && cellAdjColsFull){
				sucess++;
				System.out.println("");
				System.out.println("2");
				System.out.println(row + " " + col);
				printBoard();
				System.out.println("");
			} else if (inAdjCols && cellAdjRowsFull){
				sucess++;
				System.out.println("");
				System.out.println("3");
				System.out.println(row + " " + col);
				printBoard();
				System.out.println("");
			} else {
				putBack(removed);
			}
			count++;
		}
		System.out.println("Harder Remove got " + sucess + " values");
		
	}
	
	private boolean adjFull(int[] dir1, int[] direction2, int position){
		int[] dir2 = new int[] {direction2[0], direction2[1], position};
		for(int i = 0; i < 2; i++){
			for (int j = 0; j < 3; j++){
				if(!b.isInitiallyVisibleCell(dir1[i]+1, dir2[j]+1)){
					return false;
				}
			}
		}
		
		return true;
	}
	
	private void complexRemove(){
		int complexity = difficulty*DIFF_CALIBRATION;
		int i = 0;
		Point removed;
		int sizeIs = removable.size();
		int sizeWas = sizeIs+1;
		selector = new CellSelector(removable, b);
		
		while (i < complexity && sizeIs != sizeWas){
			while(!selector.cycleDone()){
				removed = selector.randChoose();
				if(checker.isSudokuSolutionUnique(b)){
					i += 1;
					selector.confirmRemoval();
				} else {
					putBack(removed);
				}
			}
			sizeWas = sizeIs;
			sizeIs = removable.size();
			selector.refresh();
		}
	}
	
	private Point removeRandom(){
		Random r = new Random();
		Point p;
		int x,y;
		r.nextInt(removable.size());
		p = removable.remove(r.nextInt(removable.size()));
		x = p.getRow();
		y = p.getCol();
		b.setCellVisibility(x+1, y+1, false);
		return p;
	}
	
	private void putBack(Point removed){
		int row = removed.getRow();
		int col = removed.getCol();
		removable.add(new Point(row, col));
		b.setCellVisibility(row+1, col+1, true);
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
	
	private int[] findAdjSquares(int row, int col){
		int[] adj = new int[4];
		int x = (int)Math.floor(row/3);
		int y = (int)Math.floor(col/3);
		int i, j, k;
		k = 0;
		for(i = 0; i < 3; i++){
			if (i == x){
				continue;
			}
			for(j = 0; j < 3; j++){
				if (j == y){
					continue;
				}
				adj[k] = i*3+j;
				k++;
			}
		}
		
		return adj;
	}
	
	private void initialise(){
		int row, col;
		for(row = 0; row < 9; row++){
			for(col = 0; col < 9; col++){
				removable.add(new Point(row, col));
			}
		}
	}
	
	public int squareNo(int row, int collumn){
		return ((int)Math.floor(collumn/3) + (int)Math.floor(row/3)*3);
	}
	
	//FOR DEBUGGING PURPOSES ONLY DO NOT USE
	//TODO remove after debugging is finished
	public void printBoard(){
		int i,j;
		for(i = 0; i < 9; i++){
			for(j = 0; j < 9; j++){
				//System.out.println("checking if " + i + " " + j + " is initially visible");
				if(b.isInitiallyVisibleCell(i+1, j+1)){
					System.out.printf("%d" , b.getCellValue(i+1, j+1));
				} else {
					System.out.printf("%d", 0);
				}
			}
			System.out.printf("\n");
		}
	}
	
	private Board b;
	private int difficulty;
	private SudokuSolver checker;
	private ArrayList<Point> removable;
	private CellSelector selector;

	private static final int EASY = 1;
	private static final int MEDIUM = 2;
	private static final int HARD = 3;
	private static final int MISSING = -1;
	private static final int DIFF_CALIBRATION = 10;
}
