package Presentacion.AlquilerJPA;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JTextField;

import Negocio.AlquilerJPA.TCarritoAlquiler;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class VistaEliminarMaquinaCarrito extends JFrame implements IGUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TCarritoAlquiler carrito;

	public VistaEliminarMaquinaCarrito() {

		super("[ELIMINAR MAQUINA DEL CARRITO]");
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

		JButton ok_button = new JButton("OK");
		ok_button.setVisible(true);
		ok_button.addActionListener((e) -> {
			try {
				int id = Integer.valueOf(searchText.getText());
				if (Integer.valueOf(searchText.getText()) <= 0)
					JOptionPane.showMessageDialog(null, "Error: No puede haber id maquina o duracion negativo o cero");
				else {
					carrito.set_id_maquina(id);
					Controller.getInstancia().accion(new Context(Evento.ELIMINAR_MAQUINA_EN_ALQUILER_NEGOCIO, carrito));
					this.dispose();
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Error: No puede haber campos vacios o con letras");
			}
		});
		supPanel.add(ok_button);
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

		if (evento == Evento.ELIMINAR_MAQUINA_EN_ALQUILER_VISTA) {
			setVisible(true);
		}

		else {
			updateAlquiler(evento, datos);
		}
	}

	private void updateAlquiler(Evento evento, Object datos) {

		if (evento == Evento.RES_PASAR_CARRITO_ALQUILER_A_ELIMINAR_OK)
			this.carrito = (TCarritoAlquiler) datos;

		else if (evento == Evento.RES_PASAR_CARRITO_ALQUILER_A_ELIMINAR_KO) {
			mensaje("Error en el traspaso del carrito");
			dispose();
		} else {

			if (evento == Evento.RES_ELIMINAR_MAQUINA_EN_ALQUILER_OK)
				mensaje("Exito: Maquina eliminada del carrito");

			else if (evento == Evento.RES_ELIMINAR_MAQUINA_EN_ALQUILER_KO)
				mensaje("Error: La maquina no esta en el carrito");

			Controller.getInstancia().accion(new Context(Evento.PASAR_CARRITO_ALQUILER_A_CERRAR, datos));
			dispose();
		}
	}

	private void mensaje(String texto) {
		JOptionPane.showMessageDialog(null, texto);
	}
}