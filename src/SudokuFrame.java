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
public class SudokuFrame extends JFrame
{
	public SudokuFrame(Board sBoard)
	{
		this.board = sBoard;
		setTitle("SudokuFrame");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		currentValue = BLANK;
		draftMode = false;
		
		/*
		 * 
		 * Lists added in order to quickly find each button location via row and column (might be needed)
		 * 
		 */
		//buttonGrid = new ArrayList<ArrayList<JButton>>(8);
		buttonRow = new ArrayList<JButton>(8);
		buttonValue = new LinkedList<JButton>();
		
		buttonPanel = new JPanel();
		
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
				buttonRow.add(makeGridButton(i, j ,cellVal, currentValue, buttonPanel, given));
			}
			//buttonGrid.add(buttonRow);
		}

		buttonPanel.setLayout(new GridLayout(9,9));
		
		//creating panels
		
		commandPanel = new JPanel();
		commandPanel.setLayout(new FlowLayout());
		
		easyButton = makeMenuButton("NEW EASY", Color.PINK, commandPanel);
		mediumButton = makeMenuButton("NEW MEDIUM", Color.CYAN, commandPanel);
		hardButton = makeMenuButton("NEW HARD", Color.RED, commandPanel);
		solutionButton = makeMenuButton("Give up?", Color.GREEN, commandPanel);
		checkButton = makeMenuButton("Check Square", Color.CYAN, commandPanel);
				
		
		eraseButton = makeCommandButton("Rubber", commandPanel, new eraseFunction());
		draftButton = makeCommandButton("DRAFT", commandPanel, new draftFunction());
		scorePanel = new JPanel();
		scorePanel.setLayout(new FlowLayout());
		scorePanel.setVisible(true);
		
		makeMenuButton("PAUSE", Color.BLACK, scorePanel);
		timeButton = makeMenuButton("TIME ME", Color.DARK_GRAY, scorePanel);
		
		for (int i = 0; i < LABELS.length(); i++)
	      {
	         final String label = LABELS.substring(i, i + 1);
	         JButton keyButton = new JButton(label);
	         NumberSelect action = new NumberSelect();
	         keyButton.addActionListener(action);
	         keyButton.setBackground(DEFAULT_COLOR_INPUT);
	         buttonValue.add(keyButton);
	         scorePanel.add(keyButton);
	      }
		
		
		//add or nest panels to frame
		add(buttonPanel,BorderLayout.CENTER);
		add(commandPanel,BorderLayout.NORTH);
		add(scorePanel,BorderLayout.SOUTH);
		
	}
	
	public JButton makeGridButton(int row, int col, String name, String currentValue, JPanel panel, boolean given)
	{
		JButton button = new JButton(name);
		panel.add(button);
		button.setBackground(DEFAULT_COLOR_GRID);
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
	
	public JButton makeMenuButton(String name, Color backgroundColor, JPanel panel)
	{
		JButton button = new JButton(name);
		panel.add(button);
		NumberAction action = new NumberAction(backgroundColor);
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
			if(!isEraseToggled()){
				if(!getCurrentValue().equalsIgnoreCase(BLANK)){
					if(!draftMode){
						b.setText(getCurrentValue());
						for(Component labels:b.getComponents()){
							labels.setVisible(false);
						}
						location = buttonRow.indexOf(b);
						board.setCellValue(rowVal(location), colVal(location), Integer.parseInt(b.getText()));
					} else{
						toggleDraftValues(getCurrentValue(), b);
					}
				}
			} else{
				toggleDraftFalse(b);
				b.setText(resetCurrentValue());
				toggleErase();
			}
		}
		int location;
		private JButton b;
	}
	
	public void toggleDraftFalse(JButton button){
		for(Component label: button.getComponents()){
			label.setVisible(false);
		}
	}
	
	public void toggleDraftValues(String value, JButton b){
		Component label = b.getComponent(Integer.parseInt(value)-1);
		if(label.isVisible()){
			label.setVisible(false);
		} else label.setVisible(true);
			if(!b.getText().equalsIgnoreCase(BLANK)){
				Component label2 = b.getComponent(Integer.parseInt(b.getText())-1);
				b.setText(BLANK);
				if(!label2.isVisible())
					label2.setVisible(true);
			}
	}
	
	private class eraseFunction implements ActionListener
	{
		
		public void actionPerformed(ActionEvent event)
		{
			
			toggleErase();
			resetCurrentValue();
			highlightValue(BLANK);
			
		}
		
	}
	
	public boolean isEraseToggled(){
		if(eraseButton.getBackground().equals(ERASE_TOGGLED))
			return true;
		else return false;
	}
	
	public boolean toggleErase(){
		boolean toggled = false;
		if(!eraseButton.getBackground().equals(ERASE_TOGGLED)){
			eraseButton.setBackground(ERASE_TOGGLED);
			toggled = true;
		}
		else eraseButton.setBackground(DEFAULT_COLOR_GRID);
		return toggled;
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
		}
	}
	
	private class draftFunction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(draftButton.getBackground().equals(DEFAULT_COLOR_GRID)){
				draftButton.setBackground(Color.RED);
				setDraftMode(true);
			}else {
				draftButton.setBackground(DEFAULT_COLOR_GRID);
				setDraftMode(false);
			}
		}
	}
	
	public void setDraftMode(boolean enable){
		draftMode = enable;
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
		this.currentValue = BLANK;
		return currentValue;
	}
	
	public String getCurrentValue(){
		return this.currentValue;
	}
	
	private void highlightValue(String current){
		for(JButton jb: buttonValue){
			if(jb.getText().equalsIgnoreCase(current))
				jb.setBackground(Color.PINK);
			else jb.setBackground(DEFAULT_COLOR_INPUT);
		}
	}
	
	private int rowVal(int index){
		int row = (int)Math.floor(index/9);
		return row;
	}
	
	private int colVal(int index){
		int col = index%9;
		return col;
	}
	
	public Color ERASE_TOGGLED = Color.yellow;
	public Color DEFAULT_COLOR_INPUT = new Color(238,238,238);
	public Color userTempColor = Color.BLUE;
	public Color draftColor = Color.LIGHT_GRAY;
	public Font DRAFTSMALL = new Font("Courrier",Font.CENTER_BASELINE ,15);
	public Font ARIALBOLD = new Font("Arial", Font.BOLD, 20);
	private boolean draftMode;
	private Color DEFAULT_COLOR_GRID = new Color(238,238,238);
	private JButton easyButton;
	private JButton mediumButton;
	private JButton hardButton;
	private JButton draftButton;
	private JButton timeButton;
	private JButton eraseButton;
	private JButton solutionButton;
	private JButton checkButton;
	private String currentValue;
	private JPanel buttonPanel;
	//private JPanel hintPanel;
	private JPanel commandPanel;
	//private JPanel modePanel;
	private JPanel scorePanel;
	private LinkedList<JButton> buttonValue;
	//private ArrayList<ArrayList<JButton>> buttonGrid;
	private ArrayList<JButton> buttonRow;

	public Board board;
	public static final String LABELS = "123456789";
	//public static final String EMPTY = "-1";
	public static final String BLANK = "";
	public static final int DEFAULT_WIDTH = 900;
	public static final int DEFAULT_HEIGHT = 700;
}





