package Presentacion.EmpleadoJPA;

import Negocio.EmpleadoJPA.TEmpleado;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class VistaListarTodoEmpleado extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private final String[] _columnName = { "ID Empleado", "DNI", "Nombre", "Salario", "Activo" };

	private DefaultTableModel _table;

	public VistaListarTodoEmpleado() {
		super("[LISTA DE EMPLEADOS]");
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

		JButton mostrarButton = new JButton("MOSTRAR TODOS");
		mostrarButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.LISTAR_EMPLEADO_ALL, null));
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

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
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
			Controller.getInstancia().accion(new Context(Evento.EMPLEADO, null));
			this.dispose();
		});
		bottomPanel.add(cancelButton);
	}

	@Override
	public void update(Context context) {

		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		if (evento == Evento.LISTAR_EMPLEADO_ALL) {
			setVisible(true);
		} else {
			updateEmpleado(evento, datos);
		}
	}

	private void updateEmpleado(Evento evento, Object datos) {

		if (evento == Evento.RES_LISTAR_EMPLEADO_ALL_OK) {
			fillTableOkEvent(datos);
		} else if (evento == Evento.RES_LISTAR_EMPLEADO_ALL_OK_VACIO) {
			mensaje("No se han encontrado empleados en la base de datos.");
		}
	}

	private void mensaje(String texto) {
		JOptionPane.showMessageDialog(null, texto);
	}

	@SuppressWarnings("unchecked")
	private void fillTableOkEvent(Object datos) {

		Set<TEmpleado> _list = (Set<TEmpleado>) datos;

		if (_list.isEmpty()) {
			mensaje("No hay empleados activos");
		}

		this._table.setRowCount(0);

		for (TEmpleado tEmpleado : _list) {
			Object[] row = { tEmpleado.get_id_empleado(), tEmpleado.get_DNI(), tEmpleado.get_nombre(),
					tEmpleado.get_salario(), tEmpleado.get_activo() == 1 ? "SI" : "NO" };
			this._table.addRow(row);
		}
		this._table.fireTableDataChanged();
	}
}
