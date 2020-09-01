package engine.launcher;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Launcher extends JFrame {

	private JPanel contentPane;
	static Locale locale = Locale.getDefault();
	private JList<String> list;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Launcher frame = new Launcher();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Launcher() {
		setTitle("Launcher");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		ResourceBundle bundle = ResourceBundle.getBundle("LauncherGUI", locale);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu(bundle.getString("Lbl_lang"));
		menuBar.add(mnNewMenu);

		JRadioButton ItalianRadioButton = new JRadioButton(bundle.getString("launcher.ItalianRadioButton.text")); //$NON-NLS-1$
		ItalianRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				locale = Locale.ITALIAN;
				dispose();
				main(new String[] {});
			}
		});
		mnNewMenu.add(ItalianRadioButton);
		JRadioButton EnglishRadioButton = new JRadioButton(bundle.getString("launcher.EnglishRadioButton.text")); //$NON-NLS-1$
		EnglishRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				locale = Locale.ENGLISH;
				dispose();
				main(new String[] {});
			}
		});
		mnNewMenu.add(EnglishRadioButton);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel(bundle.getString("Lbl_choose"));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);

		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(new DefaultListModel<String>());
		list.setBorder(UIManager.getBorder("FileChooser.listViewBorder"));
		contentPane.add(list, BorderLayout.CENTER);

		JButton launchButton = new JButton(bundle.getString("launcher.launchButton.text")); //$NON-NLS-1$
		launchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println(list.getSelectedValue());
			}
		});
		contentPane.add(launchButton, BorderLayout.SOUTH);
		File games = new File("./games");
		DefaultListModel<String> m = (DefaultListModel) list.getModel();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(null, "jar");
		for(File g : games.listFiles()) {
			if(filter.accept(g))
				m.addElement(g.getName());
		}
	}

}
