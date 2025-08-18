package Presentacion.Venta;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import java.util.Set;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Negocio.Venta.TLineaVenta;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class VListarPorProductoEnPlataforma extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private String[] _columnName = { "id venta", "cantidad", "precio total", "activo" };

	private DefaultTableModel _table;

	public VListarPorProductoEnPlataforma() {

		super("[LISTARPP]");
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

		JLabel idLabel = new JLabel("id producto: ");
		JTextField searchText = new JTextField();
		searchText.setVisible(true);
		searchText.setPreferredSize(new Dimension(100, 30));
		supPanel.add(idLabel);
		supPanel.add(searchText);

		JButton ok_button = new JButton("OK");
		ok_button.setVisible(true);
		ok_button.addActionListener((e) -> {
			try {
				int id = Integer.valueOf(searchText.getText());
				if (id <= 0)
					JOptionPane.showMessageDialog(null, "Error: Id no puede ser negativo o cero");
				else {
					Controller.getInstancia()
							.accion(new Context(Evento.LISTAR_VENTAS_X_PRODUCTO_EN_PLATAFORMA_NEGOCIO, id));
					this.dispose();
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Error: No puede estar el campo vacio o con letras");
			}
		});
		supPanel.add(ok_button);
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

	private void rellenarPanelInferior(JPanel bottomPanel) {

		JButton cancelButton = new JButton("CANCEL");
		cancelButton.setVisible(true);
		cancelButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.LISTAR_VENTAS_ALL_VISTA, null));
			this.dispose();
		});
		bottomPanel.add(cancelButton);
	}

	public void update(Context context) {

		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		if (evento == Evento.LISTAR_VENTAS_X_PRODUCTO_EN_PLATAFORMA_VISTA) {
			setVisible(true);
		}

		else {
			updateVenta(evento, datos);
		}
	}

	private void updateVenta(Evento evento, Object datos) {

		if (evento == Evento.RES_LISTAR_VENTAS_X_PRODUCTO_EN_PLATAFORMA_OK) {
			fillTableOkEvent(datos);
		}

		else if (evento == Evento.RES_LISTAR_VENTAS_X_PRODUCTO_EN_PLATAFORMA_KO)
			mensaje("No existe el producto o no existe venta con ese producto");
	}

	@SuppressWarnings("unchecked")
	private void fillTableOkEvent(Object datos) {

		Set<TLineaVenta> _list = (Set<TLineaVenta>) datos;

		if (_list.size() == 0) {
			mensaje("No hay ventas con ese producto en plataforma activa");
		}

		this._table.setRowCount(0);

		for (TLineaVenta tLineaVenta : _list) {
			Object[] row = { tLineaVenta.get_factura(), tLineaVenta.get_cantidad(), tLineaVenta.get_precio_cantidad(),
					tLineaVenta.get_activo() };
			this._table.addRow(row);
		}

		this._table.fireTableDataChanged();
	}

	private void mensaje(String texto) {
		JOptionPane.showMessageDialog(null, texto);
	}
}