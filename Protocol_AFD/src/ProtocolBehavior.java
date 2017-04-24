import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

public class ProtocolBehavior {
	
									//Atributos
	//---------------------------------------------------------------------//
	private String[] statusMessage;
	private int requestingPC;
	private JTextPane ConsolePane;
	private Timer timer;
	private int messageIndex;
	private StringBuilder consoleText;
	private String message_PC1;
	private String message_PC2;
	private JButton btnPc1;
	private JButton btnPc2;
	private JLabel lblErrorStatus;
	
	private AFD_Status afd_status;
	
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
	
	/**
	 * Constructor de la clase. Inicializa todos los objetos necesarios para el
	 * funcionamiento del simulador del protocolo.
	 * @param transitionsTable Tabla de transiciones del automata.
	 * @param arrayAlfabeto Arreglo con cada simbolo del alfabeto para el automata.
	 * @param arrayEstados Arreglo con cada estado del automata.
	 * @param arrayEstadosFinales Arreglo con todos los estados que son finales para el automata.
	 * @param arrayCadenas Arreglo con cada cadena que procesara el automata.
	 * @param ConsolePane El panel del area de consola de la ventana principal.
	 * @param message_PC1 Mensaje almacenado en la PC1.
	 * @param message_PC2 Mensaje almacenado en la PC2.
	 * @param requestingPC Entero que define cual de las dos PCs pidio el mensaje a la otra.
	 * @param btnPc1 Boton que contiene el icono de la PC1 en la ventana principal.
	 * @param btnPc2 Boton que contiene el icono de la PC2 en la ventana principal.
	 * @param afd_status Ventana AFD_Status que despliega informacion de los estados del automata.
	 * @param lblErrorStatus Etiqueta que se despliega en la ventana principal
	 * para indicar si esta activa la simulacion de errores.
	 */
	public ProtocolBehavior(String[][] transitionsTable, String[] arrayAlfabeto, String[] arrayEstados, 
			String[] arrayEstadosFinales,String[] arrayCadenas, JTextPane ConsolePane, 
			String message_PC1, String message_PC2, int requestingPC, 
			JButton btnPc1, JButton btnPc2, AFD_Status afd_status, JLabel lblErrorStatus){
		
		//Timer que se usa para actualizar los elementos de la interfaz grafica.
		timer = new Timer(4000, updateGUI);
		
		messageIndex = 0;
		isAccepted = false;
		consoleText = new StringBuilder();
		this.requestingPC = requestingPC;
		this.lblErrorStatus = lblErrorStatus;
		this.statusMessage = new String[arrayCadenas.length + 1];
		this.message_PC1 = message_PC1;
		this.message_PC2 = message_PC2;
		this.ConsolePane = ConsolePane;
		this.btnPc1 = btnPc1;
		this.btnPc2 = btnPc2;
		
		this.transitionsTable = transitionsTable;
		this.arrayAlfabeto = arrayAlfabeto;
		this.arrayEstados = arrayEstados;
		this.arrayEstadosFinales = arrayEstadosFinales;
		this.arrayCadenas = arrayCadenas;
		
		this.afd_status = afd_status;
		symbolIndex = new HashMap<String, Integer>();
		stateIndex = new HashMap<String, Integer>();
		FillHashMaps();
	}
	
	
	/**
	 * Se encarga de llenar los HashMaps que se usan para rapido acceso
	 * al momento de buscar en la tabla de transiciones.
	 */
	private void FillHashMaps(){
			
			for (int i = 0; i < arrayAlfabeto.length; i++) {			
				symbolIndex.put(arrayAlfabeto[i], i);		
				
			}
			
			for (int i = 0; i < arrayEstados.length; i++) {
				stateIndex.put(arrayEstados[i], i);
				
			}	
		}
	
	
	/**
	 * Se encarga de simular el comportamiento del automata. Recorre la tabla de transiciones
	 * para cada estado (usando HashMaps para rapido acceso a las ubicaciones), y va llenando
	 * el arreglo statusMessage con informacion de como se va comportando el automata. 
	 * Tambien estalece la bandera isAccepted verificando si el estado en el que finalizo el 
	 * automata pertenece a los estados finales.
	 */
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
					
					statusMessage[messageIndex] = "An error ocurred, resetting...\n";
					messageIndex++;
					break;
					
				}else if(currentStateIndex == 6){
					
					if(requestingPC == 1){
						statusMessage[messageIndex] = "The message was received succesfully!\n" + 
													"Message from PC2:\n" + message_PC2 + "\n\nEnd of message...\n\n";					
					}
					else if(requestingPC == 2){
						statusMessage[messageIndex] = "The message was received succesfully!\n" + 
													"Message from PC1:\n" + message_PC1 + "\n\nEnd of message...\n\n";
					}
					
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
		
		//Inicia el timer despues de simular el protocolo
		messageIndex = 0;
		timer.start();
	}
	
	/**
	 * Se encarga de actualizar la interfaz de la ventana principal, suscrito
	 * al evento del timer de la clase.
	 */
	ActionListener updateGUI = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				changeIcons();
				updateConsole();
			}
		};
	
		/**
		 * Se encarga de actualizar los colores de las PCs en la ventana principal
		 * dependiendo de que accion se este realizando en el protocolo.
		 */
		public void changeIcons(){
		
		//Establece cual es la longitud del arreglo de mensajes, 
		//dependiendo si esta activa la simulacion de errores
		if(messageIndex < (isAccepted ? statusMessage.length : 4)){
			
			if(requestingPC == 1){
				
				if(statusMessage[messageIndex].contains("RFNM_S")){
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_red.png")));
					updateAFDStatus(1);
				}
				else if(statusMessage[messageIndex].contains("RFNM_E")){
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/ic_airplay_black_24dp_2x.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/ic_airplay_black_24dp_2x.png")));
					updateAFDStatus(2);
				}
				else if(statusMessage[messageIndex].contains("ACK_S")){
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_red.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
					updateAFDStatus(3);
				}
				else if(statusMessage[messageIndex].contains("MSG_S")){
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_red.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
					//Si hay simulacion de errores activa, ir al estado de error
					if(lblErrorStatus.getText().length() > 0)
						updateAFDStatus(5);
					else
						updateAFDStatus(4);
				}
				else if(statusMessage[messageIndex].contains("ACK_R")){
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_red.png")));
					updateAFDStatus(6);
				}
				else if(statusMessage[messageIndex].contains("ACK_N")){
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_red.png")));
					updateAFDStatus(3);
				}
				else if(statusMessage[messageIndex].contains("The message was received succesfully!")){
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/ic_airplay_black_24dp_2x.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/ic_airplay_black_24dp_2x.png")));
					updateAFDStatus(6);
				}
				else if(statusMessage[messageIndex].contains("TO_RST")){
					if(isAccepted){
						btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
						btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
					}
					updateAFDStatus(0);
				}
			}
			else if(requestingPC == 2){
				
				if(statusMessage[messageIndex].contains("RFNM_S")){			
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_red.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
					updateAFDStatus(1);
				}				
				else if(statusMessage[messageIndex].contains("RFNM_E")){
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/ic_airplay_black_24dp_2x.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/ic_airplay_black_24dp_2x.png")));
					updateAFDStatus(2);
				}
				else if(statusMessage[messageIndex].contains("ACK_S")){
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_red.png")));
					updateAFDStatus(3);
				}
				else if(statusMessage[messageIndex].contains("MSG_S")){
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_red.png")));
					//Si hay simulacion de errores activa, ir al estado de error
					if(lblErrorStatus.getText().length() > 0)
						updateAFDStatus(5);
					else
						updateAFDStatus(4);
				}
				else if(statusMessage[messageIndex].contains("ACK_R")){
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_red.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
					updateAFDStatus(6);
				}
				else if(statusMessage[messageIndex].contains("ACK_N")){
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_red.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
					updateAFDStatus(3);
				}
				else if(statusMessage[messageIndex].contains("The message was received succesfully!")){
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/ic_airplay_black_24dp_2x.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/ic_airplay_black_24dp_2x.png")));
					updateAFDStatus(6);
				}
				else if(statusMessage[messageIndex].contains("TO_RST")){
					if(isAccepted){
						btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
						btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
					}
					updateAFDStatus(0);
				}					
				
			}
		}
		else{
			btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/ic_airplay_black_24dp_2x.png")));
			btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/ic_airplay_black_24dp_2x.png")));			
		}
	}
	
	/**
	 * Se encarga de actualizar el area de consola en la ventana principal con la informacion
	 * de los mensajes transmitidos y recibidos en el protocolo.
	 */
	private void updateConsole(){		
		int arrayLimit = 0;
		
		//Si el automata quedo en estado de error, entonces se encuentra activa la simulacion de errores
		if(isAccepted){
			//Se toma el tamaño del arreglo de mensajes normalmente
			arrayLimit = statusMessage.length;		
		}
		else{
			//Se toma el tamaño del arreglo de mensajes con errores, que es de 4
			arrayLimit = 4;
		}
		
		if(messageIndex < arrayLimit){
			consoleText.append(statusMessage[messageIndex]);
			
			//Si esta activa la simulacion de errores, mostrar el mensaje antes 
			//y regresar al estado inicial la ventana AFD_Status
			if(!isAccepted && messageIndex >= 3){
				messageIndex++;
				changeIcons();
				timer.stop();
				JOptionPane.showMessageDialog(null, "Simulation ended!", "Status", JOptionPane.INFORMATION_MESSAGE);
				updateAFDStatus(0);
			}
			messageIndex++;
		}
		else{
			JOptionPane.showMessageDialog(null, "Simulation ended!", "Status", JOptionPane.INFORMATION_MESSAGE);
			timer.stop();
		}
		
		ConsolePane.setText(consoleText.toString());
	}

	/**
	 * Actualiza el estado actual en la ventana AFD_Status haciendo una llamada al
	 * metodo updateStates de la clase AFD_Status.
	 * @param state Estado que se quiere establecer como activo en la ventana AFD_Status.
	 */
	private void updateAFDStatus(int state){
		afd_status.updateStates(state);
	}
}
