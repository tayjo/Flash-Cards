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
    private DefaultListModel<String> cardsListModel;
    private JList<String> cardsList;
    
    public EditorGui() {
    	this.saved = true;
    	studyList = new StudyList();
    }
    
    public static void main(String[] args) {
    	new EditorGui().createGui();
    }

    void createGui() {
    	
    	this.setSize(600, 400);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	this.setLayout(new BorderLayout());
    	
    	JPanel fileManager = new JPanel();
    	fileManager.setLayout(new GridLayout(1, 4));
    	
    	load = new JButton("Load");
    	load.addActionListener(new MyLoadListener());
    	fileManager.add(load);
    	
    	save = new JButton("Save");
    	fileManager.add(save);
    	
    	saveAs = new JButton("Save As");
    	saveAs.addActionListener(new MySaveAsListener());
    	fileManager.add(saveAs);
    	
    	quit = new JButton("Quit");
    	fileManager.add(quit);
    	
    	fileManager.setPreferredSize(new Dimension(0, 50));
    	this.add(fileManager, BorderLayout.NORTH);
    	
    	JPanel cardManager = new JPanel();
    	cardManager.setLayout(new BorderLayout());
    	
    	cardsListModel = new DefaultListModel<String>();
    	cardsList = new JList<String>(cardsListModel);
    	
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
    		String pattern = "^(\\S+( \\|\\| ){1}\\S+( \\|\\| \\d+)?)";
    		String prompt, newCardText, stimulus, response;
    		int timesCorrect;
    		String[] cardParts;
			prompt = "Enter card in the form: stimulus || response || timesCorrect(optional)";
    		while (true){
	    		newCardText = JOptionPane.showInputDialog(null, prompt);
	    		if (newCardText != null) {
		    		newCardText = newCardText.trim();
		    		if (newCardText.matches(pattern)) {
		    			cardParts = newCardText.split(" *\\|\\| *");
		    			stimulus = cardParts[0];
		    			if (studyList.find(stimulus) != null) {
		    				JOptionPane.showMessageDialog(null, "A card with that stimulus already exists.");
		    				continue;
		    			}
		    			response = cardParts[1];
		    			if (cardParts.length > 2) {
			    			timesCorrect = Integer.parseInt(cardParts[2]);
			    			studyList.add(new Item(stimulus, response, timesCorrect));
			    			cardsListModel.addElement(stimulus + " || " + response + " || " + timesCorrect);
		    			}
		    			else {
		    				studyList.add(new Item(stimulus, response));
		    				cardsListModel.addElement(stimulus + " || " + response);
		    			}
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
    		String pattern = "^(\\S+( \\|\\| ){1}\\S+( \\|\\| \\d+)?)";
    		String curCardText, curStimulus, prompt, newCardText, stimulus, response;
    		String[] cardParts;
    		int selectedIndex, timesCorrect;
    		Item curItem, findItem;
    		selectedIndex = cardsList.getSelectedIndex();
    		if (selectedIndex == -1) {
    			JOptionPane.showMessageDialog(null, "Select the card that you would like to edit.");
    			return;
    		}
    		curCardText = cardsList.getSelectedValue();
    		curStimulus = curCardText.split(" *\\|\\| *")[0];
    		curItem = studyList.find(curStimulus);
			prompt = "Enter card in the form: stimulus || response || timesCorrect(optional)";
    		while (true){
	    		newCardText = JOptionPane.showInputDialog(null, prompt, curCardText);
	    		if (newCardText != null) {
		    		newCardText = newCardText.trim();
		    		if (newCardText.matches(pattern)) {
		    			cardParts = newCardText.split(" *\\|\\| *");
		    			stimulus = cardParts[0];
		    			findItem = studyList.find(stimulus);
		    			if (findItem != null && (findItem != curItem)) {
		    				JOptionPane.showMessageDialog(null, "Another card with that stimulus already exists.");
		    				continue;
		    			}
		    			response = cardParts[1];
		    			if (cardParts.length > 2) {
			    			timesCorrect = Integer.parseInt(cardParts[2]);
			    			studyList.modify(curItem, stimulus, response);
			    			cardsListModel.setElementAt(stimulus + " || " + response + " || " + timesCorrect, selectedIndex);
		    			}
		    			else {
			    			studyList.modify(curItem, stimulus, response);
			    			cardsListModel.setElementAt(stimulus + " || " + response, selectedIndex);
		    			}
		    			saved = false;
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
    		search = JOptionPane.showInputDialog(null, "Enter search term (stimulus or response)");
    		for (int i = 0; i < cardsListModel.size(); i++) {
    			if (cardsListModel.getElementAt(i).contains(search)) {
    				cardsList.setSelectedIndex(i);
    				return;
    			}
    		}
    		JOptionPane.showMessageDialog(null, "Search term not found.");
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
    		curCardText = cardsList.getSelectedValue();
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
    			studyList.load();
    		}
    		catch (IOException e) {
    			JOptionPane.showMessageDialog(null, "An error occurred, please close and try again.");
    		}
    		Item card;
    		ArrayList<Item> tempList = studyList.getList();
    		String stimulus, response;
    		int timesCorrect;
    		for (int i = 0; i < tempList.size(); i++) {
    			card = tempList.get(i);
    			stimulus = card.getStimulus();
    			response = card.getResponse();
    			timesCorrect = card.getTimesCorrect();
    			cardsListModel.addElement(stimulus + " || " + response + " || " + timesCorrect);
    		}
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
}
