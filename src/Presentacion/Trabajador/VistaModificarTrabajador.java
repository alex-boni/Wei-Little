
package Presentacion.Trabajador;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Negocio.Trabajador.TSupervisor;
import Negocio.Trabajador.TTrabajador;
import Negocio.Trabajador.TVendedor;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;

public class VistaModificarTrabajador extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	JTextField idTextField;
	JTextField nombreTextField;
	JTextField dniTextField;
	JTextField tipoTextField;
	JTextField experienciaTextField;
	JTextField idiomaTextField;
	JTextField activoTextField;
	private String activo;
	TTrabajador ttrabajador;
	JPanel mainPanel;
	String experiencia;
	String idioma;
	private boolean esSupervisor = true;

	public VistaModificarTrabajador() {
		super("[MODIFICAR TRABAJADOR]");
		initGUI();
	}

	private void initGUI() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(300, 700));

		JPanel jpanel = new JPanel();
		rellenearMainPanel(jpanel, "id");

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

	private boolean validarID(String id) {
		try {
			Integer.parseInt(id);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private void rellenarAceptarPanel(JPanel jaceptar) {
		JButton aceptar = new JButton("ACEPTAR");
		aceptar.addActionListener((e) -> {

			activo = activoTextField.getText().toLowerCase();
			String idString = idTextField.getText();
			if (idString.length() == 0) {
				JOptionPane.showMessageDialog(null, "ID no introducido");
			} else if (!validarID(idString)) {
				JOptionPane.showMessageDialog(null, "ID no válido");
			} else if (dniTextField.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "DNI no introducido o no válido");
			} else if (!validarDNI(dniTextField.getText())) {
				JOptionPane.showMessageDialog(null, "DNI no válido");
			} else if (nombreTextField.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "Inserta un nombre");
			} else if (activoTextField.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "Campo activo vacio");
			} else if (!activoTextField.getText().toLowerCase().equals("si")
					&& !activoTextField.getText().toLowerCase().equals("no")) {
				JOptionPane.showMessageDialog(null, "Campo activo no valido");
			} else if (experienciaTextField.getText().length() == 0 && esSupervisor) {
				JOptionPane.showMessageDialog(null, "Experiencia no introducida");
			} else if (idiomaTextField.getText().length() == 0 && !esSupervisor) {
				JOptionPane.showMessageDialog(null, "Idioma no introducido");
			} else {
				if (esSupervisor) {
					ttrabajador = new TSupervisor();
					experiencia = experienciaTextField.getText();
					ttrabajador.set_id(Integer.parseInt(idTextField.getText()));
					ttrabajador.set_nombre(nombreTextField.getText());
					ttrabajador.set_dni(dniTextField.getText());
					((TSupervisor) ttrabajador).set_experiencia(experiencia);

					if (activo.toLowerCase().equals("si")) {
						ttrabajador.set_activo(1);
					} else if (activo.toLowerCase().equals("no")) {
						ttrabajador.set_activo(0);
					}

				} else {
					ttrabajador = new TVendedor();
					idioma = idiomaTextField.getText();
					ttrabajador.set_id(Integer.parseInt(idTextField.getText()));
					ttrabajador.set_nombre(nombreTextField.getText());
					ttrabajador.set_dni(dniTextField.getText());
					((TVendedor) ttrabajador).set_idioma(idioma);
					if (activo.toLowerCase().equals("si")) {
						ttrabajador.set_activo(1);
					} else if (activo.toLowerCase().equals("no")) {
						ttrabajador.set_activo(0);
					}

				}
				Controller.getInstancia().accion(new Context(Evento.MODIFICAR_TRABAJADOR, (Object) ttrabajador));
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

	private void rellenearMainPanel(JPanel jpanel, String palabra) {
		JLabel pLabel = new JLabel("Modificar por " + palabra + ":");
		jpanel.add(pLabel);
		idTextField = new JTextField(10);
		idTextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(idTextField);
		JLabel nombreLabel = new JLabel("Inserta nuevo nombre:");
		jpanel.add(nombreLabel);
		nombreTextField = new JTextField(30);
		nombreTextField.setMaximumSize(new Dimension(100, 80));
		jpanel.add(nombreTextField);
		JLabel dniLabel = new JLabel("Inserta el nuevo dni:");
		jpanel.add(dniLabel);
		dniTextField = new JTextField(30);
		dniTextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(dniTextField);
		JLabel experienciaLabel = new JLabel("Inserta la nueva experiencia:");
		jpanel.add(experienciaLabel);
		experienciaTextField = new JTextField(30);
		experienciaTextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(experienciaTextField);
		JLabel idiomaLabel = new JLabel("Inserta el nuevo idioma:");
		jpanel.add(idiomaLabel);
		idiomaTextField = new JTextField(30);
		idiomaTextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(idiomaTextField);
		JLabel activoLabel = new JLabel("Inserta el nuevo activo(SI/NO):");
		jpanel.add(activoLabel);
		activoTextField = new JTextField(30);
		activoTextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(activoTextField);
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

		if (context.getEvento() != Evento.VMODIFICAR_TRABAJADOR) {
			TTrabajador tTrabajador = (TTrabajador) context.getDatos();
			if (context.getEvento() == Evento.RES_MODIFICAR_TRABAJADOR_OK)
				JOptionPane.showMessageDialog(null, "Exito al modificar trabajador " + tTrabajador.get_id());
			else if (context.getEvento() == Evento.RES_MODIFICAR_TRABAJADOR_KO)
				JOptionPane.showMessageDialog(null, "Error al modificar trabajador " + tTrabajador.get_id());
			Controller.getInstancia().accion(new Context(Evento.TRABAJADOR, null));
			this.dispose();
		} else
			setVisible(true);

	}
}