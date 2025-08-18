
package Presentacion.Plataforma;

import javax.swing.JFrame;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

import Negocio.Plataforma.TPlataforma;

public class VistaAltaPlataforma extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField nombreTextField;
	private TPlataforma tplataforma;

	public VistaAltaPlataforma() {
		super("[ALTA PLATAFORMA]");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		setPreferredSize(new Dimension(600, 300));

		JPanel jpanel = new JPanel();
		rellenarMainPanel(jpanel);
		mainPanel.add(jpanel);

		JPanel jaceptar = new JPanel();
		rellenarAceptarPanel(jaceptar);
		mainPanel.add(jaceptar);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void rellenarAceptarPanel(JPanel jaceptar) {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

		JButton aceptar = new JButton("ACEPTAR");
		aceptar.addActionListener((e) -> {
			try {
				tplataforma = new TPlataforma();
				if (nombreTextField.getText().isEmpty())
					throw new Exception("Debes introducir un nombre");
				String nombre = nombreTextField.getText().toUpperCase();
				tplataforma.colocar_datos(nombre, 1);
				Context contexto = new Context(Evento.ALTA_PLATAFORMA, (Object) tplataforma);
				Controller.getInstancia().accion(contexto);
				this.dispose();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
			}
		});
		jaceptar.add(aceptar);

		JButton cancelar = new JButton("CANCELAR");
		cancelar.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.PLATAFORMA, null));
			this.dispose();
		});
		buttonPanel.add(cancelar);
		jaceptar.add(buttonPanel, BorderLayout.PAGE_END);
	}

	private void rellenarMainPanel(JPanel jpanel) {
		JLabel nombreLabel = new JLabel("INSERTAR NOMBRE:");
		jpanel.add(nombreLabel);
		nombreTextField = new JTextField(30);
		nombreTextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(nombreTextField);

	}

	public void update(Context context) {
		if (context.getEvento() == Evento.VALTA_PLATAFORMA)
			this.setVisible(true);
		else {

			if (context.getEvento() == Evento.RES_ALTA_PLATAFORMA_OK)
				JOptionPane.showMessageDialog(null, "Exito al dar de alta platarforma id " + (int) context.getDatos());
			else if (context.getEvento() == Evento.RES_ALTA_PLATAFORMA_KO)
				JOptionPane.showMessageDialog(null, "Error al dar de alta platarforma. ");
			Controller.getInstancia().accion(new Context(Evento.PLATAFORMA, null));
			this.dispose();
		}
	}
}