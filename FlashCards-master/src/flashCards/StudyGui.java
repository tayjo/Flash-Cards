/**
 * 
 */
package flashCards;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**
 * @author Martha Trevino 
 */
public class StudyGui extends JFrame {

    /**
     * @param args
     */
	private JButton button;
	
    public static void main(String[] args) {
        new StudyGui().createGui();

    }
    
    void createGui(){
    	this.setSize(600, 400);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	this.setLayout(new BorderLayout());
    	
    	JPanel fileManager = new JPanel();
    	fileManager.setLayout(new GridLayout(1, 4));
    	
    	
    	button = new JButton("OK");
    	fileManager.add(button);

    	fileManager.setPreferredSize(new Dimension(0, 50));
    	this.add(fileManager, BorderLayout.NORTH);  	
    	
    	this.setVisible(true);

    }
    
    public class MyOkListener implements ActionListener{
    	public void actionPerformed(ActionEvent event){
    		
    	}	
    }
}
