/**
 * 
 */
package Presentacion.ProveedorJPA;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Negocio.ProveedorJPA.TProveedor;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class VistaListarProveedorPorModelo extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private String[] _columnName = { "ID Proveedor", "Nombre", "CIF" };

	private DefaultTableModel _table; // TODO REVISAR IMPORT

	public VistaListarProveedorPorModelo() {
		super("[LISTAR PROVEEDOR POR MODELO]");
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
		setPreferredSize(new Dimension(600, 300));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

	}

	private void rellenarPanelSuperior(JPanel supPanel) {

		JLabel lblModelo = new JLabel("Modelo:");
		supPanel.add(lblModelo);

		JTextField textField = new JTextField();
		supPanel.add(textField);
		textField.setColumns(10);

		JButton btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (textField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "El campo de texto no puede estar vacío.");
					return;
				}
				int id = Integer.parseInt(textField.getText());

				if (id <= 0) {
					JOptionPane.showMessageDialog(null, "Id de modelo no válido. El id debe ser mayor que 0.");
					return;
				}

				Controller.getInstancia().accion(new Context(Evento.LISTAR_PROVEEDORES_MODELO_ALL_NEGOCIO, id));
				VistaListarProveedorPorModelo.this.dispose();
			}
		});
		supPanel.add(btnListar);

	}

	private void rellenarPanelCentral(JPanel midPanel) {
		_table = new DefaultTableModel() {

			private static final long serialVersionUID = 1L;

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

		if (evento == Evento.LISTAR_PROVEEDOR_MODELO_ALL_VISTA) {
			setVisible(true);
		} else {
			if (evento == Evento.RES_LISTAR_PROVEEDORES_MODELO_ALL_OK) {
				fillTableOkEvent(datos);
			} else if (evento == Evento.RES_LISTAR_PROVEEDORES_MODELO_ALL_KO) {
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