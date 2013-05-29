import java.awt.*;
import java.awt.event.*;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TimerTask;
import javax.swing.*;
import javax.swing.border.Border;

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
		
		buttonRow = new ArrayList<JButton>();
		buttonInputs = new LinkedList<JButton>();
		
		buttonPanel = new JPanel();
		buttonPanel.setBackground(DEFAULTBG);
		
		setUpGrid();
		
		commandPanel = new JPanel();
		commandPanel.setLayout(new FlowLayout());
		commandPanel.setBackground(DEFAULTBG);
		
		scorePanel = new JPanel();
		scorePanel.setLayout(new FlowLayout());
		scorePanel.setVisible(true);
		scorePanel.setBackground(DEFAULTBG);

		
		newGame = makeCommandButton("NEW GAME", commandPanel, new newGamePop());
		resetGame = makeCommandButton("Restart game", commandPanel, new resetFunction());
		revealButton = makeCommandButton("Reveal ALL", commandPanel, new revealFunction());
		solutionButton = makeCommandButton("Check my solution!", commandPanel, new solutionFunction());
		undoButton = makeCommandButton("Undo my last", commandPanel, new undoFunction());
		draftButton = makeCommandButton("DRAFT", scorePanel, new draftFunction());
		eraseButton = makeCommandButton("Rubber", scorePanel, new eraseFunction());

		//prepareForDifficulty();
		
		setUpButtonInputs();
		
		JPanel fillerEast = new JPanel();
		fillerEast.setBackground(DEFAULTBG);
		JPanel fillerWest = new JPanel();
		fillerWest.setBackground(DEFAULTBG);
		
		add(buttonPanel,BorderLayout.CENTER);
		add(commandPanel,BorderLayout.NORTH);
		add(scorePanel,BorderLayout.SOUTH);
		add(fillerEast,BorderLayout.EAST);
		add(fillerWest, BorderLayout.WEST);
		
	}
	
	private void prepareForDifficulty() {

		if(board.difficultyValueEasy() == 0){
			solutionButton.setVisible(true);
		}
		if(board.difficultyValueMedium() ==1){
			solutionButton.setVisible(true);
		}else{
			solutionButton.setVisible(false);
		}
			
		
	}
	private void setUpButtonInputs(){
		for (int i = 0; i < LABELS.length(); i++)
	      {
	         final String label = LABELS.substring(i, i + 1);
	         JButton keyButton = new JButton(label);
	         keyButton.addActionListener(new NumberSelect());
	         keyButton.setBackground(DEFAULT_COMMAND);
	         keyButton.setFont(USERINPUT);
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
				if (board.isInitiallyVisibleCell(i,j))
				{
					cellVal = Integer.toString(board.getCellValue(i,j));
					given = true;
				}else{
					cellVal = BLANK;
				}
				buttonRow.add(makeGridButton(cellVal, currentValue, buttonPanel, given));
			}
		}
	}
	
	private void labelGrid(){
		JButton b;
		for(int i = 1; i<=9; i++)
		{
			for(int j = 1; j<=9; j++){
				b = buttonRow.get(findIndex(i,j));
				if(!board.isInitiallyVisibleCell(i, j)){
					if (board.isCurrentlyVisibleCell(i,j))
					{
						b.setText(Integer.toString(board.getCellValue(i,j)));
					}else{
						b.setText(BLANK);
						for(int draft = 1; draft <= 9; draft++){
							if(board.isVisibleCellDraft(i, j, draft)){
								toggleDraftValues(Integer.toString(draft), b);
							}
						}
						
					}
				}
			}
		}
	}
	
	
	public JButton makeGridButton( String name, String currentValue, JPanel panel, boolean given)
	{
		JButton button = new JButton(name);
		panel.add(button);
		button.setBackground(DEFAULT_GRID);
		button.setFont(FIXEDSQUARES);
		button.setBorder(blackline);
		if(!given){
			NumberInsert insert = new NumberInsert(button);
			button.addActionListener(insert);
			button.setForeground(userTempColor);
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
		button.setBackground(DEFAULT_COMMAND);
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
			board.takeSnapShot(rowVal(b), colVal(b));
			if(isButtonToggled(eraseButton, DEFAULT_COMMAND)){
				toggleDraftFalse(b);
				board.removeCellValue(rowVal(b), colVal(b));
				board.setCellVisibility(rowVal(b), colVal(b), false);
			}else if(!getCurrentValue().equalsIgnoreCase(BLANK)){
				if(!isButtonToggled(draftButton, DEFAULT_COMMAND)){
					toggleDraftFalse(b);
					board.setCellValue(rowVal(b), colVal(b), Integer.parseInt(getCurrentValue()));
					board.setCellVisibility(rowVal(b), colVal(b), true);
					//if(board.difficultyValueEasy() == 0)
						checkSquare(b,2);
					} else{
						toggleDraftValues(getCurrentValue(), b);
					}
				}
			labelGrid();
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
			//resetCommands();
			toggleButton(eraseButton, COMMAND_TOGGLED, DEFAULT_COMMAND);
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
				toggleButton(eraseButton, COMMAND_TOGGLED, DEFAULT_COMMAND);
		}
	}
	
	private class newGamePop implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(pane.newGameInGame()){
				board.clear();
				board.generate(pane.chooseLevelInGame());
				//prepareForDifficulty();
				setUpGrid();
			}
		}
	}
	
	private class undoFunction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			board.undoLast();
			labelGrid();
		}
	}
	
	private class draftFunction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			resetCommands();
			if(isButtonToggled(draftButton, DEFAULT_COMMAND)){
				toggleButton(draftButton, COMMAND_TOGGLED, DEFAULT_COMMAND);
			} else toggleButton(draftButton, COMMAND_TOGGLED, DEFAULT_COMMAND);
		
		}
	}
	
	public void resetCommands(){
		for(Component commands: commandPanel.getComponents()){
			commands.setBackground(DEFAULT_COMMAND);
		}
	}
	
	private class solutionFunction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			resetCommands();
				toggleButton(solutionButton, COMMAND_TOGGLED, DEFAULT_COMMAND);
				blinkOut(solutionButton, DEFAULT_COMMAND,2);
				highlightValue(BLANK);
				for(JButton b: buttonRow){
					if(!board.isInitiallyVisibleCell(rowVal(b), colVal(b)))
						checkSquare(b,4);
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
				board.setCellVisibility(rowVal(b), colVal(b), false);
				//b.setText(BLANK);
			}
		}
	}
	
	private class resetFunction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(pane.restartGame()){
				removeAll();
				labelGrid();
			}
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
				jb.setBackground(INPUT_TOGGLED);
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
	
	public Color DEFAULTBG = new Color(200,200,250);
	public Border greyline = BorderFactory.createLineBorder(Color.gray);
	public Border blackline = BorderFactory.createLineBorder(Color.gray, 5);
	public Timer timer;
	public TimerTask task;
	public Color CORRECT = Color.GREEN;
	public Color WRONG = Color.RED;
	public Color INPUT_TOGGLED = Color.pink;
	public Color COMMAND_TOGGLED = Color.yellow;
	public Color DEFAULT_INPUT = new Color(238,238,238);
	public Color userTempColor = Color.BLUE;
	public Color draftColor = Color.LIGHT_GRAY;
	public Font USERINPUT = new Font ("Courrier", Font.CENTER_BASELINE, 25);
	public Font DRAFTSMALL = new Font("Courrier",Font.CENTER_BASELINE ,15);
	public Font FIXEDSQUARES = new Font("Arial", Font.BOLD, 20);
	private Color DEFAULT_GRID = new Color(238,238,238);
	private Color DEFAULT_COMMAND = new Color(238,238,238);
	private JButton newGame;
	private JButton resetGame;
	private JButton undoButton;
	private JButton draftButton;
	private JButton eraseButton;
	private JButton solutionButton;
	private JButton revealButton;
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





