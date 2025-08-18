/**
 * 
 */
package Presentacion.ModeloJPA;

import javax.swing.JFrame;
import javax.swing.JTextField;
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

public class VistaCalcularSeguroModelo extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;

	public VistaCalcularSeguroModelo() {
		super("Calcular Seguro Modelo");
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
				Controller.getInstancia().accion(new Context(Evento.CALCULAR_COSTE_SEGURO_MODELO_NEGOCIO, id));
				this.dispose();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Error: El campo no puede estar vacio o ser letras");
			}
		});
		okPanel.add(ok_button);

	}

	@Override
	public void update(Context context) {
		if (context.getEvento() == Evento.CALCULAR_COSTE_SEGURO_MODELO_VISTA)
			this.setVisible(true);
		else {
			if (context.getEvento() == Evento.RES_CALCULAR_COSTE_SEGURO_MODELO_OK)
				JOptionPane.showMessageDialog(null,
						"Seguro calculado del modelo. Coste total: " + (double) context.getDatos());
			else if (context.getEvento() == Evento.RES_CALCULAR_COSTE_SEGURO_MODELO_KO)
				JOptionPane.showMessageDialog(null, "Error al calcular el coste del seguro del modelo. ");
			Controller.getInstancia().accion(new Context(Evento.MODELO, null));
			this.dispose();
		}
	}
}