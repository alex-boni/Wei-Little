
package Presentacion.Habilidad;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;

public class VistaBajaHabilidad extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private List<JTextField> jTextField;
	private JTextField pTextField;

	public VistaBajaHabilidad() {
		super("[BAJA HABILIDAD]");
		this.jTextField = new ArrayList<JTextField>();
		new ArrayList<JButton>();
		initGUI();

	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(600, 300));

		JPanel jPanel = new JPanel();
		rellenearMainPanel(jPanel, "id");

		JPanel jaceptar = new JPanel();
		rellenarAceptarPanel(jaceptar);

		mainPanel.add(jPanel);
		mainPanel.add(jaceptar);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

	}

	private void rellenarAceptarPanel(JPanel jaceptar) {
		JButton aceptar = new JButton("ACEPTAR");
		aceptar.addActionListener((e) -> {
			try {
				int id = Integer.parseInt(this.jTextField.get(0).getText());
				if (id <= 0) {
					throw new Exception("Debes introducir un id númerico mayor que 0.");
				}
				Controller.getInstancia().accion(new Context(Evento.BAJA_HABILIDAD, id));
				this.dispose();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "ERROR: Debes introducir un id numérico válido.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
			}
		});
		jaceptar.add(aceptar);

		// cancelar button
		JButton cancelar = new JButton("CANCELAR");
		cancelar.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.HABILIDAD, null));
			this.dispose();
		});
		jaceptar.add(cancelar);
	}

	private void rellenearMainPanel(JPanel jpanel, String string) {
		JLabel pLabel = new JLabel("Dar de Baja por " + string + ":");
		jpanel.add(pLabel);
		pTextField = new JTextField(10);
		jTextField.add(pTextField);
		pTextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(pTextField);
	}

	@Override
	public void update(Context c) {
		if (c.getEvento() == Evento.VBAJA_HABILIDAD)
			this.setVisible(true);
		else {
			if (c.getEvento() == Evento.RES_BAJA_HABILIDAD_OK)
				JOptionPane.showMessageDialog(null, "Exito al dar de baja habilidad id " + ((int) c.getDatos()));
			else if (c.getEvento() == Evento.RES_BAJA_HABILIDAD_KO)
				JOptionPane.showMessageDialog(null, "Error al dar de baja habilidad. ");
			Controller.getInstancia().accion(new Context(Evento.HABILIDAD, null));
			this.dispose();
		}

	}

}