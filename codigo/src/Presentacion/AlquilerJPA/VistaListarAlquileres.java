/**
 * 
 */
package Presentacion.AlquilerJPA;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import Negocio.AlquilerJPA.TAlquiler;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Set;

import javax.swing.JOptionPane;

public class VistaListarAlquileres extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private String[] _columnName = { "ID Alquiler", "ID Cliente", "ID Empleado", "Fecha", "Total", "Activo" };

	private DefaultTableModel _table; // TODO REVISAR IMPORT

	public VistaListarAlquileres() {
		super("[LISTA DE ALQUILERES]");
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

		JButton listarXClienteButton = new JButton("LISTAR POR CLIENTE");
		listarXClienteButton.setVisible(true);
		listarXClienteButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.LISTAR_ALQUILERES_X_CLIENTE_VISTA, null));
			this.dispose();
		});
		supPanel.add(listarXClienteButton);

		JButton mostrarButton = new JButton("MOSTRAR TODOS");
		mostrarButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.LISTAR_ALQUILERES_ALL_NEGOCIO, null));
			this.dispose();
		});
		supPanel.add(mostrarButton);

		JButton listarXMaquinaButton = new JButton("LISTAR POR EMPLEADO");
		listarXMaquinaButton.setVisible(true);
		listarXMaquinaButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.LISTAR_ALQUILERES_X_EMPLEADO_VISTA, null));
			this.dispose();
		});
		supPanel.add(listarXMaquinaButton);
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

		JButton cancelButton = new JButton("CANCELAR");
		cancelButton.setVisible(true);
		cancelButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.ALQUILER, null));
			this.dispose();
		});
		bottomPanel.add(cancelButton);
	}

	public void update(Context context) {

		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		if (evento == Evento.LISTAR_ALQUILERES_ALL_VISTA) {
			setVisible(true);
		} else {
			updateAlquiler(evento, datos);
		}
	}

	private void updateAlquiler(Evento evento, Object datos) {

		if (evento == Evento.RES_LISTAR_ALQUILERES_ALL_OK) {
			fillTableOkEvent(datos);
		} else if (evento == Evento.RES_LISTAR_ALQUILERES_ALL_KO) {
			mensaje("Error en la carga de la lista de alquileres");
		}
	}

	private void mensaje(String texto) {
		JOptionPane.showMessageDialog(null, texto);
	}

	@SuppressWarnings("unchecked")
	private void fillTableOkEvent(Object datos) {

		Set<TAlquiler> _list = (Set<TAlquiler>) datos;

		if (_list.size() == 0) {
			mensaje("No hay alquileres activos");
		}

		this._table.setRowCount(0);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		for (TAlquiler tAlquiler : _list) {
			Object[] row = { tAlquiler.get_id_alquiler(), tAlquiler.get_id_cliente(), tAlquiler.get_id_empleado(),
					dateFormat.format(tAlquiler.get_fecha()), tAlquiler.get_total(), tAlquiler.get_activo() };
			this._table.addRow(row);
		}
		this._table.fireTableDataChanged();
	}
}
