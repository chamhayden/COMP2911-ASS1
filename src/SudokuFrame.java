import java.awt.*;
import java.awt.event.*;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.LinkedList;

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

//NB: JFrame's default layoutManager is BorderLayout (so no need to setLayout unless we want to change it)
class SudokuFrame extends JFrame
{
	public SudokuFrame(Board board)
	{
		setTitle("SudokuFrame");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		currentValue = "-1";
		
		/*
		 * 
		 * Lists added in order to quickly find each button location via row and column (might be needed)
		 * 
		 */
		buttonGrid = new ArrayList<ArrayList<JButton>>(8);
		buttonRow = new ArrayList<JButton>(8);
		buttonValue = new LinkedList<JButton>();
		
		//button panel is created, which will be added to SudokuFrame
		buttonPanel = new JPanel();
		
		//create buttons - the makebutton method adds the buttons automatically to buttonPanel 
		for(int i = 0; i<9; i++)
		{
			for(int j = 0; j<9; j++){
				String cellVal;
				if (board.isCurrentlyVisibleCell(i,j))
				{
					cellVal = Integer.toString(board.getCellValue(i,j));
				}
				else
				{
					cellVal = "";
				}
				buttonRow.add(makeGridButton(i, j ,cellVal, currentValue, buttonPanel));
			}
			buttonGrid.add(buttonRow);
		}
		
		/*
		 * buttons are set in a grid layout
		 * NB: I have read that GridBagLayout is a slightly more complex but highly customizable layout manager (we can determine the size of each button individually)
		 * - this can be looked into later
		 */
		buttonPanel.setLayout(new GridLayout(9,9));
		
		//creating a list of panels
		
		commandPanel = new JPanel();
		commandPanel.setLayout(new FlowLayout());
		easyButton = makeMenuButton("NEW EASY", Color.PINK, commandPanel);
		mediumButton = makeMenuButton("NEW MEDIUM", Color.CYAN, commandPanel);
		hardButton = makeMenuButton("NEW HARD", Color.RED, commandPanel);
		hintButton = makeMenuButton("Click for Hint", Color.BLUE, commandPanel);
		solutionButton = makeMenuButton("Give up?", Color.GREEN, commandPanel);
		
		draftButton = makeMenuButton("DRAFT", Color.PINK, commandPanel);
		checkButton = makeMenuButton("Check Square", Color.CYAN, commandPanel);
		
		scorePanel = new JPanel();
		scorePanel.setLayout(new FlowLayout());
		scorePanel.setVisible(true);
		
		makeMenuButton("PAUSE", Color.BLACK, scorePanel);
		timeButton = makeMenuButton("TIME ME", Color.DARK_GRAY, scorePanel);
		
		//Initialise buttons that control currentValue to fill grid
		String keyLabels = "123456789";
		
		for (int i = 0; i < keyLabels.length(); i++)
	      {
	         final String label = keyLabels.substring(i, i + 1);
	         JButton keyButton = new JButton(label);
	         DEFAULT_COLOR = keyButton.getBackground();
	         NumberSelect action = new NumberSelect(label);
	         keyButton.addActionListener(action);
	         buttonValue.add(keyButton);
	         scorePanel.add(keyButton);
	      }
		
		
		//add or nest panels to frame
		add(buttonPanel,BorderLayout.CENTER);
		add(commandPanel,BorderLayout.NORTH);
		add(scorePanel,BorderLayout.SOUTH);
		
	}
	
	/*
	 * makeButton makes a JButton, which is then added to the buttonPanel, with an attached ActionListener
	 * ActionListener should be detached to add different functionality to different buttons
	 * in the future, add 1 more argument to represent a type of ActionListener
	 */
	public JButton makeGridButton(int row, int col, String name, String currentValue, JPanel panel)
	{
		
		
		JButton button = new JButton(name);
		panel.add(button);
		NumberInsert insert = new NumberInsert(button);
		button.addActionListener(insert);

		return button;
	}
	
	public JButton makeMenuButton(String name, Color backgroundColor, JPanel panel)
	{
		JButton button = new JButton(name);
		panel.add(button);
		NumberAction action = new NumberAction(backgroundColor);
		button.addActionListener(action);
		return button;
	}
	
/**
 * An action listener that sets the panel's background color
 */
	
	private class NumberInsert implements ActionListener
	{
		public NumberInsert(JButton button)
		{
			//newValue = getCurrentValue();
			b = button;
		}
		
		public void actionPerformed(ActionEvent event)
		{
			if(!getCurrentValue().equalsIgnoreCase("-1"))
			b.setText(getCurrentValue());
			//b.setFont(font)
		}
		private JButton b;
		//private String newValue;
	}
	
	private class NumberSelect implements ActionListener
	{
		public NumberSelect(String select)
		{
			newCurrentValue = select;
		}
		
		public void actionPerformed(ActionEvent event)
		{
			if(currentValue.equalsIgnoreCase(event.getActionCommand())){
				highlightValue(resetCurrentValue());
			}else{
				currentValue = event.getActionCommand();
				highlightValue(currentValue);
			}
		}
		private String newCurrentValue;
	}
	
	//Dummy actionListner to be modified / deleted
	private class NumberAction implements ActionListener
	{
		public NumberAction(Color c)
		{
			backgroundColor = c;
		}
		
		public void actionPerformed(ActionEvent event)
		{
			buttonPanel.setBackground(backgroundColor);
			highlightValue(resetCurrentValue());
		}
		
		private Color backgroundColor;
	}
	
	public String resetCurrentValue(){
		this.currentValue = "-1";
		return currentValue;
	}
	
	public String getCurrentValue(){
		return this.currentValue;
	}
	
	private void highlightValue(String current){
		for(JButton jb: buttonValue){
			if(jb.getText().equalsIgnoreCase(current))
				jb.setBackground(Color.PINK);
			else jb.setBackground(DEFAULT_COLOR);
		}
	}
	
	private Color DEFAULT_COLOR;
	private JButton easyButton;
	private JButton mediumButton;
	private JButton hardButton;
	private JButton draftButton;
	private JButton timeButton;
	private JButton hintButton;
	private JButton solutionButton;
	private JButton checkButton;
	private String currentValue;
	private JPanel buttonPanel;
	//private JPanel hintPanel;
	private JPanel commandPanel;
	//private JPanel modePanel;
	private JPanel scorePanel;
	private LinkedList<JButton> buttonValue;
	private ArrayList<ArrayList<JButton>> buttonGrid;
	private ArrayList<JButton> buttonRow;

	
	public static final int DEFAULT_WIDTH = 900;
	public static final int DEFAULT_HEIGHT = 700;
}





