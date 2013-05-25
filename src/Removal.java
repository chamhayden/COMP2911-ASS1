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
	}
	
	public void remove(){
		simpleRemove();
		checker.isSudokuSolutionUnique(b);
		if (difficulty > 1){
			complexRemove();
		}
	}
	
	private void simpleRemove(){
		Random r = new Random();
		int toRemove;
		int x, y;
		int z;
		int count = 0;
		boolean can;
		if(difficulty <= 1){
			toRemove = 20;
		} else if (difficulty <= 2){
			toRemove = 30;
		} else {
			toRemove = 80;
		}
		while(toRemove > 0){
			x = r.nextInt(9);
			y = r.nextInt(9);
			z = b.getCellValue(x, y);
			can = true;
			if(b.isInitiallyVisibleCell(x, y) == false){
				continue;
			}
			//TODO the randomness and trial and error nature of this seems inefficient
			if (count > 1000){
				break;
			}
			for(int i = 1; i < 10; i++){
				if(i != z){
					if (!(b.rowHas(x, i) || b.columnHas(y, i) || b.squareHas(x,y))){
						can = false;
						count++;
						break;
					}
				}
			}
			if(can){
				b.setCellVisibility(x, y, false);
				toRemove--;
			}
		}
	}
	
	private void complexRemove(){
		int complexity = difficulty*DIFF_CALIBRATION;
		int i = 0;
		int x, y, z;
		int diff;
		Random r = new Random();
		while (i < complexity){
			x = r.nextInt();
			y = r.nextInt();
			b.setCellVisibility(x, y, false);
			//diff = checker.isSudokuSolutionUnique(b);
			//if(diff < difficulty){
			if(checker.isSudokuSolutionUnique(b)){
				i += 1;//diff;
			} else {
				b.setCellVisibility(x, y, false);
			}
		}
	}
	
	private Board b;
	private int difficulty;
	SudokuSolver checker;
	private static final int EASY = 1;
	private static final int MEDIUM = 2;
	private static final int HARD = 3;
	private static final int MISSING = -1;
	private static final int DIFF_CALIBRATION = 10;
}
