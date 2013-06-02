
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


/**
 * 
 * A class to help create various types of option pane pop ups
 * @author Hayden Smith, Laura Hodes, Jerome Robins, Steven Falconieri
 *
 */

public class OptionPanes {
	
	
	public OptionPanes(){
		/**
		 * Icons imported from http://findicons.com/
		 */		
		start = createImageIcon("Images/puzzle.gif", "puzzle");
		grumpy = createImageIcon("Images/grumpy.gif", "grumpy");
		awesome = createImageIcon("Images/awesome.gif", "awesome");
		puzzled = createImageIcon("Images/puzzled.gif", "puzzled");
		background = createImageIcon("Images/grumpy.gif", "grumpy");
		
	}
	
	/**
	 * Retrieves a chosen icon
	 * @param name of icon
	 * @return icon
	 */
	public ImageIcon getIcon(String name){
		if(name.equals("startIcon"))
			return start;
		if(name.equals("grumpyIcon"))
			return grumpy;
		if(name.equals("awesomeIcon"))
			return awesome;
		if(name.equals("puzzledIcon"))
			return puzzled;
		if(name.equals("backgroundIcon"))
			return background;
		else return null;
	}
	
	
	/**
	 * @return value of closing a window
	 */
	
	public int closedOption(){
		return JOptionPane.CLOSED_OPTION;
	}
	
	/**
	 * Choose the level of difficulty for the first game
	 * @return level of difficulty or exit value
	 */
	
	public int chooseLevel(){
		String[] buttons = {"Easy", "Medium", "Hard", "Exit" };
		difficulty = JOptionPane.showOptionDialog(null, "To start, choose your difficulty:", "Welcome to Sudoku Fun!",
		        JOptionPane.INFORMATION_MESSAGE, 0, start, buttons, buttons[3]);
		if(difficulty == EXIT_VALUE)
			difficulty = JOptionPane.CLOSED_OPTION;
		return difficulty;
	}
	
	/**
	 * Choose the level of difficulty after first game
	 * @return level of difficulty
	 *
	 */
	
	public int chooseLevelInGame(){
		String[] buttons = {"Easy", "Medium", "Hard"};
		difficulty = JOptionPane.showOptionDialog(null, "Here we go again! Choose your difficulty:", "Welcome to Sudoku Fun!",
		        JOptionPane.INFORMATION_MESSAGE, 0, start, buttons, buttons[2]);
		return difficulty;
	}
	
	
	/**
	 * Exit pop up after a finished game
	 */
	
	public void exitWinGame(){
		
		String[] buttons = {"Cool see ya!"};
		JOptionPane.showOptionDialog(null, "Oh alright, see you next time champ!", "Aurevoir Sudoku Fun!",
		        JOptionPane.INFORMATION_MESSAGE, 0, awesome, buttons, buttons[0]);
		
		System.exit(0);
	}
	

	/**
	 * Asks for user confirmation
	 * @param icon
	 * @param message
	 * @param title
	 * @return
	 */
	public boolean confirmationPopUp(Icon icon, String message, String title){
		String[] buttons = {"Yes", "No"};
		value = JOptionPane.showOptionDialog(null, message, title,
		        JOptionPane.INFORMATION_MESSAGE, 0, icon, buttons, buttons[1]);
		if(value == JOptionPane.YES_OPTION)
			return true;
		else return false;
	}
	
	/** Returns an ImageIcon, or null if the path was invalid. 
	 *  
	 *  method below copied from the Java tutorial link below - all redistribution conditions met:
	 *  http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/uiswing/examples/components/LabelDemoProject/src/components/LabelDemo.java
	 * 
	 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
	 *
	 * Redistribution and use in source and binary forms, with or without
	 * modification, are permitted provided that the following conditions
	 * are met:
	 *
	 *   - Redistributions of source code must retain the above copyright
	 *     notice, this list of conditions and the following disclaimer.
	 *
	 *   - Redistributions in binary form must reproduce the above copyright
	 *     notice, this list of conditions and the following disclaimer in the
	 *     documentation and/or other materials provided with the distribution.
	 *
	 *   - Neither the name of Oracle or the names of its
	 *     contributors may be used to endorse or promote products derived
	 *     from this software without specific prior written permission.
	 *
	 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
	 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
	 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
	 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
	 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
	 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
	 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
	 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
	 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
	 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
	 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
	 */
	
    protected static ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = OptionPanes.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
	private ImageIcon background;
    private ImageIcon start;
    private ImageIcon grumpy;
    private ImageIcon awesome;
    private ImageIcon puzzled;
    
    private int EXIT_VALUE = 3;
	private int difficulty;
	private int value;
}
