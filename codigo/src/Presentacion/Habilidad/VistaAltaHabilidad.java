
package Presentacion.Habilidad;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import javax.swing.JTextField;

import Negocio.Habilidad.THabilidad;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;

public class VistaAltaHabilidad extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	JTextField idTextField;
	JTextField nombreTextField;
	JTextField nivelTextField;
	JTextField activoTextField;
	THabilidad tHabilidad;

	public VistaAltaHabilidad() {
		super("[ALTA HABILIDAD]");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(600, 300));

		JPanel jPanel = new JPanel();
		rellenarMainPanel(jPanel);
		mainPanel.add(jPanel, BorderLayout.NORTH);

		JPanel jAceptar = new JPanel();
		rellenarAceptarPanel(jAceptar);
		mainPanel.add(jAceptar, BorderLayout.SOUTH);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void rellenarAceptarPanel(JPanel jAceptar) {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

		JButton aceptar = new JButton("ACEPTAR");
		aceptar.addActionListener((e) -> {
			try {
				tHabilidad = new THabilidad();
				if (nombreTextField.getText().isEmpty())
					throw new Exception("Debes introducir un nombre");
				String nombre = nombreTextField.getText().toUpperCase();
				int nivel = Integer.parseInt(nivelTextField.getText());
				if (nivel <= 0) {
					throw new Exception("El nivel debe ser un número mayor que 0.");
				}
				tHabilidad.colocar_datos(nombre, nivel, 1);
				Controller.getInstancia().accion(new Context(Evento.ALTA_HABILIDAD, (Object) tHabilidad));
				this.dispose();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "ERROR: Debes introducir un nivel numérico válido.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
			}

		});

		JButton cancelar = new JButton("CANCELAR");
		cancelar.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.HABILIDAD, null));
			this.dispose();
		});

		buttonPanel.add(aceptar);
		buttonPanel.add(cancelar);

		jAceptar.add(buttonPanel, BorderLayout.PAGE_END);
	}

	private void rellenarMainPanel(JPanel jPanel) {
		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

		JLabel nombreLabel = new JLabel("INSERTAR NOMBRE: ");
		nombreTextField = new JTextField(30);
		nombreTextField.setMaximumSize(new Dimension(100, 40));
		JPanel nombrePanel = new JPanel();
		nombrePanel.setLayout(new BoxLayout(nombrePanel, BoxLayout.X_AXIS));
		nombrePanel.add(nombreLabel);
		nombrePanel.add(nombreTextField);
		jPanel.add(nombrePanel);

		JLabel nivelLabel = new JLabel("INSERTAR NIVEL: ");
		nivelTextField = new JTextField(30);
		nivelTextField.setMaximumSize(new Dimension(100, 40));
		JPanel nivelPanel = new JPanel();
		nivelPanel.setLayout(new BoxLayout(nivelPanel, BoxLayout.X_AXIS));
		nivelPanel.add(nivelLabel);
		nivelPanel.add(nivelTextField);
		jPanel.add(nivelPanel);

	}

	public void update(Context context) {
		if (context.getEvento() == Evento.VALTA_HABILIDAD)
			this.setVisible(true);
		else {
			if (context.getEvento() == Evento.RES_ALTA_HABILIDAD_OK)
				JOptionPane.showMessageDialog(null, "Exito al dar de alta habilidad. Id: " + (int) context.getDatos());
			else if (context.getEvento() == Evento.RES_ALTA_HABILIDAD_KO)
				JOptionPane.showMessageDialog(null, "Error al dar de alta habilidad. ");
			Controller.getInstancia().accion(new Context(Evento.HABILIDAD, null));
			this.dispose();
		}
	}
}