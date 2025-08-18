package Presentacion.ClienteJPA;

import javax.swing.JFrame;

import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import javax.swing.JButton;
import javax.swing.JTextField;

import Negocio.ClienteJPA.TCliente;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;

public class VistaModificarCliente extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaModificarCliente() {
		super("[MODIFICAR CLIENTE]");
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

		JTextField nombreTextField = new JTextField();
		nombreTextField.setVisible(true);
		nombreTextField.setPreferredSize(new Dimension(80, 30));
		JLabel nombreLabel = new JLabel("<html><p>Nombre:</p></html>");
		nombreLabel.setPreferredSize(new Dimension(40, 30));
		textFieldPanel.add(nombreLabel);
		textFieldPanel.add(nombreTextField);

		JTextField dniTextField = new JTextField();
		dniTextField.setVisible(true);
		dniTextField.setPreferredSize(new Dimension(80, 30));
		JLabel dniLabel = new JLabel("<html><p>DNI:</p></html>");
		dniLabel.setPreferredSize(new Dimension(30, 30));
		textFieldPanel.add(dniLabel);
		textFieldPanel.add(dniTextField);

		JPanel okPanel = new JPanel();
		supPanel.add(okPanel, BorderLayout.PAGE_END);

		JButton ok_button = new JButton("OK");
		ok_button.setVisible(true);
		ok_button.addActionListener((e) -> {

			try {

				if (idTextField.getText().isEmpty() || nombreTextField.getText().isEmpty()
						|| dniTextField.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "Error: No puede haber campos vacios");
				else {

					int id_cliente = Integer.parseInt(idTextField.getText());
					String nombre = nombreTextField.getText().toUpperCase();
					String dni = dniTextField.getText().toUpperCase();
					if (id_cliente <= 0)
						JOptionPane.showMessageDialog(null, "Error: El id debe ser un numero mayor que cero");
					else if (!checkNombre(nombre))
						JOptionPane.showMessageDialog(null, "Error: El nombre no acepta caracteres numericos");
					else if (!checkDNI(dni))
						JOptionPane.showMessageDialog(null, "Error: El dni debe tener el siguiente formato 12345678A");
					else {
						TCliente tCliente = new TCliente();
						tCliente.set_id_cliente(id_cliente);
						tCliente.set_nombre(nombre);
						tCliente.set_dni(dni);
						Controller.getInstancia().accion(new Context(Evento.MODIFICAR_CLIENTE, tCliente));
						this.dispose();
					}
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
		if (context.getEvento() == Evento.VMODIFICAR_CLIENTE)
			this.setVisible(true);
		else {
			if (context.getEvento() == Evento.RES_MODIFICAR_CLIENTE_OK)
				JOptionPane.showMessageDialog(null, "Exito: Modificado cliente con id: " + (int) context.getDatos());
			else if (context.getEvento() == Evento.RES_MODIFICAR_CLIENTE_KO)
				JOptionPane.showMessageDialog(null,
						"Error: No se pudo modificar el cliente \nCompruebe datos o que el cliente este activo");
			Controller.getInstancia().accion(new Context(Evento.CLIENTE, null));
			this.dispose();
		}
	}

	private boolean checkNombre(String nombre) {

		for (char c : nombre.toCharArray()) {
			if (!Character.isLetter(c))
				return false;
		}
		return true;
	}

	private boolean checkDNI(String dni) {

		if (dni.length() != 9)
			return false;

		for (int i = 0; i < 8; i++) {
			if (!Character.isDigit(dni.charAt(i)))
				return false;
		}
		if (dni.charAt(8) < 'A' || dni.charAt(8) > 'Z')
			return false;

		return true;
	}
}