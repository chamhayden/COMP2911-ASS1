
public class ExhaustiveRemover implements Removalist {
	
	public ExhaustiveRemover (Board b){
		this.b = b;
		checker = new SudokuSolver();
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
		b.setIfInitiallySet(row+1, col+1, false);
		if(checker.isSudokuSolutionUnique(b)){
			return true;
		} else {
			b.setIfInitiallySet(row+1, col+1, true);
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
	
	Board b;
	SudokuSolver checker;
	int maxRemoved;
	int numRemoved;

	
	private static final int REMOVE_ON_EASY = 0;
	private static final int REMOVE_ON_MEDIUM = 30;
	private static final int REMOVE_ON_HARD = 100;


}
