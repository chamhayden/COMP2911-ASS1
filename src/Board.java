/**
* Class that allows creation, modification and access to
*  a board
* @author Hayden, Laura, Jerome, Steven
* @version 0.1
* 
*/
public interface Board {
	public int getCellActualFinal(int row, int col);
	public int getCellInputFinal(int row, int col);
	public boolean isPresetCell(int row, int col);
	public boolean issetCellInputTemp(int row, int col, int number);
	public void setCellInputFinal(int row, int col, int number);
	public void setCellInputTemp(int row, int col, int number, boolean isSet);
	public boolean isCorrectCell(int row, int col);
}