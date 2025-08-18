/**
 * 
 */
package Presentacion.ModeloJPA;

import javax.swing.JFrame;
import java.util.Set;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import Negocio.ModeloJPA.TModelo;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

public class VistaListarModelosPorProveedor extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaListarModelosPorProveedor() {
		super("Listar Modelo X Proveedor");
		initGUI();

	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		JPanel jmostrar = new JPanel();

		setContentPane(mainPanel);
		setPreferredSize(new Dimension(600, 300));

		jmostrar.setLayout(new BoxLayout(jmostrar, BoxLayout.Y_AXIS));
		rellenarmostrarPanel(jmostrar);
		mainPanel.add(jmostrar);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);

	}

	private void rellenarmostrarPanel(JPanel jmostrar) {
		JPanel panelSuperior = new JPanel();
		JPanel panel1 = new JPanel();

		panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));

		JTextField mostrarPorProveedor = new JTextField();
		JLabel textoMostrarPorProveedor = new JLabel("MOSTRAR POR PROVEEDOR");
		JButton mostrarModelo = new JButton("MOSTRAR");

		mostrarPorProveedor.setPreferredSize(new Dimension(150, 20));
		textoMostrarPorProveedor.setPreferredSize(new Dimension(200, 20));

		mostrarModelo.addActionListener((e) -> {

			try {

				if (mostrarPorProveedor.getText().isEmpty()) {
					throw new Exception("Id_proveedor no puede ser vacio");
				}

				int id = Integer.parseInt(mostrarPorProveedor.getText());

				if (id <= 0) {
					throw new Exception("ID no puede ser negativo ni 0");
				}

				Controller.getInstancia().accion(new Context(Evento.LISTAR_MODELO_X_PROVEEDOR_NEGOCIO, (Object) id));

				dispose();
			} catch (NumberFormatException ile) {
				JOptionPane.showMessageDialog(null, "ERROR: Debe introducir caracteres numericos");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
			}
		});

		panel1.add(textoMostrarPorProveedor);
		panel1.add(mostrarPorProveedor);
		panel1.add(mostrarModelo);

		JPanel panelInferior = new JPanel();
		JButton volver = new JButton("VOLVER");
		volver.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.MODELO, null));
			this.dispose();
		});

		panelSuperior.add(panel1);
		panelSuperior.add(new JSeparator());

		panelInferior.add(volver);

		jmostrar.add(panelSuperior);
		jmostrar.add(panelInferior);

		pack();

	}

	@Override
	public void update(Context context) {
		if (context.getEvento() == Evento.LISTAR_MODELO_X_PROVEEDOR_VISTA) {
			this.setVisible(true);
		} else {
			if (context.getEvento() == Evento.RES_LISTAR_MODELO_X_PROVEEDOR_OK
					|| context.getEvento() == Evento.RES_LISTAR_MODELO_OK_VACIO) {

				@SuppressWarnings("unchecked")
				Set<TModelo> tms = (Set<TModelo>) context.getDatos();

				if (tms.size() == 0) {
					JOptionPane.showMessageDialog(null, "Modelos no encontrados en la Base de Datos.");
					Controller.getInstancia().accion(new Context(Evento.MODELO, null));
				} else {
					new MostrarLista(tms);
				}
			} else if (context.getEvento() == Evento.RES_LISTAR_MODELO_X_PROVEEDOR_KO) {

				JOptionPane.showMessageDialog(null, "Error al listar todos los modelos.");
				Controller.getInstancia().accion(new Context(Evento.LISTAR_MODELO_X_PROVEEDOR_VISTA, null));
			}

			this.dispose();
		}
	}

	class MostrarLista extends JFrame {

		private static final long serialVersionUID = 1L;

		private Set<TModelo> tms;

		public MostrarLista(Set<TModelo> tms) {
			super("Lista modelos por proveedor");
			this.tms = tms;
			this.initGUI();
		}

		private void initGUI() {

			setPreferredSize(new Dimension(600, 300));

			JPanel panelPrincipal = new JPanel();
			JPanel panelInferior = new JPanel();

			ListaTableModel l = new ListaTableModel(tms);

			panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
			panelPrincipal.setPreferredSize(new Dimension(500, 250));
			panelPrincipal.add(new JScrollPane(new JTable(l)));

			JButton volver = new JButton("VOLVER");
			volver.addActionListener((e) -> {
				Controller.getInstancia().accion(new Context(Evento.LISTAR_MODELO_X_PROVEEDOR_VISTA, null));
				this.dispose();
			});

			panelInferior.add(volver);
			panelPrincipal.add(panelInferior);

			setContentPane(panelPrincipal);

			pack();
			setLocationRelativeTo(null);
			setVisible(true);
		}

		class ListaTableModel extends AbstractTableModel {

			private static final long serialVersionUID = 1L;

			private String[] headers = { "id_modelo", "nombre", "activo" };
			private Set<TModelo> tms;

			public ListaTableModel(Set<TModelo> tms) {
				this.tms = tms;
			}

			@Override
			public int getRowCount() {
				return tms.size();
			}

			@Override
			public int getColumnCount() {
				return headers.length;
			}

			@Override
			public String getColumnName(int col) {
				return headers[col];
			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {

				return switch (columnIndex) {
				case 0 -> ((TModelo) tms.toArray()[rowIndex]).get_id();
				case 1 -> ((TModelo) tms.toArray()[rowIndex]).get_nombre();
				case 2 -> ((TModelo) tms.toArray()[rowIndex]).get_activo();
				default -> null;
				};
			}

		}
	}

}