package Presentacion.ProductoEnPlataforma;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class VCalcularCantidadProductoEnPlataforma extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldEdad;

	public VCalcularCantidadProductoEnPlataforma() {
		super("[CALCULAR CANTIDAD DE PRODUCTO EN PLATAFORMA]");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		JLabel label = new JLabel("Introduzca la edad buscada en Productos en la Plataforma:");
		mainPanel.add(label);

		textFieldEdad = new JTextField();
		textFieldEdad.setMaximumSize(new Dimension(450, 20));
		mainPanel.add(textFieldEdad);

		JButton buttonCalcular = new JButton("Calcular cantidad");
		buttonCalcular.addActionListener((e) -> onCalcularClicked());
		mainPanel.add(buttonCalcular);

		mainPanel.add(new JSeparator());

		this.add(mainPanel);
		this.setSize(500, 200);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}

	private void onCalcularClicked() {
		try {
			int edad = Integer.parseInt(textFieldEdad.getText());
			if (edad < 0) {
				throw new Exception("La edad no puede ser menor que 0");
			}
			Controller.getInstancia().accion(new Context(Evento.CALCULAR_CANTIDAD_PRODUCTO_EN_PLATAFORMA, edad));
			dispose();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Datos incorrectos. Por favor, introduzca un número válido.", "ERROR",
					JOptionPane.ERROR_MESSAGE);
		}

		catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
		}
	}

	@Override
	public void update(Context context) {
		if (context.getEvento() == Evento.VCALCULAR_CANTIDAD_PRODUCTO_EN_PLATAFORMA) {
			this.setVisible(true);
		} else {
			if (context.getEvento() == Evento.RES_CALCULAR_CANTIDAD_PRODUCTO_EN_PLATAFORMA_OK) {
				JOptionPane.showMessageDialog(this, "Cantidad de producto en plataforma: " + context.getDatos(),
						"Resultado", JOptionPane.INFORMATION_MESSAGE);
			} else if (context.getEvento() == Evento.RES_CALCULAR_CANTIDAD_PRODUCTO_EN_PLATAFORMA_KO) {
				JOptionPane.showMessageDialog(this, "No se ha podido calcular la cantidad de producto en plataforma.",
						"Error", JOptionPane.ERROR_MESSAGE);
			}

			Controller.getInstancia().accion(new Context(Evento.PRODUCTO_EN_PLATAFORMA, null));
			this.dispose();
		}
	}

}
