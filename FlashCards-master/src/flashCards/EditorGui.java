/**
 * 
 */
package flashCards;

import javax.swing.*;

import java.io.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Josh Taylor
 */
@SuppressWarnings("serial")
public class EditorGui extends JFrame {

    private StudyList studyList;
    private boolean saved;
	private JButton load;
    private JButton save;
    private JButton saveAs;
    private JButton quit;
    private JButton addCard;
    private JButton editCard;
    private JButton findCard;
    private JButton deleteCard;
    private DefaultListModel<Item> cardsListModel;
    private JList<Item> cardsList;
    
    public EditorGui() {
    	super("EditorGUI - FlashCards");
    	this.saved = true;
    	studyList = new StudyList();
    }
    
    public static void main(String[] args) {
    	new EditorGui().createGui();
    }

    void createGui() {
    	
    	this.setSize(600, 400);
    	this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    	this.addWindowListener(new MyWindowListener());
    	
    	this.setLayout(new BorderLayout());
    	
    	JPanel fileManager = new JPanel();
    	fileManager.setLayout(new GridLayout(1, 4));
    	
    	load = new JButton("Load");
    	load.addActionListener(new MyLoadListener());
    	fileManager.add(load);
    	
    	save = new JButton("Save");
    	save.addActionListener(new MySaveListener());
    	fileManager.add(save);
    	
    	saveAs = new JButton("Save As");
    	saveAs.addActionListener(new MySaveAsListener());
    	fileManager.add(saveAs);
    	
    	quit = new JButton("Quit");
    	quit.addActionListener(new MyQuitListener());
    	fileManager.add(quit);
    	
    	fileManager.setPreferredSize(new Dimension(0, 50));
    	this.add(fileManager, BorderLayout.NORTH);
    	
    	JPanel cardManager = new JPanel();
    	cardManager.setLayout(new BorderLayout());
    	
    	cardsListModel = new DefaultListModel<Item>();
    	cardsList = new JList<Item>(cardsListModel);
    	cardsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	
    	JScrollPane cardsListPane = new JScrollPane(cardsList);
    	
    	cardManager.add(cardsListPane, BorderLayout.CENTER);
    	
    	JPanel cardEditors = new JPanel();
    	cardEditors.setLayout(new GridLayout(4, 1));
    	
    	addCard = new JButton("Add new card");
    	addCard.addActionListener(new MyAddCardListener());
    	cardEditors.add(addCard);
    	
    	editCard = new JButton("Edit card");
    	editCard.addActionListener(new MyEditCardListener());
    	cardEditors.add(editCard);
    	
    	findCard = new JButton("Find card");
    	findCard.addActionListener(new MyFindCardListener());
    	cardEditors.add(findCard);
    	
    	deleteCard = new JButton("Delete card");
    	deleteCard.addActionListener(new MyDeleteCardListener());
    	cardEditors.add(deleteCard);
    	
    	cardEditors.setPreferredSize(new Dimension(200, 0));
    	cardManager.add(cardEditors, BorderLayout.EAST);
    	
    	this.add(cardManager, BorderLayout.CENTER);
    	
    	this.setVisible(true);
    	
    }
    
    class MyAddCardListener implements ActionListener {
    	public void actionPerformed(ActionEvent event) {
    		String pattern = "^(\\S+( \\|\\| ){1}\\S+)";
    		String newCardText, stimulus, response;
    		String[] cardParts;
    		Item newItem;
			String prompt = "Enter card in the form: stimulus || response";
    		while (true){
	    		newCardText = JOptionPane.showInputDialog(null, prompt);
	    		if (newCardText != null) {
		    		newCardText = newCardText.trim();
		    		if (newCardText.matches(pattern)) {
		    			cardParts = newCardText.split(" *\\|\\| *");
		    			stimulus = cardParts[0];
		    			response = cardParts[1];
		    			newItem = new Item(stimulus, response);
		    			try {
			    			studyList.add(newItem);		    				
		    			}
		    			catch (IllegalArgumentException e) {
		    				JOptionPane.showMessageDialog(null, "Another card with that stimulus already exists.");
		    				continue;
		    			}
		    			cardsListModel.addElement(newItem);
		    			saved = false;
		    			break;
		    		}
		    		else {
		    			JOptionPane.showMessageDialog(null, "Input does not match pattern, try again.");
		    		}
	    		}
	    		else {
	    			break;
	    		}
    		}
    	}
    }
    
    class MyEditCardListener implements ActionListener {
    	public void actionPerformed(ActionEvent event) {
    		String pattern = "^(\\S+( \\|\\| ){1}\\S+)";
    		String curCardText, prompt, newCardText, stimulus, response;
    		String[] cardParts;
    		int selectedIndex;
    		Item curItem;
    		selectedIndex = cardsList.getSelectedIndex();
    		if (selectedIndex == -1) {
    			JOptionPane.showMessageDialog(null, "Select the card that you would like to edit.");
    			return;
    		}
    		curItem = cardsList.getSelectedValue();
    		curCardText = curItem.toString();
    		curCardText = curCardText.substring(0, curCardText.lastIndexOf(" || "));
			prompt = "Enter card in the form: stimulus || response";
    		while (true){
	    		newCardText = JOptionPane.showInputDialog(null, prompt, curCardText);
	    		if (newCardText != null) {
		    		newCardText = newCardText.trim();
		    		if (newCardText.matches(pattern)) {
		    			cardParts = newCardText.split(" *\\|\\| *");
		    			stimulus = cardParts[0];
		    			response = cardParts[1];
		    			try {
		    				studyList.modify(curItem, stimulus, response);
		    			}
		    			catch (IllegalArgumentException e) {
		    				JOptionPane.showMessageDialog(null, "Another card with that stimulus already exists.");
		    				continue;
		    			}
		    			saved = false;
		    			cardsList.clearSelection();
		    			break;
		    		}
		    		else {
		    			JOptionPane.showMessageDialog(null, "No good, try again.");
		    		}
	    		}
	    		else {
	    			break;
	    		}
    		}
    	}
    }
    
    class MyFindCardListener implements ActionListener {
    	public void actionPerformed(ActionEvent event) {
    		String search;
    		Item searchItem;
    		search = JOptionPane.showInputDialog(null, "Enter search term (stimulus or response)");
    		searchItem = studyList.find(search);
    		if (searchItem == null) {
    			JOptionPane.showMessageDialog(null, "Search term not found.");
    		}
    		else {
    		cardsList.setSelectedValue(searchItem, true);
    		}
    	}
    }
    
    class MyDeleteCardListener implements ActionListener {
    	public void actionPerformed(ActionEvent event) {
    		String curCardText, prompt, curStimulus;
    		int selectedIndex, userResponse;
    		Item curItem;
    		selectedIndex = cardsList.getSelectedIndex();
    		if (selectedIndex == -1) {
    			JOptionPane.showMessageDialog(null, "Select the card that you would like to delete.");
    			return;
    		}
    		curItem = cardsList.getSelectedValue();
    		curCardText = curItem.toString();
    		prompt = "Are you sure you want to delete: " + curCardText;
    		userResponse = JOptionPane.showConfirmDialog(null, prompt, prompt, JOptionPane.YES_NO_OPTION);
    		if (userResponse == JOptionPane.YES_OPTION) {
        		curStimulus = curCardText.split(" *\\|\\| *")[0];
    			curItem = studyList.find(curStimulus);
    			cardsListModel.remove(selectedIndex);
    			studyList.delete(curItem);
    			saved = false;
    		}
    	}
    }
    
    class MyLoadListener implements ActionListener {
    	public void actionPerformed(ActionEvent event) {
    		try {
    			studyList = new StudyList();
    			studyList.load();
    		}
    		catch (IOException e) {
    			JOptionPane.showMessageDialog(null, "An error occurred, please close and try again.");
    		}
    		Item card;
    		ArrayList<Item> tempList = studyList.getList();
			cardsListModel.clear();
    		for (int i = 0; i < tempList.size(); i++) {
    			card = tempList.get(i);
    			cardsListModel.addElement(card);
    		}
    	}
    }
    
    class MySaveListener implements ActionListener {
    	public void actionPerformed(ActionEvent event) {
    		try {
    			studyList.save();
    		}
    		catch (IOException e) {
    			JOptionPane.showMessageDialog(null, "An error occurred, please close and try again.");
    		}
    		saved = true;
    	}
    }
    
    class MySaveAsListener implements ActionListener {
    	public void actionPerformed(ActionEvent event) {
    		try {
    			studyList.saveAs();
    		}
    		catch (IOException e) {
    			JOptionPane.showMessageDialog(null, "An error occurred, please close and try again.");
    		}
    		saved = true;
    	}
    }
    
    class MyQuitListener implements ActionListener {
    	public void actionPerformed(ActionEvent event) {
    		closeEvent();
    	}
    }
    
    class MyWindowListener implements WindowListener {
    	@Override
    	public void windowClosing(WindowEvent arg0) {
    		closeEvent();
    	}
		public void windowOpened(WindowEvent arg0) {}
		public void windowClosed(WindowEvent arg0) {}
		public void windowIconified(WindowEvent arg0) {}
		public void windowDeiconified(WindowEvent arg0) {}
		public void windowActivated(WindowEvent arg0) {}
		public void windowDeactivated(WindowEvent arg0) {}
    }
    
    private void closeEvent() { 	
    	if (!saved) {
			String prompt = "Save before quitting?";
			int quitResponse = JOptionPane.showConfirmDialog(null, prompt);
			if (quitResponse == JOptionPane.YES_OPTION) {
				try {
					studyList.save();
					saved = true;
					this.dispose();
				}
				catch (IOException e){
					JOptionPane.showMessageDialog(null, "There was an error while saving.");
				}
			}
			else if (quitResponse == JOptionPane.NO_OPTION) {
				this.dispose();
			}
		}
    	else {
    		this.dispose();
    	}
    }
}
