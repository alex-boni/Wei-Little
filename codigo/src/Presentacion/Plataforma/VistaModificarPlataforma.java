
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

public class VistaModificarPlataforma extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	JTextField pTextField;
	JTextField p2TextField;
	JTextField p3TextField;
	TPlataforma tplataforma;

	public VistaModificarPlataforma() {
		super("[MODIFICAR PLATAFORMA]");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(800, 300));

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
				tplataforma = new TPlataforma();
				int id = Integer.parseInt(pTextField.getText());

				if (id <= 0) {
					throw new Exception("Debes introducir un id numerico mayor a cero");
				}
				tplataforma.set_id(id);
				if (p2TextField.getText().isEmpty())
					throw new Exception("Debes introducir un nombre");
				tplataforma.set_nombre(p2TextField.getText().toUpperCase());

				String s;
				if ((s = p3TextField.getText()).isEmpty() || (!s.equalsIgnoreCase("SI") && !s.equalsIgnoreCase("NO")))
					throw new Exception("Debes introducir activo SI o NO");
				s.toUpperCase();
				if (s.equalsIgnoreCase("NO"))
					tplataforma.set_activo(0);
				else
					tplataforma.set_activo(1);
				Context contexto = new Context(Evento.MODIFICAR_PLATAFORMA, (Object) tplataforma);
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
		jaceptar.add(cancelar);
	}

	private void rellenarMainPanel(JPanel jpanel, String palabra) {
		JLabel pLabel = new JLabel("Modificar por " + palabra + ":");
		jpanel.add(pLabel);
		pTextField = new JTextField(10);
		pTextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(pTextField);
		JLabel p2Label = new JLabel("Insertar nombre nuevo :");
		jpanel.add(p2Label);
		p2TextField = new JTextField(10);
		p2TextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(p2TextField);
		JLabel p3Label = new JLabel("Activo/Deshabilitado (SI/NO):");
		jpanel.add(p3Label);
		p3TextField = new JTextField("SI", 10);
		p3TextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(p3TextField);

	}

	public void update(Context context) {
		if (context.getEvento() == Evento.VMODIFICAR_PLATAFORMA)
			this.setVisible(true);
		else {
			if (context.getEvento() == Evento.RES_MODIFICAR_PLATAFORMA_OK) {
				JOptionPane.showMessageDialog(null, "Exito al modificar ");

			} else if (context.getEvento() == Evento.RES_MODIFICAR_PLATAFORMA_KO)
				JOptionPane.showMessageDialog(null, "Error Inesperado al modificar");
			else if (context.getEvento() == Evento.RES_MODIFICAR_PLATAFORMA_KO_NOID)
				JOptionPane.showMessageDialog(null, "Error: El id introducido no existe");
			else if (context.getEvento() == Evento.RES_MODIFICAR_PLATAFORMA_KO_SAMENAME)
				JOptionPane.showMessageDialog(null, "Error: Ya existe una plataforma con el mismo nombre ");
			Controller.getInstancia().accion(new Context(Evento.PLATAFORMA, null));
			this.dispose();
		}
	}
}