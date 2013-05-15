import java.util.Random;


public class Removal {
	
	public Removal(int difficulty, BoardGen b){
		this.difficulty = difficulty;
		this.b = b;
		visibility = new boolean[9][9];
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				visibility[i][j] = true;
			}
		}
	}
	
	public boolean[][] remove(){
		simpleRemove();
		return visibility;
	}
	
	private void simpleRemove(){
		Random r = new Random();
		int x,i;
		if(difficulty > 4){
			difficulty = 4;
		}
		for(i = 0; i < difficulty*2; i++){
			x = r.nextInt(9);
			b.row[i].remove(x);
			visibility[i][x] = false;
		}
	}
			
	private boolean[][] visibility;
	private BoardGen b;
	private int difficulty;
}
