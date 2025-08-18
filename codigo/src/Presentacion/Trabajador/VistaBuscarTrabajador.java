
package Presentacion.Trabajador;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Negocio.Trabajador.TSupervisor;
import Negocio.Trabajador.TTrabajador;
import Negocio.Trabajador.TVendedor;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;

public class VistaBuscarTrabajador extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	private List<JButton> jButton;

	private List<JTextField> jTextField;

	TTrabajador ttrabajador;

	public VistaBuscarTrabajador() {
		super("[BUSCAR TRABAJADOR]");
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
		mainPanel.add(jpanel);
		mainPanel.add(jaceptar);

		activarActionListenerAceptar();

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	private boolean validarID(String id) {
		try {
			int n = Integer.parseInt(id);
			if (n >= 0)
				return true;
			else
				return false;
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
				Controller.getInstancia()
						.accion(new Context(Evento.BUSCAR_TRABAJADOR, (Object) Integer.parseInt(idString)));
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
		JLabel pLabel = new JLabel("Buscar por " + palabra + ":");
		jpanel.add(pLabel);
		JTextField pTextField = new JTextField(10);
		pTextField.setMaximumSize(new Dimension(100, 40));
		jTextField.add(pTextField);
		jpanel.add(pTextField);

	}

	@Override
	public void update(Context context) {

		if (context.getEvento() != Evento.VMOSTRAR_TRABAJADOR) {

			if (context.getEvento() == Evento.RES_BUSCAR_TRABAJADOR_OK) {
				TTrabajador trabajador = (TTrabajador) context.getDatos();
				String mhtml;
				if (trabajador instanceof TSupervisor) {
					mhtml = "<html><body>" + "<p>Trabajador encontrado: </p>" + "<p>ID: " + trabajador.get_id()
							+ " </p>" + "<p>NOMBRE: " + trabajador.get_nombre() + "</p>" + "<p>DNI: "
							+ trabajador.get_dni() + "</p>" + "<p>TIPO: " + "SUPERVISOR" + "</p>" + "<p>EXPERIENCIA: "
							+ ((TSupervisor) trabajador).get_experiencia() + "</p>" + "<p>ACTIVO: "
							+ (trabajador.get_activo() == 1 ? "SI" : "NO") + "</p>" + "</body></html>";
				} else {
					mhtml = "<html><body>" + "<p>Trabajador encontrado: </p>" + "<p>ID: " + trabajador.get_id()
							+ " </p>" + "<p>NOMBRE: " + trabajador.get_nombre() + "</p>" + "<p>DNI: "
							+ trabajador.get_dni() + "</p>" + "<p>TIPO: " + "VENDEDOR" + "</p>" + "<p>IDIOMA: "
							+ ((TVendedor) trabajador).get_idioma() + "</p>" + "<p>ACTIVO: "
							+ (trabajador.get_activo() == 1 ? "SI" : "NO") + "</p>" + "</body></html>";
				}

				JOptionPane.showMessageDialog(null, mhtml, "Trabajador Encontrado", JOptionPane.INFORMATION_MESSAGE);

			} else if (context.getEvento() == Evento.RES_BUSCAR_TRABAJADOR_KO) {
				JOptionPane.showMessageDialog(null, "Trabajador no encontrado en la Base de Datos.");
			}
			Controller.getInstancia().accion(new Context(Evento.TRABAJADOR, null));
			this.dispose();
		} else
			setVisible(true);

	}

}