import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.JScrollPane;
import java.awt.Label;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.SystemColor;

public class MainWindow extends JFrame {

									//Atributos
	//---------------------------------------------------------------------//
	private JPanel contentPane;
	public static JTextPane ConsolePane;
	private JLabel lblErrorStatus;
	private static JLabel lblCurrentBehavior;
	private static JButton btnPc1;
	private static JButton btnPc2;
	private static JButton btnRequestMessagePC1;
	private static JButton btnRequestMessagePC2;
	private static AFD_Status afd_status;
	private static BehaviorChooser behaviorChooserWindow;
	
	private static String message_PC1;
	private static String message_PC2;
	private static int requestingPC = 0;
	
	
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
	private static String[] arrayCadenas = {"RFNM_S", "RFNM_E", "ACK_S", "MSG_S","ACK_R", "TO_RST"};
	
	//Se usa como apuntador al arreglo de cadenas que se usara en el automata dependiendo del comportamiento.
	private static String[] arrayCadenas_Changed = new String[11];
	//---------------------------------------------------------------------//

	
	/**
	 * Inicia la aplicacion.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					afd_status = new AFD_Status();
					
					//Hace que la ventana se cree al centro de la pantalla
					afd_status.setLocationRelativeTo(null);
					
					frame.setVisible(true);
					afd_status.setVisible(true);		
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructor del frame de la ventana principal.
	 */
	public MainWindow() {
		setTitle("Protocol_AFD");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 626, 397);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblCurrentBehavior = new JLabel("Behavior: Normal");
		lblCurrentBehavior.setForeground(SystemColor.activeCaption);
		lblCurrentBehavior.setBounds(250, 130, 140, 15);
		contentPane.add(lblCurrentBehavior);
		
		//Al inicio, se tiene por defecto el comportamiento normal
		behaviorChooserWindow = new BehaviorChooser(lblCurrentBehavior);
		arrayCadenas_Changed = behaviorChooserWindow.normal_Behavior;
		
		btnPc1 = new JButton("PC 1");
		btnPc1.setToolTipText("Configurar PC1");
		btnPc1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				message_PC1 = JOptionPane.showInputDialog(null, "Enter message to be stored in this PC:", "Config PC", JOptionPane.QUESTION_MESSAGE);
			}
		});
		btnPc1.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/ic_airplay_black_24dp_2x.png")));
		btnPc1.setBounds(39, 15, 125, 64);
		contentPane.add(btnPc1);
		
		btnPc2 = new JButton("PC 2");
		btnPc2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				message_PC2 = JOptionPane.showInputDialog(null, "Enter message to be stored in this PC:", "Config PC", JOptionPane.QUESTION_MESSAGE);
			}
		});
		btnPc2.setToolTipText("Configurar PC2");
		btnPc2.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/ic_airplay_black_24dp_2x.png")));
		btnPc2.setBounds(442, 15, 130, 64);
		contentPane.add(btnPc2);
		
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
		
		btnRequestMessagePC1 = new JButton("Request Message");
		btnRequestMessagePC1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				requestingPC = 1;
				startRequest();
			}
		});
		btnRequestMessagePC1.setToolTipText("Pedir mensaje de la PC2");
		btnRequestMessagePC1.setBounds(29, 100, 150, 25);
		contentPane.add(btnRequestMessagePC1);
		
		btnRequestMessagePC2 = new JButton("Request Message");
		btnRequestMessagePC2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				requestingPC = 2;
				startRequest();
			}
		});
		btnRequestMessagePC2.setToolTipText("Pedir mensaje de la PC1");
		btnRequestMessagePC2.setBounds(432, 100, 150, 21);
		contentPane.add(btnRequestMessagePC2);
		
		JButton btnErrorSimulation = new JButton("Toggle Error Simulation");
		btnErrorSimulation.setToolTipText("Alternar simulacion de errores");
		btnErrorSimulation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Si esta desactivada la simulacion de errores, se pone un error
				//eliminando el mensaje en la posicion 2 del arreglo
				if(lblErrorStatus.getText().length() == 0){
					lblErrorStatus.setText("ERROR SIMULATION ENABLED");
					
					//arrayCadenas = {"RFNM_S", "RFNM_E", "ACK_S", "MSG_S", "ACK_R", "TO_RST"};
					
					arrayCadenas[2] = "";
				}
				else{
					lblErrorStatus.setText("");
					
					arrayCadenas[2] = "ACK_S";
				}
				
			}
		});
		btnErrorSimulation.setBounds(217, 40, 175, 25);
		contentPane.add(btnErrorSimulation);
		
		lblErrorStatus = new JLabel("");
		lblErrorStatus.setForeground(Color.RED);
		lblErrorStatus.setBounds(217, 17, 200, 15);
		contentPane.add(lblErrorStatus);
		
		JButton btnChangeBehavior = new JButton("Change Behavior");
		btnChangeBehavior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				behaviorChooserWindow.setVisible(true);				
			}
		});
		btnChangeBehavior.setToolTipText("Elegir el comportamiento del protocolo");
		btnChangeBehavior.setBounds(235, 90, 140, 25);
		contentPane.add(btnChangeBehavior);
	}
	
	
	/**
	 * Se encarga de instanciar el objeto ProtocolBehavior, que es el que le da
	 * la funcionalidad al programa.
	 */
	private void startRequest(){
		
		//Cambia el arreglo con los mensajes del protocolo dependiendo del comportamiento elegido 
		switch (behaviorChooserWindow.chosenBehavior) {
		case 1:		
			arrayCadenas_Changed = behaviorChooserWindow.resendOneTime_Behavior;
			break;
		case 2:
			arrayCadenas_Changed = behaviorChooserWindow.resendTwoTimes_Behavior;
			break;
		default:
			arrayCadenas_Changed = behaviorChooserWindow.normal_Behavior;
			break;
		}
		
		//Si está activa la simulación de errores, se cambia el arreglo con los 
		//mensajes del protocolo por uno con errores
		if(lblErrorStatus.getText().length() > 0){
			arrayCadenas_Changed = arrayCadenas;
		}
		
		ProtocolBehavior protocolAFD = new ProtocolBehavior(transitionsTable, arrayAlfabeto, arrayEstados, arrayEstadosFinales, 
				arrayCadenas_Changed, ConsolePane, message_PC1, message_PC2, requestingPC, 
				btnPc1, btnPc2, afd_status, lblErrorStatus, btnRequestMessagePC1,btnRequestMessagePC2);
		
		afd_status.updateStates(0);
		
		//Desactiva los botones para evitar que se haga mas de una solicitud
		//de mensaje al mismo tiempo
		btnRequestMessagePC1.setEnabled(false);
		btnRequestMessagePC2.setEnabled(false);
		
		protocolAFD.AFDSimulator();
	}
}
