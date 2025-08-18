package Presentacion.ProductoEnPlataforma;

import Negocio.ProductoEnPlataforma.TProductoEnPlataforma;
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

public class VBuscarProductoEnPlataforma extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private List<JButton> jButton;
	private List<JTextField> jTextField;
	private TProductoEnPlataforma tprodenplat;

	public VBuscarProductoEnPlataforma() {
		super("[BUSCAR PRODUCTO EN PLATAFORMA]");
		jTextField = new ArrayList<>();
		jButton = new ArrayList<>();
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(600, 300));

		JPanel jpanel = new JPanel();
		rellenearMainPanel(jpanel, "id");

		JPanel jaceptar = new JPanel();
		rellenarAceptarPanel(jaceptar);

		mainPanel.add(jpanel);
		mainPanel.add(jaceptar);

		activarActionListenerAceptar();

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void activarActionListenerAceptar() {

		jButton.get(0).addActionListener((e) -> {
			try {
				tprodenplat = new TProductoEnPlataforma();

				if (jTextField.get(0).getText().isEmpty()) {
					throw new Exception("ID no puede estar vacio");
				}

				tprodenplat.set_id(Integer.parseInt(jTextField.get(0).getText()));

				if (tprodenplat.get_id() <= 0) {
					throw new Exception("ID no puede ser menor o igual que 0");
				}

				Controller.getInstancia().accion(
						new Context(Evento.BUSCAR_PRODUCTO_PLATAFORMA, Integer.valueOf(jTextField.get(0).getText())));
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
			this.dispose();
		});
		jaceptar.add(cancelar);
	}

	private void rellenearMainPanel(JPanel jpanel, String palabra) {
		JLabel pLabel = new JLabel("Buscar por " + palabra + ":");
		jpanel.add(pLabel);
		JTextField pTextField = new JTextField(10);
		pTextField.setMaximumSize(new Dimension(100, 40));
		jTextField.add(pTextField);
		jpanel.add(pTextField);

	}

	@Override
	public void update(Context context) {
		if (context.getEvento() == Evento.VBUSCAR_PRODUCTO_PLATAFORMA) {
			this.setVisible(true);
		} else {
			if (context.getEvento() == Evento.RES_BUSCAR_PRODUCTO_PLATAFORMA_OK) {
				TProductoEnPlataforma tp = (TProductoEnPlataforma) context.getDatos();
				String mhtml = "<html><body>" + "<p>Producto en Plataforma encontrado: </p>" + "<p>ID: " + tp.get_id()
						+ " </p>" + "<p>ID_PRODUCTO: " + tp.get_id_producto() + "</p>" + "<p>ID_PLATAFORMA: "
						+ tp.get_id_plataforma() + "</p>" + "<p>PRECIO: " + tp.get_precio() + "</p>" + "<p>CANTIDAD: "
						+ tp.get_cantidad() + "</p>" + "<p>ACTIVO: " + tp.get_activo() + "</p>" + "</body></html>";

				JOptionPane.showMessageDialog(null, mhtml, "Producto en Plataforma encontrado",
						JOptionPane.INFORMATION_MESSAGE);
			} else if (context.getEvento() == Evento.RES_BUSCAR_PRODUCTO_PLATAFORMA_KO) {
				JOptionPane.showMessageDialog(null, "Producto en Plataforma no encontrado en la Base de Datos.");
			}

			Controller.getInstancia().accion(new Context(Evento.PRODUCTO_EN_PLATAFORMA, null));
			this.dispose();
		}
	}
}
