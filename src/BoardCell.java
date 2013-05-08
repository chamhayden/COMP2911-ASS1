/**
 * Class to store and access values associated with an individual
 *  cell in the grid
 * @author Hayden, Laura, Jerome, Steven
 * @version 0.1
 * 
 */
public class BoardCell extends SimpleCell {

	public BoardCell(int actualFinal, boolean visible, int boardSize)
	{
		super(actualFinal);
		
		this.visible = visible;
		this.tempNumbers = new boolean[boardSize];
		this.valueInput = -1;
		
		for (int i = 0; i < boardSize; i++)
		{
			tempNumbers[i] = false;
		}
	}
	
	public void removeInput()
	{
		this.valueInput = -1;
	}

	public int getValue()
	{
		if (this.valueInput == -1)
		{
			return valueMain;
		}
		else
		{
			return valueInput;
		}
	}
	
	public void setInput(int number)
	{
		this.valueInput = number;
	}
	
	public boolean issetTemp(int number)
	{
		return tempNumbers[number - 1];
	}


	public void setTemp(int number, boolean isSet)
	{
		tempNumbers[number - 1] = isSet;
	}
	
	public boolean isCorrect()
	{
		if (valueInput == valueMain)
		{
			return true;
		}
		return false;
	}
	
	protected int valueInput;
	protected boolean tempNumbers[];
	
}
