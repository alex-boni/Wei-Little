package Presentacion.AlquilerJPA;

import javax.swing.JFrame;

import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Negocio.AlquilerJPA.TAlquiler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;

public class VistaAbrirAlquiler extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaAbrirAlquiler() {
		super("[ABRIR ALQUILER]");
		initGUI();
	}

	private void initGUI() {

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		setContentPane(mainPanel);

		JPanel supPanel = new JPanel(new BorderLayout());
		mainPanel.add(supPanel, BorderLayout.PAGE_START);
		rellenarPanelSuperior(supPanel);

		JPanel bottomPanel = new JPanel();
		mainPanel.add(bottomPanel, BorderLayout.PAGE_END);
		rellenarPanelInferior(bottomPanel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(600, 300));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void rellenarPanelSuperior(JPanel supPanel) {

		JLabel textLabel = new JLabel(
				"<html><p>Ha el inicializado el proceso de alquiler</p><p>Indique el cliente y empleado para iniciar.</p><p> CONTINUAR o cancele con CANCELAR</p></html>");
		textLabel.setVisible(true);
		textLabel.setPreferredSize(new Dimension(250, 50));
		textLabel.setHorizontalAlignment(JLabel.CENTER);
		supPanel.add(textLabel);
	}

	private void rellenarPanelInferior(JPanel bottomPanel) {

		bottomPanel.setLayout(new BorderLayout());

		JPanel textFieldPanel = new JPanel();
		JPanel textFieldPanel2 = new JPanel();
		bottomPanel.add(textFieldPanel, BorderLayout.PAGE_START);
		bottomPanel.add(textFieldPanel2, BorderLayout.CENTER);

		JLabel idLabel = new JLabel("Id Cliente: ");
		JTextField idText = new JTextField();
		idText.setVisible(true);
		idText.setPreferredSize(new Dimension(100, 30));
		textFieldPanel.add(idLabel);
		textFieldPanel.add(idText);

		JLabel idLabel2 = new JLabel("Id Empleado: ");
		JTextField idText2 = new JTextField();
		idText2.setVisible(true);
		idText2.setPreferredSize(new Dimension(100, 30));
		textFieldPanel2.add(idLabel2);
		textFieldPanel2.add(idText2);

		JPanel buttonsPanel = new JPanel();
		bottomPanel.add(buttonsPanel, BorderLayout.PAGE_END);

		JButton ok_button = new JButton("OK");
		ok_button.setVisible(true);
		ok_button.addActionListener((e) -> {
			if (validar(idText, idText2)) {
				int id_cliente = Integer.valueOf(idText.getText());
				int id_empleado = Integer.valueOf(idText2.getText());
				TAlquiler tAlquiler = new TAlquiler();
				tAlquiler.set_id_cliente(id_cliente);
				tAlquiler.set_id_empleado(id_empleado);
				Controller.getInstancia().accion(new Context(Evento.ABRIR_ALQUILER_NEGOCIO, tAlquiler));
				dispose();
			}

		});
		buttonsPanel.add(ok_button);

		JButton cancel_button = new JButton("CANCEL");
		cancel_button.setVisible(true);
		cancel_button.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.ALQUILER, null));
			dispose();
		});
		buttonsPanel.add(cancel_button);
	}

	private boolean validar(JTextField idText, JTextField idText2) {
		boolean esValido = true;
		try {
			int id_cliente = Integer.valueOf(idText.getText());
			if (id_cliente <= 0) {
				JOptionPane.showMessageDialog(null, "Error: Id cliente no puede ser negativo o cero");
				esValido = false;
			}
		} catch (Exception ex) {
			esValido = false;
			JOptionPane.showMessageDialog(null,
					"Error: No puede estar el campo de id cliente vacio o con letras " + ex.getMessage());
		}
		try {
			int id_empleado = Integer.valueOf(idText2.getText());
			if (id_empleado <= 0) {
				JOptionPane.showMessageDialog(null, "Error: Id empleado no puede ser negativo o cero");
				esValido = false;
			}
		} catch (Exception ex) {
			esValido = false;
			JOptionPane.showMessageDialog(null,
					"Error: No puede estar el campo id empleado vacio o con letras " + ex.getMessage());
		}
		return esValido;
	}

	public void update(Context context) {

		Evento evento = context.getEvento();
		Object datos = context.getDatos();

		if (evento == Evento.ABRIR_ALQUILER_VISTA) {
			setVisible(true);
		}

		else {
			updateAlquiler(evento, datos);
		}
	}

	private void updateAlquiler(Evento evento, Object datos) {

		if (evento == Evento.RES_ABRIR_ALQUILER_OK) {
			mensaje("Carrito generado con exito");
			Controller.getInstancia().accion(new Context(Evento.PASAR_CARRITO_ALQUILER_A_CERRAR, datos));
		} else if (evento == Evento.RES_ABRIR_ALQUILER_KO) {
			mensaje("Error inesperado al inicial la alquiler");
			Controller.getInstancia().accion(new Context(Evento.ALQUILER, evento));
		}

		dispose();
	}

	private void mensaje(String texto) {
		JOptionPane.showMessageDialog(null, texto);
	}
}