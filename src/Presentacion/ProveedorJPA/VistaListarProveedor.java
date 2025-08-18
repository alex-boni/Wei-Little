/**
 * 
 */
package Presentacion.ProveedorJPA;

import javax.swing.JFrame;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Negocio.ProveedorJPA.TProveedor;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class VistaListarProveedor extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private String[] _columnName = { "ID Proveedor", "Nombre", "CIF", "Activo" };

	private DefaultTableModel _table; // TODO REVISAR IMPORT

	public VistaListarProveedor() {
		super("[LISTA DE PROVEEDORES]");
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

		JButton mostrarButton = new JButton("MOSTRAR PROVEEDORES");
		mostrarButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.LISTAR_PROVEEDORES_ALL_NEGOCIO, null));
			this.dispose();
		});
		supPanel.add(mostrarButton);

	}

	private void rellenarPanelCentrar(JPanel midPanel) {

		_table = new DefaultTableModel() {

			private static final long serialVersionUID = 1L;

			@Override
			public String getColumnName(int column) {
				return _columnName[column];
			}

			@Override
			public int getColumnCount() {
				return _columnName.length;
			}

			// Sobrescribe el método para que ninguna celda sea editable
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Ninguna celda editable
			}
		};

		JTable _info = new JTable(_table);
		JScrollPane scroll = new JScrollPane(_info, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setPreferredSize(new Dimension(500, 100));
		midPanel.add(scroll);
	}

	private void rellenarPanelInferior(JPanel bottomPanel) {

		JButton cancelButton = new JButton("CANCELAR");
		cancelButton.setVisible(true);
		cancelButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.PROVEEDOR, null));
			this.dispose();
		});
		bottomPanel.add(cancelButton);
	}

	@Override
	public void update(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		if (evento == Evento.LISTAR_PROVEEDOR_ALL_VISTA) {
			setVisible(true);
		} else {
			if (evento == Evento.RES_LISTAR_PROVEEDORES_ALL_OK) {
				fillTableOkEvent(datos);
			} else if (evento == Evento.RES_LISTAR_PROVEEDORES_ALL_KO) {
				mensaje("Error en la carga de la lista de Proveedores.");
			}
		}

	}

	@SuppressWarnings("unchecked")
	private void fillTableOkEvent(Object datos) {

		Set<TProveedor> _list = (Set<TProveedor>) datos;

		if (_list.size() == 0) {
			mensaje("No hay prpveedores activos");
		}

		this._table.setRowCount(0);

		for (TProveedor tProveedor : _list) {
			Object[] row = { tProveedor.get_id_proveedor(), tProveedor.get_nombre(), tProveedor.get_CIF(),
					tProveedor.get_activo() };
			this._table.addRow(row);
		}
		this._table.fireTableDataChanged();
	}

	private void mensaje(String texto) {
		JOptionPane.showMessageDialog(null, texto);
	}

}