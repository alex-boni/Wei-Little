package Presentacion.Venta;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Dimension;

public class VBajaVenta extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VBajaVenta() {
		super("[BAJA]");
		initGUI();
	}

	private void initGUI() {

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.setContentPane(mainPanel);

		JPanel supPanel = new JPanel();
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

		JLabel textLabel = new JLabel("<html><p>ID de la factura: </p></html>");
		textLabel.setPreferredSize(new Dimension(200, 30));
		supPanel.add(textLabel);
		JTextField searchText = new JTextField();
		searchText.setVisible(true);
		searchText.setPreferredSize(new Dimension(200, 30));
		supPanel.add(searchText);

		JButton ok_button = new JButton("OK");
		ok_button.setVisible(true);
		ok_button.addActionListener((e) -> {
			try {
				int id = Integer.valueOf(searchText.getText());
				if (id <= 0)
					JOptionPane.showMessageDialog(null, "Error: Id no puede ser negativo o cero");
				else {
					Controller.getInstancia().accion(new Context(Evento.BAJA_VENTA_NEGOCIO, id));
					this.dispose();
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null,
						"Error: No puede estar el campo vacio o con letras" + ex.getMessage());
			}
		});
		supPanel.add(ok_button);
	}

	private void rellenarPanelInferior(JPanel bottomPanel) {

		JButton cancelButton = new JButton("CANCEL");
		cancelButton.setToolTipText("cancel");
		cancelButton.setVisible(true);
		cancelButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VENTA, null));
			this.dispose();
		});
		bottomPanel.add(cancelButton);
	}

	public void update(Context context) {
		Evento evento = context.getEvento();

		if (evento == Evento.BAJA_VENTA_VISTA) {
			this.setVisible(true);
		}

		else {
			updateVenta(evento);
		}
	}

	private void updateVenta(Evento evento) {

		if (evento == Evento.RES_BAJA_VENTA_OK)
			mensaje("Exito al dar de baja venta");

		else if (evento == Evento.RES_BAJA_VENTA_KO)
			mensaje("Error al dar de baja venta. ");

		Controller.getInstancia().accion(new Context(Evento.VENTA, null));
		this.dispose();
	}

	private void mensaje(String texto) {
		JOptionPane.showMessageDialog(null, texto);
	}
}