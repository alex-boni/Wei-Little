package Presentacion.ClienteJPA;

import javax.swing.JFrame;

import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.util.Set;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import Negocio.ClienteJPA.TCliente;

import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;

public class VistaListarTodoCliente extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private String[] _columnName = { "id cliente", "nombre", "dni", "activo" };

	private DefaultTableModel _table;

	public VistaListarTodoCliente() {
		super("[LISTAR CLIENTE]");
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

		JButton mostrarButton = new JButton("MOSTRAR");
		mostrarButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.LISTAR_CLIENTES_ALL, null));
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
			Controller.getInstancia().accion(new Context(Evento.CLIENTE, null));
			this.dispose();
		});
		bottomPanel.add(cancelButton);
	}

	public void update(Context context) {

		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		if (evento == Evento.VLISTAR_CLIENTES_ALL) {
			setVisible(true);
		}

		else {
			updateCliente(evento, datos);
		}
	}

	private void updateCliente(Evento evento, Object datos) {

		if (evento == Evento.RES_LISTAR_CLIENTES_ALL_OK) {
			fillTableOkEvent(datos);
		}

		else if (evento == Evento.RES_LISTAR_CLIENTES_ALL_KO) {
			mensaje("Error: No se pudo cargar la lista");
		}
	}

	private void mensaje(String texto) {
		JOptionPane.showMessageDialog(null, texto);
	}

	@SuppressWarnings("unchecked")
	private void fillTableOkEvent(Object datos) {

		Set<TCliente> _list = (Set<TCliente>) datos;

		if (_list.size() == 0) {
			mensaje("No hay clientes activos");
		}

		this._table.setRowCount(0);

		for (TCliente tCliente : _list) {
			Object[] row = { tCliente.get_id_cliente(), tCliente.get_nombre(), tCliente.get_dni(),
					tCliente.get_activo() };
			this._table.addRow(row);
		}
		this._table.fireTableDataChanged();
	}
}