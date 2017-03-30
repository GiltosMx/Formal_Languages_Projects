import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.Label;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	public JTextPane ConsolePane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
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
	public MainWindow() {
		setResizable(false);
		setTitle("Protocol_AFD");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 601, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnPc = new JButton("PC 1");
		btnPc.setToolTipText("Configurar PC1");
		btnPc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnPc.setIcon(new ImageIcon("C:\\Users\\adria\\Documents\\Eclipse\\Lenguajes de Computacion\\Protocol_AFD\\icons\\ic_airplay_black_24dp_2x.png"));
		btnPc.setBounds(39, 35, 107, 64);
		contentPane.add(btnPc);
		
		JButton btnPc_1 = new JButton("PC 2");
		btnPc_1.setIcon(new ImageIcon("C:\\Users\\adria\\Documents\\Eclipse\\Lenguajes de Computacion\\Protocol_AFD\\icons\\ic_airplay_black_24dp_2x.png"));
		btnPc_1.setBounds(442, 35, 107, 64);
		contentPane.add(btnPc_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 175, 593, 2);
		contentPane.add(separator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 186, 583, 160);
		contentPane.add(scrollPane);
		
		ConsolePane = new JTextPane();
		scrollPane.setViewportView(ConsolePane);
		
		Label ConsoleArea = new Label("Console Area");
		ConsoleArea.setBounds(246, 158, 74, 22);
		contentPane.add(ConsoleArea);
		
		
		
		
	}
}
