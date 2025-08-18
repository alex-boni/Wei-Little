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

public class VistaAltaEmpleado extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField textDNI, textNombre, textSalario, textEspecializacion, textIdiomas;
	private JPanel tecnicoPanel, dependientePanel;

	public VistaAltaEmpleado() {
		super("Alta Empleado");
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout());

		JLabel textLabel = new JLabel(
				"<html><p>Ha inicializado el proceso de alta de empleado</p><p>Introduzca los datos del empleado para continuar.</p></html>");
		textLabel.setHorizontalAlignment(JLabel.CENTER);
		add(textLabel, BorderLayout.NORTH);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		add(mainPanel, BorderLayout.CENTER);

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

				empleado.set_DNI(textDNI.getText());
				empleado.set_nombre(textNombre.getText());
				empleado.set_salario(Double.parseDouble(textSalario.getText()));
				empleado.set_activo(1);

				Controller.getInstancia().accion(new Context(Evento.ALTA_EMPLEADO, empleado));
				dispose();
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
		if (context.getEvento() != Evento.VALTA_EMPLEADO) {
			if (context.getEvento() == Evento.RES_ALTA_EMPLEADO_OK) {
				JOptionPane.showMessageDialog(null, "Exito al dar de alta empleado id " + (int) context.getDatos());
			} else if (context.getEvento() == Evento.RES_ALTA_EMPLEADO_KO) {
				JOptionPane.showMessageDialog(null, "Error al dar de alta empleado. ");
			}
			Controller.getInstancia().accion(new Context(Evento.EMPLEADO, null));
			this.dispose();

		} else {
			this.setVisible(true);
		}
	}

}
