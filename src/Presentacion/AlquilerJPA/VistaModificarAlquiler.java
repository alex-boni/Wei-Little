/**
 * 
 */
package Presentacion.AlquilerJPA;

import javax.swing.JFrame;
import javax.swing.JTextField;

import Negocio.AlquilerJPA.TAlquiler;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class VistaModificarAlquiler extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaModificarAlquiler() {
		super("[MODIFICAR ALQUILER]");
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

		JTextField alquilerTextField = new JTextField();
		alquilerTextField.setVisible(true);
		alquilerTextField.setPreferredSize(new Dimension(60, 30));
		JLabel alquilerLabel = new JLabel("ID Alquiler: ");
		alquilerLabel.setPreferredSize(new Dimension(60, 30)); // Aumenta el ancho
		textFieldPanel.add(alquilerLabel);
		textFieldPanel.add(alquilerTextField);

		JTextField clienteTextField = new JTextField();
		clienteTextField.setVisible(true);
		clienteTextField.setPreferredSize(new Dimension(60, 30));
		JLabel clienteLabel = new JLabel("ID Cliente: ");
		clienteLabel.setPreferredSize(new Dimension(60, 30)); // Aumenta el
		textFieldPanel.add(clienteLabel);
		textFieldPanel.add(clienteTextField);

		JTextField empleadoTextField = new JTextField();
		empleadoTextField.setVisible(true);
		empleadoTextField.setPreferredSize(new Dimension(60, 30));
		JLabel empleadoLabel = new JLabel(" ID Empleado: ");
		empleadoLabel.setPreferredSize(new Dimension(80, 30)); // Aumenta el ancho
		textFieldPanel.add(empleadoLabel);
		textFieldPanel.add(empleadoTextField);

		JPanel okPanel = new JPanel();
		supPanel.add(okPanel, BorderLayout.PAGE_END);

		JButton ok_button = new JButton("OK");
		ok_button.setVisible(true);
		ok_button.addActionListener((e) -> {

			try {
				int id_alquiler = Integer.parseInt(alquilerTextField.getText());

				// Comprobar si el id es mayor que 0
				if (id_alquiler <= 0) {
					JOptionPane.showMessageDialog(null, "Error: El id alquiler debe ser mayor que 0");
					return;
				}

				int id_cliente = Integer.parseInt(clienteTextField.getText());

				// Comprobar si el id es mayor que 0
				if (id_cliente <= 0) {
					JOptionPane.showMessageDialog(null, "Error: El id cliente debe ser mayor que 0");
					return;
				}

				int id_empleado = Integer.parseInt(empleadoTextField.getText());

				// Comprobar si el id es mayor que 0
				if (id_empleado <= 0) {
					JOptionPane.showMessageDialog(null, "Error: El id empleado debe ser mayor que 0");
					return;
				}

				TAlquiler tAlquiler = new TAlquiler();
				tAlquiler.set_id_alquiler(id_alquiler);
				tAlquiler.set_id_cliente(id_cliente);
				tAlquiler.set_id_empleado(id_empleado);

				Controller.getInstancia().accion(new Context(Evento.MODIFICAR_ALQUILER_NEGOCIO, tAlquiler));
				this.dispose();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Error: No puede haber campos vacios y el id debe ser numerico");
			}
		});
		okPanel.add(ok_button);

	}

	private void rellenarPanelInferior(JPanel bottomPanel) {

		JButton cancelButton = new JButton("CANCEL");
		cancelButton.setToolTipText("cancel");
		cancelButton.setVisible(true);
		cancelButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.ALQUILER, null));
			this.dispose();
		});
		bottomPanel.add(cancelButton);
	}

	public void update(Context context) {
		if (context.getEvento() == Evento.MODIFICAR_ALQUILER_VISTA)
			this.setVisible(true);
		else {
			if (context.getEvento() == Evento.RES_MODIFICAR_ALQUILER_OK)
				JOptionPane.showMessageDialog(null, "Exito al modificar el alquiler. Id: " + (int) context.getDatos());
			else if (context.getEvento() == Evento.RES_MODIFICAR_ALQUILER_KO)
				JOptionPane.showMessageDialog(null, "Error al modificar el alquiler.");
			else if (context.getEvento() == Evento.RES_MODIFICAR_ALQUILER_KO_NOCLIENTE)
				JOptionPane.showMessageDialog(null, "Error al modificar el alquiler. No existe el cliente.");
			else if (context.getEvento() == Evento.RES_MODIFICAR_ALQUILER_KO_NOEMPLEADO)
				JOptionPane.showMessageDialog(null, "Error al modificar el alquiler. No existe el empleado.");
			else if (context.getEvento() == Evento.RES_MODIFICAR_ALQUILER_KO_NOALQUILER)
				JOptionPane.showMessageDialog(null, "Error al modificar el alquiler. No existe el alquiler.");
			Controller.getInstancia().accion(new Context(Evento.ALQUILER, null));
			this.dispose();
		}
	}

}