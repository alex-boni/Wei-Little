
package Presentacion.Trabajador;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Negocio.Trabajador.TTrabajador;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;

public class VistaListarTodoTrabajador extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private ModeloTablaTrabajador tablatrabajador;
	private JTable tabla;

	public VistaListarTodoTrabajador() {
		super("[LISTAR TRABAJADORES]");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(600, 300));

		JPanel jmostrar = new JPanel();
		rellenarmostrarPanel(jmostrar);
		mainPanel.add(jmostrar, BorderLayout.NORTH);

		tablatrabajador = new ModeloTablaTrabajador();
		tabla = new JTable(tablatrabajador);
		JScrollPane tablesp = new JScrollPane(tabla);
		mainPanel.add(tablesp, BorderLayout.CENTER);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	private void rellenarmostrarPanel(JPanel jmostrar) {
		JButton mostrar = new JButton("MOSTRAR");
		mostrar.addActionListener((e) -> {
			int a = 1;
			Controller.getInstancia().accion(new Context(Evento.LISTAR_TRABAJADOR_ALL, (Object) a));
			dispose();

		});
		jmostrar.add(mostrar);

		JButton volver = new JButton("VOLVER");
		volver.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.TRABAJADOR, null));
			this.dispose();
		});
		jmostrar.add(volver);
	}

	@Override
	public void update(Context context) {
		if (context.getEvento() == Evento.RES_LISTAR_TRABAJADOR_ALL_OK) {
			@SuppressWarnings("unchecked")
			Set<TTrabajador> trabajadores = (Set<TTrabajador>) context.getDatos();
			if (trabajadores.size() == 0) {
				JOptionPane.showMessageDialog(null, "Trabajadores no encontrados en la Base de Datos.");
			} else {
				tablatrabajador.setLista(trabajadores);
				tablatrabajador.fireTableDataChanged();
			}
		} else if (context.getEvento() == Evento.RES_LISTAR_TRABAJADOR_ALL_KO) {
			JOptionPane.showMessageDialog(null, "Error al listar los trabajadores.");
		}
		setVisible(true);

	}
}