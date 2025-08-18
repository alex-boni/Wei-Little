/**
 * 
 */
package Presentacion.MaquinaJPA;

import javax.swing.JFrame;

import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import Negocio.MaquinaJPA.TArcade;
import Negocio.MaquinaJPA.TMaquina;
import Negocio.MaquinaJPA.TRecreativa;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;

public class VistaModificarMaquina extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	JTextField textfieldpreciohoraactual;
	JTextField textfieldid;
	JTextField textfieldnombre;
	JTextField textfieldnumserie;
	JTextField textfieldalquilado;
	JTextField textfieldidmodelo;
	JTextField textfieldactivo;
	JTextField textfieldpreciopantalla;
	JTextField textfieldpreciomantenimiento;
	JTextField textfieldcoevalormul;
	JTextField textfieldcoevalordiv;
	JPanel mainPanel;
	private JComboBox<String> tipoComboBox;

	private TMaquina tmaquina;

	Double precio_hora_actual, precio_pantalla, precio_mantenimiento, coevalormul, coevalordiv = null;
	Integer idModelo, id_maquina = null;
	String nombre, numserie = null;

	public VistaModificarMaquina() {
		super("[MODIFICAR MAQUINA]");
		initGUI();
	}

	private void initGUI() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(600, 300));

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

	private boolean validarID(String id) {
		try {
			Integer.parseInt(id);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private boolean validarDouble(String precio) {
		try {
			Double.parseDouble(precio);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private void rellenarAceptarPanel(JPanel jaceptar) {
		JButton aceptar = new JButton("ACEPTAR");
		aceptar.addActionListener((e) -> {

			if (textfieldid.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "ID no introducido");
			}

			else if (!validarID(textfieldid.getText())) {
				JOptionPane.showMessageDialog(null, "ID no válido");
			}

			if (textfieldidmodelo.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "ID modelo no introducido");
			}

			else if (!validarID(textfieldidmodelo.getText())) {
				JOptionPane.showMessageDialog(null, "ID modelo no válido");
			}

			else if (textfieldnumserie.getText().length() != 9) {
				JOptionPane.showMessageDialog(null, "Numero de serie no introducido o no válido");
			}

			else if (textfieldnombre.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "Inserta un nombre");

			} else if (textfieldpreciohoraactual.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "Inserta el precio/hora");
			}

			else if (!validarDouble(textfieldpreciohoraactual.getText())) {
				JOptionPane.showMessageDialog(null, "Precio/hora no válido");
			}

			else if (textfieldpreciopantalla.getText().length() == 0
					&& tipoComboBox.getSelectedItem().equals("Arcade")) {
				JOptionPane.showMessageDialog(null, "Precio pantalla no introducido");
			} else if (!validarDouble(textfieldpreciopantalla.getText())
					&& tipoComboBox.getSelectedItem().equals("Arcade")) {
				JOptionPane.showMessageDialog(null, "Precio de pantalla no válido");
			}

			else if (textfieldpreciomantenimiento.getText().length() == 0
					&& tipoComboBox.getSelectedItem().equals("Recreativa")) {
				JOptionPane.showMessageDialog(null, "Precio mantenimiento no introducido");

			}

			else if (!validarDouble(textfieldpreciomantenimiento.getText())
					&& tipoComboBox.getSelectedItem().equals("Recreativa")) {
				JOptionPane.showMessageDialog(null, "Precio de mantenimiento no válido");
			}

			else if (textfieldcoevalormul.getText().length() == 0 && tipoComboBox.getSelectedItem().equals("Arcade")) {
				JOptionPane.showMessageDialog(null, "Coef. valor MUL no introducido");
			}

			else if (!validarDouble(textfieldcoevalormul.getText())
					&& tipoComboBox.getSelectedItem().equals("Arcade")) {
				JOptionPane.showMessageDialog(null, "Coef. valor MUL no válido");
			}

			else if (textfieldcoevalordiv.getText().length() == 0
					&& tipoComboBox.getSelectedItem().equals("Recreativa")) {
				JOptionPane.showMessageDialog(null, "Coef. valor DIV no introducido");
			}

			else if (!validarDouble(textfieldcoevalordiv.getText())
					&& tipoComboBox.getSelectedItem().equals("Recreativa")) {
				JOptionPane.showMessageDialog(null, "Coef. valor DIV no válido");
			}

			else if (tipoComboBox.getSelectedItem().equals("Introduzca el tipo de la maquina")) {
				JOptionPane.showMessageDialog(null, "Selecciona un tipo");
			}

			else {
				id_maquina = Integer.valueOf(textfieldid.getText());
				precio_hora_actual = Double.valueOf(textfieldpreciohoraactual.getText());
				nombre = String.valueOf(textfieldnombre.getText());
				numserie = String.valueOf(textfieldnumserie.getText());
				idModelo = Integer.valueOf(textfieldidmodelo.getText());

				if (tipoComboBox.getSelectedItem().equals("Arcade")) {
					precio_pantalla = Double.valueOf(textfieldpreciopantalla.getText());
					coevalormul = Double.valueOf(textfieldcoevalormul.getText());
					tmaquina = new TArcade(id_maquina, nombre, idModelo, numserie, 0, precio_hora_actual, 1,
							precio_pantalla, coevalormul);

				} else if (tipoComboBox.getSelectedItem().equals("Recreativa")) {
					precio_mantenimiento = Double.valueOf(textfieldpreciomantenimiento.getText());
					coevalordiv = Double.valueOf(textfieldcoevalordiv.getText());
					tmaquina = new TRecreativa(id_maquina, nombre, idModelo, numserie, 0, 1, precio_hora_actual,
							precio_mantenimiento, coevalordiv);

				}
				Controller.getInstancia().accion(new Context(Evento.MODIFICAR_MAQUINA_NEGOCIO, (Object) tmaquina));
				this.dispose();
			}
		});
		jaceptar.add(aceptar);

		JButton cancelar = new JButton("CANCELAR");
		cancelar.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.MAQUINA, null));
			this.dispose();
		});
		jaceptar.add(cancelar);
	}

	private void rellenearMainPanel(JPanel jpanel, String palabra) {

		jpanel.setLayout(new GridLayout(0, 2));
		JLabel pLabel = new JLabel("Modificar por " + palabra + ":");
		jpanel.add(pLabel);
		textfieldid = new JTextField(10);
		textfieldid.setMaximumSize(new Dimension(100, 40));
		jpanel.add(textfieldid);

		JLabel nombreLabel = new JLabel("Inserta nuevo nombre:");
		jpanel.add(nombreLabel);
		textfieldnombre = new JTextField(30);
		textfieldnombre.setMaximumSize(new Dimension(100, 80));
		jpanel.add(textfieldnombre);

		JLabel idmodeloLabel = new JLabel("Inserta id modelo:");
		jpanel.add(idmodeloLabel);
		textfieldidmodelo = new JTextField(30);
		textfieldidmodelo.setMaximumSize(new Dimension(100, 80));
		jpanel.add(textfieldidmodelo);

		JLabel numserieLabel = new JLabel("Inserta el nuevo numero de serie (9 caracteres):");
		jpanel.add(numserieLabel);
		textfieldnumserie = new JTextField(30);
		textfieldnumserie.setMaximumSize(new Dimension(100, 40));
		jpanel.add(textfieldnumserie);

		JLabel preciohoraactualLabel = new JLabel("Inserta el nuevo precio/hora actual:");
		jpanel.add(preciohoraactualLabel);
		textfieldpreciohoraactual = new JTextField(30);
		textfieldpreciohoraactual.setMaximumSize(new Dimension(100, 40));
		jpanel.add(textfieldpreciohoraactual);

		JLabel preciopantallaLabel = new JLabel("Inserta el nuevo precio de pantalla:");
		jpanel.add(preciopantallaLabel);
		textfieldpreciopantalla = new JTextField(30);
		textfieldpreciopantalla.setMaximumSize(new Dimension(100, 40));
		jpanel.add(textfieldpreciopantalla);
		preciopantallaLabel.setVisible(false);
		textfieldpreciopantalla.setVisible(false);

		JLabel coefvalormulLabel = new JLabel("Inserta el nuevo Coef. valor MUL:");
		jpanel.add(coefvalormulLabel);
		textfieldcoevalormul = new JTextField(30);
		textfieldcoevalormul.setMaximumSize(new Dimension(100, 40));
		jpanel.add(textfieldcoevalormul);
		coefvalormulLabel.setVisible(false);
		textfieldcoevalormul.setVisible(false);

		JLabel preciomantenimientoLabel = new JLabel("Inserta el nuevo precio de mantenimiento:");
		jpanel.add(preciomantenimientoLabel);
		textfieldpreciomantenimiento = new JTextField(30);
		textfieldpreciomantenimiento.setMaximumSize(new Dimension(100, 40));
		jpanel.add(textfieldpreciomantenimiento);
		preciomantenimientoLabel.setVisible(false);
		textfieldpreciomantenimiento.setVisible(false);

		JLabel coefvalordivLabel = new JLabel("Inserta el nuevo Coef. valor DIV:");
		jpanel.add(coefvalordivLabel);
		textfieldcoevalordiv = new JTextField(30);
		textfieldcoevalordiv.setMaximumSize(new Dimension(100, 40));
		jpanel.add(textfieldcoevalordiv);
		coefvalordivLabel.setVisible(false);
		textfieldcoevalordiv.setVisible(false);

		tipoComboBox = new JComboBox<>();
		tipoComboBox.addItem("Introduzca el tipo de la maquina");
		tipoComboBox.addItem("Arcade");
		tipoComboBox.addItem("Recreativa");
		jpanel.add(tipoComboBox);
		tipoComboBox.addActionListener(e -> {
			if (tipoComboBox.getSelectedItem().equals("Arcade")) {
				preciopantallaLabel.setVisible(true);
				textfieldpreciopantalla.setVisible(true);
				coefvalormulLabel.setVisible(true);
				textfieldcoevalormul.setVisible(true);
				textfieldpreciomantenimiento.setVisible(false);
				preciomantenimientoLabel.setVisible(false);
				coefvalordivLabel.setVisible(false);
				textfieldcoevalordiv.setVisible(false);
			}
			if (tipoComboBox.getSelectedItem().equals("Recreativa")) {
				preciopantallaLabel.setVisible(false);
				textfieldpreciopantalla.setVisible(false);
				coefvalormulLabel.setVisible(false);
				textfieldcoevalormul.setVisible(false);
				textfieldpreciomantenimiento.setVisible(true);
				preciomantenimientoLabel.setVisible(true);
				coefvalordivLabel.setVisible(true);
				textfieldcoevalordiv.setVisible(true);
			}
			if ("Introduzca el tipo de Maquina".equals(tipoComboBox.getSelectedItem())) {
				preciopantallaLabel.setVisible(false);
				textfieldpreciopantalla.setVisible(false);
				coefvalormulLabel.setVisible(false);
				textfieldcoevalormul.setVisible(false);
				textfieldpreciomantenimiento.setVisible(false);
				preciomantenimientoLabel.setVisible(false);
				coefvalordivLabel.setVisible(false);
				textfieldcoevalordiv.setVisible(false);
			}
		});
	}

	public void update(Context context) {
		if (context.getEvento() == Evento.MODIFICAR_MAQUINA_VISTA)
			this.setVisible(true);
		else {
			if (context.getEvento() == Evento.RES_MODIFICAR_MAQUINA_OK) {
				JOptionPane.showMessageDialog(null, "Exito al modificar maquina. Id: " + (int) context.getDatos());
			} else if (context.getEvento() == Evento.RES_MODIFICAR_MAQUINA_KO)
				JOptionPane.showMessageDialog(null, "Error al modificar maquina. ");
			Controller.getInstancia().accion(new Context(Evento.MAQUINA, null));
			this.dispose();
		}
	}

}