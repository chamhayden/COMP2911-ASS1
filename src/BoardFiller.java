

import java.util.LinkedList;
import java.util.Random;

/**
 * Generates a board
 * Randomly assigns values, checks they work, reassigns if required
 * Not very tidy, I'd rather not use a do while loop and so many break and continue statements
 * Ill neaten it up later
 * Yes I am aware that I have misspelt column
 * @author laura
 *
 */

public class BoardFiller {
	
	public BoardFiller(Board b) 
	{
		this.b = b;
	}
	
	public boolean fillBoard(){
		int i, j, k;
		int x = 0;
		Random r = new Random();  
		LinkedList<Integer> l = new LinkedList<Integer>();
		int resetPoint;
		int counter = 0;
		
		
		for(i = 1; i < 10; i++){
			counter = 0;
			j = 1;
			while(j < 10){				
				k = squareNo(i, j);
				// TODO THIS IS A NICE PLACE TO WATCH FROM IF YOU WANT TO =)
				System.out.printf("\n\n\n\n");
				printBoard();
				System.out.printf("\n\n\n");
				//remove to here
				for(int m = 1; m < 10; m++){
					l.add(m);
				}
				do{
					x = l.remove(r.nextInt(l.size()));
				} while(!l.isEmpty() && (b.rowHas(i, x) || b.columnHas(j, x) || b.squareHas(k, x)));
				
				if(l.isEmpty()){
					if(i%3 == 2){
						resetPoint = ((int)Math.floor(j/3)*3);
					} else {
						resetPoint = 0;
					}
					for(int p = resetPoint; p < 9; p++){
						b.removeCellValue(i, p);
					}
					j = resetPoint;
					counter++;
					if (counter > 50){
						b.reset();
						return false;
					}
					continue;
				}
				b.setCellValue(i, j, x);
				j++;
			}
		}
		printBoard();
		return true;
	}
	
	public int squarePos(int row, int collumn){
		row--;
		collumn--;
		return (collumn % 3) + 3*((row % 3));
	}
	
	public int squareNo(int row, int collumn){
		row--;
		collumn--;
		return ((int)Math.floor(collumn/3) + (int)Math.floor(row/3)*3);
	}
	
	//FOR DEBUGGING PURPOSES ONLY DO NOT USE
	//TODO remove after debugging is finished
	public void printBoard(){
		int i,j;
		for(i = 1; i < 10; i++){
			for(j = 1; j < 10; j++){
				System.out.println("checking if " + i + " " + j + " is initially visible");
				if(b.isInitiallyVisibleCell(i, j)){
					System.out.printf("%d" , b.getCellValue(i, j));
				} else {
					System.out.printf("%d", 0);
				}
			}
			System.out.printf("\n");
		}
	}
	
	
	private Board b;
}
