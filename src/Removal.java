import java.util.ArrayList;
import java.util.Random;


public class Removal {
	
	public Removal(Board b){
		this.b = b;
		if(b.getDifficulty() == b.difficultyValueEasy()){
			difficulty = EASY;
		} else if (b.getDifficulty() == b.difficultyValueMedium()){
			difficulty = MEDIUM;
		} else {
			difficulty = HARD;
		}
		checker = new SudokuSolver();
		removable = new ArrayList<Point>();
		initialise();
	}
	
	public void remove(){
		simpleRemove();
		//checker.isSudokuSolutionUnique(b);
		if (difficulty > 1){
			harderRemove();
			//complexRemove();
		}
	}
	
	private void simpleRemove(){
		Random r = new Random();
		int toRemove;
		int index;
		int row, col;
		Point p;
		int z;
		Point removed;
		int count = 0;
		boolean can;
		if(difficulty <= 1){
			toRemove = 20;
		} else if (difficulty <= 2){
			toRemove = 30;
		} else {
			toRemove = 80;
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
				System.out.println("");
				System.out.println(row + " " + col);
				printBoard();
				System.out.println("");
			} else {
				putBack(removed);
			}
		}
	}
	
	private void harderRemove(){
		
	}
	
	private void complexRemove(){
		int complexity = difficulty*DIFF_CALIBRATION;
		int i = 0;
		Point removed;
		int diff;
		Random r = new Random();
		while (i < complexity){
			removed = removeRandom();
			//diff = checker.isSudokuSolutionUnique(b);
			//if(diff < difficulty){
			if(checker.isSudokuSolutionUnique(b)){
				i += 1;//diff;
			} else {
				putBack(removed);
			}
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
	SudokuSolver checker;
	ArrayList<Point> removable;
	private static final int EASY = 1;
	private static final int MEDIUM = 2;
	private static final int HARD = 3;
	private static final int MISSING = -1;
	private static final int DIFF_CALIBRATION = 10;
}
