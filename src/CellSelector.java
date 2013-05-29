import java.util.ArrayList;
import java.util.Random;

public class CellSelector {
	public CellSelector(ArrayList<Point> removable, Board b){
		cells = removable;
		indices = new ArrayList<Integer>();
		this.b = b;
		removed = new ArrayList<Integer>();
		refresh();
		
	}
	
	public Point randChoose(){
		//System.out.println("");
		//System.out.println("called");
		Random r = new Random();
		Point p;
		int x,y;
		pendingRemoval = indices.remove(r.nextInt(indices.size()));
		//System.out.println("Trying to remove " + toRemove);
		p = cells.get(pendingRemoval);
		x = p.getRow();
		y = p.getCol();
		b.setCellVisibility(x+1, y+1, false);
		return p;
	}
	
	public void confirmRemoval(){
		removed.add(pendingRemoval);
	}
	
	public boolean cycleDone(){
		return indices.isEmpty();
	}
	
	public void refresh(){
		
		for(int i : removed){
			cells.remove(i);
		}
		
		int size = cells.size();

		for(int i = 0; i < size; i++){
			indices.add(i);
		}
	}
	
	private ArrayList<Point> cells;
	private ArrayList<Integer> indices;
	private Board b;
	private int pendingRemoval;
	private ArrayList<Integer> removed;
}
