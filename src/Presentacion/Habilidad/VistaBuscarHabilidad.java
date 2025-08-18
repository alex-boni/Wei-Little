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

public class VistaBuscarHabilidad extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	THabilidad tHabilidad;
	private JTextField pTextField;

	public VistaBuscarHabilidad() {
		super("[BUSCAR HABILIDAD]");
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

		mainPanel.add(jpanel);
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
				int id = Integer.parseInt(pTextField.getText());

				if (id <= 0) {
					throw new Exception("Debes introducir un número mayor que 0.");
				}

				Controller.getInstancia().accion(new Context(Evento.BUSCAR_HABILIDAD, id));
				this.dispose();

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "ERROR: Debes introducir un id numérico válido.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
			}
		});
		jaceptar.add(aceptar);

		JButton cancelar = new JButton("CANCELAR");
		cancelar.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.HABILIDAD, null));
			this.dispose();
		});
		jaceptar.add(cancelar);
	}

	private void rellenearMainPanel(JPanel jpanel, String string) {
		JLabel pLabel = new JLabel("Buscar por " + string + ":");
		jpanel.add(pLabel);
		pTextField = new JTextField(10);
		pTextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(pTextField);
	}

	@Override
	public void update(Context contexto) {

		if (contexto.getEvento() == Evento.VBUSCAR_HABILIDAD) {
			this.setVisible(true);
		} else {
			if (contexto.getEvento() == Evento.RES_BUSCAR_HABILIDAD_OK) {
				THabilidad th = (THabilidad) contexto.getDatos();
				String mhtml = "<html><body>" + "<p>Habilidad encontrada: </p>" + "<p>ID: " + th.get_id() + " </p>"
						+ "<p>NOMBRE: " + th.get_nombre() + "</p>" + "<p>NIVEL: " + th.get_nivel() + "</p>"
						+ "<p>ACTIVO: " + (th.get_activo() == 1 ? "SI" : "NO") + "</p>" + "</body></html>";
				JOptionPane.showMessageDialog(null, mhtml, "Habilidad Encontrada", JOptionPane.INFORMATION_MESSAGE);

			} else if (contexto.getEvento() == Evento.RES_BUSCAR_HABILIDAD_KO) {
				JOptionPane.showMessageDialog(null, "Habilidad no encontrada en la Base de Datos.");
			}
			Controller.getInstancia().accion(new Context(Evento.HABILIDAD, null));
			this.dispose();
		}

	}

}