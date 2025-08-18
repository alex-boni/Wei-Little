
package Presentacion.AlquilerJPA;

import javax.swing.JFrame;

import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import javax.swing.JTextField;

import Negocio.AlquilerJPA.TCarritoAlquiler;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;

public class VistaAnyadirMaquinaAlquiler extends JFrame implements IGUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TCarritoAlquiler carrito;

	public VistaAnyadirMaquinaAlquiler() {
		super("[ANADIR MAQUINA]");
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

		JLabel idLabel = new JLabel("id maquina: ");
		JTextField searchText = new JTextField();
		searchText.setVisible(true);
		searchText.setPreferredSize(new Dimension(100, 30));
		supPanel.add(idLabel);
		supPanel.add(searchText);

		JLabel duracionLabel = new JLabel("duracion: ");
		JTextField duracionText = new JTextField();
		duracionText.setVisible(true);
		duracionText.setPreferredSize(new Dimension(100, 30));
		supPanel.add(duracionLabel);
		supPanel.add(duracionText);

		JButton ok_button = new JButton("OK");
		ok_button.setVisible(true);
		ok_button.addActionListener((e) -> {
			if (validar(searchText, duracionText)) {
				int id = Integer.valueOf(searchText.getText());
				int duracion = Integer.valueOf(duracionText.getText());
				carrito.set_id_maquina(id);
				carrito.set_duracion(duracion);
				Controller.getInstancia().accion(new Context(Evento.INSERTAR_MAQUINA_EN_ALQUILER_NEGOCIO, carrito));
				this.dispose();
			}
		});
		supPanel.add(ok_button);
	}

	private boolean validar(JTextField searchText, JTextField duracionText) {
		boolean esValido = true;
		try {
			if (Integer.valueOf(searchText.getText()) <= 0 || Integer.valueOf(duracionText.getText()) <= 0) {
				esValido = false;
				JOptionPane.showMessageDialog(null, "Error: No puede haber id maquina o duracion negativo o cero");
			}
		} catch (Exception ex) {
			esValido = false;
			JOptionPane.showMessageDialog(null, "Error: No puede haber campos vacios o con letras");
		}
		return esValido;
	}

	private void rellenarPanelInferior(JPanel bottomPanel) {

		JButton cancelButton = new JButton("CANCEL");
		cancelButton.setToolTipText("cancel");
		cancelButton.setVisible(true);
		cancelButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.PASAR_CARRITO_ALQUILER_A_CERRAR, carrito));
			this.dispose();
		});
		bottomPanel.add(cancelButton);
	}

	public void update(Context context) {

		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		if (evento == Evento.INSERTAR_MAQUINA_EN_ALQUILER_VISTA) {
			setVisible(true);
		}

		else {
			updateAlquiler(evento, datos);
		}
	}

	private void updateAlquiler(Evento evento, Object datos) {

		if (evento == Evento.RES_PASAR_CARRITO_ALQUILER_A_INSERTAR_OK)
			this.carrito = (TCarritoAlquiler) datos;

		else {

			if (evento == Evento.RES_PASAR_CARRITO_ALQUILER_A_INSERTAR_KO) {
				mensaje("Error en el traspaso del carrito");
			} else {

				if (evento == Evento.RES_INSERTAR_MAQUINA_EN_ALQUILER_OK)
					mensaje("Maquina anyadido al carrito");

				else if (evento == Evento.RES_INSERTAR_MAQUINA_EN_ALQUILER_KO)
					mensaje("No se puede anyadir la misma maquina al carrito ");

				Controller.getInstancia().accion(new Context(Evento.PASAR_CARRITO_ALQUILER_A_CERRAR, datos));
			}

			this.dispose();
		}
	}

	private void mensaje(String texto) {
		JOptionPane.showMessageDialog(null, texto);
	}
}