
package Presentacion.Producto;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JTextField;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

import java.awt.Dimension;

public class VistaBajaProducto extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private List<JButton> jButton;

	private List<JTextField> jTextField;


	public VistaBajaProducto() {
		super("[BAJA PRODUCTO]");
		this.jTextField = new ArrayList<JTextField>();
		this.jButton = new ArrayList<JButton>();
		this.initGUI();

	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(600, 300));

		JPanel jpanel = new JPanel();
		rellenearMainPanel(jpanel, "id");
		JPanel jpanel1 = new JPanel();
		jpanel1.setPreferredSize(new Dimension(600, 25));
		JPanel jpanel2 = new JPanel();
		rellenearMainPanel(jpanel2, "nombre");
		JPanel jaceptar = new JPanel();
		rellenarAceptarPanel(jaceptar);
		JPanel jaceptar2 = new JPanel();
		rellenarAceptarPanel(jaceptar2);
		activarActionListenerAceptar();

		mainPanel.add(jpanel);
		mainPanel.add(jaceptar);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(false);
	}

	private void activarActionListenerAceptar() {

		jButton.get(0).addActionListener((e) -> {
			try {
				int id = Integer.parseInt(jTextField.get(0).getText());
				if (id > 0) {
					Controller.getInstancia().accion(new Context(Evento.BAJA_PRODUCTO, id));
					this.dispose();
				} else
					JOptionPane.showMessageDialog(this, "Por favor, introduzca un id correcto", "Advertencia",
							JOptionPane.WARNING_MESSAGE);
			} catch (NumberFormatException n) {
				JOptionPane.showMessageDialog(this, "Por favor, introduzca un id correcto", "Advertencia",
						JOptionPane.WARNING_MESSAGE);
			}

		});

	}

	private void rellenarAceptarPanel(JPanel jaceptar) {
		JButton aceptar = new JButton("ACEPTAR");
		jButton.add(aceptar);
		jaceptar.add(aceptar);
		JButton cancelar = new JButton("CANCELAR");
		cancelar.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.PRODUCTO, null));
			this.dispose();
		});
		jaceptar.add(cancelar);
	}

	private void rellenearMainPanel(JPanel jpanel, String palabra) {
		JLabel pLabel = new JLabel("Dar de Baja por " + palabra + ":");
		jpanel.add(pLabel);
		JTextField pTextField = new JTextField(10);
		jTextField.add(pTextField);
		pTextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(pTextField);

	}

	public void update(Context contexto) {

		if (contexto.getEvento() == Evento.VBAJA_PRODUCTO) {
			setVisible(true);
		} else {
			if (contexto.getEvento() == Evento.RES_BAJA_PRODUCTO_OK) {
				int id = (int) contexto.getDatos();
				JOptionPane.showMessageDialog(null, "Exito al dar de baja producto id " + id);
			} else if (contexto.getEvento() == Evento.RES_BAJA_PRODUCTO_KO)
				JOptionPane.showMessageDialog(null, "Error al dar de baja producto. ");
			Controller.getInstancia().accion(new Context(Evento.PRODUCTO, null));
			this.dispose();
		}

	}

}