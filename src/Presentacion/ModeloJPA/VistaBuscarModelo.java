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

public class VistaBuscarModelo extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaBuscarModelo() {
		super("Buscar Modelo");
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

		JTextField idTextField = new JTextField();
		idTextField.setVisible(true);
		idTextField.setPreferredSize(new Dimension(80, 20));
		JLabel idLabel = new JLabel("<html><p>id:</p></html>");
		idLabel.setPreferredSize(new Dimension(50, 20));
		textFieldPanel.add(idLabel);
		textFieldPanel.add(idTextField);

		JPanel okPanel = new JPanel();
		supPanel.add(okPanel, BorderLayout.PAGE_END);

		JButton ok_button = new JButton("OK");
		ok_button.setVisible(true);
		ok_button.addActionListener((e) -> {

			try {

				int id = Integer.parseInt(idTextField.getText());

				if (id < 1)
					throw new NumberFormatException();

				Controller.getInstancia().accion(new Context(Evento.BUSCAR_MODELO_NEGOCIO, id));
				this.dispose();
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Error: El id no puede ser 0 o negativo");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Error: El campo no puede estar vacio o ser letras");
			}
		});
		okPanel.add(ok_button);

	}

	@Override
	public void update(Context context) {
		if (context.getEvento() == Evento.BUSCAR_MODELO_VISTA)
			this.setVisible(true);
		else {
			if (context.getEvento() == Evento.RES_BUSCAR_MODELO_OK) {

				TModelo mod = (TModelo) context.getDatos();

				String mhtml = "<html><body>" + "<p>Modelo encontrado: </p>" + "<p>ID: " + mod.get_id() + " </p>"
						+ "<p>NOMBRE: " + mod.get_nombre() + "</p>" + "<p>ACTIVO: "
						+ (mod.get_activo() == 1 ? "SI" : "NO") + "</p>" + "</body></html>";

				JOptionPane.showMessageDialog(null, mhtml, "Modelo Encontrado", JOptionPane.INFORMATION_MESSAGE);

			} else if (context.getEvento() == Evento.RES_BUSCAR_MODELO_KO)
				JOptionPane.showMessageDialog(null, "Error al buscar Modelo. ");

			Controller.getInstancia().accion(new Context(Evento.MODELO, null));
			dispose();
		}

	}

}