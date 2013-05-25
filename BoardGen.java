

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

public class BoardGen {
	
	public BoardGen(SudokuBoard b) 
	{
		
		
		boolean filled = false;
		int size = b.getBoardSize();
		row = new SectionGen[size];
		collumn = new SectionGen[size];
		square = new SectionGen[size];
		initialiseBoard(b);
		int i = 2;
		int j = 3;
		assert(collumn[i].getCell(j) == row[j].getCell(i));
		while(!filled){
			filled = fillBoard(b);	
		}
		assert(collumn[i].getCell(j) == row[j].getCell(i));
		assert(collumn[i].getCell(j) == row[j].getCell(i));
	}
	
	@Override
	public Object clone() {
		try{
			return super.clone();
		}
		catch(Exception e){ 
			return null; 
		}
	}
	
	private boolean fillBoard(SudokuBoard b){
		int i, j, k;
		int x = 0;
		Random r = new Random();  
		LinkedList<Integer> l = new LinkedList<Integer>();
		int resetPoint;
		int counter = 0;
		
		
		for(i = 0; i < 9; i++){
			counter = 0;
			j = 0;
			while(j < 9){				
				k = squareNo(i, j);
				//THIS IS A NICE PLACE TO WATCH FROM IF YOU WANT TO =)
				//System.out.printf("\n\n\n\n");
				//printBoard();
				//System.out.printf("\n\n\n");
				for(int m = 1; m < 10; m++){
					l.add(m);
				}
				do{
					x = l.remove(r.nextInt(l.size()));
				} while(!l.isEmpty() && (row[i].has(x) || collumn[j].has(x) || square[k].has(x)));
				
				if(l.isEmpty()){
					if(i%3 == 2){
						resetPoint = ((int)Math.floor(j/3)*3);
					} else {
						resetPoint = 0;
					}
					for(int p = resetPoint; p < 9; p++){
						row[i].setValue(p, 0);
					}
					j = resetPoint;
					counter++;
					if (counter > 50){
						b.reset();
						initialiseBoard(b);
						return false;
					}
					continue;
				}
				
				row[i].setValue(j, x);
				collumn[j].setValue(i, x);
				square[k].setValue(squarePos(i, j), x);
				j++;
			}
		}
		return true;
	}
	
	private void initialiseBoard(SudokuBoard b){
		int i,j;
		for(i = 0; i < 9; i++){
			row[i] = new SectionGen();
			collumn[i] = new SectionGen();
			square[i] = new SectionGen();
			for(j = 0; j < 9; j ++){
				row[i].setCell(j, b.getCell(i, j));
			}
		}
		for(i = 0; i < 9; i++){
			for(j = 0; j < 9; j++){
				collumn[i].setCell(j, row[j].getCell(i));
			}
		}
		for(i = 0; i < 9; i++){
			for(j = 0; j < 9; j++){
				square[squareNo(i,j)].setCell(squarePos(i,j), row[i].getCell(j));
			}
		}
		
	}
	
	public int squarePos(int row, int collumn){
		return (collumn % 3) + 3*((row % 3));
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
				if(row[i].displays(j)){
					System.out.printf("%d" ,row[i].get(j));
				} else {
					System.out.printf("%d", 0);
				}
			}
			System.out.printf("\n");
		}
	}
	
	
	SectionGen[] row;
	SectionGen[] collumn;
	SectionGen[] square;
}
