package game;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import engine.GameDescription;

public class Manager extends JFrame {
	private final Action newGameAction = new NewGame();
	private final Action loadAction = new Load();
	private final Action actionStart = new Start();
	private final Action actionBack = new Back();
	private JLayeredPane layeredPane = new JLayeredPane();
	private Map<String, GameDescription> saves = new HashMap<String, GameDescription>();
	private HandleDB db;
	private GameDescription selected = null;
	private final Action deleteAction = new Delete();

	JPanel newGamePanel = new JPanel();
	JPanel mainPanel = new JPanel();
	String player;


	/**
	 * Create the panel.
	 */
	public Manager() throws Exception{

		db = new HandleDB();
		saves = db.recoveryTuple();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().add(layeredPane);

		//init mainPanel
		this.setTitle("Salvataggi Asylum Game");
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

		JButton btnNewButton_2 = new JButton("Delete");
		btnNewButton_2.setAction(deleteAction);
		btnNewButton_2.setBounds(262, 205, 89, 23);
		mainPanel.add(btnNewButton_2);

		//init lista dei salvataggi
		JList list = new JList();
		list.setBounds(52, 40, 299, 157);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(new DefaultListModel<String>());
		DefaultListModel<String> m = (DefaultListModel) list.getModel();
		mainPanel.add(list);
		mainPanel.setVisible(true);
		setVisible(true);
		layeredPane.setLayer(newGamePanel, 1);
		for(String k : saves.keySet())
			m.addElement(k);

		JLabel thumb = new JLabel(new javax.swing.ImageIcon(getClass().getResource("/header_manager.png")));
		thumb.setBounds(52,0,299,41);
		mainPanel.add(thumb);

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
			//Crea pannello per la creazione del giocatore
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
			JTextField name = (JTextField) newGamePanel.getComponent(1);
			player = name.getText();
			close();
		}
	}

	private class Load extends AbstractAction {
		public Load() {
			putValue(NAME, "Load");
			putValue(SHORT_DESCRIPTION, "Loading a save");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			String selectedSave = ((JList<String>) mainPanel.getComponent(3)).getSelectedValue();
			selected = saves.get(selectedSave);
			//seleziona nome giocatore
			player = selectedSave.split(" ")[0];
			close();
		}
	}

	public String getPlayer() {
		return player;
	}

	public GameDescription getSave() {
		return selected;
	}

	private class Back extends AbstractAction {
		public Back() {
			putValue(NAME, "Back");
			putValue(SHORT_DESCRIPTION, "Go back to main panel");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			//ripristina pannello principale
			setBounds(100, 100, 450, 300);
			newGamePanel.setVisible(false);
			mainPanel.setVisible(true);
		}
	}

	private void close() {
		this.dispose();
	}

	private class Delete extends AbstractAction {

		public Delete() {
			putValue(NAME, "Delete");
			putValue(SHORT_DESCRIPTION, "Delete the selected save");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String selectedPlayer = ((JList<String>) mainPanel.getComponent(3)).getSelectedValue();
				String[] tokens = selectedPlayer.split("\\s+");
				int index = ((JList<String>) mainPanel.getComponent(3)).getSelectedIndex();
				DefaultListModel<String> m = (DefaultListModel) ((JList<String>) mainPanel.getComponent(3)).getModel();
				m.remove(index);
				db.deleteTuple(tokens[0]);
			}catch (Exception e1) {
				// TODO: handle exception
				System.out.print(e1);
			}

		}
	}
}