/**
 * 
 */
package flashCards;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author David Matuszek, Martha Trevino & Josh Taylor
 */
public class StudyList {

	private ArrayList<Item> flashCards = new ArrayList<Item>();
	
    public StudyList() {
        // TODO Auto-generated constructor stub
    }
    
    public void add(Item item) {
    	this.flashCards.add(item);
 
    }
    
    public Item find(String stimulusOrResponse) {
        return null;
    }
    
    public void delete(Item item) {
    	this.flashCards.remove(item);
    }
    
    public void modify(Item item, String newStimulus, String newResponse) {
        
    }
    
    public void load() throws IOException {
        
    }
    
    public void save() throws IOException {
        
    }
    
    public void saveAs() throws IOException {
        
    }
}
