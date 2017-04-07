import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class ProtocolBehavior {
	
									//Atributos
	//---------------------------------------------------------------------//
	public String message;
	private JTextPane ConsolePane;
	
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
		isAccepted = false;
		this.message = new String();
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
		
//		System.out.println("symbolIndex:\n\n" + symbolIndex.toString());
		
		for (int i = 0; i < arrayEstados.length; i++) {
			stateIndex.put(arrayEstados[i], i);
			
		}	
		
//		System.out.println("stateIndex:" + stateIndex.toString());
	}
	
	public void AFDSimulator(){
		
		Integer currentStateIndex = stateIndex.get(arrayEstados[0]); //Siempre el primer estado es el inicial
		isAccepted = false; //Bandera para saber si las cadenas son aceptadas
		
		//Itera sobre cada mensaje a evaluar
		for (int i = 0; i < arrayCadenas.length; i++) {
			
			if(arrayCadenas[i].trim().length() > 0) { //Tratar de procesar solo si hay mensaje
													
				Integer currentSymbol = symbolIndex.get(arrayCadenas[i]);
				
				message += arrayCadenas[i] + "...\n";
				
				ConsolePane.setText(message);
				
				
				currentStateIndex = stateIndex.get(transitionsTable[currentStateIndex][currentSymbol].trim());
				
				if(currentStateIndex == 5){
					
					message += "Hubo un error. Espere...\n";
					
				}else if(currentStateIndex == 6){
					
					message += "El mensaje se recibio correctamente!\n";
				}
				
			}
				
			//Verifica si el estado en el que quedo pertenece a los finales
			for (String estado : arrayEstadosFinales) {
				if(estado.equals(arrayEstados[currentStateIndex])){					
					isAccepted = true;
					
					
				}
			}
		}
	}

	

}
