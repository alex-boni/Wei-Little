package Presentacion.Venta;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;

import javax.swing.JTextField;

public class VAbrirVenta extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VAbrirVenta() {
		super("[VENTA AL CLIENTE]");
		initGUI();
	}

	private void initGUI() {

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		setContentPane(mainPanel);

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

		JLabel textLabel = new JLabel(
				"<html><p>Ha el inicializado el proceso de compra</p><p>Indique trabajador y OK o cancele con CANCEL</p></html>");
		textLabel.setVisible(true);
		textLabel.setPreferredSize(new Dimension(250, 50));
		textLabel.setHorizontalAlignment(JLabel.CENTER);
		supPanel.add(textLabel);
	}

	private void rellenarPanelInferior(JPanel bottomPanel) {

		bottomPanel.setLayout(new BorderLayout());

		JPanel textFieldPanel = new JPanel();
		bottomPanel.add(textFieldPanel, BorderLayout.PAGE_START);

		JLabel idLabel = new JLabel("id Trabajador: ");
		JTextField idText = new JTextField();
		idText.setVisible(true);
		idText.setPreferredSize(new Dimension(100, 30));
		textFieldPanel.add(idLabel);
		textFieldPanel.add(idText);

		JPanel buttonsPanel = new JPanel();
		bottomPanel.add(buttonsPanel, BorderLayout.CENTER);

		JButton ok_button = new JButton("OK");
		ok_button.setVisible(true);
		ok_button.addActionListener((e) -> {
			try {
				int id_trabajador = Integer.valueOf(idText.getText());
				if (id_trabajador <= 0)
					JOptionPane.showMessageDialog(null, "Error: Id no puede ser negativo o cero");
				else {
					Controller.getInstancia().accion(new Context(Evento.ABRIR_VENTA_NEGOCIO, id_trabajador));
					dispose();
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null,
						"Error: No puede estar el campo vacio o con letras " + ex.getMessage());
			}
		});
		buttonsPanel.add(ok_button);

		JButton cancel_button = new JButton("CANCEL");
		cancel_button.setVisible(true);
		cancel_button.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VENTA, null));
			dispose();
		});
		buttonsPanel.add(cancel_button);
	}

	public void update(Context context) {

		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		if (evento == Evento.ABRIR_VENTA_VISTA) {
			setVisible(true);
		}

		else {
			updateVenta(evento, datos);
		}
	}

	private void updateVenta(Evento evento, Object datos) {

		if (evento == Evento.RES_ABRIR_VENTA_OK) {
			mensaje("Carrito generado con exito");
			Controller.getInstancia().accion(new Context(Evento.PASAR_CARRITO_A_CERRAR, datos));
		} else if (evento == Evento.RES_ABRIR_VENTA_KO) {
			mensaje("Error inesperado al inicial la venta");
			Controller.getInstancia().accion(new Context(Evento.VENTA, evento));
		}

		dispose();
	}

	private void mensaje(String texto) {
		JOptionPane.showMessageDialog(null, texto);
	}
}
