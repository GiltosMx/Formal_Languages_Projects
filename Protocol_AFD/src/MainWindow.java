import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.Label;
import java.util.concurrent.TimeUnit;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	public static JTextPane ConsolePane;
	
	
	private static String[][] transitionsTable = {
				//RFNM_S  RFNM_E   ACK_S   ACK_R   ACK_N   MSG_S  TO_RST 
		/*q0*/	{"q1",    "q5",    "q5",   "q5",   "q5",   "q5",  "q0"},
		/*q1*/	{"q5",    "q2",    "q5",   "q5",   "q5",   "q5",  "q5"},
		/*q2*/	{"q5",    "q5",    "q3",   "q5",   "q5",   "q5",  "q5"},
		/*q3*/	{"q5",    "q5",    "q5",   "q5",   "q5",   "q4",  "q5"},
		/*q4*/	{"q5",    "q5",    "q5",   "q6",   "q3",   "q5",  "q5"},
		/*q5*/	{"q5",    "q5",    "q5",   "q5",   "q5",   "q5",  "q0"},
		/*q6*/  {"q0",    "q0",    "q0",   "q0",   "q0",   "q0",  "q0"}
	};

	private static String[] arrayAlfabeto = {"RFNM_S","RFNM_E","ACK_S", "ACK_R", "ACK_N", "MSG_S","TO_RST"};
	private static String[] arrayEstados = {"q0", "q1", "q2", "q3", "q4", "q5", "q6"};
	private static String[] arrayEstadosFinales = {"q0"};
	private static String[] arrayCadenas = {"RFNM_S", "RFNM_E", "ACK_S", "MSG_S", "ACK_R", "TO_RST"};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
					
					ProtocolBehavior protocolAFD = new ProtocolBehavior(transitionsTable, arrayAlfabeto, arrayEstados, arrayEstadosFinales, arrayCadenas, ConsolePane);
					
					
					protocolAFD.AFDSimulator();					
					System.out.println("\n" + protocolAFD.message);

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
		setTitle("Protocol_AFD");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 626, 397);
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
		btnPc.setIcon(new ImageIcon("/home/adrian/EclipseWorkspaces/Formal_Languages_Projects/Protocol_AFD/icons/ic_airplay_black_24dp_2x.png"));
		btnPc.setBounds(39, 35, 125, 64);
		contentPane.add(btnPc);
		
		JButton btnPc_1 = new JButton("PC 2");
		btnPc_1.setToolTipText("Configurar PC2");
		btnPc_1.setIcon(new ImageIcon("/home/adrian/EclipseWorkspaces/Formal_Languages_Projects/Protocol_AFD/icons/ic_airplay_black_24dp_2x.png"));
		btnPc_1.setBounds(442, 35, 130, 64);
		contentPane.add(btnPc_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 175, 617, 2);
		contentPane.add(separator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setToolTipText("");
		scrollPane.setBounds(9, 186, 605, 160);
		contentPane.add(scrollPane);
		
		ConsolePane = new JTextPane();
		ConsolePane.setToolTipText("Despliegue de mensajes de status");
		scrollPane.setViewportView(ConsolePane);
		
		Label ConsoleArea = new Label("Console Area");
		ConsoleArea.setBounds(250, 158, 97, 21);
		contentPane.add(ConsoleArea);
		
		
		
		
	}
}
