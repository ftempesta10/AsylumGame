package engine.launcher;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

public class launcher extends JFrame {

	private JPanel contentPane;
	Locale locale = Locale.getDefault();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					launcher frame = new launcher();
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
	public launcher() {
		setTitle("Launcher");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		ResourceBundle bundle = ResourceBundle.getBundle("LauncherGUI", locale);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu(bundle.getString("Lbl_lang"));
		menuBar.add(mnNewMenu);

		JRadioButton ItalianRadioButton = new JRadioButton(bundle.getString("launcher.ItalianRadioButton.text")); //$NON-NLS-1$
		mnNewMenu.add(ItalianRadioButton);
		JRadioButton EnglishRadioButton = new JRadioButton(bundle.getString("launcher.EnglishRadioButton.text")); //$NON-NLS-1$
		mnNewMenu.add(EnglishRadioButton);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel(bundle.getString("Lbl_choose"));
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
	}

}
