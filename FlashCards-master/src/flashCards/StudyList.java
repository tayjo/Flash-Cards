/**
 * 
 */
package flashCards;

import java.io.IOException;
import java.util.ArrayList;

import simpleIO.SimpleIO;

/**
 * @author David Matuszek, Martha Trevino & Josh Taylor
 */
public class StudyList {

	private ArrayList<Item> flashCards;
	
    public StudyList() {
        flashCards = new ArrayList<Item>();
    }
    
    public ArrayList<Item> getList() {
    	return flashCards;
    }
    
    public void add(Item item) {
    	if (this.find(item.getStimulus()) != null) {
    		throw new IllegalArgumentException();
    	}
    	this.flashCards.add(item);
    }
    
    public Item find(String stimulusOrResponse) {
        Item card;
    	for (int i = 0; i < flashCards.size(); i++){
        	card = flashCards.get(i);
        	if (card.toString().toLowerCase().contains(stimulusOrResponse.toLowerCase())) {
        		return card;
        	}
        }
    	return null;
    }
    
    public void delete(Item item) {
    	this.flashCards.remove(item);
    }
    
    public void modify(Item item, String newStimulus, String newResponse) {
    	if (newStimulus.toLowerCase() != item.getStimulus().toLowerCase()) {
    		if (isDuplicateStimulus(newStimulus)) {
    			throw new IllegalArgumentException();
    		}
    	}
        item.setStimulus(newStimulus);
        item.setResponse(newResponse);
    }
    
    public void load() throws IOException {
        ArrayList<String> lines = SimpleIO.load();
		String pattern = "^(\\S+( \\|\\| ){1}\\S+( \\|\\| \\d+)?)";
		String line, stimulus, response;
		String[] lineParts;
		int timesCorrect;
        if (lines == null) {
        	return;
        }
        for (int i = 0; i < lines.size(); i++) {
        	line = lines.get(i);
        	if (!line.matches(pattern)) {
        		throw new IOException();
        	}
        	lineParts = line.split(" *\\|\\| *");
        	stimulus = lineParts[0];
        	response = lineParts[1];
        	if (lineParts.length > 2) {
        		timesCorrect = Integer.parseInt(lineParts[2]);
        		this.add(new Item(stimulus, response, timesCorrect));
        	}
        	else {
        		this.add(new Item(stimulus, response));
        	}
        }
    }
    
    public void save() throws IOException {
    	ArrayList<String> flashCardsStr = new ArrayList<String>();
    	Item card;
    	for (int i = 0; i < flashCards.size(); i++) {
    		card = flashCards.get(i);
    		flashCardsStr.add(card.toString());
    	}
        SimpleIO.save(flashCardsStr);
    }
    
    public void saveAs() throws IOException {
    	ArrayList<String> flashCardsStr = new ArrayList<String>();
    	Item card;
    	for (int i = 0; i < flashCards.size(); i++) {
    		card = flashCards.get(i);
    		flashCardsStr.add(card.toString());
    	}
        SimpleIO.saveAs(flashCardsStr);
    }
    
    public boolean isDuplicateStimulus (String newStimulus) {
		for (int i = 0; i < flashCards.size(); i++) {
			if (newStimulus.toLowerCase().equals(flashCards.get(i).getStimulus().toLowerCase())) {
	    		return true;
			}
		}
		return false;
    }
}
