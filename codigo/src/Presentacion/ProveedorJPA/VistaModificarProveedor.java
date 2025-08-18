/**
 * 
 */
package Presentacion.ProveedorJPA;

import javax.swing.JFrame;

import javax.swing.JPanel;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JTextField;

import Negocio.ProveedorJPA.TProveedor;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class VistaModificarProveedor extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	JTextField idTextField;
	JTextField nombreTextField;
	JTextField CIFTextField;

	public VistaModificarProveedor() {
		super("[MODIFICAR PROVEEDOR]");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(1000, 300));

		JPanel jpanel = new JPanel();
		rellenearMainPanel(jpanel, "id");
		JPanel jpanel1 = new JPanel();
		jpanel1.setPreferredSize(new Dimension(1000, 25));
		JPanel jaceptar = new JPanel();
		rellenarAceptarPanel(jaceptar);
		mainPanel.add(jpanel);
		mainPanel.add(jaceptar);
		mainPanel.add(jpanel1);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void rellenarAceptarPanel(JPanel jaceptar) {
		JButton aceptar = new JButton("ACEPTAR");
		aceptar.addActionListener((e) -> {
			try {
				TProveedor t_proveedor = new TProveedor();
				int id = Integer.parseInt(idTextField.getText());
				if (id <= 0) {
					throw new Exception("Debes introducir un id númerico mayor que 0.");
				}
				t_proveedor.set_id_proveedor(id);

				if (!checkNombre(nombreTextField.getText().toUpperCase()))
					throw new Exception(
							"Formato incorrecto en el nombre, no puede estar vacío y debe ser combinacion alfanumerica");

				t_proveedor.set_nombre(nombreTextField.getText().toUpperCase());

				if (!checkCIF(CIFTextField.getText().toUpperCase()))
					throw new Exception("Formato incorrecto en el CIF, debería tener formato 12345678A");

				t_proveedor.set_CIF(CIFTextField.getText().toUpperCase());

				Controller.getInstancia().accion(new Context(Evento.MODIFICAR_PROVEEDOR_NEGOCIO, (Object) t_proveedor));
				this.dispose();

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null,
						"ERROR: Debes introducir un id numérico válido o el activo númerico.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
			}
		});
		jaceptar.add(aceptar);

		// cancelar button
		JButton cancelar = new JButton("CANCELAR");
		cancelar.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.PROVEEDOR, null));
			this.dispose();
		});
		jaceptar.add(cancelar);
	}

	private void rellenearMainPanel(JPanel jpanel, String string) {
		JLabel pLabel = new JLabel("Modificar por " + string + ":");
		jpanel.add(pLabel);
		idTextField = new JTextField(10);
		idTextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(idTextField);
		JLabel p2Label = new JLabel("Insertar nombre nuevo :");
		jpanel.add(p2Label);
		nombreTextField = new JTextField(10);
		nombreTextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(nombreTextField);
		JLabel p4Label = new JLabel("Insertar nuevo CIF :");
		jpanel.add(p4Label);
		CIFTextField = new JTextField(10);
		CIFTextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(CIFTextField);
	}

	@Override
	public void update(Context contexto) {

		if (contexto.getEvento() == Evento.MODIFICAR_PROVEEDOR_VISTA) {
			this.setVisible(true);
		} else {
			if (contexto.getEvento() == Evento.RES_MODIFICAR_PROVEEDOR_OK) {
				JOptionPane.showMessageDialog(null, "Exito al modificar el proveedor. ");
			} else if (contexto.getEvento() == Evento.RES_MODIFICAR_PROVEEDOR_KO)
				JOptionPane.showMessageDialog(null,
						"Error inesperado al modificar. Compruebe datos: CIF no repetido y proveedor activo.");

			Controller.getInstancia().accion(new Context(Evento.PROVEEDOR, null));
			this.dispose();

		}

	}

	boolean checkNombre(String s) {

		if (s.length() == 0) {
			return false;
		}
		try {
			Integer.parseInt(s);
			return false;
		} catch (NumberFormatException e) {
			return true;
		}

	}

	boolean checkCIF(String CIF) {

		if (CIF.length() != 9) {
			return false;
		}

		for (int i = 0; i < 8; i++) {
			if (!Character.isDigit(CIF.charAt(i))) {
				return false;
			}
		}

		if (CIF.charAt(8) < 'A' || CIF.charAt(8) > 'Z') {
			return false;
		}

		return true;
	}

}