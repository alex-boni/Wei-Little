
package Presentacion.Trabajador;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import javax.swing.JPanel;
import javax.swing.JTextField;

import Negocio.Trabajador.TVinculacionTrabHab;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;

public class VistaDesvincularHabilidad extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	TVinculacionTrabHab tvinculo;
	JTextField idhabilidadTextField;
	JTextField idtrabajadorTextField;

	public VistaDesvincularHabilidad() {
		super("[DESVINCULAR HABILIDAD TRABAJADOR]");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(350, 300));
		JPanel jpanel = new JPanel();
		rellenearMainPanel(jpanel);
		JPanel jaceptar = new JPanel();
		rellenarAceptarPanel(jaceptar);
		mainPanel.add(jpanel);
		mainPanel.add(jaceptar);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	private boolean validarID(String id) {
		try {
			int n = Integer.parseInt(id);
			if (n > 0)
				return true;
			else
				return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private void rellenarAceptarPanel(JPanel jaceptar) {
		JButton aceptar = new JButton("ACEPTAR");
		aceptar.addActionListener((e) -> {
			tvinculo = new TVinculacionTrabHab();
			String idString1 = idtrabajadorTextField.getText();
			String idString2 = idhabilidadTextField.getText();
			if (idString1.length() == 0 || idString2.length() == 0) {
				JOptionPane.showMessageDialog(null, "ID no introducido");
			} else if (!validarID(idString1) || !validarID(idString2)) {
				JOptionPane.showMessageDialog(null, "ID no válido");
			} else {
				tvinculo.colocar_datos(1, Integer.parseInt(idString1), Integer.parseInt(idString2));
				Controller.getInstancia()
						.accion(new Context(Evento.DESVINCULAR_HABILIDAD_TRABAJADOR, (Object) tvinculo));
				this.dispose();
			}

		});
		jaceptar.add(aceptar);

		JButton cancelar = new JButton("CANCELAR");
		cancelar.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.TRABAJADOR, null));
			this.dispose();
		});
		jaceptar.add(cancelar);
	}

	private void rellenearMainPanel(JPanel jpanel) {
		JLabel idtrabajadorLabel = new JLabel("INSERTAR ID TRABAJADOR:");
		jpanel.add(idtrabajadorLabel);
		idtrabajadorTextField = new JTextField(30);
		idtrabajadorTextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(idtrabajadorTextField);
		JLabel idhabilidadLabel = new JLabel("INSERTAR ID HABILIDAD:");
		jpanel.add(idhabilidadLabel);
		idhabilidadTextField = new JTextField(30);
		idhabilidadTextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(idhabilidadTextField);

	}

	@Override
	public void update(Context context) {
		if (context.getEvento() != Evento.VDESVINCULAR_HABILIDAD_TRABAJADOR) {
			tvinculo = (TVinculacionTrabHab) (context.getDatos());
			if (context.getEvento() == Evento.RES_DESVINCULAR_HABILIDAD_TRABAJADOR_OK)
				JOptionPane.showMessageDialog(null, "Exito al desvincular la habilidad " + tvinculo.get_id_habilidad()
						+ " del trabajador " + tvinculo.get_id_trabajador());
			else if (context.getEvento() == Evento.RES_DESVINCULAR_HABILIDAD_TRABAJADOR_KO)
				JOptionPane.showMessageDialog(null, "Error al desvincular la habilidad del trabajador. ");
			Controller.getInstancia().accion(new Context(Evento.TRABAJADOR, null));
			this.dispose();
		} else
			setVisible(true);

	}
}