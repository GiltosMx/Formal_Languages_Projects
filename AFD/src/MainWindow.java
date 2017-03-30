import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MainWindow {

	//Atributos programación
	String[] arrayAlfabeto; 
	String[] arrayEstados;
	String[] arrayEstadosFinales;
	String[] arrayCadenas;
	
	//Atributos de la ventana
	private JFrame frmMainWindow;
	private JTextField txtAlfabeto;
	private JTextField txtEstados;
	private JTextField txtEstadosFinales;
	private JTextField txtCadenas;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmMainWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public MainWindow() {
		initialize();
	}

	
	private void initialize() {
		frmMainWindow = new JFrame();
		frmMainWindow.setTitle("Interprete de AFDs");
		frmMainWindow.setBounds(100, 100, 547, 429);
		frmMainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMainWindow.getContentPane().setLayout(null);
		
		JLabel lblAlfabeto = new JLabel("Introduce el alfabeto (separado por comas):");
		lblAlfabeto.setBounds(121, 11, 369, 14);
		frmMainWindow.getContentPane().add(lblAlfabeto);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 82, 480, 2);
		frmMainWindow.getContentPane().add(separator);
		
		txtAlfabeto = new JTextField();
		txtAlfabeto.setToolTipText("Por ejemplo: 0,1");
		txtAlfabeto.setBounds(71, 37, 364, 20);
		frmMainWindow.getContentPane().add(txtAlfabeto);
		txtAlfabeto.setColumns(10);
		
		JLabel lblEstados = new JLabel("Introduce los estados (separados por comas):");
		lblEstados.setBounds(121, 95, 369, 14);
		frmMainWindow.getContentPane().add(lblEstados);
		
		txtEstados = new JTextField();
		txtEstados.setToolTipText("Por ejemplo: q0, q1, q2");
		txtEstados.setBounds(71, 120, 364, 20);
		frmMainWindow.getContentPane().add(txtEstados);
		txtEstados.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 166, 480, 2);
		frmMainWindow.getContentPane().add(separator_1);
		
		JLabel lblEstFinales = new JLabel("Introduce cuales estados son finales (de los especificados arriba):");
		lblEstFinales.setBounds(67, 176, 440, 14);
		frmMainWindow.getContentPane().add(lblEstFinales);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 265, 1, 2);
		frmMainWindow.getContentPane().add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(10, 265, 480, 2);
		frmMainWindow.getContentPane().add(separator_3);
		
		JButton btnContinuar = new JButton("Continuar >");
		btnContinuar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Obtiene los simbolos del alfabeto e instancia el arreglo 
				//del alfabeto de size necesario
				String tmpString = txtAlfabeto.getText().trim();
				arrayAlfabeto = new String[tmpString.split(",").length];
				arrayAlfabeto = tmpString.split(",");
				
				//Obtiene cada estado e instancia el arreglo de estados
				//con size necesario
				tmpString = txtEstados.getText().trim();
				arrayEstados = new String[tmpString.split(",").length];
				arrayEstados = tmpString.split(",");
				
				//Obtiene cuales estados son finales e instancia el arreglo 
				//de estados finales con size necesario
				tmpString = txtEstadosFinales.getText().trim();
				arrayEstadosFinales = new String[tmpString.split(",").length];
				arrayEstadosFinales = tmpString.split(",");
				
				//Obtiene las cadenas a evualuar y las guarda en un arreglo de
				//cadenas de size necesario
				tmpString = txtCadenas.getText().trim();
				arrayCadenas = new String[tmpString.split(",").length];
				arrayCadenas = tmpString.split(",");
								
				//Mostrar la tabla de transiciones para ser llenada y evaluar las cadenas
				TranTable tablaFrm = new TranTable(arrayAlfabeto, arrayEstados, arrayEstadosFinales,arrayCadenas);				
				tablaFrm.setVisible(true);
			}
		});
		btnContinuar.setBounds(416, 356, 105, 23);
		frmMainWindow.getContentPane().add(btnContinuar);
		
		txtEstadosFinales = new JTextField();
		txtEstadosFinales.setToolTipText("Por ejemplo: q2,q0");
		txtEstadosFinales.setBounds(71, 213, 364, 20);
		frmMainWindow.getContentPane().add(txtEstadosFinales);
		txtEstadosFinales.setColumns(10);
		
		JLabel lblCadenas = new JLabel("Introduce cadenas a evaluar (separadas por comas):");
		lblCadenas.setBounds(88, 278, 402, 14);
		frmMainWindow.getContentPane().add(lblCadenas);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(10, 343, 480, 2);
		frmMainWindow.getContentPane().add(separator_4);
		
		txtCadenas = new JTextField();
		txtCadenas.setToolTipText("Por ejemplo: 001, 110");
		txtCadenas.setBounds(71, 303, 364, 20);
		frmMainWindow.getContentPane().add(txtCadenas);
		txtCadenas.setColumns(10);
	}
}
