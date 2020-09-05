package game;

import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.BoxLayout;
import javax.swing.AbstractAction;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import javax.swing.Action;

public class Saves extends JPanel {
	private final Action action = new NewGame();
	private final Action action_1 = new Load();
	private final Action action_2 = new Exit();

	/**
	 * Create the panel.
	 */
	public Saves() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		Frame s = new Frame();
		JLayeredPane layeredPane = new JLayeredPane();
		add(layeredPane);
		
		JButton btnNewButton = new JButton("New Game");
		btnNewButton.setAction(action);
		btnNewButton.setBounds(148, 42, 89, 23);
		layeredPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Load");
		btnNewButton_1.setAction(action_1);
		btnNewButton_1.setBounds(148, 110, 89, 23);
		layeredPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Exit");
		btnNewButton_2.setAction(action_2);
		btnNewButton_2.setBounds(148, 178, 89, 23);
		layeredPane.add(btnNewButton_2);
		
		s.add(layeredPane);
		s.setVisible(true);

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
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
