
package Presentacion.Producto;

import javax.swing.JFrame;
import javax.swing.JComboBox;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Negocio.Producto.TComplemento;
import Negocio.Producto.TProducto;
import Negocio.Producto.TVideojuego;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.Dimension;

public class VistaModificarProducto extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField pTextField;
	private JTextField nombreTextField;
	private JTextField activoTextField;
	private JTextField pesoTextField;
	private JTextField edadTextField;
	JComboBox<String> tipoComboBox;

	private TProducto tProducto;
	private JTextField marcaTextField;

	public VistaModificarProducto() {
		super("[MODIFICAR PRODUCTO]");
		this.initGUI();
	}

	public void update(Context contexto) {

		if (contexto.getEvento() == Evento.VMODIFICAR_PRODUCTO) {
			setVisible(true);
		} else {
			if (contexto.getEvento() == Evento.RES_MODIFICAR_PRODUCTO_OK) {
				TProducto tp = (TProducto) contexto.getDatos();
				String mhtml = "<html><body>" + "<p>Producto modificado: </p>" + "<p>ID: " + tp.get_id() + " </p>"
						+ "<p>NOMBRE: " + tp.get_nombre() + "</p>" + "<p>MARCA: " + tp.get_marca() + "</p>"
						+ "</body></html>";
				JOptionPane.showMessageDialog(null, mhtml, "Producto modificado", JOptionPane.INFORMATION_MESSAGE);
			}

			else if (contexto.getEvento() == Evento.RES_MODIFICAR_PRODUCTO_KO)
				JOptionPane.showMessageDialog(null,
						"<html>No ha sido posible realizar las modificaciones, por favor compruebe datos "
								+ "<p>(NO ES POSIBLE CAMBIAR SU TIPO, NI SU NOMBRE PUEDE ESTAR REPETIDO EN BD)</p></html>");
			Controller.getInstancia().accion(new Context(Evento.PRODUCTO, null));
			this.dispose();
		}
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(800, 300));

		JPanel jpanel = new JPanel();
		rellenearMainPanel(jpanel, "id");
		JPanel jpanel1 = new JPanel();
		jpanel1.setPreferredSize(new Dimension(800, 25));

		JPanel jaceptar = new JPanel();
		rellenarAceptarPanel(jaceptar);

		mainPanel.add(jpanel);
		mainPanel.add(jaceptar);
		mainPanel.add(jpanel1);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(false);
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
			if (this.checkInteger(this.nombreTextField.getText()) || this.checkInteger(this.marcaTextField.getText())
					|| nombreTextField.getText().isEmpty() || marcaTextField.getText().isEmpty()
					|| activoTextField.getText().isEmpty()
					|| tipoComboBox.getSelectedItem().equals("Selecciona el tipo del producto")
					|| (edadTextField.getText().isEmpty() && pesoTextField.getText().isEmpty())
					|| Integer.parseInt(pTextField.getText()) <= 0) {

				JOptionPane.showMessageDialog(this, "Por favor, introduzca todos los parametros", "Advertencia",
						JOptionPane.WARNING_MESSAGE);
			} else {
				try {
					this.tProducto = null;
					if (tipoComboBox.getSelectedItem().equals(("Videojuego"))) {
						this.tProducto = new TVideojuego(nombreTextField.getText().toUpperCase(),
								marcaTextField.getText().toUpperCase(), Integer.parseInt(activoTextField.getText()),
								Integer.parseInt(edadTextField.getText()));

					} else if (tipoComboBox.getSelectedItem().equals(("Complemento"))) {
						this.tProducto = new TComplemento(nombreTextField.getText().toUpperCase(),
								marcaTextField.getText().toUpperCase(), Integer.parseInt(activoTextField.getText()),
								Double.parseDouble(pesoTextField.getText()));

					}

					this.tProducto.set_id(Integer.parseInt(pTextField.getText()));

					Controller.getInstancia().accion(new Context(Evento.MODIFICAR_PRODUCTO, this.tProducto));
					this.dispose();

				} catch (NumberFormatException n) {
					JOptionPane.showMessageDialog(this, "Por favor, introduzca bien el tipo de los parametros",
							"Advertencia", JOptionPane.WARNING_MESSAGE);
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

	private void rellenearMainPanel(JPanel jpanel, String palabra) {
		jpanel.setLayout(new BoxLayout(jpanel, BoxLayout.Y_AXIS));

		JLabel pLabel = new JLabel("Modificar por " + palabra + ":");
		jpanel.add(pLabel);
		pTextField = new JTextField(20);
		pTextField.setMaximumSize(new Dimension(200, 40));
		jpanel.add(pTextField);

		JLabel p2Label = new JLabel("Insertar nombre nuevo:");
		jpanel.add(p2Label);
		nombreTextField = new JTextField(20);
		nombreTextField.setMaximumSize(new Dimension(200, 40));
		jpanel.add(nombreTextField);

		JLabel marcaLabel = new JLabel("Insertar marca nueva:");
		jpanel.add(marcaLabel);
		marcaTextField = new JTextField(20);
		marcaTextField.setMaximumSize(new Dimension(200, 40));
		jpanel.add(marcaTextField);

		JLabel p3Label = new JLabel("Activo/Inactivo (1/0):");
		jpanel.add(p3Label);
		activoTextField = new JTextField(10);
		activoTextField.setMaximumSize(new Dimension(200, 40));
		jpanel.add(activoTextField);

		JLabel tipoLabel = new JLabel("Seleccionar tipo:");
		jpanel.add(tipoLabel);
		tipoComboBox = new JComboBox<>();
		tipoComboBox.addItem("Selecciona el tipo del producto");
		tipoComboBox.addItem("Videojuego");
		tipoComboBox.addItem("Complemento");
		jpanel.add(tipoComboBox);
		JPanel espacioPanel = new JPanel();
		espacioPanel.setPreferredSize(new Dimension(10, 20));
		jpanel.add(espacioPanel);

		JLabel pesoLabel = new JLabel("Insertar peso:");
		jpanel.add(pesoLabel);
		pesoTextField = new JTextField(30);
		pesoTextField.setMaximumSize(new Dimension(50, 20));
		jpanel.add(pesoTextField);
		pesoLabel.setVisible(false);
		pesoTextField.setVisible(false);

		JLabel edadLabel = new JLabel("Insertar edad:");
		jpanel.add(edadLabel);
		edadTextField = new JTextField(30);
		edadTextField.setMaximumSize(new Dimension(50, 20));
		jpanel.add(edadTextField);
		edadLabel.setVisible(false);
		edadTextField.setVisible(false);
		tipoComboBox.addActionListener(e -> {
			if (tipoComboBox.getSelectedItem().equals("Complemento")) {
				pesoLabel.setVisible(true);
				pesoTextField.setVisible(true);
				edadTextField.setVisible(false);
				edadLabel.setVisible(false);
			} else if (tipoComboBox.getSelectedItem().equals("Videojuego")) {
				edadTextField.setVisible(true);
				edadLabel.setVisible(true);
				pesoLabel.setVisible(false);
				pesoTextField.setVisible(false);
			} else {
				edadTextField.setVisible(false);
				edadLabel.setVisible(false);
				pesoLabel.setVisible(false);
				pesoTextField.setVisible(false);
			}
		});

	}

}