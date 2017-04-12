import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.Timer;
import javax.swing.JTextPane;

public class ProtocolBehavior {
	
									//Atributos
	//---------------------------------------------------------------------//
	public String[] statusMessage;
	private JTextPane ConsolePane;
	private Timer timer;
	private int messageIndex;
	private StringBuilder consoleText;
	
	private String[][] transitionsTable;
	private String[] arrayAlfabeto;
	private String[] arrayEstados;
	private String[] arrayEstadosFinales;
	private String[] arrayCadenas;
	
	private boolean isAccepted;
	
	//Mapas Hash para acceso a los indices de forma rapida
	private HashMap<String, Integer> symbolIndex;
	private HashMap<String, Integer> stateIndex;
	
	//---------------------------------------------------------------------//
	
	public ProtocolBehavior(String[][] transitionsTable, String[] arrayAlfabeto, String[] arrayEstados, String[] arrayEstadosFinales, String[] arrayCadenas, JTextPane ConsolePane){
		
		timer = new Timer(4000, updateConsole);
		timer.start();
		
		messageIndex = 0;
		isAccepted = false;
		consoleText = new StringBuilder();
		this.statusMessage = new String[7];
		this.ConsolePane = ConsolePane;
		
		this.transitionsTable = transitionsTable;
		this.arrayAlfabeto = arrayAlfabeto;
		this.arrayEstados = arrayEstados;
		this.arrayEstadosFinales = arrayEstadosFinales;
		this.arrayCadenas = arrayCadenas;
		
		symbolIndex = new HashMap<String, Integer>();
		stateIndex = new HashMap<String, Integer>();
		FillHashMaps();
	}
	
	
	private void FillHashMaps(){
		
		for (int i = 0; i < arrayAlfabeto.length; i++) {			
			symbolIndex.put(arrayAlfabeto[i], i);		
			
		}
		
		for (int i = 0; i < arrayEstados.length; i++) {
			stateIndex.put(arrayEstados[i], i);
			
		}	
	}
	
	public void AFDSimulator(){
		
		Integer currentStateIndex = stateIndex.get(arrayEstados[0]); //Siempre el primer estado es el inicial
		isAccepted = false; //Bandera para saber si las cadenas son aceptadas
		
		//Itera sobre cada mensaje a evaluar
		for (int i = 0; i < arrayCadenas.length; i++) {
			
			if(arrayCadenas[i].trim().length() > 0) { //Tratar de procesar solo si hay mensaje
													
				Integer currentSymbol = symbolIndex.get(arrayCadenas[i]);
				
				statusMessage[messageIndex] = arrayCadenas[i] + "...\n";
				messageIndex++;
				
				currentStateIndex = stateIndex.get(transitionsTable[currentStateIndex][currentSymbol].trim());
				
				if(currentStateIndex == 5){
					
					statusMessage[messageIndex] = "Hubo un error. Espere...\n";
					messageIndex++;					
					
				}else if(currentStateIndex == 6){
					
					statusMessage[messageIndex] = "El mensaje se recibio correctamente!\n";
					messageIndex++;					
				}
				
			}
				
			//Verifica si el estado en el que quedo pertenece a los finales
			for (String estado : arrayEstadosFinales) {
				if(estado.equals(arrayEstados[currentStateIndex])){					
					isAccepted = true;
				}
			}
		}
		messageIndex = 0;
	}

	ActionListener updateConsole = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(messageIndex < statusMessage.length){
				consoleText.append(statusMessage[messageIndex]);
				messageIndex++;
			}
			else{
				timer.stop();
			}
			
			ConsolePane.setText(consoleText.toString());
		}
	};

}
