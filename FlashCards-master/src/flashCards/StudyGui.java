/**
 * 
 */
package flashCards;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Martha Trevino
 */
@SuppressWarnings("serial")
public class StudyGui extends JFrame {

	/**
	 * 
	 */
	private StudyList studyList;
	private ArrayList<Item> flashCards;
	private Item currentFC;
	private boolean load;
	private boolean start;
	private boolean saved;
	private int nextFC;
	private JPanel panel;
	private JButton loadButton;
	private JButton saveButton;
	private JButton startButton;
	private JButton nextButton;
	private JTextField responseText;
	private JTextArea stimulusText;

	public StudyGui() {
		super("StudyGUI - FlashCards");
		studyList = new StudyList();
		load = false;
		start = true;
		saved = false;
	}

	public static void main(String[] args) {
		new StudyGui().createGui();
	}

	void createGui() {
		setLayout(new BorderLayout());

		panel = new JPanel();
		panel.setLayout(new GridLayout(4, 1));

		loadButton = new JButton("Load Study List");
		loadButton.setToolTipText("Click to load a Study List.");
		panel.add(loadButton);
		MyLoadListener loadListener = new MyLoadListener();
		loadButton.addActionListener(loadListener);

		saveButton = new JButton("Save Progress");
		saveButton.setToolTipText("Click to save your progress.");
		panel.add(saveButton);
		MySaveListener saveListener = new MySaveListener();
		saveButton.addActionListener(saveListener);

		startButton = new JButton("Start Studying");
		startButton.setToolTipText("Click to start studying.");
		panel.add(startButton);
		MyStartListener startListener = new MyStartListener();
		startButton.addActionListener(startListener);

		nextButton = new JButton("Next Flashcard");
		nextButton.setToolTipText("Click to load next flashcard.");
		panel.add(nextButton);
		MyNextListener nextListener = new MyNextListener();
		nextButton.addActionListener(nextListener);

		add(panel, BorderLayout.EAST);

		stimulusText = new JTextArea(
				"\n \n \t Load a Study List to start learning!", 10, 10);
		Font font = new Font("Verdana", Font.BOLD, 15);
		stimulusText.setFont(font);
		stimulusText.setForeground(Color.BLUE);
		stimulusText.setEditable(false);
		stimulusText.setToolTipText("Stimulus.");
		add(stimulusText, BorderLayout.CENTER);

		responseText = new JTextField(
				"Type your response here and press Enter.", 30);
		responseText.setToolTipText("Type your response here and press Enter.");
		add(responseText, BorderLayout.SOUTH);

		MyResponseListener responseListener = new MyResponseListener();
		responseText.addActionListener(responseListener);

		setSize(650, 400);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new MyWindowListener());
		setVisible(true);

	}

	private class MyResponseListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (load && !start) {
				saved = false;
				int timesCorrect = currentFC.getTimesCorrect();
				String responseUser = String.format(event.getActionCommand());

				if (responseUser.toLowerCase().equals(
						currentFC.getResponse().toLowerCase())) {
					currentFC.setTimesCorrect(timesCorrect + 1);
					stimulusText.setText("\n\n\tCorrect!\n\t"
							+ currentFC.getStimulus() + " : "
							+ currentFC.getResponse());
				} else {
					currentFC.setTimesCorrect(0);

					stimulusText
							.setText("\n\n\t The stimulus was: "
									+ currentFC.getStimulus()
									+ "\n\t The correct response is: "
									+ currentFC.getResponse()
									+ "\n Please type the correct response to continue: ");
				}
			}
		}
	}

	private class MyNextListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (load && !start) {
				saved = false;
				nextFC++;
				if (nextFC == flashCards.size()) {
					// flashCards.shuffle();
					nextFC = 0;
				}
				currentFC = flashCards.get(nextFC);
				int timesCorrect = currentFC.getTimesCorrect();
				int count = 0;
				while (timesCorrect >= 4 && count <= flashCards.size()) {
					nextFC++;
					count++;
					currentFC = flashCards.get(nextFC);
					timesCorrect = currentFC.getTimesCorrect();
				}
				if (count == flashCards.size()) {
					JOptionPane
							.showMessageDialog(null,
									"\n\n\tCongratulations!! \n\t You learned all your flashcards.");
				} else {
					stimulusText
							.setText("\n\n\n \t " + currentFC.getStimulus());
				}
			} else if (!load) {
				JOptionPane.showMessageDialog(null,
						"Please load a Study List first.");
			} else {
				JOptionPane.showMessageDialog(null,
						"Press Start to show first Flashcard.");
			}
		}
	}

	private class MySaveListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (load) {
				try {
					studyList.save();
					saved = true;
					JOptionPane.showMessageDialog(null,
							"Your progress has been saved.");

				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,
							"An error occurred, please close and try again.");
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"Please load a Study List before saving.");
			}
		}
	}

	private class MyLoadListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				studyList.load();
				flashCards = studyList.getList();
				if (!flashCards.isEmpty()) {
					load = true;
					saved = true;
					stimulusText
							.setText("\n\n\n \t Study list loaded! \n \t Press Start.");
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,
						"An error occurred, please close and try again.");
			}

		}
	}

	private class MyStartListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (load && start) {
				flashCards = studyList.getList();
				currentFC = flashCards.get(0);
				stimulusText.setText("\n\n\n \t " + currentFC.getStimulus());
				start = false;
			} else if (!load) {
				JOptionPane.showMessageDialog(null,
						"Please load a Study List to start studying.");
			}
		}
	}

	class MyWindowListener implements WindowListener {
		@Override
		public void windowClosing(WindowEvent event) {
			if (load && !saved) {
				int yesNo = JOptionPane.showConfirmDialog(null,
						"You want to save before quitting?");
				if (yesNo == JOptionPane.YES_OPTION) {
					try {
						studyList.save();
						saved = true;
						JOptionPane.showMessageDialog(null,
								"Your progress has been saved.");
						dispose();

					} catch (IOException e) {
						JOptionPane
								.showMessageDialog(null,
										"An error occurred, please close and try again.");
					}
				} else if (yesNo == JOptionPane.NO_OPTION) {
					dispose();
				}
			} else if (saved || start) {
				dispose();
			}

		}

		@Override
		public void windowActivated(WindowEvent arg0) {
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
		}
	}

}
