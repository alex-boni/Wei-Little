/**
 * 
 */
package Presentacion.AlquilerJPA;

import javax.swing.JFrame;

import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import javax.swing.JTextField;

import Negocio.AlquilerJPA.TLineaAlquiler;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;

public class VistaCancelarAlquiler extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaCancelarAlquiler() {
		super("[CANCELAR ALQUILER]");
		this.initGUI();
	}

	private void initGUI() {

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.setContentPane(mainPanel);

		JPanel supPanel = new JPanel();
		mainPanel.add(supPanel, BorderLayout.PAGE_START);
		this.rellenarPanelSuperior(supPanel);

		JPanel infPanel = new JPanel();
		mainPanel.add(infPanel, BorderLayout.PAGE_END);
		this.rellenarPanelInferior(infPanel);

		// Ajusta el tamaño de la ventana a 600x300 como en la ventana
		// 'VistaCancelarAlquiler'
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(600, 300)); // Establece el tamaño preferido de la ventana
		this.pack(); // Ajusta el tamaño de la ventana según los componentes
		this.setLocationRelativeTo(null); // Centra la ventana en la pantalla
		this.setVisible(true); // Asegúrate de que la ventana sea visible
	}

	private void rellenarPanelSuperior(JPanel supPanel) {

		supPanel.setLayout(new BorderLayout());

		// Crear un panel para contener los dos campos de texto en una sola línea
		JPanel fieldsPanel = new JPanel();
		fieldsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		supPanel.add(fieldsPanel, BorderLayout.PAGE_START);

		/* Creación textfield y label para el Alquiler */
		JLabel labelAlquiler = new JLabel("ID Alquiler: ");
		JTextField textFieldAlquiler = new JTextField();
		textFieldAlquiler.setVisible(true);
		textFieldAlquiler.setPreferredSize(new Dimension(100, 20));
		fieldsPanel.add(labelAlquiler);
		fieldsPanel.add(textFieldAlquiler);

		/* Creación textfield para la Máquina */
		JLabel labelMaquina = new JLabel("ID Máquina: ");
		JTextField textFieldMaquina = new JTextField();
		textFieldMaquina.setVisible(true);
		textFieldMaquina.setPreferredSize(new Dimension(100, 20));
		fieldsPanel.add(labelMaquina);
		fieldsPanel.add(textFieldMaquina);

		// Crear el panel de botones
		JPanel buttonsPanel = new JPanel();
		supPanel.add(buttonsPanel, BorderLayout.PAGE_END);

		/* Creación botón para aceptar */
		JButton buttonAceptar = new JButton("OK");
		buttonAceptar.setVisible(true);
		buttonAceptar.setPreferredSize(new Dimension(80, 30));
		buttonAceptar.addActionListener((e) -> {

			if (this.validar(textFieldAlquiler, textFieldMaquina)) {
				int id_alquiler = Integer.valueOf(textFieldAlquiler.getText());
				int id_maquina = Integer.valueOf(textFieldMaquina.getText());

				TLineaAlquiler tLineaAlquiler = new TLineaAlquiler();
				tLineaAlquiler.set_id_alquiler(id_alquiler);
				tLineaAlquiler.set_id_maquina(id_maquina);

				Controller.getInstancia().accion(new Context(Evento.CANCELAR_ALQUILER_NEGOCIO, tLineaAlquiler));
				this.dispose();
			}

		});

		buttonAceptar.setPreferredSize(new Dimension(50, 30));
		buttonsPanel.add(buttonAceptar);
	}

	private void rellenarPanelInferior(JPanel infPanel) {

		/* Creación botón para cancelar */
		JButton buttonCancelar = new JButton("CANCEL");
		buttonCancelar.setVisible(true);
		buttonCancelar.addActionListener((e) -> {

			// Abro la ventana de alquiler
			Controller.getInstancia().accion(new Context(Evento.ALQUILER, null));
			this.dispose();
		});

		buttonCancelar.setPreferredSize(new Dimension(80, 30));
		infPanel.add(buttonCancelar);
	}

	// Método para validar los campos introducidos
	private boolean validar(JTextField textFieldAlquiler, JTextField textFieldMaquina) {

		boolean esValido = true;
		try {

			int id_alquiler = Integer.parseInt(textFieldAlquiler.getText());

			if (id_alquiler <= 0) {
				throw new Exception("Debes introducir un número mayor que 0 para el identificador de alquiler.");
			}

			int id_maquina = Integer.parseInt(textFieldMaquina.getText());

			if (id_maquina <= 0) {
				throw new Exception("Debes introducir un número mayor que 0 para el identificador de máquina.");
			}

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "ERROR: Debes introducir un id numérico válido.", "Error",
					JOptionPane.ERROR_MESSAGE);
			esValido = false;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			esValido = false;
		}

		return esValido;
	}

	public void update(Context context) {
		if (context.getEvento() == Evento.CANCELAR_ALQUILER_VISTA) {
			setVisible(true);
		} else {

			if (context.getEvento() == Evento.RES_CANCELAR_ALQUILER_OK) {// Res==1
				JOptionPane.showMessageDialog(null, "Maquina devuelta correctamente", "Cancelar alquiler",
						JOptionPane.INFORMATION_MESSAGE);
			} else if (context.getEvento() == Evento.RES_CANCELAR_ALQUILER_KO_NO_ALQUILER) {// Res = -1
				JOptionPane.showMessageDialog(null, "No existe el alquiler introducido.", "Cancelar alquiler",
						JOptionPane.ERROR_MESSAGE);
			} else if (context.getEvento() == Evento.RES_CANCELAR_ALQUILER_KO_NO_LINEA_ALQUILER) {
				JOptionPane.showMessageDialog(null, "No existe una maquina asociada a ese alquiler.",
						"Cancelar alquiler", JOptionPane.ERROR_MESSAGE);

			} else if (context.getEvento() == Evento.RES_CANCELAR_ALQUILER_KO_MAQUINA_YA_DEVUELTA) {
				JOptionPane.showMessageDialog(null, "La máquina ya ha sido devuelta anteriormente.",
						"Cancelar alquiler", JOptionPane.ERROR_MESSAGE);
			} else if (context.getEvento() == Evento.RES_CANCELAR_ALQUILER_KO_MAQUINA_NO_ENCONTRADA) {
				JOptionPane.showMessageDialog(null, "La máquina no ha sido encontrada.", "Cancelar alquiler",
						JOptionPane.ERROR_MESSAGE);
			} else if (context.getEvento() == Evento.RES_CANCELAR_AQUILER_KO_ALQUILER_NO_ACTIVO) {
				JOptionPane.showMessageDialog(null, "El alquilar no está activo.", "Cancelar alquiler",
						JOptionPane.ERROR_MESSAGE);
			}
			Controller.getInstancia().accion(new Context(Evento.ALQUILER, null));
			this.dispose();
		}

	}
}