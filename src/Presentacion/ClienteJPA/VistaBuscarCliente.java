package Presentacion.ClienteJPA;

import javax.swing.JFrame;

import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Negocio.ClienteJPA.TCliente;

import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;

public class VistaBuscarCliente extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaBuscarCliente() {
		super("[BUSCAR CLIENTE]");
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
		idLabel.setPreferredSize(new Dimension(20, 30));
		textFieldPanel.add(idLabel);
		textFieldPanel.add(idTextField);

		JPanel okPanel = new JPanel();
		supPanel.add(okPanel, BorderLayout.PAGE_END);

		JButton ok_button = new JButton("OK");
		ok_button.setVisible(true);
		ok_button.addActionListener((e) -> {

			try {
				if (idTextField.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "Error: El campo id no puede estar vacio");
				else if (Integer.parseInt(idTextField.getText()) <= 0)
					JOptionPane.showMessageDialog(null, "Error: El id debe ser un numero mayor que cero");
				else {
					int id = Integer.parseInt(idTextField.getText());
					Controller.getInstancia().accion(new Context(Evento.BUSCAR_CLIENTE, id));
					this.dispose();
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Error: Error inesperado");
			}
		});
		okPanel.add(ok_button);
	}

	private void rellenarPanelInferior(JPanel bottomPanel) {

		JButton cancelButton = new JButton("CANCEL");
		cancelButton.setToolTipText("cancel");
		cancelButton.setVisible(true);
		cancelButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.CLIENTE, null));
			this.dispose();
		});
		bottomPanel.add(cancelButton);
	}

	public void update(Context context) {
		if (context.getEvento() == Evento.VBUSCAR_CLIENTE)
			this.setVisible(true);
		else {
			if (context.getEvento() == Evento.RES_BUSCAR_CLIENTE_OK) {
				TCliente tCliente = (TCliente) context.getDatos();
				JOptionPane.showMessageDialog(null,
						"Exito: Cliente encontrado: \nId: " + tCliente.get_id_cliente() + " \nNombre: "
								+ tCliente.get_nombre() + " \nDNI: " + tCliente.get_dni() + " \nActivo: "
								+ tCliente.get_activo());
			} else if (context.getEvento() == Evento.RES_BUSCAR_CLIENTE_KO)
				JOptionPane.showMessageDialog(null, "Error: No se pudo encontrar al cliente ");
			Controller.getInstancia().accion(new Context(Evento.CLIENTE, null));
			this.dispose();
		}
	}
}