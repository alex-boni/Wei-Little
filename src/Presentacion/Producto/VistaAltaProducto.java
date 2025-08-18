package Presentacion.Producto;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JTextField;

import Negocio.Producto.TComplemento;
import Negocio.Producto.TProducto;
import Negocio.Producto.TVideojuego;

import javax.swing.JFrame;

public class VistaAltaProducto extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField nombreTexto;
	private JTextField marcaTexto;
	private JTextField edadTexto;
	private JTextField pesoTexto;
	private JLabel edadLabel;
	private JLabel pesoLabel;
	TProducto tProducto;
	private JComboBox<String> tipoComboBox;

	public VistaAltaProducto() {
		super("[ALTA PRODUCTO]");
		this.initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(600, 300));
		JPanel jpanel = new JPanel();
		rellenearMainPanel(jpanel);
		JPanel jaceptar = new JPanel();
		rellenarAceptarPanel(jaceptar);
		mainPanel.add(jpanel);
		mainPanel.add(jaceptar);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(false);

	}

	private void rellenearMainPanel(JPanel jpanel) {
		jpanel.setLayout(new GridLayout(0, 2));
		JLabel nombreLabel = new JLabel(" INSERTAR NOMBRE:");
		jpanel.add(nombreLabel);
		nombreTexto = new JTextField(30);
		nombreTexto.setMaximumSize(new Dimension(50, 20));
		jpanel.add(nombreTexto);

		JLabel marcaLabel = new JLabel(" INSERTAR MARCA:");
		jpanel.add(marcaLabel);
		marcaTexto = new JTextField(30);
		marcaTexto.setMaximumSize(new Dimension(50, 20));
		jpanel.add(marcaTexto);

		pesoLabel = new JLabel(" INSERTAR PESO:");
		jpanel.add(pesoLabel);
		pesoTexto = new JTextField(30);
		pesoTexto.setMaximumSize(new Dimension(50, 20));
		jpanel.add(pesoTexto);
		pesoLabel.setVisible(false);
		pesoTexto.setVisible(false);

		edadLabel = new JLabel(" INSERTAR EDAD:");
		jpanel.add(edadLabel);
		edadTexto = new JTextField(30);
		edadTexto.setMaximumSize(new Dimension(50, 20));
		jpanel.add(edadTexto);
		edadTexto.setVisible(false);
		edadLabel.setVisible(false);

		JLabel tipoLabel = new JLabel(" SELECCIONAR TIPO:");
		jpanel.add(tipoLabel);

		tipoComboBox = new JComboBox<>();
		tipoComboBox.addItem("Introduzca el tipo del producto");
		tipoComboBox.addItem("Videojuego");
		tipoComboBox.addItem("Complemento");
		jpanel.add(tipoComboBox);
		tipoComboBox.addActionListener(e -> {
			if (tipoComboBox.getSelectedItem().equals("Complemento")) {
				pesoLabel.setVisible(true);
				pesoTexto.setVisible(true);
				edadTexto.setVisible(false);
				edadLabel.setVisible(false);
			}
			if (tipoComboBox.getSelectedItem().equals("Videojuego")) {
				edadTexto.setVisible(true);
				edadLabel.setVisible(true);
				pesoLabel.setVisible(false);
				pesoTexto.setVisible(false);
			}
			if ("Introduzca el tipo del producto".equals(tipoComboBox.getSelectedItem())) {
				edadTexto.setVisible(false);
				edadLabel.setVisible(false);
				pesoLabel.setVisible(false);
				pesoTexto.setVisible(false);
			}
		});

	}

	private boolean checkInteger(String text) {

		try {
			Double.parseDouble(text);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	private void rellenarAceptarPanel(JPanel jaceptar) {
		JButton aceptar = new JButton("ACEPTAR");
		aceptar.addActionListener((e) -> {

			int activo = 1;
			String tipoSeleccionado = (String) tipoComboBox.getSelectedItem();
			if (this.checkInteger(this.nombreTexto.getText()) && this.checkInteger(this.marcaTexto.getText()))
				JOptionPane.showMessageDialog(this, "Por favor, el nombre y/o la marca no puede ser numerico",
						"Advertencia", JOptionPane.WARNING_MESSAGE);
			else {
				if (tipoSeleccionado.equals("Complemento")) {
					if (pesoTexto.getText().isEmpty())
						JOptionPane.showMessageDialog(this, "Por favor, introduzca el peso del producto", "Advertencia",
								JOptionPane.WARNING_MESSAGE);
					else {
						try {
							this.tProducto = new TComplemento(this.nombreTexto.getText().toUpperCase(),
									marcaTexto.getText().toUpperCase(), activo,
									Double.parseDouble(pesoTexto.getText()));

						} catch (NumberFormatException n) {
							JOptionPane.showMessageDialog(this,
									"Por favor, introduzca el peso del producto correctamente", "Advertencia",
									JOptionPane.WARNING_MESSAGE);

						}
					}

				} else if (tipoSeleccionado.equals("Videojuego")) {
					if (edadTexto.getText().isEmpty())
						JOptionPane.showMessageDialog(this, "Por favor, introduzca la restriccion de edad del producto",
								"Advertencia", JOptionPane.WARNING_MESSAGE);
					else {
						this.tProducto = new TVideojuego(this.nombreTexto.getText().toUpperCase(),
								marcaTexto.getText().toUpperCase(), activo, Integer.parseInt(edadTexto.getText()));
					}

				} else if ("Introduzca el tipo del producto".equals(tipoComboBox.getSelectedItem())) {
					JOptionPane.showMessageDialog(this, "Por favor, introduzca un tipo valido", "Advertencia",
							JOptionPane.WARNING_MESSAGE);
				}
				if (tProducto != null) {
					Controller.getInstancia().accion(new Context(Evento.ALTA_PRODUCTO, tProducto));

					this.dispose();

				}
			}

		});
		jaceptar.add(aceptar);

		JButton cancelar = new JButton("CANCELAR");
		cancelar.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.PRODUCTO, null));

			this.dispose();
		});
		jaceptar.add(cancelar);
	}

	public void update(Context contexto) {

		if (contexto.getEvento() == Evento.VALTA_PRODUCTO) {
			setVisible(true);
		} else {
			int id = (int) contexto.getDatos();
			if (contexto.getEvento() == Evento.RES_ALTA_PRODUCTO_OK) {
				JOptionPane.showMessageDialog(null, "Exito al dar de alta producto id " + id);
			} else if (contexto.getEvento() == Evento.RES_ALTA_PRODUCTO_KO)
				JOptionPane.showMessageDialog(null, "Error al dar de alta producto. ");
			Controller.getInstancia().accion(new Context(Evento.PRODUCTO, null));
			this.dispose();
		}

	}
}