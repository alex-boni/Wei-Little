package Presentacion.Venta;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import javax.swing.JTextField;

import Negocio.Venta.TVenta;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

public class VModificarVenta extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField textfield;

	public VModificarVenta() {
		super("[MODIFICACION]");
		initGUI();
	}

	private void initGUI() {

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		setContentPane(mainPanel);

		JPanel supPanel = new JPanel();
		mainPanel.add(supPanel, BorderLayout.PAGE_START);
		rellenarPanelSuperior(supPanel);

		JPanel bottomPanel = new JPanel();
		mainPanel.add(bottomPanel, BorderLayout.PAGE_END);
		rellenarPanelInferior(bottomPanel);

		JPanel centerPanel = new JPanel();
		textfield = new JTextField();
		textfield.setEditable(false);
		textfield.setPreferredSize(new Dimension(500, 100));
		centerPanel.add(textfield);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(600, 300));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void rellenarPanelSuperior(JPanel supPanel) {

		JTextField searchText = new JTextField();
		searchText.setPreferredSize(new Dimension(80, 30));

		JTextField searchVentaText = new JTextField();
		searchVentaText.setPreferredSize(new Dimension(80, 30));

		JLabel idTrabajadorLabel = new JLabel("ID del nuevo trabajador:");
		JLabel idVentaLabel = new JLabel("ID de la venta:");

		JButton ok_button = botonConAccion(accionBotonOk(searchVentaText, searchText), "OK", "Okey");

		supPanel.add(idTrabajadorLabel);
		supPanel.add(searchText);
		supPanel.add(idVentaLabel);
		supPanel.add(searchVentaText);
		supPanel.add(ok_button);
	}

	private ActionListener accionBotonOk(JTextField searchVentaText, JTextField searchText) {
		return e -> {
			try {
				TVenta tVenta = new TVenta();

				int idVenta = Integer.valueOf(searchVentaText.getText());
				int idTrabajador = Integer.valueOf(searchText.getText());

				if (idVenta <= 0 || idTrabajador <= 0)
					mensaje("Error: No puede haber datos negativos o cero");

				tVenta.set_id(idVenta);
				tVenta.set_trabajador(idTrabajador);

				accionConDatos(Evento.MODIFICAR_VENTA_NEGOCIO, tVenta);
			} catch (Exception ex) {
				mensaje("Error: No puede haber campos vacios o con letras");
			}
		};
	}

	private void rellenarPanelInferior(JPanel bottomPanel) {
		JButton cancelButton = botonSinAccion(Evento.VENTA, "CANCEL", "cancel");
		bottomPanel.add(cancelButton);
	}

	public void update(Context context) {

		Evento evento = context.getEvento();

		if (evento == Evento.MODIFICAR_VENTA_VISTA) {
			setVisible(true);
		}

		else {
			updateVenta(evento);
		}
	}

	private void updateVenta(Evento evento) {

		if (evento == Evento.RES_MODIFICAR_VENTA_OK) {
			mensaje("Exito, venta modificado");
		}

		else if (evento == Evento.RES_MODIFICAR_VENTA_KO) {
			mensaje("Error, compruebe datos");
		}

		accionSinDatos(Evento.VENTA);
	}

	private void mensaje(String texto) {
		JOptionPane.showMessageDialog(null, texto);
	}

	private void accionSinDatos(Evento evento) {
		accionConDatos(evento, null);
	}

	private void accionConDatos(Evento evento, Object datos) {
		Controller.getInstancia().accion(new Context(evento, datos));
		dispose();
	}

	private JButton botonSinAccion(Evento evento, String texto, String tooltip) {
		return botonConAccion(e -> accionSinDatos(evento), texto, tooltip);
	}

	private JButton botonConAccion(ActionListener al, String texto, String tooltip) {
		JButton boton = new JButton(texto);
		boton.setToolTipText(tooltip);
		boton.addActionListener(al);
		return boton;
	}
}