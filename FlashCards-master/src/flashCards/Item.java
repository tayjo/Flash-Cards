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

	public boolean userInputCorrect(String userInput) {
		return this.getResponse().toLowerCase().equals(userInput);
	}

	public String gotCorrect() {
		this.setTimesCorrect(this.getTimesCorrect() + 1);
		return "\n\n\tCorrect!\n\t" + this.getStimulus() + " : "
				+ this.getResponse();
	}

	public String gotIncorrect(String userInput) {
		this.setTimesCorrect(0);
		return "\n\n\t The stimulus was: " + this.getStimulus()
				+ "\n\t You typed: " + userInput
				+ "\n\t The correct response is: " + this.getResponse()
				+ "\n\n\t Please type the correct \n\t response to continue: ";
	}

	public String toString() {
		return stimulus + " || " + response + " || " + timesCorrect;
	}
}
