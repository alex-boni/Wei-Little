/**
 * 
 */
package Presentacion.ModeloJPA;

import javax.swing.JFrame;
import javax.swing.JTextField;

import Negocio.ModeloJPA.TVinculacionModeloProveedor;

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

public class VistaDesvincularModeloProveedor extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaDesvincularModeloProveedor() {
		super("Desvincular Modelo Proveedor");
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
		idTextField.setPreferredSize(new Dimension(80, 40));
		JLabel idLabel = new JLabel("<html><p>id modelo:</p></html>");
		idLabel.setPreferredSize(new Dimension(60, 40));
		textFieldPanel.add(idLabel);
		textFieldPanel.add(idTextField);

		JTextField idTextField2 = new JTextField();
		idTextField2.setVisible(true);
		idTextField2.setPreferredSize(new Dimension(80, 40));
		JLabel idLabel2 = new JLabel("<html><p>id proveedor:</p></html>");
		idLabel2.setPreferredSize(new Dimension(70, 40));
		textFieldPanel.add(idLabel2);
		textFieldPanel.add(idTextField2);

		JPanel okPanel = new JPanel();
		supPanel.add(okPanel, BorderLayout.PAGE_END);

		JButton ok_button = new JButton("OK");
		ok_button.setVisible(true);
		ok_button.addActionListener((e) -> {

			try {
				int id_modelo = Integer.parseInt(idTextField.getText());
				int id_proveedor = Integer.parseInt(idTextField2.getText());
				Controller.getInstancia().accion(new Context(Evento.DESVINCULAR_PROVEEDOR_MODELO_NEGOCIO,
						new TVinculacionModeloProveedor(id_modelo, id_proveedor)));
				dispose();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Error: El campo no puede estar vacio o ser letras");
			}
		});
		okPanel.add(ok_button);
	}

	@Override
	public void update(Context context) {
		if (context.getEvento() == Evento.DESVINCULAR_PROVEEDOR_MODELO_VISTA)
			this.setVisible(true);
		else {
			if (context.getEvento() == Evento.RES_DESVINCULAR_PROVEEDOR_MODELO_OK) {
				TVinculacionModeloProveedor vinculacion = (TVinculacionModeloProveedor) context.getDatos();
				int id_modelo = vinculacion.get_id_model();
				int id_proveedor = vinculacion.get_id_provider();
				JOptionPane.showMessageDialog(null,
						String.format("Exito al desvincular el modelo %d del proveedor %d.", id_modelo, id_proveedor));
			} else if (context.getEvento() == Evento.RES_DESVINCULAR_PROVEEDOR_MODELO_KO)
				JOptionPane.showMessageDialog(null, "Error al desvincular el proveedor del modelo. ");
			Controller.getInstancia().accion(new Context(Evento.MODELO, null));
			this.dispose();
		}
	}
}