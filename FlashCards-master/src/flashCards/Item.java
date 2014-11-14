/**
 * 
 */
package flashCards;

/**
 * @authors David Matuszek, Martha Trevino & Josh Taylor 
 */
public class Item {

    private String stimulus;
    private String response;
    private int timesCorrect;
    
    public Item(String stimulus, String response) {
        this.stimulus = stimulus;
        this.response = response;
        this.timesCorrect = 0;
    }
    
    public Item(String stimulus, String response, int timesCorrect) {
        this.stimulus = stimulus;
        this.response = response;
        this.timesCorrect = timesCorrect;
    }
    
    public String getStimulus() {
        return this.stimulus;
    }
    
    public void setStimulus(String stimulus) {
        this.stimulus = stimulus;
    }
    
    public String getResponse() {
        return this.response;
    }
    
    public void setResponse(String response) {
        this.response = response;
    }
    
    public int getTimesCorrect() {
        return this.timesCorrect;
    }
    
    public void setTimesCorrect(int times) {
    	if (times < 0) {
    		throw new IllegalArgumentException();
    	}
        this.timesCorrect = times;
    }
}
