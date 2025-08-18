/**
 * 
 */
package Presentacion.ModeloJPA;

import javax.swing.JFrame;

import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import javax.swing.JTextField;

import Negocio.ModeloJPA.TModelo;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;

public class VistaAltaModelo extends JFrame implements IGUI {

	private JTextField textfieldnombre;
	private TModelo tmodelo;

	private static final long serialVersionUID = 1L;

	public VistaAltaModelo() {
		super("Alta Modelo");
		initGUI();

	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		JPanel jaceptar = new JPanel();
		JPanel jpanel = new JPanel();

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		this.setContentPane(mainPanel);

		this.setPreferredSize(new Dimension(600, 300));

		rellenearMainPanel(jpanel);
		rellenarAceptarPanel(jaceptar);

		mainPanel.add(jpanel);
		mainPanel.add(jaceptar);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);

	}

	private void rellenarAceptarPanel(JPanel jaceptar) {
		JButton aceptar = new JButton("ACEPTAR");

		aceptar.addActionListener((e) -> {

			String nombre;

			try {

				if (textfieldnombre.getText().isEmpty()) {
					throw new Exception("Cantidad no puede estar vacia");
				}

				nombre = String.valueOf(textfieldnombre.getText());
				this.tmodelo = new TModelo();
				tmodelo.set_nombre(nombre);

				Controller.getInstancia().accion(new Context(Evento.ALTA_MODELO_NEGOCIO, (Object) tmodelo));

				dispose();

			} catch (NumberFormatException ile) {
				JOptionPane.showMessageDialog(null, "ERROR: Debe introducir caracteres numericos");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
			}
		});

		jaceptar.add(aceptar);

		JButton cancelar = new JButton("CANCELAR");

		cancelar.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.MODELO, null));
			this.dispose();
		});

		jaceptar.add(cancelar);

	}

	private void rellenearMainPanel(JPanel jpanel) {

		JLabel nombreLabel = new JLabel(" INSERTAR NOMBRE:");
		jpanel.add(nombreLabel);
		textfieldnombre = new JTextField();
		textfieldnombre.setPreferredSize(new Dimension(200, 20));
		jpanel.add(textfieldnombre);

	}

	public void update(Context context) {
		if (context.getEvento() == Evento.ALTA_MODELO_VISTA) {
			this.setVisible(true);
		} else {
			if (context.getEvento() == Evento.RES_ALTA_MODELO_OK) {
				JOptionPane.showMessageDialog(null, "Exito al dar de alta modelo, id: " + (Integer) context.getDatos());
			} else if (context.getEvento() == Evento.RES_ALTA_MODELO_KO) {
				JOptionPane.showMessageDialog(null, "Error al dar de alta modelo.");
			}

			Controller.getInstancia().accion(new Context(Evento.MODELO, null));
			this.dispose();
		}
	}
}