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

public class VistaBajaEmpleado extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaBajaEmpleado() {
		super("Baja Empleado");
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
				"<html><p>Ha inicializado el proceso de baja de empleado</p><p>Introduzca el ID del empleado a dar de baja.</p></html>");
		textLabel.setHorizontalAlignment(JLabel.CENTER);
		supPanel.add(textLabel, java.awt.BorderLayout.NORTH);

		JPanel inputPanel = new JPanel();
		JTextField textID = new JTextField();
		textID.setPreferredSize(new java.awt.Dimension(250, 30));
		inputPanel.add(textID);
		supPanel.add(inputPanel, java.awt.BorderLayout.CENTER);

		JButton botonAceptar = new JButton("Aceptar");
		botonAceptar.setPreferredSize(new java.awt.Dimension(100, 30));
		botonAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					int id = Integer.parseInt(textID.getText());

					if (id < 1)
						throw new NumberFormatException();

					Controller.getInstancia().accion(new Context(Evento.BAJA_EMPLEADO, id));
					dispose();
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null, "Error: El id no puede ser 0 o negativo");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error: El campo no puede estar vacio o ser letras");
				}
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
		if (context.getEvento() != Evento.VBAJA_EMPLEADO) {
			if (context.getEvento() == Evento.RES_BAJA_EMPLEADO_OK)
				JOptionPane.showMessageDialog(null, "Exito al dar de baja empleado id " + (int) context.getDatos());
			else if (context.getEvento() == Evento.RES_BAJA_EMPLEADO_KO)
				JOptionPane.showMessageDialog(null, "Error al dar de baja empleado. ");
			Controller.getInstancia().accion(new Context(Evento.EMPLEADO, null));
			this.dispose();
		} else
			this.setVisible(true);
	}
}
