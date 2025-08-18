
package Presentacion.Plataforma;

import javax.swing.JFrame;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import javax.swing.JButton;
import javax.swing.JTextField;

import Negocio.Plataforma.TPlataforma;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Dimension;

public class VistaBuscarPlataforma extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField pTextField;

	public VistaBuscarPlataforma() {
		super("[BUSCAR PLATAFORMA]");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(600, 300));

		JPanel jpanel = new JPanel();
		rellenarMainPanel(jpanel, "id");
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
				int id_plataforma = Integer.parseInt(pTextField.getText());

				if (id_plataforma <= 0)
					throw new Exception();
				Context contexto = new Context(Evento.MOSTRAR_PLATAFORMA, id_plataforma);
				Controller.getInstancia().accion(contexto);
				this.dispose();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "ERROR: Debes introducir un id numerico mayor a cero");
			}
		});
		;
		jaceptar.add(aceptar);

		JButton cancelar = new JButton("CANCELAR");
		cancelar.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.PLATAFORMA, null));
			this.dispose();
		});
		jaceptar.add(cancelar);
	}

	private void rellenarMainPanel(JPanel jpanel, String palabra) {
		JLabel pLabel = new JLabel("Buscar por " + palabra + ":");
		jpanel.add(pLabel);
		pTextField = new JTextField(10);
		pTextField.setMaximumSize(new Dimension(100, 40));
		;
		jpanel.add(pTextField);

	}

	@Override
	public void update(Context context) {
		if (context.getEvento() == Evento.VMOSTRAR_PLATAFORMA)
			this.setVisible(true);
		else {
			if (context.getEvento() == Evento.RES_MOSTRAR_PLATAFORMA_OK) {
				TPlataforma tp = (TPlataforma) context.getDatos();
				String mhtml = "<html><body>" + "<p>Plataforma encontrada: </p>" + "<p>ID: " + tp.get_id() + " </p>"
						+ "<p>NOMBRE: " + tp.get_nombre() + "</p>" + "<p>ACTIVO: "
						+ (tp.get_activo() == 1 ? "SI" : "NO") + "</p>" + "</body></html>";
				JOptionPane.showMessageDialog(null, mhtml, "Plataforma Encontrada", JOptionPane.INFORMATION_MESSAGE);

			} else if (context.getEvento() == Evento.RES_MOSTRAR_PLATAFORMA_KO) {
				JOptionPane.showMessageDialog(null, "Plataforma no encontrada en la Base de Datos.");
			}
			Controller.getInstancia().accion(new Context(Evento.PLATAFORMA, null));
			this.dispose();
		}
	}

}