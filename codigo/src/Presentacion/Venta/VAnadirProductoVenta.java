package Presentacion.Venta;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Negocio.Venta.TCarrito;

import java.awt.BorderLayout;
import java.awt.Dimension;

public class VAnadirProductoVenta extends JFrame implements IGUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TCarrito carrito;

	public VAnadirProductoVenta() {
		super("[ANADIRPP]");
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

		JLabel idLabel = new JLabel("id producto: ");
		JTextField searchText = new JTextField();
		searchText.setVisible(true);
		searchText.setPreferredSize(new Dimension(100, 30));
		supPanel.add(idLabel);
		supPanel.add(searchText);

		JLabel cantidadLabel = new JLabel("cantidad: ");
		JTextField cantidadText = new JTextField();
		cantidadText.setVisible(true);
		cantidadText.setPreferredSize(new Dimension(100, 30));
		supPanel.add(cantidadLabel);
		supPanel.add(cantidadText);

		JButton ok_button = new JButton("OK");
		ok_button.setVisible(true);
		ok_button.addActionListener((e) -> {
			try {
				int id = Integer.valueOf(searchText.getText());
				int cantidad = Integer.valueOf(cantidadText.getText());
				if (Integer.valueOf(searchText.getText()) <= 0 || Integer.valueOf(cantidadText.getText()) <= 0)
					JOptionPane.showMessageDialog(null, "Error: No puede haber id producto o cantidad negativo o cero");
				else {

					carrito.set_id_producto_final(id);
					carrito.set_cantidad(cantidad);
					Controller.getInstancia().accion(new Context(Evento.INSERTAR_PP_EN_VENTA_NEGOCIO, carrito));
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
			Controller.getInstancia().accion(new Context(Evento.PASAR_CARRITO_A_CERRAR, carrito));
			this.dispose();
		});
		bottomPanel.add(cancelButton);
	}

	public void update(Context context) {

		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		if (evento == Evento.INSERTAR_PP_EN_VENTA_VISTA) {
			setVisible(true);
		}

		else {
			updateVenta(evento, datos);
		}
	}

	private void updateVenta(Evento evento, Object datos) {

		if (evento == Evento.RES_PASAR_CARRITO_A_INSERTAR_OK)
			this.carrito = (TCarrito) datos;

		else {

			if (evento == Evento.RES_PASAR_CARRITO_A_INSERTAR_KO) {
				mensaje("Error en el traspaso del carrito");
			} else {

				if (evento == Evento.RES_INSERTAR_PP_EN_VENTA_OK)
					mensaje("Producto anyadido al carrito");

				else if (evento == Evento.RES_INSERTAR_PP_EN_VENTA_KO)
					mensaje("No se pudo anyadir al carrito comprueba datos");

				Controller.getInstancia().accion(new Context(Evento.PASAR_CARRITO_A_CERRAR, datos));
			}

			this.dispose();
		}
	}

	private void mensaje(String texto) {
		JOptionPane.showMessageDialog(null, texto);
	}

}