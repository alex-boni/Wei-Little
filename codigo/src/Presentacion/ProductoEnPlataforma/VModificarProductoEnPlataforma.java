package Presentacion.ProductoEnPlataforma;

import Negocio.ProductoEnPlataforma.TProductoEnPlataforma;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VModificarProductoEnPlataforma extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField textoID;
	private JTextField textoPrecio;
	private JTextField textoCantidad;
	private DefaultComboBoxModel<String> activo;
	private JComboBox<String> comboActivo;

	private TProductoEnPlataforma tprodenplat;

	public VModificarProductoEnPlataforma() {
		super("[MODIFICAR PRODUCTO EN PLATAFORMA]");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(900, 300));

		JPanel jpanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		rellenearMainPanel(jpanel, "id");

		JPanel jaceptar = new JPanel();
		rellenarAceptarPanel(jaceptar);

		mainPanel.add(jpanel);
		mainPanel.add(jaceptar);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void rellenarAceptarPanel(JPanel jaceptar) {
		JButton aceptar = new JButton("ACEPTAR");
		aceptar.addActionListener((e) -> {
			try {
				tprodenplat = new TProductoEnPlataforma();

				if (textoID.getText().isEmpty()) {
					throw new Exception("ID no puede estar vacio");
				}
				if (textoPrecio.getText().isEmpty()) {
					throw new Exception("Precio no puede estar vacio");
				}
				if (textoCantidad.getText().isEmpty()) {
					throw new Exception("Cantidad no puede estar vacia");
				}

				tprodenplat.set_id(Integer.parseInt(textoID.getText()));
				tprodenplat.set_cantidad(Integer.parseInt(textoCantidad.getText()));
				tprodenplat.set_precio(Double.parseDouble(textoPrecio.getText()));
				tprodenplat.set_activo(comboActivo.getSelectedItem().toString().equals("SI") ? 1 : 0);

				if (tprodenplat.get_id() <= 0) {
					throw new Exception("ID no puede ser negativo ni 0");
				}
				if (tprodenplat.get_precio() < 0) {
					throw new Exception("Precio no puede ser negativo");
				}
				if (tprodenplat.get_cantidad() < 0) {
					throw new Exception("Cantidad no puede ser negativa");
				}

				Controller.getInstancia().accion(new Context(Evento.MODIFICAR_PRODUCTO_PLATAFORMA, tprodenplat));
				this.dispose();
			} catch (NumberFormatException ile) {
				JOptionPane.showMessageDialog(null, "ERROR: Debe introducir caracteres numericos");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
			}

		});
		jaceptar.add(aceptar);

		JButton cancelar = new JButton("CANCELAR");
		cancelar.addActionListener((e) -> {

			Controller.getInstancia().accion(new Context(Evento.PRODUCTO_EN_PLATAFORMA, null));
			dispose();
		});
		jaceptar.add(cancelar);
	}

	private void rellenearMainPanel(JPanel jpanel, String palabra) {

		jpanel.setLayout(new BoxLayout(jpanel, BoxLayout.Y_AXIS));
		JPanel psuperior = new JPanel();
		JPanel pinferior = new JPanel();
		JLabel pLabel = new JLabel("Modificar por " + palabra + ":");
		JLabel p2Label = new JLabel("Insertar precio nuevo :");
		JLabel p3Label = new JLabel("Insertar cantidad nueva:");
		JLabel p4Label = new JLabel("Activo: ");

		psuperior.add(pLabel);

		textoID = new JTextField(10);
		textoID.setMaximumSize(new Dimension(100, 40));

		psuperior.add(textoID);

		jpanel.add(psuperior);

		pinferior.add(p2Label);

		textoPrecio = new JTextField(10);
		textoPrecio.setMaximumSize(new Dimension(100, 40));

		pinferior.add(textoPrecio);
		pinferior.add(p3Label);

		textoCantidad = new JTextField(10);
		textoCantidad.setMaximumSize(new Dimension(100, 40));

		pinferior.add(textoCantidad);
		pinferior.add(p4Label);

		activo = new DefaultComboBoxModel<>();
		activo.addElement("SI");
		activo.addElement("NO");

		comboActivo = new JComboBox<>(activo);
		comboActivo.setMaximumSize(new Dimension(100, 40));

		pinferior.add(comboActivo);
		jpanel.add(pinferior);
	}

	@Override
	public void update(Context context) {
		if (context.getEvento() == Evento.VMODIFICAR_PRODUCTO_PLATAFORMA) {
			this.setVisible(true);
		} else {
			if (context.getEvento() == Evento.RES_MODIFICAR_PRODUCTO_PLATAFORMA_OK) {
				JOptionPane.showMessageDialog(null, "Exito al modificar el producto en plataforma.");
			} else if (context.getEvento() == Evento.RES_MODIFICAR_PRODUCTO_PLATAFORMA_KO) {
				JOptionPane.showMessageDialog(null, "Error al modificar el producto en plataforma.");
			}

			Controller.getInstancia().accion(new Context(Evento.PRODUCTO_EN_PLATAFORMA, null));
			this.dispose();
		}
	}

}
