import java.util.Random;


public class Removal {
	
	public Removal(int difficulty, BoardGen b){
		this.difficulty = difficulty;
		this.b = b;
		game = b.getBoardValues();
		visibility = new boolean[9][9];
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				visibility[i][j] = true;
			}
		}
	}
	
	public boolean[][] remove(){
		simpleRemove();
		//isSudokuSolutionUnique(game);
		if (difficulty > 1){
			//complexRemove();
		}
		return visibility;
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
			z = b.getValue(x, y);
			can = true;
			if(visibility[x][y] == false){
				continue;
			}
			//TODO the randomness and trial and error nature of this seems inefficient
			if (count > 1000){
				break;
			}
			for(int i = 1; i < 10; i++){
				if(i != z){
					if (!(b.row[x].has(i) || b.collumn[y].has(i) || b.square[b.squareNo(x,y)].has(i))){
						can = false;
						count++;
						break;
					}
				}
			}
			if(can){
				visibility[x][y] = false;
				game[x][y] = MISSING;
				b.row[x].remove(y);
				toRemove--;
			}
		}
	}
	
	/*
	private void complexRemove(){
		int complexity = difficulty*DIFF_CALIBRATION;
		int i = 0;
		int x, y, z;
		int diff;
		Random r = new Random();
		while (i < complexity){
			x = r.nextInt();
			y = r.nextInt();
			z = game[x][y];
			game[x][y] = MISSING;
			diff = isSudokuSolutionUnique(game);
			if(diff < difficulty){
				visibility[x][y] = false;
				i += diff;
			} else {
				game[x][y] = z;
			}
		}
	}*/
	
	private boolean[][] visibility;
	private int[][] game;
	private BoardGen b;
	private int difficulty;
	private static final int MISSING = -1;
	private static final int DIFF_CALIBRATION = 10;
}
