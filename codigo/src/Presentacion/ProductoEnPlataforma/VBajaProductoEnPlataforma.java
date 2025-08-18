package Presentacion.ProductoEnPlataforma;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VBajaProductoEnPlataforma extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private List<JTextField> jTextField;
	private List<JButton> jButton;

	private int id;

	public VBajaProductoEnPlataforma() {
		super("[BAJA PRODUCTO EN PLATAFORMA]");
		jTextField = new ArrayList<>();
		jButton = new ArrayList<>();
		initGUI();
	}

	private void initGUI() {

		JPanel mainPanel = new JPanel();
		JPanel jaceptar = new JPanel();
		JPanel jpanel = new JPanel();

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		setContentPane(mainPanel);
		setPreferredSize(new Dimension(600, 300));

		rellenearMainPanel(jpanel, "id");
		rellenarAceptarPanel(jaceptar);
		activarActionListenerAceptar();

		mainPanel.add(jpanel);
		mainPanel.add(jaceptar);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void activarActionListenerAceptar() {
		jButton.get(0).addActionListener((e) -> {
			try {

				if (jTextField.get(0).getText().isEmpty()) {
					throw new Exception("ID no puede estar vacio");
				}

				id = Integer.parseInt(jTextField.get(0).getText());

				if (id <= 0) {
					throw new Exception("ID no puede ser negativo ni 0");
				}

				Controller.getInstancia().accion(new Context(Evento.BAJA_PRODUCTO_PLATAFORMA, (Object) id));

				dispose();
			} catch (NumberFormatException ile) {
				JOptionPane.showMessageDialog(null, "ERROR: Debe introducir caracteres numericos");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
			}

		});
	}

	private void rellenarAceptarPanel(JPanel jaceptar) {

		JButton aceptar = new JButton("ACEPTAR");

		jButton.add(aceptar);
		jaceptar.add(aceptar);

		JButton cancelar = new JButton("CANCELAR");
		cancelar.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.PRODUCTO_EN_PLATAFORMA, null));
			dispose();
		});

		jaceptar.add(cancelar);
	}

	private void rellenearMainPanel(JPanel jpanel, String palabra) {

		JLabel pLabel = new JLabel("Dar de Baja por " + palabra + ":");
		JTextField pTextField = new JTextField(10);

		jpanel.add(pLabel);
		jpanel.add(pTextField);

		pTextField.setMaximumSize(new Dimension(100, 40));

		jTextField.add(pTextField);
	}

	@Override
	public void update(Context context) {
		if (context.getEvento() == Evento.VBAJA_PRODUCTO_PLATAFORMA) {
			this.setVisible(true);
		} else {
			if (context.getEvento() == Evento.RES_BAJA_PRODUCTO_PLATAFORMA_OK) {
				JOptionPane.showMessageDialog(null, "Exito al dar de baja producto en plataforma.");
			} else if (context.getEvento() == Evento.RES_BAJA_PRODUCTO_PLATAFORMA_KO) {
				JOptionPane.showMessageDialog(null, "Error al dar de baja producto en plataforma.");
			}

			Controller.getInstancia().accion(new Context(Evento.PRODUCTO_EN_PLATAFORMA, null));
			this.dispose();
		}
	}
}
