
package Presentacion.Trabajador;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Negocio.Trabajador.TSupervisor;
import Negocio.Trabajador.TTrabajador;
import Negocio.Trabajador.TVendedor;

import java.awt.Dimension;

public class VistaAltaTrabajador extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField nombreTextField;
	private JTextField dniTextField;
	private JTextField idiomaTextField;
	private JTextField experienciaTextField;
	private TTrabajador ttrabajador;
	private JPanel mainPanel;
	private boolean esSupervisor = true;
	String idioma;
	String experiencia;
	String tipo;

	public VistaAltaTrabajador() {
		super("[ALTA TRABAJADOR]");
		initGUI();
	}

	private void initGUI() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(400, 300));
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

	private boolean validarDNI(String dni) {
		String formatocorrecto = "\\d{8}[a-zA-Z]";

		return dni.matches(formatocorrecto);
	}

	private void rellenarAceptarPanel(JPanel jaceptar) {
		JButton aceptar = new JButton("ACEPTAR");
		aceptar.addActionListener((e) -> {
			if (dniTextField.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "DNI no introducido o no válido");
			} else if (!validarDNI(dniTextField.getText())) {
				JOptionPane.showMessageDialog(null, "DNI no válido");
			} else if (nombreTextField.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "Nombre no introducido");
			} else if (experienciaTextField.getText().length() == 0 && esSupervisor) {
				JOptionPane.showMessageDialog(null, "Experiencia no introducida");
			} else if (idiomaTextField.getText().length() == 0 && !esSupervisor) {
				JOptionPane.showMessageDialog(null, "Idioma no introducido");
			} else {
				if (esSupervisor) {
					ttrabajador = new TSupervisor();
					experiencia = experienciaTextField.getText();
					ttrabajador.colocar_datos(1, nombreTextField.getText(), dniTextField.getText());
					((TSupervisor) ttrabajador).set_experiencia(experiencia);
					((TSupervisor) ttrabajador).set_activo(1);
				} else {
					ttrabajador = new TVendedor();
					if (dniTextField.getText().length() == 0) {
						JOptionPane.showMessageDialog(null, "DNI no introducido");
					}
					idioma = idiomaTextField.getText();
					ttrabajador.colocar_datos(1, nombreTextField.getText(), dniTextField.getText());
					((TVendedor) ttrabajador).set_idioma(idioma);
					((TVendedor) ttrabajador).set_activo(1);
				}
				Controller.getInstancia().accion(new Context(Evento.ALTA_TRABAJADOR, (Object) ttrabajador));
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
		JLabel nombreLabel = new JLabel("INSERTAR NOMBRE:");
		jpanel.add(nombreLabel);
		nombreTextField = new JTextField(30);
		nombreTextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(nombreTextField);
		JLabel dniLabel = new JLabel("INSERTAR DNI:");
		jpanel.add(dniLabel);
		dniTextField = new JTextField(30);
		dniTextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(dniTextField);
		JLabel experienciaLabel = new JLabel("INSERTAR EXPERIENCIA:");
		jpanel.add(experienciaLabel);
		experienciaTextField = new JTextField(30);
		experienciaTextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(experienciaTextField);
		JLabel idiomaLabel = new JLabel("INSERTAR IDIOMA:");
		jpanel.add(idiomaLabel);
		idiomaTextField = new JTextField(30);
		idiomaTextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(idiomaTextField);
		JSpinner fieldspinner = new JSpinner();
		fieldspinner.setModel(new SpinnerListModel(new String[] { "Supervisor", "Vendedor" }));
		fieldspinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (fieldspinner.getValue().toString().equals("Supervisor")) {
					esSupervisor = true;
				} else {
					esSupervisor = false;
				}

				experienciaLabel.setVisible(esSupervisor);
				experienciaTextField.setVisible(esSupervisor);
				idiomaLabel.setVisible(!esSupervisor);
				idiomaTextField.setVisible(!esSupervisor);

			}
		});
		experienciaLabel.setVisible(esSupervisor);
		experienciaTextField.setVisible(esSupervisor);
		idiomaLabel.setVisible(!esSupervisor);
		idiomaTextField.setVisible(!esSupervisor);
		fieldspinner.setBounds(211, 34, 173, 20);
		mainPanel.add(fieldspinner);
	}

	@Override
	public void update(Context context) {
		if (context.getEvento() != Evento.VALTA_TRABAJADOR) {
			if (context.getEvento() == Evento.RES_ALTA_TRABAJADOR_OK)
				JOptionPane.showMessageDialog(null, "Exito al dar de alta trabajador id " + (int) context.getDatos());
			else if (context.getEvento() == Evento.RES_ALTA_TRABAJADOR_KO)
				JOptionPane.showMessageDialog(null, "Error al dar de alta trabajador. ");
			Controller.getInstancia().accion(new Context(Evento.TRABAJADOR, null));
			this.dispose();

		} else {
			this.setVisible(true);
		}

	}
}