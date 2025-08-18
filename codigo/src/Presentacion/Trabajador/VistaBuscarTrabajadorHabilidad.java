
package Presentacion.Trabajador;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import Negocio.Trabajador.TVinculacionTrabHab;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Dimension;

public class VistaBuscarTrabajadorHabilidad extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private List<JButton> jButton;
	private List<JTextField> jTextField;

	TVinculacionTrabHab tvinculacion;
	private ModeloTablaTrabajadorHabilidad tablavinculos;
	private JTable tabla;

	public VistaBuscarTrabajadorHabilidad() {
		super("[BUSCAR TRABAJADOR POR HABILIDAD]");
		jTextField = new ArrayList<JTextField>();
		jButton = new ArrayList<JButton>();
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(600, 300));

		JPanel jpanel = new JPanel();
		rellenearMainPanel(jpanel, "idhabilidad");
		JPanel jaceptar = new JPanel();
		rellenarAceptarPanel(jaceptar);
		mainPanel.add(jpanel);
		mainPanel.add(jaceptar);

		activarActionListenerAceptar();

		tablavinculos = new ModeloTablaTrabajadorHabilidad();
		tabla = new JTable(tablavinculos);
		JScrollPane tablesp = new JScrollPane(tabla);
		mainPanel.add(tablesp, BorderLayout.CENTER);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	private boolean validarID(String id) {
		try {
			int n = Integer.parseInt(id);
			if (n > 0)
				return true;
			else
				return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private void activarActionListenerAceptar() {

		jButton.get(0).addActionListener((e) -> {
			tvinculacion = new TVinculacionTrabHab();
			String idString = jTextField.get(0).getText();
			if (idString.length() == 0) {
				JOptionPane.showMessageDialog(null, "ID no introducido");
			} else if (!validarID(idString)) {
				JOptionPane.showMessageDialog(null, "ID no válido");
			} else {
				tvinculacion.set_id_habilidad(Integer.parseInt(jTextField.get(0).getText()));
				Controller.getInstancia()
						.accion(new Context(Evento.LISTAR_TRABAJADOR_X_HABILIDAD, Integer.parseInt(idString)));
				dispose();
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
		JLabel pLabel = new JLabel("Buscar por " + palabra + ":");
		jpanel.add(pLabel);
		JTextField pTextField = new JTextField(10);
		pTextField.setMaximumSize(new Dimension(100, 40));
		jTextField.add(pTextField);
		jpanel.add(pTextField);

	}

	@Override
	public void update(Context context) {
		if (context.getEvento() == Evento.RES_LISTAR_TRABAJADOR_X_HABILIDAD_OK) {
			@SuppressWarnings("unchecked")
			Set<TVinculacionTrabHab> vinculos = (Set<TVinculacionTrabHab>) context.getDatos();
			if (vinculos.size() == 0) {
				JOptionPane.showMessageDialog(null, "Trabajadores no encontrados en la Base de Datos.");
			} else {
				tablavinculos.setLista(vinculos);
				tablavinculos.fireTableDataChanged();
			}
		} else if (context.getEvento() == Evento.RES_LISTAR_TRABAJADOR_ALL_KO) {
			JOptionPane.showMessageDialog(null, "Error al listar los trabajadores.");
		}
		this.setVisible(true);
	}
}