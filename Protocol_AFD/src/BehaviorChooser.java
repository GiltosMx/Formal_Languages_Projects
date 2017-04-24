import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import java.awt.Dialog.ModalityType;

public class BehaviorChooser extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	public String[] resendOneTime_Behavior = {"RFNM_S", "RFNM_E", "ACK_S", "MSG_S", "ACK_N", "MSG_S","ACK_R", "TO_RST"};
	public String[] resendTwoTimes_Behavior = {"RFNM_S", "RFNM_E", "ACK_S", "MSG_S", "ACK_N", "MSG_S","ACK_N","MSG_S", "ACK_R", "TO_RST"};
	public String[] normal_Behavior = {"RFNM_S", "RFNM_E", "ACK_S", "MSG_S","ACK_R","TO_RST"};
	
	public int chosenBehavior = 0;
	private int oldBehavior = 0;
	private String oldBehaviorString = "Behavior: Normal";

	public BehaviorChooser(JLabel lblCurrentBehavior) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Behavior Chooser");
		setBounds(100, 100, 347, 298);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblChoose = new JLabel("Choose one of the following behaviors:");
		lblChoose.setBounds(12, 12, 235, 15);
		contentPanel.add(lblChoose);
		{
			JButton btnChoose1 = new JButton("Choose");
			btnChoose1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					updateBehavior(1, lblCurrentBehavior);
				}
			});
			btnChoose1.setBounds(12, 55, 80, 25);
			contentPanel.add(btnChoose1);
		}
		{
			JButton btnChoose2 = new JButton("Choose");
			btnChoose2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					updateBehavior(2, lblCurrentBehavior);
				}
			});
			btnChoose2.setBounds(12, 110, 80, 25);
			contentPanel.add(btnChoose2);
		}
		{
			JButton btnChoose3 = new JButton("Choose");
			btnChoose3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					updateBehavior(3, lblCurrentBehavior);
				}
			});
			btnChoose3.setBounds(12, 165, 80, 25);
			contentPanel.add(btnChoose3);
		}
		
		JLabel lblResendOnce = new JLabel("Protocol resends the message once");
		lblResendOnce.setBounds(110, 60, 220, 15);
		contentPanel.add(lblResendOnce);
		{
			JLabel lblResendTwice = new JLabel("Protocol resends the message twice");
			lblResendTwice.setBounds(110, 115, 220, 15);
			contentPanel.add(lblResendTwice);
		}
		{
			JLabel lblNormalBehavior = new JLabel("Protocol behaves normally");
			lblNormalBehavior.setBounds(110, 170, 165, 15);
			contentPanel.add(lblNormalBehavior);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						chosenBehavior = oldBehavior;
						lblCurrentBehavior.setText(oldBehaviorString);
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	
	/**
	 * Actualiza cual comportamiento fue elegido para el protocolo.
	 * @param behaviorNumber Numero de comportamiento seleccionado.
	 * @param lblCurrentBehavior JLabel que se quiere actualizar con el nuevo comportamiento.
	 */
	private void updateBehavior(int behaviorNumber, JLabel lblCurrentBehavior){
		JOptionPane.showMessageDialog(null, "Behavior #" + behaviorNumber + " chosen!");
		oldBehavior = chosenBehavior;
		chosenBehavior = behaviorNumber;
		oldBehaviorString = lblCurrentBehavior.getText();
		
		//Cambia el String en la ventana principal para mostrar el comportamiento seleccionado
		switch (behaviorNumber) {
		case 1:
			lblCurrentBehavior.setText("Behavior: Resend once");			
			break;
		case 2:
			lblCurrentBehavior.setText("Behavior: Resend twice");
			break;
		default:
			lblCurrentBehavior.setText("Behavior: Normal");
			break;
		}
		
	}
}
