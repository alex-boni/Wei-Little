/**
 * 
 */
package Presentacion.MaquinaJPA;

import javax.swing.JFrame;
import Presentacion.FactoriaPresentacion.IGUI;
import Presentacion.FactoriaPresentacion.Evento;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;

public class VistaBajaMaquina extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaBajaMaquina() {
		super("[BAJA MAQUINA]");
		initGUI();
	}

	private void initGUI() {

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.setContentPane(mainPanel);

		JPanel supPanel = new JPanel(new BorderLayout());
		mainPanel.add(supPanel, BorderLayout.PAGE_START);
		rellenarPanelSuperior(supPanel);

		JPanel bottomPanel = new JPanel();
		mainPanel.add(bottomPanel, BorderLayout.PAGE_END);
		rellenarPanelInferior(bottomPanel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(600, 300));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void rellenarPanelSuperior(JPanel supPanel) {

		JPanel textFieldPanel = new JPanel();
		supPanel.add(textFieldPanel, BorderLayout.PAGE_START);

		JTextField idTextField = new JTextField();
		idTextField.setVisible(true);
		idTextField.setPreferredSize(new Dimension(80, 30));
		JLabel idLabel = new JLabel("<html><p>id:</p></html>");
		idLabel.setPreferredSize(new Dimension(80, 30));
		textFieldPanel.add(idLabel);
		textFieldPanel.add(idTextField);

		JPanel okPanel = new JPanel();
		supPanel.add(okPanel, BorderLayout.PAGE_END);

		JButton ok_button = new JButton("OK");
		ok_button.setVisible(true);
		ok_button.addActionListener((e) -> {

			try {
				int id = Integer.parseInt(idTextField.getText());
				Controller.getInstancia().accion(new Context(Evento.BAJA_MAQUINA_NEGOCIO, id));
				this.dispose();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Error: El campo no puede estar vacio o ser letras");
			}
		});
		okPanel.add(ok_button);
	}

	private void rellenarPanelInferior(JPanel bottomPanel) {

		JButton cancelButton = new JButton("CANCEL");
		cancelButton.setToolTipText("cancel");
		cancelButton.setVisible(true);
		cancelButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.MAQUINA, null));
			this.dispose();
		});
		bottomPanel.add(cancelButton);
	}

	public void update(Context context) {
		if (context.getEvento() == Evento.BAJA_MAQUINA_VISTA)
			this.setVisible(true);
		else {
			if (context.getEvento() == Evento.RES_BAJA_MAQUINA_OK)
				JOptionPane.showMessageDialog(null, "Exito al dar de baja maquina. Id: " + (int) context.getDatos());
			else if (context.getEvento() == Evento.RES_BAJA_MAQUINA_KO)
				JOptionPane.showMessageDialog(null, "Error al dar de baja maquina. ");
			Controller.getInstancia().accion(new Context(Evento.MAQUINA, null));
			this.dispose();
		}
	}
}