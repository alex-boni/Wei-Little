
package Presentacion.AlquilerJPA;

import javax.swing.JFrame;

import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import java.util.Iterator;
import java.util.Set;
import javax.swing.table.DefaultTableModel;

import Negocio.AlquilerJPA.TAlquiler;
import Negocio.AlquilerJPA.TCarritoAlquiler;
import Negocio.AlquilerJPA.TLineaAlquiler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;

public class VistaCerrarAlquiler extends JFrame implements IGUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DefaultTableModel _table;

	private String[] _columnName = { "id maquina", "duracion" };

	private TCarritoAlquiler carrito;

	public VistaCerrarAlquiler() {

		super("[CERRAR ALQUILER]");
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
				"<html><p>Ha entrado en el proceso de compra</p> <p> CERRAR ALQUILER para finalizar CANCEL para salir</p></html>");
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

		JButton anadirButton = new JButton("ANADIR MAQ.");
		anadirButton.setVisible(true);
		anadirButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.PASAR_CARRITO_ALQUILER_A_INSERTAR, carrito));
			this.dispose();
		});
		rightPanel.add(anadirButton);

		JButton eliminarButton = new JButton("ELIMINAR MAQ.");
		eliminarButton.setVisible(true);
		eliminarButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.PASAR_CARRITO_ALQUILER_A_ELIMINAR, carrito));
			this.dispose();
		});
		leftPanel.add(eliminarButton);

	}

	private void rellenarPanelInferior(JPanel bottomPanel) {

		JButton cerrarAlquilerButton = new JButton("CERRAR ALQUILER");
		cerrarAlquilerButton.setVisible(true);
		cerrarAlquilerButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.CERRAR_ALQUILER_NEGOCIO, carrito));
			this.dispose();
		});

		JButton cancelButton = new JButton("CANCEL");
		cancelButton.setVisible(true);
		cancelButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.ALQUILER, null));
			this.dispose();
		});
		bottomPanel.add(cerrarAlquilerButton);
		bottomPanel.add(cancelButton);
	}

	@Override
	public void update(Context context) {

		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		if (evento == Evento.CERRAR_ALQUILER_VISTA) {
			setVisible(true);
		}

		else {
			updateAlquiler(evento, datos);
		}
	}

	private void updateAlquiler(Evento evento, Object datos) {

		if (evento == Evento.RES_PASAR_CARRITO_ALQUILER_A_CERRAR_OK) {
			carrito = (TCarritoAlquiler) datos;
			Iterator<TLineaAlquiler> i = carrito.iterator();

			while (i.hasNext()) {
				TLineaAlquiler lineaAlquiler = i.next();
				Object[] fila = { lineaAlquiler.get_id_maquina(), lineaAlquiler.get_duracion() };
				this._table.addRow(fila);
			}

		} else {
			if (evento == Evento.RES_PASAR_CARRITO_ALQUILER_A_CERRAR_KO) {
				mensaje("Error en el traspaso del carrito");
			} else {

				if (evento == Evento.RES_CERRAR_ALQUILER_OK) {
					String factura = rellenarFactura((TCarritoAlquiler) datos);
					mensaje("Exito al cerrar la alquiler" + factura);
				} else if (evento == Evento.RES_CERRAR_ALQUILER_KO)
					mensaje("Error al cerrar alquiler");
				else if (evento == Evento.RES_CERRAR_ALQUILER_KO_NOCLIENTE)
					mensaje("Error no existe el cliente");
				else if (evento == Evento.RES_CERRAR_ALQUILER_KO_NOEMPLEADO)
					mensaje("Error no existe el empleado");
				else if (evento == Evento.RES_CERRAR_ALQUILER_KO_CARRITO_VACIO)
					mensaje("Error todos las maquinas solicitadas estan alquiladas");

				Controller.getInstancia().accion(new Context(Evento.ALQUILER, null));
			}
			this.dispose();
		}
	}

	private void mensaje(String texto) {
		JOptionPane.showMessageDialog(null, texto);
	}

	private String rellenarFactura(TCarritoAlquiler compra) {

		TAlquiler alquiler = compra.get_tAlquiler();
		Set<TLineaAlquiler> lista_lineasAlquiler = compra.get_tLineasAlquiler();

		String cabecera = rellenarCabecera(alquiler);
		String lineasAlquiler = rellenarLineasAlquiler(lista_lineasAlquiler);

		return cabecera + lineasAlquiler;
	}

	private String rellenarCabecera(TAlquiler alquiler) {

		StringBuilder builder = new StringBuilder();

		builder.append("\nHa finalizado la compra, id: " + alquiler.get_id_alquiler());
		builder.append("\nEmpleado: " + alquiler.get_id_empleado());
		builder.append("\nCliente: " + alquiler.get_id_cliente());
		builder.append("\nTotal factura: " + alquiler.get_total() + "\n--------------------");

		return builder.toString();
	}

	private String rellenarLineasAlquiler(Set<TLineaAlquiler> lista_lineasAlquiler) {

		StringBuilder builder = new StringBuilder();

		Iterator<TLineaAlquiler> it_lineasAlquiler = lista_lineasAlquiler.iterator();

		while (it_lineasAlquiler.hasNext()) {
			TLineaAlquiler lineaAlquiler = it_lineasAlquiler.next();
			builder.append("\nid_maquina: " + lineaAlquiler.get_id_maquina());
			builder.append(", Duracion: " + lineaAlquiler.get_duracion());
			builder.append(", Precio: " + lineaAlquiler.get_precio());

			builder.append(
					", Devuelto: " + (lineaAlquiler.get_devuelto() == 0 ? "NO" : "SI") + "\n--------------------");
		}
		builder.append("\n SI NO ESTA ALGUNA MAQUINA QUE ESTABA EN CARRITO PUEDE DEBERSE A:"
				+ "\n1: NO HAY SUFICIENTES EXISTENCIAS" + "\n2: LA MAQUINA NO EXISTE" + "\n GRACIAS POR SU COMPRA");

		return builder.toString();
	}
}