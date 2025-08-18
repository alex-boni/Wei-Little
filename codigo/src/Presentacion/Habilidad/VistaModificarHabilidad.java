/**
 * 
 */
package Presentacion.Habilidad;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import Negocio.Habilidad.THabilidad;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Dimension;

public class VistaModificarHabilidad extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	JTextField pTextField;
	JTextField p2TextField;
	JTextField p3TextField;
	JTextField p4TextField;
	THabilidad tHabilidad;

	public VistaModificarHabilidad() {
		super("[MODIFICAR HABILIDAD]");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(1000, 300));

		JPanel jpanel = new JPanel();
		rellenearMainPanel(jpanel, "id");
		JPanel jpanel1 = new JPanel();
		jpanel1.setPreferredSize(new Dimension(1000, 25));
		JPanel jaceptar = new JPanel();
		rellenarAceptarPanel(jaceptar);
		mainPanel.add(jpanel);
		mainPanel.add(jaceptar);
		mainPanel.add(jpanel1);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void rellenarAceptarPanel(JPanel jaceptar) {
		JButton aceptar = new JButton("ACEPTAR");
		aceptar.addActionListener((e) -> {
			try {
				tHabilidad = new THabilidad();
				int id = Integer.parseInt(pTextField.getText());
				if (id <= 0) {
					throw new Exception("Debes introducir un id númerico mayor que 0.");
				}
				tHabilidad.set_id(id);

				if (p2TextField.getText().isEmpty())
					throw new Exception("Debes introducir un nombre");

				int lvl = Integer.parseInt(p4TextField.getText());
				if (lvl <= 0) {
					throw new Exception("Debes introducir un nivel numérico mayor que 0.");
				}
				tHabilidad.set_nivel(lvl);

				tHabilidad.set_nombre(p2TextField.getText().toUpperCase());
				int activo = Integer.parseInt(p3TextField.getText());

				if (activo != 0 && activo != 1) {
					throw new Exception("Debes introducir activo 1 o 0");
				}

				tHabilidad.set_activo(activo);

				Controller.getInstancia().accion(new Context(Evento.MODIFICAR_HABILIDAD, (Object) tHabilidad));
				this.dispose();

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null,
						"ERROR: Debes introducir un id numérico válido o el activo númerico.");
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
		JLabel pLabel = new JLabel("Modificar por " + string + ":");
		jpanel.add(pLabel);
		pTextField = new JTextField(10);
		pTextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(pTextField);
		JLabel p2Label = new JLabel("Insertar nombre nuevo :");
		jpanel.add(p2Label);
		p2TextField = new JTextField(10);
		p2TextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(p2TextField);
		JLabel p4Label = new JLabel("Insertar nuevo nivel :");
		jpanel.add(p4Label);
		p4TextField = new JTextField(10);
		p4TextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(p4TextField);
		JLabel p3Label = new JLabel("Activo/Deshabilitado (1/0):");
		jpanel.add(p3Label);
		p3TextField = new JTextField(10);
		p3TextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(p3TextField);
	}

	@Override
	public void update(Context contexto) {

		if (contexto.getEvento() == Evento.VMODIFICAR_HABILIDAD) {
			this.setVisible(true);
		} else {
			if (contexto.getEvento() == Evento.RES_MODIFICAR_HABILIDAD_OK) {
				JOptionPane.showMessageDialog(null, "Exito al modificar ");
			} else if (contexto.getEvento() == Evento.RES_MODIFICAR_HABILIDAD_KO)
				JOptionPane.showMessageDialog(null, "Error inesperado al modificar.");
			else if (contexto.getEvento() == Evento.RES_MODIFICAR_HABILIDAD_KONOID)
				JOptionPane.showMessageDialog(null, "Error: el id introducido no existe.");
			else if (contexto.getEvento() == Evento.RES_MODIFICAR_HABILIDAD_KOSAMENAME)
				JOptionPane.showMessageDialog(null, "Error: ya existe una habilidad con el mismo nombre.");
			Controller.getInstancia().accion(new Context(Evento.HABILIDAD, null));
			this.dispose();

		}

	}
}