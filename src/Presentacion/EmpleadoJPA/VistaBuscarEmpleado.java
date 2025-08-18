package Presentacion.EmpleadoJPA;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Negocio.EmpleadoJPA.TEmpleado;
import Negocio.EmpleadoJPA.TEmpleadoDependiente;
import Negocio.EmpleadoJPA.TEmpleadoTecnico;

public class VistaBuscarEmpleado extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaBuscarEmpleado() {
		super("Buscar Empleado");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new java.awt.BorderLayout());
		setContentPane(mainPanel);

		JPanel supPanel = new JPanel(new java.awt.BorderLayout());
		mainPanel.add(supPanel, java.awt.BorderLayout.CENTER);
		rellenarPanel(supPanel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new java.awt.Dimension(400, 200));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void rellenarPanel(JPanel supPanel) {

		JLabel textLabel = new JLabel(
				"<html><p>Ha inicializado el proceso de buscar empleado</p><p>Introduzca el ID del empleado</p></html>");
		textLabel.setHorizontalAlignment(JLabel.CENTER);
		supPanel.add(textLabel, java.awt.BorderLayout.NORTH);

		JPanel inputPanel = new JPanel();
		JTextField textID = new JTextField();
		textID.setPreferredSize(new java.awt.Dimension(250, 30));
		inputPanel.add(textID);
		supPanel.add(inputPanel, java.awt.BorderLayout.CENTER);

		JButton botonAceptar = new JButton("Aceptar");
		botonAceptar.setPreferredSize(new java.awt.Dimension(100, 30));
		botonAceptar.addActionListener((e) -> {
			try {

				int id = Integer.parseInt(textID.getText());

				if (id < 1)
					throw new NumberFormatException();

				Controller.getInstancia().accion(new Context(Evento.BUSCAR_EMPLEADO, id));
				dispose();

			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Error: El id no puede ser 0 o negativo");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Error: El campo no puede estar vacio o ser letras");
			}
		});

		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.setPreferredSize(new java.awt.Dimension(100, 30));
		botonCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getInstancia().accion(new Context(Evento.EMPLEADO, null));
				dispose();
			}
		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(botonAceptar);
		buttonPanel.add(botonCancelar);
		supPanel.add(buttonPanel, java.awt.BorderLayout.SOUTH);
	}

	@Override
	public void update(Context context) {
		if (context.getEvento() != Evento.VBUSCAR_EMPLEADO) {
			if (context.getEvento() == Evento.RES_BUSCAR_EMPLEADO_OK) {
				TEmpleado emp = (TEmpleado) context.getDatos();
				String mhtml = "<html><body>" + "<p>Empleado encontrado: </p>" + "<p>ID: " + emp.get_id_empleado()
						+ " </p>" + "<p>NOMBRE: " + emp.get_nombre() + "</p>" + "<p>SALARIO: " + emp.get_salario()
						+ "</p>" + "<p>DNI: " + emp.get_DNI() + "</p>" + "<p>ACTIVO: "
						+ (emp.get_activo() == 1 ? "SI" : "NO") + "</p>";

				if (emp instanceof TEmpleadoDependiente) {
					TEmpleadoDependiente ed = (TEmpleadoDependiente) emp;
					mhtml += "<p>TIPO: DEPENDIENTE</p>";
					mhtml += "<p>IDIOMA: " + ed.getIdioma() + "</p>";

				} else {

					TEmpleadoTecnico et = (TEmpleadoTecnico) emp;
					mhtml += "<p>TIPO: TECNICO</p>";
					mhtml += "<p>ESPECIALIZACION: " + et.getEspecializacion() + "</p>";
				}

				mhtml += "</body></html>";

				JOptionPane.showMessageDialog(null, mhtml, "Modelo Encontrado", JOptionPane.INFORMATION_MESSAGE);
			} else if (context.getEvento() == Evento.RES_BUSCAR_EMPLEADO_KO)
				JOptionPane.showMessageDialog(null, "Error al buscar empleado. ");
			Controller.getInstancia().accion(new Context(Evento.EMPLEADO, null));
			this.dispose();
		} else
			this.setVisible(true);
	}
}
