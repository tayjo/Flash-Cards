/**
 * 
 */
package flashCards;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * @author Martha Trevino
 */
public class StudyGui extends JFrame {

	private StudyList studyList;
	private boolean load;
	private boolean saved;
	private JPanel panel;
	private JLabel title;
	private JButton loadButton;
	private JButton saveButton;
	private JTextField responseText;
	private JTextArea stimulusText;

	public StudyGui() {
		super("StudyGUI - FlashCards");
		studyList = new StudyList();
		load = false;
		saved = false;
	}

	public static void main(String[] args) {
		new StudyGui().createGui();
	}

	void createGui() {
		setLayout(new BorderLayout());

		panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));

		// title = new JLabel("Welcome to your StudyGUI");
		// add(title, BorderLayout.NORTH);

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	private class MyResponseListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			String stimulusUser = String.format(event.getActionCommand());

			JOptionPane.showMessageDialog(null, stimulusUser);

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
						"Please load a study list before saving.");
			}
		}
	}

	private class MyLoadListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				studyList.load();
				load = true;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,
						"An error occurred, please close and try again.");
			}

		}
	}

}
