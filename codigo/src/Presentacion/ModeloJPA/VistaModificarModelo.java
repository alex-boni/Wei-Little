/**
 * 
 */
package Presentacion.ModeloJPA;

import javax.swing.JFrame;
import javax.swing.JTextField;

import Negocio.ModeloJPA.TModelo;

import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

public class VistaModificarModelo extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaModificarModelo() {
		super("Modificar Modelo");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.setContentPane(mainPanel);

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

	private void rellenarPanelInferior(JPanel bottomPanel) {
		JButton cancelButton = new JButton("CANCEL");
		cancelButton.setToolTipText("cancel");
		cancelButton.setVisible(true);
		cancelButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.MODELO, null));
			this.dispose();
		});
		bottomPanel.add(cancelButton);
	}

	private void rellenarPanelSuperior(JPanel supPanel) {

		JPanel textFieldPanel = new JPanel();
		supPanel.add(textFieldPanel, BorderLayout.PAGE_START);

		// id
		JTextField idTextField = new JTextField();
		idTextField.setVisible(true);
		idTextField.setPreferredSize(new Dimension(80, 20));
		JLabel idLabel = new JLabel("<html><p>id:</p></html>");
		idLabel.setPreferredSize(new Dimension(50, 20));
		textFieldPanel.add(idLabel);
		textFieldPanel.add(idTextField);

		// nombre
		JTextField nombreTextField = new JTextField();
		nombreTextField.setVisible(true);
		nombreTextField.setPreferredSize(new Dimension(80, 20));
		JLabel nombreLabel = new JLabel("<html><p>Nombre:</p></html>");
		nombreLabel.setPreferredSize(new Dimension(50, 20));
		textFieldPanel.add(nombreLabel);
		textFieldPanel.add(nombreTextField);

		JPanel okPanel = new JPanel();
		supPanel.add(okPanel, BorderLayout.PAGE_END);

		JButton ok_button = new JButton("OK");
		ok_button.setVisible(true);
		ok_button.addActionListener((e) -> {

			try {
				int id = Integer.parseInt(idTextField.getText());
				String nombre = nombreTextField.getText();
				if (checkInteger(nombre))
					JOptionPane.showMessageDialog(null, "Error: El nombre no puede ser numeros");
				else {
					TModelo tModelo = new TModelo();
					tModelo.set_nombre(nombre);
					tModelo.set_id(id);

					Controller.getInstancia().accion(new Context(Evento.MODIFICAR_MODELO_NEGOCIO, tModelo));
					this.dispose();
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Error: No puede haber campos vacios y el id debe ser numerico");
			}
		});
		okPanel.add(ok_button);

	}

	private boolean checkInteger(String nombre) {

		try {
			Double.parseDouble(nombre);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	@Override
	public void update(Context context) {

		if (context.getEvento() == Evento.MODIFICAR_MODELO_VISTA)
			this.setVisible(true);
		else {
			if (context.getEvento() == Evento.RES_MODIFICAR_MODELO_OK) {
				TModelo mod = (TModelo) context.getDatos();

				String mhtml = "<html><body>" + "<p>Modelo encontrado: </p>" + "<p>ID: " + mod.get_id() + " </p>"
						+ "<p>NOMBRE NUEVO: " + mod.get_nombre() + "</p>" + "</body></html>";

				JOptionPane.showMessageDialog(null, mhtml, "Modelo Modificado", JOptionPane.INFORMATION_MESSAGE);
			} else if (context.getEvento() == Evento.RES_MODIFICAR_MODELO_KO)
				JOptionPane.showMessageDialog(null, "Error al modificar modelo. ");
			Controller.getInstancia().accion(new Context(Evento.MODELO, null));
			this.dispose();
		}
	}
}