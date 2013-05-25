/**
 * Keeps track of groups of Cells, e.g rows columns and squares
 * Allows you to manipulate Cells as a group
 * @author laura
 *
 */

public class SectionGen {
	
	//TODO Deprecated, remove after testing
	public SectionGen(int x){
		cells = new BoardCell[9]; 
		int i; 
		for(i = 0; i < 9; i++){
			cells[i] = new BoardCell(0);
		}
	}
	
	public SectionGen(){
		cells = new BoardCell[9];
	}
	
	public boolean has(int x){
		int i;
		for(i = 0; i < 9; i++){
			if (cells[i].getFinalValue() == x && cells[i].isCurrentlyVisible()){
				return true;
			}
		}
		return false;
	}	
	
	//TODO having both this and the above is a source of confusion
	public void setValue(int position, int x){
		cells[position].setFinalValue(x);
	}
	
	public int get(int position){
		return cells[position].getFinalValue();
	}
	
	public int[] getValues(){
		int[] values = new int[9];
		int i = 0;
		for(BoardCell c : cells){
			values[i] = c.getFinalValue();
			i++;
		}
		return values;
	}
	
	/*
	private void setValues(int [] values){
		for(int i = 0; i < 9; i++){
			this.cells[i].setValue(values[i]);
		}
	}
	*/
	
	public BoardCell getCell(int position){
		return cells[position];
	}
	
	public void setCell(int position, BoardCell c){
		cells[position] = c;
	}
	
	public boolean displays(int position){
		return cells[position].isCurrentlyVisible();
	}
	
	public void remove(int position){
		cells[position].setCurrentlyVisible(false);
	}

	public void putBack(int position){
		cells[position].setCurrentlyVisible(true);
	}
	
	private BoardCell[] cells;
	
}
