package Presentacion.AlquilerJPA;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import Negocio.AlquilerJPA.TAlquiler;
import Negocio.AlquilerJPA.TAlquilerTOA;
import Negocio.AlquilerJPA.TLineaAlquiler;
import Negocio.ClienteJPA.TCliente;
import Negocio.EmpleadoJPA.TEmpleado;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import java.awt.Dimension;
import java.util.ArrayList;

public class VistaBuscarAlquiler extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField clienteIdField;
	private JTable alquileresTable;
	private DefaultTableModel tableModel;
	private JPanel panelDatos;
	// TODO REVISAR LAS COLUMNAS
	private String[] columnNames = { "ID Maquina", "Duracion", "Precio", "Devuelto" };

	public VistaBuscarAlquiler() {
		super("[BUSCAR ALQUILER]");
		initGUI();
	}

	private void initGUI() {

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);

		JPanel supPanel = new JPanel();
		mainPanel.add(supPanel);
		rellenarPanelSuperior(supPanel);
		panelDatos = new JPanel();
		mainPanel.add(panelDatos);
		JPanel midPanel = new JPanel();
		mainPanel.add(midPanel);
		rellenarPanelCentral(midPanel);

		JPanel bottomPanel = new JPanel();
		mainPanel.add(bottomPanel);
		rellenarPanelInferior(bottomPanel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(600, 400));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void rellenarPanelSuperior(JPanel supPanel) {
		JLabel clienteIdLabel = new JLabel("ID DEL ALQUILER:");
		supPanel.add(clienteIdLabel);

		clienteIdField = new JTextField();
		clienteIdField.setPreferredSize(new Dimension(100, 25));
		supPanel.add(clienteIdField);

		JButton buscarButton = new JButton("BUSCAR");
		buscarButton.addActionListener(e -> buscarAlquileresPorCliente());
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
			Controller.getInstancia().accion(new Context(Evento.ALQUILER, null));
			this.dispose();
		});
		bottomPanel.add(cancelButton);
	}

	private void buscarAlquileresPorCliente() {
		try {
			int clienteId = Integer.parseInt(clienteIdField.getText());
			// TODO CORREGIR ERROR "context is null"
			Controller.getInstancia().accion(new Context(Evento.BUSCAR_ALQUILER_NEGOCIO, clienteId));
			this.dispose();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El ID del alquiler debe ser un número válido.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// @SuppressWarnings("unchecked")
	@Override
	public void update(Context context) {
		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		if (evento == Evento.RES_BUSCAR_ALQUILER_OK) {
			TAlquilerTOA alquiler = (TAlquilerTOA) datos;

			actualizarTabla(alquiler);
		} else if (evento == Evento.RES_BUSCAR_ALQUILER_KO) {
			JOptionPane.showMessageDialog(this, "Error al cargar el alquiler.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void actualizarTabla(TAlquilerTOA alquiler) {
		TAlquiler tAlquiler = alquiler.get_tAlquiler();
		TCliente tCliente = alquiler.get_tCliente();
		TEmpleado tEmpleado = alquiler.get_tEmpleado();
		panelDatos.removeAll();
		panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));
		panelDatos.add(new JLabel("\nFecha: " + tAlquiler.get_fecha() + "\n"));
		panelDatos.add(new JLabel("ID Alquiler: " + tAlquiler.get_id_alquiler() + "\n"));
		panelDatos.add(new JLabel("Total Alquiler: " + tAlquiler.get_total() + "\n"));
		panelDatos.add(new JLabel("ID Cliente: " + tCliente.get_id_cliente() + " "));
		panelDatos.add(new JLabel("--------Nombre Cliente: " + tCliente.get_nombre() + "\n"));
		panelDatos.add(new JLabel("--------DNI Cliente: " + tCliente.get_dni() + "\n"));
		panelDatos.add(new JLabel("ID Empleado: " + tEmpleado.get_id_empleado() + " "));
		panelDatos.add(new JLabel("--------Nombre Empleado: " + tEmpleado.get_nombre() + "\n"));
		panelDatos.add(new JLabel("--------DNI Empleado: " + tEmpleado.get_DNI() + "\n"));
		this.revalidate();
		this.repaint();

		ArrayList<TLineaAlquiler> lineas = new ArrayList<>(alquiler.get_tLineasAlquiler());

		tableModel.setRowCount(0);
		for (TLineaAlquiler linea : lineas) {
			tableModel.addRow(new Object[] { linea.get_id_maquina(), linea.get_duracion(), linea.get_precio(),
					linea.get_devuelto() == 0 ? "NO" : "SI" });
		}

		tableModel.fireTableDataChanged();
	}
}
