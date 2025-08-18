/**
 * 
 */
package Presentacion.MaquinaJPA;

import javax.swing.JFrame;

import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import java.awt.Dimension;
import java.util.Set;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import Negocio.MaquinaJPA.TArcade;
import Negocio.MaquinaJPA.TMaquina;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;

public class VistaListarTodasMaquinas extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaListarTodasMaquinas() {
		super("[LISTAR MAQUINAS]");
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

		JLabel textoMostrarTodo = new JLabel("MOSTRAR TODAS LAS MAQUINAS");
		JButton mostrar = new JButton("MOSTRAR");

		textoMostrarTodo.setPreferredSize(new Dimension(355, 20));

		mostrar.addActionListener((e) -> {
			int a = 1;
			Controller.getInstancia().accion(new Context(Evento.LISTAR_MAQUINAS_ALL_NEGOCIO, (Object) a));
			this.dispose();
		});

		panel1.add(textoMostrarTodo);
		panel1.add(mostrar);

		JPanel panelInferior = new JPanel();
		JButton volver = new JButton("VOLVER");
		volver.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.MAQUINA, null));
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
		if (context.getEvento() == Evento.LISTAR_MAQUINAS_ALL_VISTA) {
			this.setVisible(true);
		} else {
			if (context.getEvento() == Evento.RES_LISTAR_MAQUINAS_ALL_OK) {

				@SuppressWarnings("unchecked")
				Set<TMaquina> tms = (Set<TMaquina>) context.getDatos();

				if (tms.size() == 0) {
					JOptionPane.showMessageDialog(null, "Maquinas no encontradas en la Base de Datos.");
					Controller.getInstancia().accion(new Context(Evento.MAQUINA, null));
				} else {
					new MostrarLista(tms);
				}

			} else if (context.getEvento() == Evento.RES_LISTAR_MAQUINAS_ALL_KO) {

				JOptionPane.showMessageDialog(null, "Error al listar todas las máquinas.");
				Controller.getInstancia().accion(new Context(Evento.LISTAR_MAQUINAS_ALL_VISTA, null));
			}

			this.dispose();
		}
	}

	class MostrarLista extends JFrame {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Set<TMaquina> tms;

		public MostrarLista(Set<TMaquina> tms) {
			super("Lista Maquinas");
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
				Controller.getInstancia().accion(new Context(Evento.LISTAR_MAQUINAS_ALL_VISTA, null));
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

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private String[] headers = { "id_maquina", "id_modelo", "nombre", "numero de serie", "precio/hora actual",
					"alquilada", "tipo", "activo" };
			private Set<TMaquina> tms;

			public ListaTableModel(Set<TMaquina> tms) {
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
				case 0 -> ((TMaquina) tms.toArray()[rowIndex]).get_id();
				case 1 -> ((TMaquina) tms.toArray()[rowIndex]).get_id_modelo();
				case 2 -> ((TMaquina) tms.toArray()[rowIndex]).get_nombre();
				case 3 -> ((TMaquina) tms.toArray()[rowIndex]).get_num_serie();
				case 4 -> ((TMaquina) tms.toArray()[rowIndex]).get_precio_hora_actual();
				case 5 -> ((TMaquina) tms.toArray()[rowIndex]).get_alquilado() == 1 ? "SI" : "NO";
				case 6 -> ((TMaquina) tms.toArray()[rowIndex]) instanceof TArcade ? "Arcade" : "Recreativa";
				default -> ((TMaquina) tms.toArray()[rowIndex]).get_activo() == 1 ? "SI" : "NO";
				};
			}
		}
	}
}
