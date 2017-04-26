import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.SystemColor;

public class AFD_Status extends JFrame {

								//Atributos
	//---------------------------------------------------------------------//
	private JPanel contentPane;
	private JRadioButtonMenuItem radioButtonQ0;
	private JRadioButtonMenuItem radioButtonQ1;
	private JRadioButtonMenuItem radioButtonQ2;
	private JRadioButtonMenuItem radioButtonQ3;
	private JRadioButtonMenuItem radioButtonQ4;
	private JRadioButtonMenuItem radioButtonQ5;
	private JRadioButtonMenuItem radioButtonQ6;
	//---------------------------------------------------------------------//

	public AFD_Status() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("Automaton Status");
		setBounds(100, 100, 603, 137);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		radioButtonQ0 = new JRadioButtonMenuItem("q0");
		radioButtonQ0.setForeground(Color.BLUE);
		radioButtonQ0.setBounds(12, 50, 50, 20);
		//Siempre empieza en el estado 0
		radioButtonQ0.setSelected(true);
		radioButtonQ0.setToolTipText("Azul = Estado final");
		contentPane.add(radioButtonQ0);
		
		JLabel lblAutomata = new JLabel("Automaton States:");
		lblAutomata.setBounds(230, 12, 150, 15);
		contentPane.add(lblAutomata);
		
		radioButtonQ1 = new JRadioButtonMenuItem("q1");
		radioButtonQ1.setBounds(95, 50, 50, 20);
		contentPane.add(radioButtonQ1);
		
		radioButtonQ2 = new JRadioButtonMenuItem("q2");
		radioButtonQ2.setBounds(178, 50, 50, 20);
		contentPane.add(radioButtonQ2);
		
		radioButtonQ3 = new JRadioButtonMenuItem("q3");
		radioButtonQ3.setBounds(268, 50, 50, 20);
		contentPane.add(radioButtonQ3);
		
		radioButtonQ4 = new JRadioButtonMenuItem("q4");
		radioButtonQ4.setBounds(356, 50, 50, 20);
		contentPane.add(radioButtonQ4);
		
		radioButtonQ5 = new JRadioButtonMenuItem("q5");
		radioButtonQ5.setForeground(Color.RED);
		radioButtonQ5.setBounds(444, 50, 50, 20);
		radioButtonQ5.setToolTipText("Rojo = Estado de error");
		contentPane.add(radioButtonQ5);
		
		radioButtonQ6 = new JRadioButtonMenuItem("q6");
		radioButtonQ6.setBounds(532, 50, 50, 20);
		contentPane.add(radioButtonQ6);
	}

	/**
	 * Se encarga de actualizar el estado activo en la ventana desplegada.
	 * @param state El estado que se quiere establecer como activo.
	 */
	public void updateStates(int state){
			
		if(state == 1){
			radioButtonQ0.setSelected(false);
			radioButtonQ1.setSelected(true);
			radioButtonQ2.setSelected(false);
			radioButtonQ3.setSelected(false);
			radioButtonQ4.setSelected(false);
			radioButtonQ5.setSelected(false);
			radioButtonQ6.setSelected(false);
		}
		else if(state == 2){
			radioButtonQ0.setSelected(false);
			radioButtonQ1.setSelected(false);
			radioButtonQ2.setSelected(true);
			radioButtonQ3.setSelected(false);
			radioButtonQ4.setSelected(false);
			radioButtonQ5.setSelected(false);
			radioButtonQ6.setSelected(false);
		}
		else if(state == 3){
			radioButtonQ0.setSelected(false);
			radioButtonQ1.setSelected(false);
			radioButtonQ2.setSelected(false);
			radioButtonQ3.setSelected(true);
			radioButtonQ4.setSelected(false);
			radioButtonQ5.setSelected(false);
			radioButtonQ6.setSelected(false);
		}
		else if(state == 4){
			radioButtonQ0.setSelected(false);
			radioButtonQ1.setSelected(false);
			radioButtonQ2.setSelected(false);
			radioButtonQ3.setSelected(false);
			radioButtonQ4.setSelected(true);
			radioButtonQ5.setSelected(false);
			radioButtonQ6.setSelected(false);
		}
		else if(state == 5){
			radioButtonQ0.setSelected(false);
			radioButtonQ1.setSelected(false);
			radioButtonQ2.setSelected(false);
			radioButtonQ3.setSelected(false);
			radioButtonQ4.setSelected(false);
			radioButtonQ5.setSelected(true);
			radioButtonQ6.setSelected(false);
		}
		else if(state == 6){
			radioButtonQ0.setSelected(false);
			radioButtonQ1.setSelected(false);
			radioButtonQ2.setSelected(false);
			radioButtonQ3.setSelected(false);
			radioButtonQ4.setSelected(false);
			radioButtonQ5.setSelected(false);
			radioButtonQ6.setSelected(true);
		}
		else if(state == 0){
			radioButtonQ0.setSelected(true);
			radioButtonQ1.setSelected(false);
			radioButtonQ2.setSelected(false);
			radioButtonQ3.setSelected(false);
			radioButtonQ4.setSelected(false);
			radioButtonQ5.setSelected(false);
			radioButtonQ6.setSelected(false);
		}	
	}
}
