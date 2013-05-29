import java.util.ArrayList;
import java.util.Random;

public class RandCell {
	public RandCell(ArrayList<Point> removable, Board b){
		cells = removable;
		indices = new ArrayList<Integer>();
		this.b = b;
		refresh();
		
	}
	
	public Point randRemove(){
		System.out.println("");
		System.out.println("called");
		Random r = new Random();
		Point p;
		int x,y;
		int toRemove = indices.remove(r.nextInt(indices.size()));
		System.out.println("Trying to remove " + toRemove);
		p = cells.get(toRemove);
		x = p.getRow();
		y = p.getCol();
		b.setCellVisibility(x+1, y+1, false);
		return p;
	}
	
	public boolean isEmpty(){
		return indices.isEmpty();
	}
	
	public void refresh(){
		int size = cells.size();

		for(int i = 0; i < size; i++){
			indices.add(i);
		}
	}
	
	private ArrayList<Point> cells;
	private ArrayList<Integer> indices;
	private Board b;
}
