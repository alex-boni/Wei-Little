
package Presentacion.Trabajador;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import Negocio.Trabajador.TTrabajador;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;

public class VistaBuscarTrabajadorTipo extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private List<JButton> jButton;
	private List<JTextField> jTextField;
	private ModeloTablaSupervisor tablasupervisor;
	private ModeloTablaVendedor tablavendedor;
	private JTable tabla;
	private JTable tabla2;
	private JPanel mainPanel;

	public VistaBuscarTrabajadorTipo() {
		super("[BUSCAR TRABAJADOR POR TIPO]");
		jTextField = new ArrayList<JTextField>();
		jButton = new ArrayList<JButton>();
		initGUI();
	}

	private void initGUI() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(500, 700));

		JPanel jpanel = new JPanel();
		rellenearMainPanel(jpanel, "tipo");
		JPanel jaceptar = new JPanel();
		rellenarAceptarPanel(jaceptar);
		mainPanel.add(jpanel);
		mainPanel.add(jaceptar);

		activarActionListenerAceptar();

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	private void activarActionListenerAceptar() {

		jButton.get(0).addActionListener((e) -> {
			String tipo = jTextField.get(0).getText().toLowerCase();
			if (tipo.length() == 0) {
				JOptionPane.showMessageDialog(null, "tipo vacio");
			} else if (tipo.equals("supervisor") || tipo.equals("vendedor")) {
				Controller.getInstancia().accion(new Context(Evento.LISTAR_TRABAJADOR_X_TIPO, tipo));
				dispose();
			} else {
				JOptionPane.showMessageDialog(null, "tipo no valido");
			}
		});

	}

	private void rellenarAceptarPanel(JPanel jaceptar) {
		JButton aceptar = new JButton("ACEPTAR");
		jButton.add(aceptar);
		jaceptar.add(aceptar);

		JButton cancelar = new JButton("CANCELAR");
		cancelar.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.TRABAJADOR, null));
			this.dispose();
		});
		jaceptar.add(cancelar);
	}

	private void rellenearMainPanel(JPanel jpanel, String palabra) {
		JPanel tablaPanel = new JPanel();
		tablaPanel.setLayout(new BoxLayout(tablaPanel, BoxLayout.Y_AXIS));

		JLabel pLabel = new JLabel("Buscar por " + palabra + ":");
		jpanel.add(pLabel);
		JTextField pTextField = new JTextField(10);
		pTextField.setMaximumSize(new Dimension(100, 40));
		jTextField.add(pTextField);
		jpanel.add(pTextField);

		tablasupervisor = new ModeloTablaSupervisor();
		tabla = new JTable(tablasupervisor);
		JScrollPane tablesp = new JScrollPane(tabla);
		tablesp.setPreferredSize(new Dimension(400, 200)); // Ajustar el tamaño preferido de la tabla
		tablaPanel.add(tablesp);

		tablavendedor = new ModeloTablaVendedor();
		tabla2 = new JTable(tablavendedor);
		JScrollPane tablesp2 = new JScrollPane(tabla2);
		tablesp2.setPreferredSize(new Dimension(400, 200)); // Ajustar el tamaño preferido de la tabla
		tablaPanel.add(tablesp2);

		jpanel.add(tablaPanel);

	}

	@Override
	public void update(Context context) {

		if (context.getEvento() == Evento.RES_LISTAR_TRABAJADOR_X_TIPO_KO)
			JOptionPane.showMessageDialog(null, "Error al listar los trabajadores por el tipo.");

		else if (context.getEvento() == Evento.RES_LISTAR_TRABAJADOR_X_TIPO_SUPERVISOR_OK) {
			@SuppressWarnings("unchecked")
			Set<TTrabajador> trabajadores = (Set<TTrabajador>) context.getDatos();
			if (trabajadores.size() == 0) {
				JOptionPane.showMessageDialog(null, "Trabajadores no encontrados en la Base de Datos.");
			} else {
				tablasupervisor.setLista(trabajadores);
				tablasupervisor.fireTableDataChanged();
			}

		} else if (context.getEvento() == Evento.RES_LISTAR_TRABAJADOR_X_TIPO_VENDEDOR_OK) {
			@SuppressWarnings("unchecked")
			Set<TTrabajador> trabajadores = (Set<TTrabajador>) context.getDatos();
			if (trabajadores.size() == 0) {
				JOptionPane.showMessageDialog(null, "Trabajadores no encontrados en la Base de Datos.");
			} else {
				tablavendedor.setLista(trabajadores);
				tablavendedor.fireTableDataChanged();
			}
		}

		setVisible(true);

	}
}