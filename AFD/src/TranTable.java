import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.awt.event.ActionEvent;

public class TranTable extends JFrame {

	private JPanel contentPane;
	JTextField[][] data;
	//Mapas Hash para acceso a los indices de forma rapida
	private HashMap<String, Integer> symbolIndex;
	private HashMap<String, Integer> stateIndex;

	public TranTable(String[] arrayAlfabeto, String[] arrayEstados, String[] arrayEstadosFinales, String[] arrayCadenas) {
		symbolIndex = new HashMap<String, Integer>();
		stateIndex = new HashMap<String, Integer>();
		
		setTitle("Tabla de Transiciones");
		setType(Type.POPUP);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnContinuar = new JButton("Continuar >");
		btnContinuar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Verifica que todos los estados ingresados en la tabla sean validos
				for (int i = 0; i < arrayEstados.length; i++) {
					
					for (int j = 0; j < arrayAlfabeto.length; j++) {						
						String currentState = data[i][j].getText().trim();
						
						if(!stateIndex.containsKey(currentState)){
							JOptionPane.showMessageDialog(null, "Estado " + currentState + " no reconocido!", "Error!", JOptionPane.ERROR_MESSAGE);
							return;
						}												
					}	
				}
				
				AFDSimulator(arrayCadenas, arrayEstadosFinales, arrayAlfabeto, arrayEstados, data);

			}
		});
		btnContinuar.setBounds(320, 227, 104, 23);
		contentPane.add(btnContinuar);
		
		
		//Crea dinamicamente la tabla de transiciones
		//y llena los HashMap con los datos de la misma
		//para acceso rapido a los indices.
		
		data = new JTextField[arrayEstados.length][arrayAlfabeto.length];
		
		for (int i=0; i < arrayAlfabeto.length; i++) {			
			
			JLabel newLbl = new JLabel(arrayAlfabeto[i]);
			newLbl.setBounds(60 + i*55,5, 50,18);
			contentPane.add(newLbl);	
			
			symbolIndex.put(arrayAlfabeto[i], i);//HashMap<String, Integer>
		}
		
		
		for (int i = 0; i < arrayEstados.length; i++) {
			
			JLabel newLbl = new JLabel(arrayEstados[i]);
			newLbl.setBounds(5, 22 + i*24, 50, 18);
			contentPane.add(newLbl);						
			
			stateIndex.put(arrayEstados[i], i);//HashMap<String, Integer>
			
			for (int j = 0; j < arrayAlfabeto.length; j++) {
								
				JTextField newTxt = new JTextField();
				newTxt.setBounds(60 + j*55, 22 + i*24, 50, 18);
				contentPane.add(newTxt);
				
				data[i][j] = newTxt; //Arreglo de txtFields 
			}
		}		
	}
	
	private void AFDSimulator(String[] arrayCadenas, String[] arrayEstadosFinales, String[] arrayAlfabeto, String[] arrayEstados, JTextField[][] data){

		//Itera sobre cada cadena a evaluar
		for (int i = 0; i < arrayCadenas.length; i++) {
			Integer currentStateIndex = stateIndex.get(arrayEstados[0]); //Siempre el primer estado es el inicial
			boolean isAccepted = false; //Bandera para saber si las cadenas son aceptadas
			
			if(arrayCadenas[i].trim().length() > 0) { //Tratar de procesar solo si hay cadena
				String[] currentSymbols = arrayCadenas[i].split(""); //Arreglo con cada simbolo de la cadena actual
				
				//Itera sobre cada simbolo de la cadena actual
				for (int k = 0; k < currentSymbols.length; k++) {
									
					Integer currentSymbol = symbolIndex.get(currentSymbols[k]);
					
					currentStateIndex = stateIndex.get(data[currentStateIndex][currentSymbol].getText().trim());				
				}
			}
				
			//Verifica si el estado en el que quedo pertenece a los finales
			for (String estado : arrayEstadosFinales) {
				if(estado.equals(arrayEstados[currentStateIndex])){
					JOptionPane.showMessageDialog(null, "La cadena \"" + arrayCadenas[i] + "\" es aceptada!" , "Resultado", JOptionPane.PLAIN_MESSAGE);
					isAccepted = true;
				}
			}
			
			if(!isAccepted){
				JOptionPane.showMessageDialog(null, "La cadena \"" + arrayCadenas[i] + "\" no es aceptada!" , "Resultado", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
