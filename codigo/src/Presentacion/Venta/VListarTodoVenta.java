package Presentacion.Venta;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Negocio.Venta.TVenta;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Set;

public class VListarTodoVenta extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private String[] _columnName = { "id venta", "id trabajador", "total factura", "activo" };

	private DefaultTableModel _table;

	public VListarTodoVenta() {
		super("[LISTA]");
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

		JButton listarXTrabajadorButton = new JButton("LISTAR POR TRABAJADOR");
		listarXTrabajadorButton.setVisible(true);
		listarXTrabajadorButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.LISTAR_VENTAS_X_TRABAJADOR_VISTA, null));
			this.dispose();
		});
		supPanel.add(listarXTrabajadorButton);

		JButton mostrarButton = new JButton("MOSTRAR");
		mostrarButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.LISTAR_VENTAS_ALL_NEGOCIO, null));
			this.dispose();
		});
		supPanel.add(mostrarButton);

		JButton listarXPPButton = new JButton("LISTAR POR PROD. EN PLATAFORMA");
		listarXPPButton.setVisible(true);
		listarXPPButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.LISTAR_VENTAS_X_PRODUCTO_EN_PLATAFORMA_VISTA, null));
			this.dispose();
		});
		supPanel.add(listarXPPButton);
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

		JTable _info = new JTable(_table);
		JScrollPane scroll = new JScrollPane(_info, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setPreferredSize(new Dimension(500, 100));
		midPanel.add(scroll);
	}

	private void rellenarPanelInferior(JPanel bottomPanel) {

		JButton cancelButton = new JButton("CANCEL");
		cancelButton.setVisible(true);
		cancelButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VENTA, null));
			this.dispose();
		});
		bottomPanel.add(cancelButton);
	}

	public void update(Context context) {

		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		if (evento == Evento.LISTAR_VENTAS_ALL_VISTA) {
			setVisible(true);
		}

		else {
			updateVenta(evento, datos);
		}
	}

	private void updateVenta(Evento evento, Object datos) {

		if (evento == Evento.RES_LISTAR_VENTAS_ALL_OK) {
			fillTableOkEvent(datos);
		}

		else if (evento == Evento.RES_LISTAR_VENTAS_ALL_KO) {
			mensaje("Error en la carga de la lista");
		}
	}

	private void mensaje(String texto) {
		JOptionPane.showMessageDialog(null, texto);
	}

	@SuppressWarnings("unchecked")
	private void fillTableOkEvent(Object datos) {

		Set<TVenta> _list = (Set<TVenta>) datos;

		if (_list.size() == 0) {
			mensaje("No hay ventas activas");
		}

		this._table.setRowCount(0);

		for (TVenta tVenta : _list) {
			Object[] row = { tVenta.get_id(), tVenta.get_trabajador(), tVenta.get_total_factura(),
					tVenta.get_activo() };
			this._table.addRow(row);
		}
		this._table.fireTableDataChanged();
	}
}