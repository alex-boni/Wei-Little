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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JTextField;

import Negocio.ProductoEnPlataforma.TProductoEnPlataforma;
import Negocio.Trabajador.TTrabajador;
import Negocio.Venta.TLineaVenta;
import Negocio.Venta.TVenta;
import Negocio.Venta.TVentaCompletaTOA;

public class VBuscarVenta extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VBuscarVenta() {
		super("[BUSCAR]");
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
		JLabel idLabel = new JLabel("id venta: ");
		JTextField searchTextField = new JTextField();
		searchTextField.setVisible(true);
		searchTextField.setPreferredSize(new Dimension(100, 30));
		supPanel.add(idLabel);
		supPanel.add(searchTextField);

		JButton ok_button = new JButton("OK");
		ok_button.setVisible(true);
		ok_button.addActionListener((e) -> {
			try {
				int id_factura = Integer.valueOf(searchTextField.getText());
				if (id_factura <= 0)
					JOptionPane.showMessageDialog(null, "Error: Id no puede ser negativo o cero");
				else {
					Controller.getInstancia().accion(new Context(Evento.BUSCAR_VENTA_NEGOCIO, id_factura));
					this.dispose();
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Error: No puede estar el campo vacio o con letras");
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

	@Override
	public void update(Context context) {

		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		if (evento == Evento.BUSCAR_VENTA_VISTA) {
			setVisible(true);
		}

		else {
			updateVenta(evento, datos);
		}
	}

	private void updateVenta(Evento evento, Object datos) {

		if (evento == Evento.RES_BUSCAR_VENTA_OK) {
			String factura = rellenarFactura((TVentaCompletaTOA) datos);
			mensaje(factura);
		}

		else if (evento == Evento.RES_BUSCAR_VENTA_KO) {
			mensaje("Venta no enontrada en la Base de Datos.");
		}

		Controller.getInstancia().accion(new Context(Evento.BUSCAR_VENTA_VISTA, null));
		this.dispose();
	}

	private void mensaje(String texto) {
		JOptionPane.showMessageDialog(null, texto);
	}

	private String rellenarFactura(TVentaCompletaTOA ventaCompleta) {

		TVenta venta = ventaCompleta.get_venta();
		TTrabajador trabajador = ventaCompleta.get_trabajador();
		Set<TLineaVenta> lista_lineasVenta = ventaCompleta.get_lista_lineasVenta();
		Set<TProductoEnPlataforma> lista_producto_plataforma = ventaCompleta.get_lista_producto_plataforma();

		String cabecera = rellenarCabecera(venta, trabajador);
		String lineasVenta = rellenarLineasVenta(lista_lineasVenta, lista_producto_plataforma);

		return cabecera + lineasVenta;
	}

	private String rellenarCabecera(TVenta venta, TTrabajador trabajador) {

		StringBuilder builder = new StringBuilder();

		builder.append("\nSe ha encontrado venta con id: " + venta.get_id());
		builder.append("\nTrabajador: " + trabajador.get_id());
		builder.append(", DNI: " + trabajador.get_dni());
		builder.append(", nombre: " + trabajador.get_nombre());
		builder.append(", activo: " + trabajador.get_activo());
		builder.append("\nTotal factura: " + venta.get_total_factura()
				+ "\n-------------------------------------------------------------------------------------");

		return builder.toString();
	}

	private String rellenarLineasVenta(Set<TLineaVenta> lista_lineasVenta,
			Set<TProductoEnPlataforma> lista_producto_plataforma) {

		StringBuilder builder = new StringBuilder();

		Iterator<TLineaVenta> it_lineasVenta = lista_lineasVenta.iterator();
		Iterator<TProductoEnPlataforma> it_producto_plataforma = lista_producto_plataforma.iterator();

		while (it_lineasVenta.hasNext() && it_producto_plataforma.hasNext()) {
			TLineaVenta lineaVenta = it_lineasVenta.next();
			TProductoEnPlataforma productoPlataforma = it_producto_plataforma.next();
			// if(lineaVenta.get_activo()==1) {
			builder.append("\nid_producto_plataforma: " + lineaVenta.get_producto());
			builder.append(", cantidad: " + lineaVenta.get_cantidad());
			builder.append(", precio: " + lineaVenta.get_precio_cantidad());
			builder.append(", activo: " + lineaVenta.get_activo());
			builder.append(" \n         Con los siguientes datos de ProcEnPlat actuales: ");
			builder.append("\n          id_producto: " + productoPlataforma.get_id_producto());
			builder.append(", id_plataforma: " + productoPlataforma.get_id_plataforma());
			builder.append(", stock: " + productoPlataforma.get_cantidad());
			builder.append(", precio actual: " + productoPlataforma.get_precio());
			builder.append(", activo: " + productoPlataforma.get_activo()
					+ "\n-------------------------------------------------------------------------------------");
			// }
		}

		return builder.toString();
	}
}