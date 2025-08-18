/**
 * 
 */
package Presentacion.ProveedorJPA;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

import Negocio.ProveedorJPA.TProveedor;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class VistaAltaProveedor extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	JTextField idTextField;
	JTextField nombreTextField;
	JTextField CIFTextField;

	public VistaAltaProveedor() {
		super("[ALTA PROVEEDOR]");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(600, 300));

		JPanel jPanel = new JPanel();
		rellenarMainPanel(jPanel);
		mainPanel.add(jPanel, BorderLayout.NORTH);

		JPanel jAceptar = new JPanel();
		rellenarAceptarPanel(jAceptar);
		mainPanel.add(jAceptar, BorderLayout.SOUTH);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void rellenarAceptarPanel(JPanel jAceptar) {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

		JButton aceptar = new JButton("ACEPTAR");
		aceptar.addActionListener((e) -> {
			try {
				TProveedor t_proveedor = new TProveedor();
				String nombre = nombreTextField.getText().toUpperCase();
				if (!checkNombre(nombre))
					throw new Exception(
							"Formato incorrecto en el nombre, no puede estar vacío y debe ser combinacion alfanumerica");

				String CIF = CIFTextField.getText().toUpperCase();
				if (!checkCIF(CIF)) {
					throw new Exception("Formato incorrecto en el CIF, debería tener formato 12345678A");
				}

				t_proveedor.set_nombre(nombre);
				t_proveedor.set_CIF(CIF);
				t_proveedor.set_activo(1);
				Controller.getInstancia().accion(new Context(Evento.ALTA_PROVEEDOR_NEGOCIO, (Object) t_proveedor));
				this.dispose();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
			}

		});

		JButton cancelar = new JButton("CANCELAR");
		cancelar.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.PROVEEDOR, null));
			this.dispose();
		});

		buttonPanel.add(aceptar);
		buttonPanel.add(cancelar);

		jAceptar.add(buttonPanel, BorderLayout.PAGE_END);
	}

	private void rellenarMainPanel(JPanel jPanel) {
		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

		JLabel nombreLabel = new JLabel("INSERTAR NOMBRE: ");
		nombreTextField = new JTextField(30);
		nombreTextField.setMaximumSize(new Dimension(100, 40));
		JPanel nombrePanel = new JPanel();
		nombrePanel.setLayout(new BoxLayout(nombrePanel, BoxLayout.X_AXIS));
		nombrePanel.add(nombreLabel);
		nombrePanel.add(nombreTextField);
		jPanel.add(nombrePanel);

		JLabel nivelLabel = new JLabel("INSERTAR CIF: ");
		CIFTextField = new JTextField(30);
		CIFTextField.setMaximumSize(new Dimension(100, 40));
		JPanel nivelPanel = new JPanel();
		nivelPanel.setLayout(new BoxLayout(nivelPanel, BoxLayout.X_AXIS));
		nivelPanel.add(nivelLabel);
		nivelPanel.add(CIFTextField);
		jPanel.add(nivelPanel);

	}

	public void update(Context context) {
		if (context.getEvento() == Evento.ALTA_PROVEEDOR_VISTA)
			this.setVisible(true);
		else {
			if (context.getEvento() == Evento.RES_ALTA_PROVEEDOR_OK)
				JOptionPane.showMessageDialog(null,
						"Exito al dar de alta al proveedor. Id: " + (int) context.getDatos());
			else if (context.getEvento() == Evento.RES_ALTA_PROVEEDOR_KO)
				JOptionPane.showMessageDialog(null, "Error al dar de alta al proveedor. ");
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