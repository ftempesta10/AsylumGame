package game;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Manager extends JFrame {
	private final Action newGameAction = new NewGame();;
	private final Action loadAction = new Load();
	private final Action actionStart = new Start();
	private final Action actionBack = new Back();
	private JLayeredPane layeredPane = new JLayeredPane();
	JPanel newGamePanel = new JPanel();
	JPanel mainPanel = new JPanel();
	int value;


	/**
	 * Create the panel.
	 */
	public Manager() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().add(layeredPane);

		//init mainPanel
		mainPanel.setSize(414, 239);
		mainPanel.setLocation(10, 11);
		layeredPane.setLayer(mainPanel, 1);
		layeredPane.add(mainPanel);
		mainPanel.setLayout(null);

		JButton btnNewButton = new JButton("New Game");
		btnNewButton.setAction(newGameAction);
		btnNewButton.setBounds(62, 205, 93, 23);
		mainPanel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Load");
		btnNewButton_1.setAction(loadAction);
		btnNewButton_1.setBounds(176, 205, 67, 23);
		mainPanel.add(btnNewButton_1);

		JList list = new JList();
		list.setBounds(52, 37, 299, 157);
		mainPanel.add(list);
		mainPanel.setVisible(true);
		setVisible(true);
		layeredPane.setLayer(newGamePanel, 1);

		//init newGamePanel
		layeredPane.add(newGamePanel);
		newGamePanel.setSize(250, 200);
		newGamePanel.setLocation(10, 11);
		newGamePanel.setLayout(null);
		JLabel label = new JLabel("Name");
		label.setBounds(45, 20, 47, 14);
		newGamePanel.add(label);
		JTextField textField = new JTextField();
		textField.setBounds(100, 20, 133, 20);
		newGamePanel.add(textField);
		textField.setColumns(10);
		JButton startButton = new JButton("Start");
		JButton backButton = new JButton("Indietro");
		startButton.setBounds(50, 90, 80, 23);
		backButton.setBounds(160, 90, 71, 23);
		newGamePanel.add(backButton);
		backButton.setAction(actionBack);
		startButton.setAction(actionStart);

		newGamePanel.add(startButton);
		newGamePanel.setVisible(false);
	}

	private class NewGame extends AbstractAction {
		public NewGame() {
			putValue(NAME, "NewGame");
			putValue(SHORT_DESCRIPTION, "Start a new game");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			setBounds(100, 100, 300, 230);
			mainPanel.setVisible(false);
			newGamePanel.setVisible(true);
		}
	}

	//usata nel button new game
	private class Start extends AbstractAction {
		public Start() {
			putValue(NAME, "Start");
			putValue(SHORT_DESCRIPTION, "Start the game");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			//INIZIA GIOCO
			value = 1;
		}
	}

	private class Load extends AbstractAction {
		public Load() {
			putValue(NAME, "Load");
			putValue(SHORT_DESCRIPTION, "Loading a save");
		}
		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	public int getValue() {
		return value;
	}

	private class Back extends AbstractAction {
		public Back() {
			putValue(NAME, "Back");
			putValue(SHORT_DESCRIPTION, "Go back to main panel");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			setBounds(100, 100, 450, 300);
			newGamePanel.setVisible(false);
			mainPanel.setVisible(true);
		}
	}
}
