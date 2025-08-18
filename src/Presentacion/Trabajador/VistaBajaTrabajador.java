
package Presentacion.Trabajador;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;

public class VistaBajaTrabajador extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private List<JButton> jButton;
	private List<JTextField> jTextField;

	public VistaBajaTrabajador() {
		super("[BAJA TRABAJADOR]");
		jTextField = new ArrayList<JTextField>();
		jButton = new ArrayList<JButton>();
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();

		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(600, 300));

		JPanel jpanel = new JPanel();
		rellenearMainPanel(jpanel, "id");

		JPanel jaceptar = new JPanel();
		rellenarAceptarPanel(jaceptar);
		activarActionListenerAceptar();

		mainPanel.add(jpanel);
		mainPanel.add(jaceptar);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	private boolean validarID(String id) {
		try {
			Integer.parseInt(id);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private void activarActionListenerAceptar() {

		jButton.get(0).addActionListener((e) -> {
			String idString = jTextField.get(0).getText();
			if (idString.length() == 0) {
				JOptionPane.showMessageDialog(null, "ID no introducido");
			} else if (!validarID(idString)) {
				JOptionPane.showMessageDialog(null, "ID no válido");
			} else {

				int id = Integer.parseInt(jTextField.get(0).getText());
				Controller.getInstancia().accion(new Context(Evento.BAJA_TRABAJADOR, (Object) id));
				this.dispose();
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
		JLabel pLabel = new JLabel("Dar de Baja por " + palabra + ":");
		jpanel.add(pLabel);
		JTextField pTextField = new JTextField(10);
		jTextField.add(pTextField);
		pTextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(pTextField);

	}

	@Override
	public void update(Context context) {
		if (context.getEvento() != Evento.VBAJA_TRABAJADOR) {
			if (context.getEvento() == Evento.RES_BAJA_TRABAJADOR_OK)
				JOptionPane.showMessageDialog(null, "Exito al dar de baja trabajador id " + (int) context.getDatos());
			else if (context.getEvento() == Evento.RES_BAJA_TRABAJADOR_KO)
				JOptionPane.showMessageDialog(null, "Error al dar de baja trabajador. ");
			Controller.getInstancia().accion(new Context(Evento.TRABAJADOR, null));
			this.dispose();
		} else
			this.setVisible(true);
	}
}