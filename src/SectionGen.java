/**
 * Keeps track of groups of Cells, e.g rows columns and squares
 * Allows you to manipulate Cells as a group
 * @author laura
 *
 */

public class SectionGen {
	
	//TODO Deprecated, remove after testing
	public SectionGen(int x){
		cells = new StandardCell[9]; 
		int i; 
		for(i = 0; i < 9; i++){
			cells[i] = new StandardCell(0);
		}
	}
	
	public SectionGen(){
		cells = new StandardCell[9];
	}
	
	public boolean has(int x){
		int i;
		for(i = 0; i < 9; i++){
			if (cells[i].getValue() == x && cells[i].isVisible()){
				return true;
			}
		}
		return false;
	}	
	
	public void cellInit(int position, int x){
		if (cells[position] != null){
			System.out.println("ERROR you are re-initialising an already initialied cell");
		}
		cells[position] = new StandardCell(x);
	}
	
	//TODO having both this and the above is a source of confusion
	public void setValue(int position, int x){
		cells[position].setValue(x);
	}
	
	public int get(int position){
		return cells[position].getValue();
	}
	
	public int[] getValues(){
		int[] values = new int[9];
		int i = 0;
		for(StandardCell c : cells){
			values[i] = c.getValue();
			i++;
		}
		return values;
	}
	
	public void swap(SectionGen other){
		int[] temp = this.getValues();
		this.setValues(other.getValues());
		other.setValues(temp);
		
	}
	
	private void setValues(int [] values){
		for(int i = 0; i < 9; i++){
			this.cells[i].setValue(values[i]);
		}
	}
	
	public StandardCell getCell(int position){
		return cells[position];
	}
	
	public void setCell(int position, StandardCell c){
		cells[position] = c;
	}
	
	public boolean displays(int position){
		return cells[position].isVisible();
	}
	
	public void remove(int position){
		cells[position].setVisible(false);
	}

	public void putBack(int position){
		cells[position].setVisible(true);
	}
	
	/* TODO make private */ public StandardCell[] cells;
	
}
