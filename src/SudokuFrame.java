import java.awt.*;
import java.awt.event.*;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TimerTask;
import javax.swing.*;

/**
 * 
 * @author Hayden, Laura, Jerome, Steven
 * some code copied from Core Java Fundamentals Book 1 Chapter 8, Event Handling p329/ Cay Hortsmann
 * @version 0.1
 */


/**
 * A frame with a panel button
 * @param currentValue is the value the ActionListener uses to change grid labels
 */

public class SudokuFrame extends JFrame
{
	public SudokuFrame(Board sBoard)
	{
		this.board = sBoard;
		
		setTitle("SUDOKU FUN!");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		currentValue = BLANK;
		draftMode = false;
		
		buttonRow = new ArrayList<JButton>();
		buttonInputs = new LinkedList<JButton>();
		
		buttonPanel = new JPanel();
		setUpGrid();
		
		commandPanel = new JPanel();
		commandPanel.setLayout(new FlowLayout());
		
		scorePanel = new JPanel();
		scorePanel.setLayout(new FlowLayout());
		scorePanel.setVisible(true);

		resetGame = makeCommandButton("Restart game", commandPanel, new resetFunction());
		newGame = makeCommandButton("NEW GAME", commandPanel, new newGamePop());
		draftButton = makeCommandButton("DRAFT", commandPanel, new draftFunction());
		eraseButton = makeCommandButton("Rubber", commandPanel, new eraseFunction());
		revealButton = makeCommandButton("Reveal ALL", commandPanel, new revealFunction());
		solutionButton = makeCommandButton("Check my solution!", scorePanel, new solutionFunction());
		
		//prepareForDifficulty();
		
		setUpButtonInputs();
		
		add(buttonPanel,BorderLayout.CENTER);
		add(commandPanel,BorderLayout.NORTH);
		add(scorePanel,BorderLayout.SOUTH);
		
	}
/*	
	private void prepareForDifficulty() {

		//if(board.difficultyValueEasy()){
			solutionButton.setVisible(false);
		}
		//if(board.difficultyValueMedium()){
			
		}else{
			solutionButton.setVisible(false);
		}
			
		
	}*/
	private void setUpButtonInputs(){
		for (int i = 0; i < LABELS.length(); i++)
	      {
	         final String label = LABELS.substring(i, i + 1);
	         JButton keyButton = new JButton(label);
	         keyButton.addActionListener(new NumberSelect());
	         keyButton.setBackground(DEFAULT_COMMAND);
	         buttonInputs.add(keyButton);
	         scorePanel.add(keyButton);
	      }
	}
	
	
	private void setUpGrid(){
		
		buttonPanel.removeAll();
		buttonPanel.setLayout(new GridLayout(9,9));
		buttonRow.removeAll(buttonRow);
		for(int i = 1; i<=9; i++)
		{
			for(int j = 1; j<=9; j++){
				String cellVal;
				boolean given = false;
				if (board.isCurrentlyVisibleCell(i,j))
				{
					cellVal = Integer.toString(board.getCellValue(i,j));
					given = true;
				}
				else
				{
					cellVal = BLANK;
				}
				buttonRow.add(makeGridButton(cellVal, currentValue, buttonPanel, given));
			}
		}
	}
	
	public JButton makeGridButton( String name, String currentValue, JPanel panel, boolean given)
	{
		JButton button = new JButton(name);
		panel.add(button);
		button.setBackground(DEFAULT_GRID);
		button.setFont(ARIALBOLD);
		if(!given){
			NumberInsert insert = new NumberInsert(button);
			button.addActionListener(insert);
			button.setForeground(userTempColor);
			//setting GridLayout on button for draft values
			button.setLayout(new GridLayout(3,3));
			for (int i = 0; i < LABELS.length(); i++)
			{
			    String label = LABELS.substring(i, i + 1);
			    JLabel l = new JLabel(label);
			    l.setVisible(false);
			    l.setFont(DRAFTSMALL);
				l.setForeground(draftColor);
				button.add(l);
			}
		}
		return button;
	}
	
	public JButton makeCommandButton(String name, JPanel panel, ActionListener action)
	{
		JButton button = new JButton(name);
		panel.add(button);
		button.addActionListener(action);
		return button;
	}
	
/**
 * An action listener that sets the
 */
	
	private class NumberInsert implements ActionListener
	{
		public NumberInsert(JButton button)
		{
			b = button;
		}
		
		public void actionPerformed(ActionEvent event)
		{
			if(isButtonToggled(eraseButton, DEFAULT_COMMAND)){
				toggleDraftFalse(b);
				b.setText(resetCurrentValue());
				board.setCellValue(rowVal(b), colVal(b), -1);
			}else if(!getCurrentValue().equalsIgnoreCase(BLANK)){
				if(!draftMode){
					b.setText(getCurrentValue());
					for(Component labels:b.getComponents()){
						labels.setVisible(false);
					}
					board.setCellValue(rowVal(b), colVal(b), Integer.parseInt(b.getText()));
					//if(board.difficultyValueEasy())
						checkSquare(b,2);
					} else{
						toggleDraftValues(getCurrentValue(), b);
					}
				}
		}
		private JButton b;
	}
	
	public void toggleDraftFalse(JButton button){
		for(Component label: button.getComponents()){
			label.setVisible(false);
			board.setCellDraftVisibility(rowVal(button), colVal(button), Integer.parseInt(((JLabel) label).getText()), false);
		}
	}
	
	private void toggleDraftValues(String value, JButton b){
		Component label = b.getComponent(Integer.parseInt(value)-1);
		if(label.isVisible()){
			label.setVisible(false);
			board.setCellDraftVisibility(rowVal(b), colVal(b), Integer.parseInt(value), false);
		} else{
			label.setVisible(true);
			board.setCellDraftVisibility(rowVal(b), colVal(b), Integer.parseInt(b.getText()), true);
		}
		if(!b.getText().equalsIgnoreCase(BLANK)){
			Component label2 = b.getComponent(Integer.parseInt(b.getText())-1);
			b.setText(BLANK);
			label2.setVisible(true);
			board.setCellDraftVisibility(rowVal(b), colVal(b), Integer.parseInt(b.getText()), true);
		}
	}
	
	private class eraseFunction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			toggleButton(eraseButton, ERASE_TOGGLED, DEFAULT_COMMAND);
			resetCurrentValue();
			highlightValue(BLANK);
		}
	}
	
	public boolean isButtonToggled(JButton button, Color defaultColour){
		if(!button.getBackground().equals(defaultColour))
			return true;
		else return false;
	}
	
	public void toggleButton(JButton button, Color colour, Color defaultColour){
		if(!button.getBackground().equals(colour)){
			button.setBackground(colour);
		}
		else button.setBackground(defaultColour);
	}
	
	private class NumberSelect implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(currentValue.equalsIgnoreCase(event.getActionCommand())){
				highlightValue(resetCurrentValue());
			}else{
				currentValue = event.getActionCommand();
				highlightValue(currentValue);
			}
			if(isButtonToggled(eraseButton, DEFAULT_COMMAND))
				toggleButton(eraseButton, ERASE_TOGGLED, DEFAULT_COMMAND);
		}
	}
	
	private class newGamePop implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(pane.newGameInGame()){
				board.clear();
				board.generate(pane.chooseLevelInGame());
				setUpGrid();
			}
		}
	}
	
	private class draftFunction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(draftButton.getBackground().equals(DEFAULT_COMMAND)){
				draftButton.setBackground(Color.RED);
				setDraftMode(true);
			}else {
				draftButton.setBackground(DEFAULT_COMMAND);
				setDraftMode(false);
			}
		}
	}
	
	private class solutionFunction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
				for(Component commands: commandPanel.getComponents()){
					commands.setBackground(DEFAULT_COMMAND);
				}
				toggleButton(solutionButton, CHECK_HIGHLIGHT, DEFAULT_COMMAND);
				blinkOut(solutionButton, DEFAULT_GRID,3);
				highlightValue(BLANK);
				for(JButton b: buttonRow){
					if(!board.isInitiallyVisibleCell(rowVal(b), colVal(b)))
						checkSquare(b,8);
					//blinkOut(b,DEFAULT_GRID,3);
				}
		}
	}
	
	private class revealFunction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(pane.revealMessage()){
				revealAll();
			}
		}
	}

	public void checkSquare(JButton b, int seconds)
		{
			if(!b.getText().equals(BLANK)){
				if(board.isCorrectCell(rowVal(b),colVal(b))){
					b.setBackground(CORRECT);
				}
				else {
					b.setBackground(WRONG);
				}
				blinkOut(b,DEFAULT_GRID, seconds);
			}
		}
	
	private void blinkOut(JButton button , Color colour, int seconds){
        Timer timer = new Timer(seconds*1000, new blinkFunction(button, colour));
        timer.start(); 
    }
	
	private class blinkFunction implements ActionListener{

		public blinkFunction(JButton button , Color colour){
			b = button;
			c = colour;
		}
		public void actionPerformed(ActionEvent e) {
			b.setBackground(c);
		}
		private JButton b;
        private Color c;
	}
	
	public void removeAll(){
		for(JButton b: buttonRow){
			if (!board.isInitiallyVisibleCell(rowVal(b),colVal(b))){
				toggleDraftFalse(b);
				board.removeCellValue(rowVal(b), colVal(b));
				b.setText(BLANK);
			}
		}
	}
	
	private class resetFunction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(pane.restartGame())
				removeAll();
		}
	}
	
	public void revealAll(){
		for(int i = 1; i<=9; i++)
		{
			JButton finalButton;
			for(int j = 1; j<=9; j++){
				String cellVal;
				cellVal = Integer.toString(board.getCellValue(i,j));
				if (!board.isCurrentlyVisibleCell(i,j)){
					finalButton = buttonRow.get(findIndex(i,j));
					finalButton.setText(cellVal);
					finalButton.setEnabled(false);
				}
			}
		}
	}
	
	public void setDraftMode(boolean enable){
		draftMode = enable;
	}
	
	
	public String resetCurrentValue(){
		this.currentValue = BLANK;
		return currentValue;
	}
	
	public String getCurrentValue(){
		return this.currentValue;
	}
	
	public void highlightValue(String current){
		for(JButton jb: buttonInputs){
			if(jb.getText().equalsIgnoreCase(current))
				jb.setBackground(INPUT_HIGHLIGHTS);
			else jb.setBackground(DEFAULT_INPUT);
		}
	}
	
	private int findIndex(int row, int col){
		int index;
			index = col - 1;
		if(row > 1){
			index = index + (row-1) * 9;
		}
		return index;
	}
	
	private int rowVal(JButton b){
		int index = buttonRow.indexOf(b);
		int row;
		 row = 1 + ((int)Math.floor((index)/9));
		return row;
	}
	
	private int colVal(JButton b){
		int index = buttonRow.indexOf(b);
		int col = 1 + index % 9;
		return col;
	}
	
	public Timer timer;
	public TimerTask task;
	public Color CORRECT = Color.GREEN;
	public Color WRONG = Color.RED;
	public Color CHECK_HIGHLIGHT = Color.RED;
	public Color INPUT_HIGHLIGHTS = Color.pink;
	public Color ERASE_TOGGLED = Color.yellow;
	public Color DEFAULT_INPUT = new Color(238,238,238);
	public Color userTempColor = Color.BLUE;
	public Color draftColor = Color.LIGHT_GRAY;
	public Font DRAFTSMALL = new Font("Courrier",Font.CENTER_BASELINE ,15);
	public Font ARIALBOLD = new Font("Arial", Font.BOLD, 20);
	private boolean draftMode;
	private Color DEFAULT_GRID = new Color(238,238,238);
	private Color DEFAULT_COMMAND = new Color(238,238,238);
	private JButton newGame;
	private JButton resetGame;
	private JButton draftButton;
	private JButton eraseButton;
	private JButton solutionButton;
	private JButton revealButton;
	private JButton checkButton;
	private String currentValue;
	private JPanel buttonPanel;
	private JPanel commandPanel;
	private JPanel scorePanel;
	public LinkedList<JButton> buttonInputs;
	private ArrayList<JButton> buttonRow;
	private OptionPanes pane = new OptionPanes();

	public Board board;
	public static final String LABELS = "123456789";
	public static final String BLANK = "";
	public static final int DEFAULT_WIDTH = 900;
	public static final int DEFAULT_HEIGHT = 700;
}





