import java.awt.*;
import java.awt.event.*;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * 
 * @author Hayden, Laura, Jerome, Steven
 * some code inspired from Core Java Fundamentals Book 1 Chapter 8, Event Handling p329/ Cay Hortsmann
 * code for blinkFuntion command inspired from:
 * http://www.daniweb.com/software-development/java/threads/268414/changing-a-jbuttons-colour-with-a-timer
 * @version 0.1
 */


/**
 * SudokuGUI creates the GUI for Board (implemented by SudokuBoard)
 */

public class SudokuFrame extends JFrame
{
	public SudokuFrame(Board sudokuBoard)
	{
		this.board = sudokuBoard;
		
		setTitle("SUDOKU FUN!");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		currentValue = BLANK;
		
		buttonRow = new ArrayList<JButton>();
		buttonInputs = new LinkedList<JButton>();
		
		buttonPanel = new JPanel();
		
		setUpGrid();
		
		commandPanel = new JPanel();
		commandPanel.setLayout(new FlowLayout());
		commandPanel.setBackground(DEFAULTBG);
		
		scorePanel = new JPanel();
		scorePanel.setLayout(new FlowLayout());
		scorePanel.setVisible(true);
		scorePanel.setBackground(DEFAULTBG);

		newGameButton = makeCommandButton("NEW GAME", commandPanel, new newGameFunction(), "Allows you to switch difficulties.");
		resetGameButton = makeCommandButton("Restart game", commandPanel, new resetFunction(), "Give this puzzle another go!");
		revealButton = makeCommandButton("Reveal ALL", commandPanel, new revealFunction(), "Give up?");
		solutionButton = makeCommandButton("Check my solution!", commandPanel, new solutionFunction(), null);
		undoButton = makeCommandButton("Undo my last", commandPanel, new undoFunction(), "Undo your last move");
		exitButton = makeCommandButton("EXIT GAME", commandPanel, new exitFunction(), "Bye bye!" );
		
		
		setUpButtonInputs();
		
		draftButton = makeCommandButton("DRAFT", scorePanel, new draftFunction(), "Click me if you're not sure!");
		eraseButton = makeCommandButton("RUBBER", scorePanel, new eraseFunction(), "Made a mistake? Click me!");

		prepareSolutionButtonForDifficulty();
		
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
	
	/************************************************
	 * GUI component set up methods
	 * 
	 ************************************************/
	
	
	/**
	 * Prepares a game layout according to difficulty
	 */
	private void prepareSolutionButtonForDifficulty() {
		String tooltip = null;
		if(board.isDifficultyEasy()){
			solutionButton.setVisible(false);
		} else if(board.isDifficultyMedium()){
			solutionButton.setVisible(true);
			tooltip = "I'll check what you've done so far!";
		}else{
			solutionButton.setVisible(true);
			tooltip = "I'll check what you've done when you've filled the board up!";
		}
		solutionButton.setToolTipText(tooltip);
	}
	
	/**
	 * Sets up the number inputs and adds to an ArrayList and to a JPanel
	 */
	private void setUpButtonInputs(){
		for (int i = 0; i < LABELS.length(); i++)
	      {
	         final String label = LABELS.substring(i, i + 1);
	         JButton keyButton = new JButton(label);
	         keyButton.addActionListener(new NumberSelect());
	         keyButton.setBackground(DEFAULT_INPUT);
	         keyButton.setFont(USERINPUT);
	         buttonInputs.add(keyButton);
	         scorePanel.add(keyButton);
	      }
	}
	
	/**
	 * Sets up the grid at the beginning of a new game (not a reset)
	 */
	private void setUpGrid(){
		buttonPanel.removeAll();
		buttonPanel.setLayout(new GridLayout(3,3));
		buttonRow.removeAll(buttonRow);
		ArrayList<JPanel> insetPanels = new ArrayList<JPanel>();
		for(int i = 0; i<9; i++){
			JPanel JP = new JPanel();
			JP.setLayout(new GridLayout(3,3));
			JP.setBorder(panelLine);
			insetPanels.add(JP);
			buttonPanel.add(JP);
		}
		JPanel panel = null;
		for(int i = 1; i<=9; i++)
		{
			for(int j = 1; j<=9; j++){
				String cellVal;
				boolean given = false;
				if (board.isInitiallySet(i,j))
				{
					cellVal = Integer.toString(board.getCellValue(i,j));
					given = true;
				}else{
					cellVal = BLANK;
				}
				if(i<=3){
					if(j <= 3)
						panel = insetPanels.get(0);
					else if(j <= 6)
						panel = insetPanels.get(1);
					else if(j <= 9)
						panel = insetPanels.get(2);
				} else if(i<=6) {
					if(j <= 3)
						panel = insetPanels.get(3);
					else if(j<= 6)
						panel = insetPanels.get(4);
					else if( j <= 9)
						panel = insetPanels.get(5);
				} else if(i<=9){
					if( j <= 3)
						panel = insetPanels.get(6);
					else if(j <= 6)
						panel = insetPanels.get(7);
					else if(j <= 9)
						panel = insetPanels.get(8);
				}
				buttonRow.add(makeGridButton(cellVal, panel, given));
			}
		}
	}
	
	/**
	 * Creates a new JButton used in the Sudoku 'play' area
	 * @param name is the button text
	 * @param panel is the JPanel button will belong to
	 * @param given says whether a button value is initially given or not
	 * @return new JButton
	 */
	private JButton makeGridButton( String name, JPanel panel, boolean given)
	{
		JButton button = new JButton(name);
		panel.add(button);
		button.setBackground(DEFAULT_GRID);
		button.setFont(FIXEDSQUARES);
		button.setBorder(buttonLine);
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
	
	/**
	 * Scans the board's current state and updates all values
	 */
	
	private void labelGrid(){
		JButton b;
		for(int i = 1; i<=9; i++){
			for(int j = 1; j<=9; j++){
				b = buttonRow.get(findIndex(i,j));
				if(!board.isInitiallySet(i, j)){
					if (board.hasInput(i,j)){
						b.setText(Integer.toString(board.getCellValue(i,j)));
						for(Component label: b.getComponents()){
							label.setVisible(false);
						}
					}else{
						b.setText(BLANK);
						if(board.hasDrafts(i,j)){
							for(int draft = 1; draft <= 9; draft++){
								System.out.println("hello");
								System.out.println((board.isVisibleCellDraft(rowVal(b), colVal(b), draft)));
								if(board.isVisibleCellDraft(rowVal(b), colVal(b), draft)){
									//toggleDraftValues(Integer.toString(draft), b);
									System.out.println("hello");
									
									b.getComponent(draft-1).setVisible(true);
								}
							}
						}
						
					}
				}
			}
		}
	}
	
	/**
	 * Creates a new command or feature button
	 * @param name
	 * @param panel
	 * @param action is the listener to be attached
	 * @return JButton
	 */
	private JButton makeCommandButton(String name, JPanel panel, ActionListener action, String tooltip)
	{
		JButton button = new JButton(name);
		panel.add(button);
		button.addActionListener(action);
		button.setBackground(DEFAULT_COMMAND);
		button.setToolTipText(tooltip);
		if(panel.equals(scorePanel))
				button.setFont(BIGFONT);
		return button;
	}
	
	/************************************************
	 * Gaming experience methods
	 * 
	 ************************************************/
	
	/**
	 * Toggles a particular draft value
	 * @param value is the value to be toggled
	 * @param b is the button concerned in this action 
	 */
	
	private void toggleDraftValues(String value, JButton b){
		//Component label = b.getComponent(Integer.parseInt(value)-1);
		if(board.isVisibleCellDraft(rowVal(b), colVal(b), Integer.parseInt(value))){
			//label.setVisible(false);
			board.setCellDraftVisibility(rowVal(b), colVal(b), Integer.parseInt(value), false);
		} else{
			//label.setVisible(true);
			board.setCellDraftVisibility(rowVal(b), colVal(b), Integer.parseInt(value), true);
			
		}
	}
	
	/**
	 * Checks whether a button is toggled
	 * @param button
	 * @param defaultColour
	 * @return boolean
	 */
	
	private boolean isButtonToggled(JButton button, Color defaultColour){
		if(!button.getBackground().equals(defaultColour))
			return true;
		else return false;
	}
	
	/**
	 * Toggles a button 
	 * @param button
	 * @param colour
	 * @param defaultColour
	 */
	
	private void toggleButton(JButton button, Color colour, Color defaultColour){
		if(!button.getBackground().equals(colour)){
			resetCommandsToggle();
			button.setBackground(colour);
		}
		else button.setBackground(defaultColour);
	}
	
	/**
	 * Untoggles all commands
	 */
	
	private void resetCommandsToggle(){
		for(Component commands: commandPanel.getComponents()){
			commands.setBackground(DEFAULT_COMMAND);
		}
		for(Component commands: scorePanel.getComponents()){
			if(!buttonInputs.contains(commands))
				commands.setBackground(DEFAULT_COMMAND);
		}
		
	}
	
	/**
	 * Checks if board is filled and correct and initiates end game options
	 * 
	 */
	private void checkEndGame(){
		if(board.isFilledBoard() && board.isCorrectBoard()){
			if(pane.winGame()){
				board.clear();
				board.generate(pane.chooseLevelInGame());
				prepareSolutionButtonForDifficulty();
				setUpGrid();
			} else pane.exitWinGame();
		}
	}
	
	/**
	 * Enables and disables certain command buttons 
	 * @param enable
	 */
	private void enableCommands(boolean enable){
		for(Component commands: commandPanel.getComponents()){
			if(!enable && !commands.equals(newGameButton))
				commands.setEnabled(false);
			else commands.setEnabled(true);
		}
		
		
	}
	
	
	/**
	 * Checks whether or not a square is filled correctly and makes it flash appropriately
	 * @param b is button to be checked
	 * @param seconds time delay for blink
	 * @return whether input is correct or not
	 */
	
	private boolean checkSquare(JButton b, int seconds)	{
		boolean correct;
		if(board.isDifficultyEasy())
			correct = board.isCorrectInputForCell(rowVal(b), colVal(b), Integer.decode(getCurrentValue()));
		else correct = board.isCorrectCell(rowVal(b), colVal(b));
		
		if(correct)		
			b.setBackground(CORRECT);
		else
			b.setBackground(WRONG);
		blinkOut(b,DEFAULT_GRID, seconds);
		return correct;
	}
	
	/**
	 * Sets up timer for blinkerFunction
	 * @param button
	 * @param colour
	 * @param seconds
	 */
	
	private void blinkOut(JButton button , Color colour, int seconds){
        Timer timer = new Timer(seconds*1000, new blinkFunction(button, colour));
        timer.start(); 
    }
	
	/**
	 * Highlights a chosen number input
	 * @param current is the value to be highlighted - BLANK to be passed in to un-highlight
	 */
	
	private void highlightInputValue(String current){
		for(JButton jb: buttonInputs){
			if(jb.getText().equalsIgnoreCase(current)){
				jb.setBackground(INPUT_TOGGLED);
			}
			else {
				jb.setBackground(DEFAULT_INPUT);
			}
		}
	}
	
	/**
	 * Highlights all grid buttons with the chosen string value
	 * @param current
	 */
	private void highlightGridValue(String current){
		for(JButton jb: buttonRow){
			if(!jb.getText().equals(BLANK) && jb.getText().equals(current))
				jb.setBackground(INPUT_TOGGLED);
			else jb.setBackground(DEFAULT_GRID);
		}
	}
	
	
	/************************************************
	 * ActionListeners private classes
	 * 
	 ************************************************/
	
	/**
	 * ActionListener attached to Sudoku 'play' area buttons
	 */
	
	private class NumberInsert implements ActionListener
	{
		/**
		 * Constructor
		 * @param button is button attached to listener
		 */
		public NumberInsert(JButton button)
		{
			b = button;
		}
		
		public void actionPerformed(ActionEvent event){
			if(isButtonToggled(eraseButton, DEFAULT_COMMAND)){
				board.removeCellValue(rowVal(b), colVal(b));
			}else if(!getCurrentValue().equalsIgnoreCase(BLANK)){
				if(!isButtonToggled(draftButton, DEFAULT_COMMAND)){
					if(board.isDifficultyEasy()){
						if(checkSquare(b,1)){
							board.setCellValue(rowVal(b), colVal(b), Integer.parseInt(getCurrentValue()));
						}
					}else{
						board.setCellValue(rowVal(b), colVal(b), Integer.parseInt(getCurrentValue()));
					}
				} else{
					toggleDraftValues(getCurrentValue(), b);
				}
			}
			labelGrid();
			checkEndGame();
		}
		private JButton b;
	}
	
	
	/**
	 * ActionListener attached to Sudoku eraseButton
	 * 
	 */
	
	private class eraseFunction implements ActionListener
	{
		
		public void actionPerformed(ActionEvent event)
		{
			if(!isButtonToggled(eraseButton, DEFAULT_COMMAND)){
				resetCommandsToggle();
			}
			toggleButton(eraseButton, COMMAND_TOGGLED, DEFAULT_COMMAND);
			resetCurrentValue();
			highlightInputValue(BLANK);
			highlightGridValue(BLANK);
		}
	}
	
	
	/**
	 * ActionListener attached to Sudoku number inputs on scorePanel
	 * Toggles number inputs and changes currentValue to be input
	 */
	
	private class NumberSelect implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(event.getActionCommand().equalsIgnoreCase(getCurrentValue())){
				highlightInputValue(resetCurrentValue());
				highlightGridValue(resetCurrentValue());
			}else{
				setCurrentValue(event.getActionCommand());
				highlightInputValue(getCurrentValue());
				highlightGridValue(getCurrentValue());
			}
			if(isButtonToggled(eraseButton, DEFAULT_COMMAND))
				toggleButton(eraseButton, COMMAND_TOGGLED, DEFAULT_COMMAND);
		}
	}
	
	/**
	 * ActionListener attached to Sudoku newGameButton
	 * Creates a pop-up with choice of new game difficulty and sets the new game up
	 */
	
	private class newGameFunction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(pane.newGameInGame()){
				board.clear();
				board.generate(pane.chooseLevelInGame());
				enableCommands(true);
				prepareSolutionButtonForDifficulty();
				setUpGrid();
			}
		}
	}
	
	/**
	 * Undoes last action attached to undoButton
	 */
	
	private class undoFunction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			board.undoLast();
			labelGrid();
		}
	}
	
	/**
	 * ActionListener attached to draftButton - toggles on/off
	 */
	
	private class draftFunction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(!isButtonToggled(draftButton, DEFAULT_COMMAND)){
				resetCommandsToggle();
			} 
			toggleButton(draftButton, COMMAND_TOGGLED, DEFAULT_COMMAND);
		}
	}
	
	
	/**
	 * ActionListener attached to Sudoku solutionButton
	 * 
	 */
	
	private class solutionFunction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			resetCommandsToggle();
			highlightInputValue(BLANK);
			highlightGridValue(BLANK);
			toggleButton(solutionButton, COMMAND_TOGGLED, DEFAULT_COMMAND);
			blinkOut(solutionButton, DEFAULT_COMMAND,2);
			for(JButton b: buttonRow){
				if(!board.isInitiallySet(rowVal(b), colVal(b))){
					if(board.isDifficultyHard() && board.isFilledBoard()){
						checkSquare(b,3);
					} else if(!board.isEmptyCell(rowVal(b), colVal(b)))
						checkSquare(b,3);
				}
			}
			checkEndGame();
		}
	}
	
	/**
	 * ActionListener attached to Sudoku revealButton
	 * 
	 */
	
	private class revealFunction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(pane.revealMessage()){
				revealAll();
				enableCommands(false);
				labelGrid();
				
			}
		}
	}

	/**
	 * ActionListener attached blinkOut method
	 * 
	 */
	private class blinkFunction implements ActionListener{
		/**
		 * Constructor
		 * @param button
		 * @param colour
		 */
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
	
	/**
	 * Action listener linked to resetButton
	 * Resets current puzzle to original set values
	 */
	
	private class exitFunction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(pane.exitMessageInGame()){
				System.exit(0);
			}
		}
	}
	
	/**
	 * Action listener linked to resetButton
	 * Resets current puzzle to original set values
	 */
	
	private class resetFunction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(pane.restartGame()){
				removeAllUserValues();
				labelGrid();
			}
		}
	}
	
	/************************************************
	 * Helper methods
	 * 
	 ************************************************/
	
	/**
	 * Helper method for resetFunction - removes all input values
	 */
	
	private void removeAllUserValues(){
		for(JButton b: buttonRow){
			if (!board.isInitiallySet(rowVal(b),colVal(b))){
				board.removeCellValue(rowVal(b), colVal(b));
			}
		}
	}
	
	/**
	 * Reveals the grid and disables any further gameplay
	 */
	
	private void revealAll(){
		for(int i = 1; i<=9; i++)
		{
			JButton finalButton;
			for(int j = 1; j<=9; j++){
				if (!board.isInitiallySet(i,j)){
					board.setCellValue(i, j, (board.getCellValue(i,j)));
					finalButton = buttonRow.get(findIndex(i,j));
					finalButton.setEnabled(false);
				}
			}
		}
	}
	
	/**
	 * Resets currentValue to BLANK
	 * @return currentValue
	 */
	
	private String resetCurrentValue(){
		this.currentValue = BLANK;
		return currentValue;
	}
	
	/**
	 * Gets current value
	 * @return currentValue
	 */
	
	private String getCurrentValue(){
		return this.currentValue;
	}
	
	/**
	 * Sets currentValue
	 * @param value
	 */
	private void setCurrentValue(String value) {
		currentValue = value;
	}
	
	/**
	 * Converts a button's row and column values to an index
	 * @param row
	 * @param column
	 * @return index fir buttonRow ArrayList
	 */
	
	private int findIndex(int row, int col){
		int index;
			index = col - 1;
		if(row > 1){
			index = index + (row-1) * 9;
		}
		return index;
	}
	
	/**
	 * Converts a button's index to a row value
	 * @param JButton b
	 * @return row value for JButton
	 */
	
	private int rowVal(JButton b){
		int index = buttonRow.indexOf(b);
		int row;
		 row = 1 + ((int)Math.floor((index)/9));
		return row;
	}
	
	/**
	 * Converts a button's index to a column value
	 * @param JButton b
	 * @return column value for JButton
	 */
	
	private int colVal(JButton b){
		int index = buttonRow.indexOf(b);
		int col = 1 + index % 9;
		return col;
	}
	
	public Border buttonLine = BorderFactory.createLineBorder(Color.gray, 2);
	public Border panelLine = BorderFactory.createLineBorder(Color.gray, 5);
	public Timer timer;
	
	public Color DEFAULTBG = new Color(200,200,250);
	public Color CORRECT = Color.GREEN;
	public Color WRONG = Color.RED;
	public Color INPUT_TOGGLED = Color.yellow;
	public Color COMMAND_TOGGLED = Color.yellow;
	public Color DEFAULT_INPUT = new Color(90,200,150);
	public Color userTempColor = Color.BLUE;
	public Color draftColor = Color.LIGHT_GRAY;
	public Color DEFAULT_GRID = new Color(238,238,238);
	public Color DEFAULT_COMMAND = new Color(238,150,140);
	
	public Font USERINPUT = new Font ("Courrier", Font.CENTER_BASELINE, 25);
	public Font DRAFTSMALL = new Font("Courrier",Font.CENTER_BASELINE ,15);
	public Font FIXEDSQUARES = new Font("Arial", Font.BOLD, 20);
	public Font BIGFONT = new Font("Courrier", Font.CENTER_BASELINE, 25);
	
	private JButton exitButton;
	private JButton newGameButton;
	private JButton resetGameButton;
	private JButton undoButton;
	private JButton draftButton;
	private JButton eraseButton;
	private JButton solutionButton;
	private JButton revealButton;
	
	private JPanel buttonPanel;
	private JPanel commandPanel;
	private JPanel scorePanel;
	private LinkedList<JButton> buttonInputs;
	private ArrayList<JButton> buttonRow;
	private OptionPanes pane = new OptionPanes();

	public Board board;
	
	private String currentValue;
	public static final String LABELS = "123456789";
	public static final String BLANK = "";
	public static final int DEFAULT_WIDTH = 900;
	public static final int DEFAULT_HEIGHT = 700;
}





