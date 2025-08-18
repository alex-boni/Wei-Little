package Presentacion.ProductoEnPlataforma;

import Negocio.ProductoEnPlataforma.TProductoEnPlataforma;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.awt.Dimension;
import java.util.Set;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings({ "unchecked", "serial" })
public class VListarProductoEnPlataforma extends JFrame implements IGUI {

	public VListarProductoEnPlataforma() {
		super("[LISTAR PRODUCTO EN PLATAFORMA]");
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
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();

		panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));

		JLabel textoMostrarTodo = new JLabel("MOSTRAR TODOS LOS PRODUCTOS EN PLATAFORMA");
		JButton mostrar = new JButton("MOSTRAR");

		textoMostrarTodo.setPreferredSize(new Dimension(355, 20));

		mostrar.addActionListener((e) -> {
			int a = 1;
			Controller.getInstancia().accion(new Context(Evento.LISTAR_PRODUCTO_PLATAFORMA_ALL, (Object) a));
			this.dispose();
		});

		panel1.add(textoMostrarTodo);
		panel1.add(mostrar);

		JPanel panelInferior = new JPanel();
		JButton volver = new JButton("VOLVER");
		volver.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.PRODUCTO_EN_PLATAFORMA, null));
			this.dispose();
		});

		JTextField mostrarPorProducto = new JTextField();
		JLabel textoMostrarPorProducto = new JLabel("MOSTRAR POR PRODUCTO");
		JButton mostrarProducto = new JButton("MOSTRAR");

		mostrarPorProducto.setPreferredSize(new Dimension(150, 20));
		textoMostrarPorProducto.setPreferredSize(new Dimension(200, 20));

		mostrarProducto.addActionListener((e) -> {

			try {

				if (mostrarPorProducto.getText().isEmpty()) {
					throw new Exception("Id_producto no puede ser vacio");
				}

				int id = Integer.parseInt(mostrarPorProducto.getText());

				if (id <= 0) {
					throw new Exception("ID no puede ser negativo ni 0");
				}

				Controller.getInstancia().accion(new Context(Evento.LISTAR_PP_X_PRODUCTO, (Object) id));

				dispose();
			} catch (NumberFormatException ile) {
				JOptionPane.showMessageDialog(null, "ERROR: Debe introducir caracteres numericos");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
			}
		});

		panel2.add(textoMostrarPorProducto);
		panel2.add(mostrarPorProducto);
		panel2.add(mostrarProducto);

		JTextField mostrarPorPlataforma = new JTextField();
		JLabel textoMostrarPorPlataforma = new JLabel("MOSTRAR POR PLATAFORMA");
		JButton mostrarPlataforma = new JButton("MOSTRAR");

		mostrarPorPlataforma.setPreferredSize(new Dimension(150, 20));
		textoMostrarPorPlataforma.setPreferredSize(new Dimension(200, 20));

		mostrarPlataforma.addActionListener((e) -> {
			try {

				if (mostrarPorPlataforma.getText().isEmpty()) {
					throw new Exception("Id_plataforma no puede ser vacio");
				}

				int id = Integer.parseInt(mostrarPorPlataforma.getText());

				if (id < 0) {
					throw new Exception("ID no puede ser negativo");
				}

				Controller.getInstancia().accion(new Context(Evento.LISTAR_PP_X_PLATAFORMA, (Object) id));

				dispose();
			} catch (NumberFormatException ile) {
				JOptionPane.showMessageDialog(null, "ERROR: Debe introducir caracteres numericos");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
			}
		});

		panel3.add(textoMostrarPorPlataforma);
		panel3.add(mostrarPorPlataforma);
		panel3.add(mostrarPlataforma);

		panelSuperior.add(panel1);
		panelSuperior.add(new JSeparator());
		panelSuperior.add(panel2);
		panelSuperior.add(new JSeparator());
		panelSuperior.add(panel3);

		panelInferior.add(volver);

		jmostrar.add(panelSuperior);
		jmostrar.add(panelInferior);

		pack();
	}

	@Override
	public void update(Context context) {
		if (context.getEvento() == Evento.VLISTAR_TODO_PP || context.getEvento() == Evento.VLISTAR_PRODUCTOS_X_TIPO) {
			this.setVisible(true);
		} else {
			if (context.getEvento() == Evento.RES_LISTAR_PRODUCTO_PLATAFORMA_ALL_OK
					|| context.getEvento() == Evento.RES_LISTAR_PP_X_PRODUCTO_OK
					|| context.getEvento() == Evento.RES_LISTAR_PP_X_PLATAFORMA_OK) {

				Set<TProductoEnPlataforma> tps = (Set<TProductoEnPlataforma>) context.getDatos();

				if (tps.size() == 0) {
					JOptionPane.showMessageDialog(null, "Producto en Plataforma no encontrado en la Base de Datos.");
					Controller.getInstancia().accion(new Context(Evento.PRODUCTO_EN_PLATAFORMA, null));
				} else {
					new MostrarLista(tps);
				}

			} else if (context.getEvento() == Evento.RES_LISTAR_PRODUCTO_PLATAFORMA_ALL_KO
					|| context.getEvento() == Evento.RES_LISTAR_PP_X_PRODUCTO_KO
					|| context.getEvento() == Evento.RES_LISTAR_PP_X_PLATAFORMA_KO) {

				JOptionPane.showMessageDialog(null, "Error al listar todos los Productos en Plataforma.");
				Controller.getInstancia().accion(new Context(Evento.VLISTAR_TODO_PP, null));
			}

			this.dispose();
		}
	}

	class MostrarLista extends JFrame {

		private Set<TProductoEnPlataforma> tps;

		public MostrarLista(Set<TProductoEnPlataforma> tps) {
			super("Lista producto en plataforma");
			this.tps = tps;
			this.initGUI();
		}

		private void initGUI() {

			setPreferredSize(new Dimension(600, 300));

			JPanel panelPrincipal = new JPanel();
			JPanel panelInferior = new JPanel();

			ListaTableModel l = new ListaTableModel(tps);

			panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
			panelPrincipal.setPreferredSize(new Dimension(500, 250));
			panelPrincipal.add(new JScrollPane(new JTable(l)));

			JButton volver = new JButton("VOLVER");
			volver.addActionListener((e) -> {
				Controller.getInstancia().accion(new Context(Evento.VLISTAR_TODO_PP, null));
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

			private String[] headers = { "id_producto_plataforma", "id_producto", "id_plataforma", "precio", "cantidad",
					"activo" };
			private Set<TProductoEnPlataforma> tps;

			public ListaTableModel(Set<TProductoEnPlataforma> tps) {
				this.tps = tps;
			}

			@Override
			public int getRowCount() {
				return tps.size();
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
				case 0 -> ((TProductoEnPlataforma) tps.toArray()[rowIndex]).get_id();
				case 1 -> ((TProductoEnPlataforma) tps.toArray()[rowIndex]).get_id_producto();
				case 2 -> ((TProductoEnPlataforma) tps.toArray()[rowIndex]).get_id_plataforma();
				case 3 -> ((TProductoEnPlataforma) tps.toArray()[rowIndex]).get_precio();
				case 4 -> ((TProductoEnPlataforma) tps.toArray()[rowIndex]).get_cantidad();
				default -> ((TProductoEnPlataforma) tps.toArray()[rowIndex]).get_activo() == 1 ? "SI" : "NO";
				};
			}

		}
	}
}
