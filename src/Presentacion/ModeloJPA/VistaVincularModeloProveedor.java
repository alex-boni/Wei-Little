
package Presentacion.ModeloJPA;

import javax.swing.JFrame;
import javax.swing.JTextField;

import Negocio.ModeloJPA.TVinculacionModeloProveedor;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JOptionPane;

import Presentacion.Controlador.Context;
import Presentacion.FactoriaPresentacion.IGUI;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;

public class VistaVincularModeloProveedor extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldModelo;
	private JTextField textFieldProveedor;

	public VistaVincularModeloProveedor() {
		super("Vincular Modelo Proveedor");
		initGUI();
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

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(500, 300));
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void rellenarPanelSuperior(JPanel supPanel) {
		supPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

		JLabel modeloLabel = new JLabel("ID del Modelo: ");
		textFieldModelo = new JTextField();
		textFieldModelo.setPreferredSize(new Dimension(100, 20));
		supPanel.add(modeloLabel);
		supPanel.add(textFieldModelo);

		JLabel proveedorLabel = new JLabel("ID del Proveedor: ");
		textFieldProveedor = new JTextField();
		textFieldProveedor.setPreferredSize(new Dimension(100, 20));
		supPanel.add(proveedorLabel);
		supPanel.add(textFieldProveedor);
	}

	private void rellenarPanelInferior(JPanel infPanel) {
		JButton aceptarButton = new JButton("OK");
		aceptarButton.setPreferredSize(new Dimension(80, 30));
		aceptarButton.addActionListener((e) -> {
			if (validar()) {
				int id_modelo = Integer.parseInt(textFieldModelo.getText());
				int id_proveedor = Integer.parseInt(textFieldProveedor.getText());
				Controller.getInstancia().accion(new Context(Evento.VINCULAR_PROVEEDOR_MODELO_NEGOCIO,
						new TVinculacionModeloProveedor(id_modelo, id_proveedor)));
				this.dispose();
			}
		});
		infPanel.add(aceptarButton);

		JButton cancelarButton = new JButton("Cancelar");
		cancelarButton.setPreferredSize(new Dimension(80, 30));
		cancelarButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.MODELO, null));
			this.dispose();
		});
		infPanel.add(cancelarButton);
	}

	private boolean validar() {
		boolean esValido = true;

		try {
			int id_modelo = Integer.parseInt(textFieldModelo.getText());
			int id_proveedor = Integer.parseInt(textFieldProveedor.getText());

			if (id_modelo <= 0 || id_proveedor <= 0) {
				throw new Exception("El id del modelo y el id del proveedor no pueden ser menores o iguales a 0");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El id del modelo y el id del proveedor deben ser números enteros",
					"Error", JOptionPane.ERROR_MESSAGE);
			esValido = false;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			esValido = false;
		}

		return esValido;
	}

	@Override
	public void update(Context context) {

		if (context.getEvento() == Evento.VINCULAR_PROVEEDOR_MODELO_VISTA)
			this.setVisible(true);

		else {
			if (context.getEvento() == Evento.RES_VINCULAR_PROVEEDOR_MODELO_KO) {
				JOptionPane.showMessageDialog(this, "No se ha podido vincular el modelo con el proveedor.", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else if (context.getEvento() == Evento.RES_VINCULAR_PROVEEDOR_MODELO_OK) {
				JOptionPane.showMessageDialog(this, "Modelo vinculado con el proveedor exitosamente.", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
			}
			Controller.getInstancia().accion(new Context(Evento.MODELO, null));
			dispose();
		}
	}
}
