/**
 * 
 */
package Presentacion.MaquinaJPA;

import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Negocio.MaquinaJPA.TArcade;
import Negocio.MaquinaJPA.TMaquina;
import Negocio.MaquinaJPA.TRecreativa;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;

public class VistaAltaMaquina extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField textfieldpreciohoraactual;
	private JTextField textfieldnombre;
	private JTextField textfieldnumserie;
	private JTextField textfieldidmodelo;
	private JTextField textfieldpreciopantalla;
	private JTextField textfieldpreciomantenimiento;
	private JTextField textfieldcoevalormul;
	private JTextField textfieldcoevalordiv;
	private JComboBox<String> tipoComboBox;

	private TMaquina tmaquina;

	public VistaAltaMaquina() {
		super("[ALTA]");
		initGUI();
	}

	private void initGUI() {

		JPanel mainPanel = new JPanel();
		JPanel jaceptar = new JPanel();
		JPanel jpanel = new JPanel();

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		this.setContentPane(mainPanel);

		this.setPreferredSize(new Dimension(600, 300));

		rellenearMainPanel(jpanel);
		rellenarAceptarPanel(jaceptar);

		mainPanel.add(jpanel);
		mainPanel.add(jaceptar);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
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

			Double precio_hora_actual, precio_pantalla, precio_mantenimiento, coevalormul, coevalordiv = null;
			Integer idModelo;
			String nombre, numserie = null;

			String tipoSeleccionado = (String) tipoComboBox.getSelectedItem();

			if (textfieldnumserie.getText().length() != 9) {
				JOptionPane.showMessageDialog(null, "Numero de serie no introducido o no válido");
			}

			else if (!validarID(textfieldidmodelo.getText()) || textfieldidmodelo.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Id modelo no introducido o no valido");
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

			else if (tipoSeleccionado.equals("Introduzca el tipo de la maquina")) {
				JOptionPane.showMessageDialog(null, "Introduce un tipo");
			}

			else {

				precio_hora_actual = Double.valueOf(textfieldpreciohoraactual.getText());
				nombre = String.valueOf(textfieldnombre.getText());
				numserie = String.valueOf(textfieldnumserie.getText());
				idModelo = Integer.valueOf(textfieldidmodelo.getText());

				if (tipoSeleccionado.equals("Arcade")) {
					precio_pantalla = Double.valueOf(textfieldpreciopantalla.getText());
					coevalormul = Double.valueOf(textfieldcoevalormul.getText());
					this.tmaquina = new TArcade(0, nombre, idModelo, numserie, 0, precio_hora_actual, 1,
							precio_pantalla, coevalormul);
				}

				if (tipoSeleccionado.equals("Recreativa")) {

					precio_mantenimiento = Double.valueOf(textfieldpreciomantenimiento.getText());
					coevalordiv = Double.valueOf(textfieldcoevalordiv.getText());
					this.tmaquina = new TRecreativa(0, nombre, idModelo, numserie, 0, 1, precio_hora_actual,
							precio_mantenimiento, coevalordiv);
				}

				Controller.getInstancia().accion(new Context(Evento.ALTA_MAQUINA_NEGOCIO, (Object) tmaquina));

				dispose();
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

	private void rellenearMainPanel(JPanel jpanel) {

		jpanel.setLayout(new GridLayout(0, 2));
		JLabel nombreLabel = new JLabel(" INSERTAR NOMBRE:");
		jpanel.add(nombreLabel);
		textfieldnombre = new JTextField(30);
		textfieldnombre.setMaximumSize(new Dimension(50, 20));
		jpanel.add(textfieldnombre);

		JLabel idModeloLabel = new JLabel(" INSERTAR MODELO:");
		jpanel.add(idModeloLabel);
		textfieldidmodelo = new JTextField(30);
		textfieldidmodelo.setMaximumSize(new Dimension(50, 20));
		jpanel.add(textfieldidmodelo);

		JLabel numserieLabel = new JLabel(" INSERTAR NUMERO DE SERIE (9 CARACTERES):");
		jpanel.add(numserieLabel);
		textfieldnumserie = new JTextField(30);
		textfieldnumserie.setMaximumSize(new Dimension(50, 20));
		jpanel.add(textfieldnumserie);

		JLabel preciohoraactualLabel = new JLabel(" INSERTAR PRECIO POR HORA ACTUAL:");
		jpanel.add(preciohoraactualLabel);
		textfieldpreciohoraactual = new JTextField(30);
		textfieldpreciohoraactual.setMaximumSize(new Dimension(50, 20));
		jpanel.add(textfieldpreciohoraactual);

		JLabel precio_pantalla_Label = new JLabel(" INSERTAR PRECIO DE LA PANTALLA:");
		jpanel.add(precio_pantalla_Label);
		textfieldpreciopantalla = new JTextField(30);
		textfieldpreciopantalla.setMaximumSize(new Dimension(50, 20));
		jpanel.add(textfieldpreciopantalla);
		precio_pantalla_Label.setVisible(false);
		textfieldpreciopantalla.setVisible(false);

		JLabel coevalormul_Label = new JLabel(" INSERTAR EL VALOR MULTIPLICATIVO:");
		jpanel.add(coevalormul_Label);
		textfieldcoevalormul = new JTextField(30);
		textfieldcoevalormul.setMaximumSize(new Dimension(50, 20));
		jpanel.add(textfieldcoevalormul);
		coevalormul_Label.setVisible(false);
		textfieldcoevalormul.setVisible(false);

		JLabel precio_mantenimiento_Label = new JLabel(" INSERTAR PRECIO DE MANTENIMIENTO:");
		jpanel.add(precio_mantenimiento_Label);
		textfieldpreciomantenimiento = new JTextField(30);
		textfieldpreciomantenimiento.setMaximumSize(new Dimension(50, 20));
		jpanel.add(textfieldpreciomantenimiento);
		precio_mantenimiento_Label.setVisible(false);
		textfieldpreciomantenimiento.setVisible(false);

		JLabel coevalordiv_Label = new JLabel(" INSERTAR EL VALOR DIVISIBLE:");
		jpanel.add(coevalordiv_Label);
		textfieldcoevalordiv = new JTextField(30);
		textfieldcoevalordiv.setMaximumSize(new Dimension(50, 20));
		jpanel.add(textfieldcoevalordiv);
		coevalordiv_Label.setVisible(false);
		textfieldcoevalordiv.setVisible(false);

		JLabel tipoLabel = new JLabel(" SELECCIONAR TIPO:");
		jpanel.add(tipoLabel);

		tipoComboBox = new JComboBox<>();
		tipoComboBox.addItem("Introduzca el tipo de la maquina");
		tipoComboBox.addItem("Arcade");
		tipoComboBox.addItem("Recreativa");
		jpanel.add(tipoComboBox);
		tipoComboBox.addActionListener(e -> {
			if (tipoComboBox.getSelectedItem().equals("Arcade")) {
				precio_pantalla_Label.setVisible(true);
				textfieldpreciopantalla.setVisible(true);
				coevalormul_Label.setVisible(true);
				textfieldcoevalormul.setVisible(true);
				textfieldpreciomantenimiento.setVisible(false);
				precio_mantenimiento_Label.setVisible(false);
				coevalordiv_Label.setVisible(false);
				textfieldcoevalordiv.setVisible(false);
			}
			if (tipoComboBox.getSelectedItem().equals("Recreativa")) {
				precio_pantalla_Label.setVisible(false);
				textfieldpreciopantalla.setVisible(false);
				coevalormul_Label.setVisible(false);
				textfieldcoevalormul.setVisible(false);
				textfieldpreciomantenimiento.setVisible(true);
				precio_mantenimiento_Label.setVisible(true);
				coevalordiv_Label.setVisible(true);
				textfieldcoevalordiv.setVisible(true);
			}
			if ("Introduzca el tipo de Maquina".equals(tipoComboBox.getSelectedItem())) {
				precio_pantalla_Label.setVisible(false);
				textfieldpreciopantalla.setVisible(false);
				coevalormul_Label.setVisible(false);
				textfieldcoevalormul.setVisible(false);
				textfieldpreciomantenimiento.setVisible(false);
				precio_mantenimiento_Label.setVisible(false);
				coevalordiv_Label.setVisible(false);
				textfieldcoevalordiv.setVisible(false);
			}
		});
	}

	@Override
	public void update(Context context) {
		if (context.getEvento() == Evento.ALTA_MAQUINA_VISTA) {
			this.setVisible(true);
		} else {
			if (context.getEvento() == Evento.RES_ALTA_MAQUINA_OK) {
				JOptionPane.showMessageDialog(null, "Exito al dar de alta maquina" + " " + (int) context.getDatos());
			} else if (context.getEvento() == Evento.RES_ALTA_MAQUINA_KO) {
				JOptionPane.showMessageDialog(null, "Error al dar de alta maquina.");
			}

			Controller.getInstancia().accion(new Context(Evento.MAQUINA, null));
			this.dispose();
		}
	}
}