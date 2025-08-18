/**
 * 
 */
package Presentacion.ModeloJPA;

import javax.swing.JFrame;
import java.util.Set;
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

public class VistaListarModelos extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaListarModelos() {
		super("Listar Modelos");
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

		JLabel textoMostrarTodo = new JLabel("MOSTRAR TODOS LOS MODELOS");
		JButton mostrar = new JButton("MOSTRAR");

		textoMostrarTodo.setPreferredSize(new Dimension(355, 20));

		mostrar.addActionListener((e) -> {
			int a = 1;
			Controller.getInstancia().accion(new Context(Evento.LISTAR_MODELO_ALL_NEGOCIO, (Object) a));
			this.dispose();
		});

		panel1.add(textoMostrarTodo);
		panel1.add(mostrar);

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

		if (context.getEvento() == Evento.LISTAR_MODELO_ALL_VISTA) {
			this.setVisible(true);
		} else {
			if (context.getEvento() == Evento.RES_LISTAR_MODELO_ALL_OK) {

				@SuppressWarnings("unchecked")
				Set<TModelo> tms = (Set<TModelo>) context.getDatos();

				if (tms.size() == 0) {
					JOptionPane.showMessageDialog(null, "Modelo no encontrados en la Base de Datos.");
					Controller.getInstancia().accion(new Context(Evento.MODELO, null));
				} else {
					new MostrarLista(tms);
				}

			} else if (context.getEvento() == Evento.RES_LISTAR_MODELO_ALL_KO) {

				JOptionPane.showMessageDialog(null, "Error al listar todos los modelos.");
				Controller.getInstancia().accion(new Context(Evento.LISTAR_MODELO_ALL_VISTA, null));
			}

			this.dispose();
		}

	}

	class MostrarLista extends JFrame {

		private static final long serialVersionUID = 1L;

		private Set<TModelo> tms;

		public MostrarLista(Set<TModelo> tms) {
			super("Lista modelos");
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
				Controller.getInstancia().accion(new Context(Evento.LISTAR_MODELO_ALL_VISTA, null));
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
				default -> ((TModelo) tms.toArray()[rowIndex]).get_activo() == 1 ? "SI" : "NO";
				};
			}

		}
	}

}