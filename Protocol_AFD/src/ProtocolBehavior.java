import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

public class ProtocolBehavior {
	
									//Atributos
	//---------------------------------------------------------------------//
	public String[] statusMessage;
	private int requestingPC;
	private JTextPane ConsolePane;
	private Timer timer;
	private int messageIndex;
	private StringBuilder consoleText;
	private String message_PC1;
	private String message_PC2;
	private JButton btnPc1;
	private JButton btnPc2;
	
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
	
	public ProtocolBehavior(String[][] transitionsTable, String[] arrayAlfabeto, String[] arrayEstados, 
			String[] arrayEstadosFinales,String[] arrayCadenas, JTextPane ConsolePane, 
			String message_PC1, String message_PC2, int requestingPC, JButton btnPc1, JButton btnPc2){
		
		timer = new Timer(2500, updateGUI);
		
		messageIndex = 0;
		isAccepted = false;
		consoleText = new StringBuilder();
		this.requestingPC = requestingPC;
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
					
					statusMessage[messageIndex] = "An error ocurred. Please wait...\n";
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
	
	ActionListener updateGUI = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				changeIcons();
				updateConsole();
			}
		};
	
	public void changeIcons(){
		
		if(messageIndex < (isAccepted ? statusMessage.length : 4)){
			
			if(requestingPC == 1){
				
				if(statusMessage[messageIndex].contains("RFNM_S")){
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_red.png")));
				}
				else if(statusMessage[messageIndex].contains("RFNM_E")){
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/ic_airplay_black_24dp_2x.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/ic_airplay_black_24dp_2x.png")));
				}
				else if(statusMessage[messageIndex].contains("ACK_S")){
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_red.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
				}
				else if(statusMessage[messageIndex].contains("MSG_S")){
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_red.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
				}
				else if(statusMessage[messageIndex].contains("ACK_R")){
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_red.png")));
				}
				else if(statusMessage[messageIndex].contains("ACK_N")){
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_red.png")));
				}
				else if(statusMessage[messageIndex].contains("The message was received succesfully!")){
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/ic_airplay_black_24dp_2x.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/ic_airplay_black_24dp_2x.png")));
				}
				else if(statusMessage[messageIndex].contains("TO_RST")){
					if(isAccepted){
						btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
						btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
					}
					else if(!isAccepted){
						btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_red.png")));
						btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_red.png")));
					}
				}
			}
			else if(requestingPC == 2){
				
				if(statusMessage[messageIndex].contains("RFNM_S")){			
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_red.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));				
				}				
				else if(statusMessage[messageIndex].contains("RFNM_E")){
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/ic_airplay_black_24dp_2x.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/ic_airplay_black_24dp_2x.png")));			
				}
				else if(statusMessage[messageIndex].contains("ACK_S")){
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_red.png")));
				}
				else if(statusMessage[messageIndex].contains("MSG_S")){
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_red.png")));
				}
				else if(statusMessage[messageIndex].contains("ACK_R")){
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_red.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
				}
				else if(statusMessage[messageIndex].contains("ACK_N")){
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_red.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
				}
				else if(statusMessage[messageIndex].contains("The message was received succesfully!")){
					btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/ic_airplay_black_24dp_2x.png")));
					btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/ic_airplay_black_24dp_2x.png")));
				}
				else if(statusMessage[messageIndex].contains("TO_RST")){
					if(isAccepted){
						btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
						btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_blue.png")));
					}
					else if(!isAccepted){
						btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_red.png")));
						btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/icon_red.png")));
					}
				}								
			}
		}
		else{
			btnPc1.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/ic_airplay_black_24dp_2x.png")));
			btnPc2.setIcon(new ImageIcon(ProtocolBehavior.class.getResource("/icons/ic_airplay_black_24dp_2x.png")));
			
		}
	}
	
	private void updateConsole(){		
		int arrayLimit = 0;
		
		if(isAccepted){
			arrayLimit = statusMessage.length;			
		}
		else{
			arrayLimit = 4;
		}
		
		if(messageIndex < arrayLimit){
			consoleText.append(statusMessage[messageIndex]);
			if(!isAccepted && messageIndex >= 3){
				messageIndex++;
				changeIcons();
				timer.stop();
				JOptionPane.showMessageDialog(null, "Simulation ended!", "Status", JOptionPane.INFORMATION_MESSAGE);
			}
			messageIndex++;
		}
		else{
			JOptionPane.showMessageDialog(null, "Simulation ended!", "Status", JOptionPane.INFORMATION_MESSAGE);
			timer.stop();
		}
		
		ConsolePane.setText(consoleText.toString());
	}

}
