/**
 * 
 */
package Presentacion.ProveedorJPA;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Negocio.ProveedorJPA.TProveedor;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import javax.swing.JButton;

import java.awt.Dimension;
import javax.swing.JPanel;

public class VistaBuscarProveedor extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField pTextField;

	public VistaBuscarProveedor() {
		super("[BUSCAR PROVEEDOR]");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
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
		setVisible(true);
	}

	private void rellenarAceptarPanel(JPanel jaceptar) {
		JButton aceptar = new JButton("ACEPTAR");
		aceptar.addActionListener((e) -> {
			try {
				int id = Integer.parseInt(pTextField.getText());

				if (id <= 0) {
					throw new Exception("Debes introducir un número mayor que 0.");
				}

				Controller.getInstancia().accion(new Context(Evento.BUSCAR_PROVEEDOR_NEGOCIO, id));
				this.dispose();

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "ERROR: Debes introducir un id numérico válido.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
			}
		});
		jaceptar.add(aceptar);

		JButton cancelar = new JButton("CANCELAR");
		cancelar.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.PROVEEDOR, null));
			this.dispose();
		});
		jaceptar.add(cancelar);
	}

	private void rellenearMainPanel(JPanel jpanel, String string) {
		JLabel pLabel = new JLabel("Buscar por " + string + ":");
		jpanel.add(pLabel);
		pTextField = new JTextField(10);
		pTextField.setMaximumSize(new Dimension(100, 40));
		jpanel.add(pTextField);
	}

	@Override
	public void update(Context contexto) {

		if (contexto.getEvento() == Evento.BUSCAR_PROVEEDOR_VISTA) {
			this.setVisible(true);
		} else {
			if (contexto.getEvento() == Evento.RES_BUSCAR_PROVEEDOR_OK) {
				TProveedor th = (TProveedor) contexto.getDatos();
				String mhtml = "<html><body>" + "<p>Proveedor encontrado: </p>" + "<p>ID: " + th.get_id_proveedor()
						+ " </p>" + "<p>NOMBRE: " + th.get_nombre() + "</p>" + "<p>CIF: " + th.get_CIF() + "</p>"
						+ "<p>ACTIVO: " + (th.get_activo() == 1 ? "1" : "0") + "</p>" + "</body></html>";
				JOptionPane.showMessageDialog(null, mhtml, "Proveedor Encontrado", JOptionPane.INFORMATION_MESSAGE);

			} else if (contexto.getEvento() == Evento.RES_BUSCAR_PROVEEDOR_KO) {
				JOptionPane.showMessageDialog(null, "Proveedor no encontrado en la Base de Datos.");
			}
			Controller.getInstancia().accion(new Context(Evento.PROVEEDOR, null));
			this.dispose();
		}

	}
}