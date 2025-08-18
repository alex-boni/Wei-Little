package Presentacion.Venta;

import Negocio.Venta.TCarrito;
import Negocio.Venta.TLineaVenta;
import Negocio.Venta.TVenta;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Iterator;
import java.util.Set;

public class VCerrarVenta extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private DefaultTableModel _table;

	private String[] _columnName = { "id producto final", "cantidad" };

	private TCarrito carrito;

	public VCerrarVenta() {

		super("[CERRAR VENTA]");
		initGUI();
	}

	private void initGUI() {

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.setContentPane(mainPanel);

		JPanel supPanel = new JPanel();
		mainPanel.add(supPanel, BorderLayout.PAGE_START);
		rellenarPanelSuperior(supPanel);

		JPanel midPanel = new JPanel();
		mainPanel.add(midPanel, BorderLayout.CENTER);
		rellenarPanelCentrar(midPanel);

		JPanel rightPanel = new JPanel();
		mainPanel.add(rightPanel, BorderLayout.LINE_START);
		JPanel leftPanel = new JPanel();
		mainPanel.add(leftPanel, BorderLayout.LINE_END);
		rellenarPanelLaterales(rightPanel, leftPanel);

		JPanel bottomPanel = new JPanel();
		mainPanel.add(bottomPanel, BorderLayout.PAGE_END);
		rellenarPanelInferior(bottomPanel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// setPreferredSize(new Dimension(600, 300));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void rellenarPanelSuperior(JPanel supPanel) {

		JLabel textLabel = new JLabel(
				"<html><p>Ha entrado en el proceso de compra</p> <p> CERRAR VENTA para finalizar CANCEL para salir</p></html>");
		textLabel.setVisible(true);
		textLabel.setPreferredSize(new Dimension(300, 50));
		supPanel.add(textLabel);
	}

	private void rellenarPanelCentrar(JPanel midPanel) {

		_table = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public String getColumnName(int column) {

				return _columnName[column];
			}

			@Override
			public int getColumnCount() {

				return _columnName.length;
			}
		};

		JTable table = new JTable(_table);
		JScrollPane scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setPreferredSize(new Dimension(500, 100));
		midPanel.add(scroll);
	}

	private void rellenarPanelLaterales(JPanel rightPanel, JPanel leftPanel) {

		JButton anadirButton = new JButton("ANADIR PROD.");
		anadirButton.setVisible(true);
		anadirButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.PASAR_CARRITO_A_INSERTAR, carrito));
			this.dispose();
		});
		rightPanel.add(anadirButton);

		JButton eliminarButton = new JButton("ELIMINAR PROD.");
		eliminarButton.setVisible(true);
		eliminarButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.PASAR_CARRITO_A_ELIMINAR, carrito));
			this.dispose();
		});
		leftPanel.add(eliminarButton);

	}

	private void rellenarPanelInferior(JPanel bottomPanel) {

		JButton cerrarVentaButton = new JButton("CERRAR VENTA");
		cerrarVentaButton.setVisible(true);
		cerrarVentaButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.CERRAR_VENTA_NEGOCIO, carrito));
			this.dispose();
		});

		JButton cancelButton = new JButton("CANCEL");
		cancelButton.setVisible(true);
		cancelButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VENTA, null));
			this.dispose();
		});
		bottomPanel.add(cerrarVentaButton);
		bottomPanel.add(cancelButton);
	}

	@Override
	public void update(Context context) {

		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		if (evento == Evento.CERRAR_VENTA_VISTA) {
			setVisible(true);
		}

		else {
			updateVenta(evento, datos);
		}
	}

	private void updateVenta(Evento evento, Object datos) {

		if (evento == Evento.RES_PASAR_CARRITO_A_CERRAR_OK) {

			carrito = (TCarrito) datos;
			Iterator<TLineaVenta> i = carrito.iterator();

			while (i.hasNext()) {
				TLineaVenta lineaVenta = i.next();
				Object[] fila = { lineaVenta.get_producto(), lineaVenta.get_cantidad() };
				this._table.addRow(fila);
			}

		} else {
			if (evento == Evento.RES_PASAR_CARRITO_A_CERRAR_KO) {
				mensaje("Error en el traspaso del carrito");
			} else {

				if (evento == Evento.RES_CERRAR_VENTA_OK) {
					String factura = rellenarFactura((TCarrito) datos);
					mensaje("Exito al cerrar la venta" + factura);
				} else if (evento == Evento.RES_CERRAR_VENTA_KO)
					mensaje("Error al cerrar venta");

				Controller.getInstancia().accion(new Context(Evento.VENTA, null));
			}
			this.dispose();
		}
	}

	private void mensaje(String texto) {
		JOptionPane.showMessageDialog(null, texto);
	}

	private String rellenarFactura(TCarrito compra) {

		TVenta venta = compra.get_venta();
		Set<TLineaVenta> lista_lineasVenta = compra.get_lista_lineaVenta();

		String cabecera = rellenarCabecera(venta);
		String lineasVenta = rellenarLineasVenta(lista_lineasVenta);

		return cabecera + lineasVenta;
	}

	private String rellenarCabecera(TVenta venta) {

		StringBuilder builder = new StringBuilder();

		builder.append("\nHa finalizado la compra, id: " + venta.get_id());
		builder.append("\nTrabajador: " + venta.get_trabajador());
		builder.append("\nTotal factura: " + venta.get_total_factura() + "\n--------------------");

		return builder.toString();
	}

	private String rellenarLineasVenta(Set<TLineaVenta> lista_lineasVenta) {

		StringBuilder builder = new StringBuilder();

		Iterator<TLineaVenta> it_lineasVenta = lista_lineasVenta.iterator();

		while (it_lineasVenta.hasNext()) {
			TLineaVenta lineaVenta = it_lineasVenta.next();
			builder.append("\nid_producto_plataforma: " + lineaVenta.get_producto());
			builder.append(", Cantidad: " + lineaVenta.get_cantidad());
			builder.append(", Precio: " + lineaVenta.get_precio_cantidad() + "\n--------------------");
		}
		builder.append("\n SI NO ESTA ALGUN PRODUCTO QUE ESTABA EN CARRITO PUEDE DEBERSE A:"
				+ "\n1: NO HAY SUFICIENTES EXISTENCIAS" + "\n2: EL PRODUCTO NO EXISTE" + "\n GRACIAS POR SU COMPRA");

		return builder.toString();
	}
}