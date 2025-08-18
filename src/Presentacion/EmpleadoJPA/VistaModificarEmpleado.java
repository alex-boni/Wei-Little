package Presentacion.EmpleadoJPA;

import Negocio.EmpleadoJPA.TEmpleado;
import Negocio.EmpleadoJPA.TEmpleadoDependiente;
import Negocio.EmpleadoJPA.TEmpleadoTecnico;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class VistaModificarEmpleado extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField textDNI, textNombre, textSalario, textEspecializacion, textIdiomas;
	private JPanel tecnicoPanel, dependientePanel;

	public VistaModificarEmpleado() {
		super("Modificar Empleado");
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout());

		JLabel textLabel = new JLabel(
				"<html><p>Ha inicializado el proceso de modificación de empleado</p><p>Introduzca los datos del empleado para continuar.</p></html>");
		textLabel.setHorizontalAlignment(JLabel.CENTER);
		add(textLabel, BorderLayout.NORTH);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		add(mainPanel, BorderLayout.CENTER);

		JPanel idPanel = new JPanel();
		JLabel idLabel = new JLabel("ID:");
		JTextField textID = new JTextField();
		textID.setPreferredSize(new Dimension(250, 30));
		idPanel.add(idLabel);
		idPanel.add(textID);
		mainPanel.add(idPanel);

		JPanel typePanel = new JPanel();
		JLabel typeLabel = new JLabel("Tipo de empleado:");
		JComboBox<String> typeComboBox = new JComboBox<>(new String[] { "Técnico", "Dependiente" });
		typePanel.add(typeLabel);
		typePanel.add(typeComboBox);
		mainPanel.add(typePanel);

		JPanel dniPanel = new JPanel();
		JLabel dniLabel = new JLabel("DNI:");
		textDNI = new JTextField();
		textDNI.setPreferredSize(new Dimension(250, 30));
		dniPanel.add(dniLabel);
		dniPanel.add(textDNI);
		mainPanel.add(dniPanel);

		JPanel nombrePanel = new JPanel();
		JLabel nombreLabel = new JLabel("Nombre:");
		textNombre = new JTextField();
		textNombre.setPreferredSize(new Dimension(250, 30));
		nombrePanel.add(nombreLabel);
		nombrePanel.add(textNombre);
		mainPanel.add(nombrePanel);

		JPanel salarioPanel = new JPanel();
		JLabel salarioLabel = new JLabel("Salario:");
		textSalario = new JTextField();
		textSalario.setPreferredSize(new Dimension(250, 30));
		salarioPanel.add(salarioLabel);
		salarioPanel.add(textSalario);
		mainPanel.add(salarioPanel);

		tecnicoPanel = new JPanel();
		JLabel especializacionLabel = new JLabel("Especialización:");
		textEspecializacion = new JTextField();
		textEspecializacion.setPreferredSize(new Dimension(250, 30));
		tecnicoPanel.add(especializacionLabel);
		tecnicoPanel.add(textEspecializacion);

		dependientePanel = new JPanel();
		JLabel idiomasLabel = new JLabel("Idiomas:");
		textIdiomas = new JTextField();
		textIdiomas.setPreferredSize(new Dimension(250, 30));
		dependientePanel.add(idiomasLabel);
		dependientePanel.add(textIdiomas);

		mainPanel.add(tecnicoPanel);

		typeComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedType = (String) typeComboBox.getSelectedItem();
				if ("Técnico".equals(selectedType)) {
					mainPanel.remove(dependientePanel);
					mainPanel.add(tecnicoPanel);
				} else if ("Dependiente".equals(selectedType)) {
					mainPanel.remove(tecnicoPanel);
					mainPanel.add(dependientePanel);
				}
				mainPanel.revalidate();
				mainPanel.repaint();
			}
		});

		JPanel buttonPanel = new JPanel();
		JButton acceptButton = new JButton("Aceptar");
		acceptButton.setPreferredSize(new Dimension(100, 30));
		acceptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					String selectedType = (String) typeComboBox.getSelectedItem();
					TEmpleado empleado = null;

					if ("Técnico".equals(selectedType)) {
						TEmpleadoTecnico tecnico = new TEmpleadoTecnico();
						tecnico.setEspecializacion(textEspecializacion.getText());
						empleado = tecnico;
					} else if ("Dependiente".equals(selectedType)) {
						TEmpleadoDependiente dependiente = new TEmpleadoDependiente();
						dependiente.setIdioma(textIdiomas.getText());
						empleado = dependiente;
					}

					int id = Integer.parseInt(textID.getText());

					if (id < 1)
						throw new NumberFormatException();

					empleado.set_id_empleado(id);
					empleado.set_DNI(textDNI.getText());
					empleado.set_nombre(textNombre.getText());
					empleado.set_salario(Double.parseDouble(textSalario.getText()));
					empleado.set_activo(1);

					Controller.getInstancia().accion(new Context(Evento.MODIFICAR_EMPLEADO, empleado));
					dispose();

				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null, "Error: El id no puede ser 0 o negativo");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error: El campo no puede estar vacio o ser letras");
				}
			}
		});

		JButton cancelButton = new JButton("Cancelar");
		cancelButton.setPreferredSize(new java.awt.Dimension(100, 30));
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getInstancia().accion(new Context(Evento.EMPLEADO, null));
				dispose();
			}
		});

		buttonPanel.add(acceptButton);
		buttonPanel.add(cancelButton);
		add(buttonPanel, BorderLayout.SOUTH);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(600, 400));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void update(Context context) {
		if (context.getEvento() != Evento.VMODIFICAR_EMPLEADO) {
			if (context.getEvento() == Evento.RES_MODIFICAR_EMPLEADO_OK) {
				JOptionPane.showMessageDialog(null, "Exito al modificar empleado id " + (int) context.getDatos());
			} else if (context.getEvento() == Evento.RES_MODIFICAR_EMPLEADO_KO) {
				JOptionPane.showMessageDialog(null, "Error al modificar empleado. ");
			}
			Controller.getInstancia().accion(new Context(Evento.EMPLEADO, null));
			this.dispose();

		} else {
			this.setVisible(true);
		}
	}
}
