package Presentacion.AlquilerJPA;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import Negocio.AlquilerJPA.TAlquiler;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Set;

public class VistaListarAlquileresPorEmpleado extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField empleadoIdField;
	private JTable alquileresTable;
	private DefaultTableModel tableModel;
	private String[] columnNames = { "ID Alquiler", "ID Cliente", "ID Empleado", "Fecha", "Total", "Activo" };

	public VistaListarAlquileresPorEmpleado() {
		super("[LISTAR ALQUILERES POR EMPLEADO]");
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
		rellenarPanelCentral(midPanel);
		JPanel bottomPanel = new JPanel();
		mainPanel.add(bottomPanel, BorderLayout.PAGE_END);
		rellenarPanelInferior(bottomPanel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(600, 400));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void rellenarPanelSuperior(JPanel supPanel) {
		JLabel empleadoIdLabel = new JLabel("ID del Empleado:");
		supPanel.add(empleadoIdLabel);

		empleadoIdField = new JTextField();
		empleadoIdField.setPreferredSize(new Dimension(100, 25));
		supPanel.add(empleadoIdField);

		JButton buscarButton = new JButton("BUSCAR");
		buscarButton.addActionListener(e -> buscarAlquileresPorEmpleado());
		supPanel.add(buscarButton);
	}

	private void rellenarPanelCentral(JPanel midPanel) {
		tableModel = new DefaultTableModel(columnNames, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		alquileresTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(alquileresTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(500, 200));
		midPanel.add(scrollPane);
	}

	private void rellenarPanelInferior(JPanel bottomPanel) {
		JButton cancelButton = new JButton("CANCELAR");
		cancelButton.addActionListener(e -> {
			Controller.getInstancia().accion(new Context(Evento.LISTAR_ALQUILERES_ALL_VISTA, null));
			this.dispose();
		});
		bottomPanel.add(cancelButton);
	}

	private void buscarAlquileresPorEmpleado() {
		try {
			int empleadoId = Integer.parseInt(empleadoIdField.getText());
			Controller.getInstancia().accion(new Context(Evento.LISTAR_ALQUILERES_X_EMPLEADO_NEGOCIO, empleadoId));
			this.dispose();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El ID del empleado debe ser un número válido.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		if (evento == Evento.RES_LISTAR_ALQUILERES_X_EMPLEADO_OK) {
			Set<TAlquiler> alquileres = (Set<TAlquiler>) datos;
			actualizarTabla(alquileres);
		} else if (evento == Evento.RES_LISTAR_ALQUILERES_X_EMPLEADO_KO) {
			JOptionPane.showMessageDialog(this, "Error al cargar los alquileres del empleado.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void actualizarTabla(Set<TAlquiler> alquileres) {
		tableModel.setRowCount(0);

		if (alquileres.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No se encontraron alquileres para este empleado.", "Información",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		for (TAlquiler alquiler : alquileres) {
			Object[] row = { alquiler.get_id_alquiler(), alquiler.get_id_cliente(), alquiler.get_id_empleado(),
					dateFormat.format(alquiler.get_fecha()), alquiler.get_total(), alquiler.get_activo() };
			tableModel.addRow(row);
		}
		tableModel.fireTableDataChanged();
	}
}
