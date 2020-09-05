package game;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JTextField;

public class Manager extends JFrame {
	private final Action action;
	private final Action action_1 = new Load();
	private final Action action_2 = new Exit();
	private JLayeredPane layeredPane;

	/**
	 * Create the panel.
	 */
	public Manager() {
		//setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		//Frame s = new Frame();
		action = new NewGame();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		layeredPane = new JLayeredPane();
		getContentPane().add(layeredPane);

		JButton btnNewButton = new JButton("New Game");
		btnNewButton.setAction(action);
		btnNewButton.setBounds(57, 213, 89, 23);
		layeredPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Load");
		btnNewButton_1.setAction(action_1);
		btnNewButton_1.setBounds(156, 213, 89, 23);
		layeredPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Exit");
		btnNewButton_2.setAction(action_2);
		btnNewButton_2.setBounds(290, 213, 89, 23);
		layeredPane.add(btnNewButton_2);

		JList list = new JList();
		list.setBounds(57, 30, 313, 156);
		layeredPane.add(list);
		setVisible(true);

	}

	private class NewGame extends AbstractAction {
		public NewGame() {
			putValue(NAME, "NewGame");
			putValue(SHORT_DESCRIPTION, "Start a new game");
		}
		public void actionPerformed(ActionEvent e) {
			JFrame frame;
			JTextField textField;
			final Action action = new Start();
			/**
			 * Initialize the contents of the frame.
			 */

			frame = new JFrame();
			frame.setBounds(100, 100, 450, 300);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.getContentPane().setLayout(null);
			JLabel label = new JLabel("Name");
			label.setBounds(78, 102, 67, 47);
			frame.getContentPane().add(label);
			textField = new JTextField();
			textField.setBounds(145, 115, 155, 20);
			frame.getContentPane().add(textField);
			textField.setColumns(10);
			JButton btnNewButton = new JButton("Start");
			btnNewButton.setAction(action);
			btnNewButton.setBounds(145, 146, 89, 23);
			frame.getContentPane().add(btnNewButton);
			frame.setVisible(true);
		}
	}

	//usata nel button new game
	private class Start extends AbstractAction {
		public Start() {
			putValue(NAME, "Start");
			putValue(SHORT_DESCRIPTION, "Start the game");
		}
		public void actionPerformed(ActionEvent e) {
			//INIZIA GIOCO
		}
	}

	private class Load extends AbstractAction {
		public Load() {
			putValue(NAME, "Load");
			putValue(SHORT_DESCRIPTION, "Loading a save");
		}
		public void actionPerformed(ActionEvent e) {

		}
	}

	private class Exit extends AbstractAction {
		public Exit() {
			putValue(NAME, "Exit");
			putValue(SHORT_DESCRIPTION, "Exit");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
