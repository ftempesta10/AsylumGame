package game;

import javax.swing.JPanel;

import javax.swing.JButton;
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
