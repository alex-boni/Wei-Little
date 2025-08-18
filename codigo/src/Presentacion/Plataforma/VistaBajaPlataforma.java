
package Presentacion.Plataforma;

import javax.swing.JFrame;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import javax.swing.JTextField;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;

public class VistaBajaPlataforma extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField pTextField;

	public VistaBajaPlataforma() {
		super("[BAJA PLATAFORMA]");
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
				Context contexto = new Context(Evento.BAJA_PLATAFORMA, id_plataforma);
				Controller.getInstancia().accion(contexto);
				this.dispose();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "ERROR: Debes introducir un id numerico mayor a cero");
			}
		});
		jaceptar.add(aceptar);

		JButton cancelar = new JButton("CANCELAR");
		cancelar.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.PLATAFORMA, null));
			this.dispose();
		});
		jaceptar.add(cancelar);
	}

	private void rellenarMainPanel(JPanel jpanel, String palabra) {
		JLabel pLabel = new JLabel("Dar de Baja por " + palabra + ":");
		jpanel.add(pLabel);
		pTextField = new JTextField(10);
		jpanel.add(pTextField);

	}

	@Override
	public void update(Context context) {
		if (context.getEvento() == Evento.VBAJA_PLATAFORMA)
			this.setVisible(true);
		else {
			if (context.getEvento() == Evento.RES_BAJA_PLATAFORMA_OK)
				JOptionPane.showMessageDialog(null, "Exito al dar de baja ");
			else if (context.getEvento() == Evento.RES_BAJA_PLATAFORMA_KO)
				JOptionPane.showMessageDialog(null, "Error al dar de baja platarforma. ");
			Controller.getInstancia().accion(new Context(Evento.PLATAFORMA, null));
			this.dispose();
		}
	}

}