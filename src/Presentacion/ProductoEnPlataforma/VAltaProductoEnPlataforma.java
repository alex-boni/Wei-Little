package Presentacion.ProductoEnPlataforma;

import Negocio.ProductoEnPlataforma.TProductoEnPlataforma;
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
import javax.swing.JTextField;

public class VAltaProductoEnPlataforma extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField textfieldprecio;
	private JTextField textfieldcantidad;
	private JTextField textfieldidplat;
	private JTextField textfieldidprod;
	private TProductoEnPlataforma tprodenplat;

	public VAltaProductoEnPlataforma() {
		super("[ALTA PRODUCTO EN PLATAFORMA]");
		initGUI();
	}

	private void initGUI() {

		JPanel mainPanel = new JPanel();
		JPanel jaceptar = new JPanel();
		JPanel jpanel = new JPanel();

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		this.setContentPane(mainPanel);

		this.setPreferredSize(new Dimension(600, 300));

		rellenearMainPanel(jpanel);
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
			tprodenplat = new TProductoEnPlataforma();

			Double precio = null;
			Integer cantidad, idProducto, idPlataforma = null;

			try {

				if (textfieldprecio.getText().isEmpty()) {
					throw new Exception("Precio no puede estar vacio");
				}
				if (textfieldcantidad.getText().isEmpty()) {
					throw new Exception("Cantidad no puede estar vacia");
				}
				if (textfieldidprod.getText().isEmpty()) {
					throw new Exception("Id_producto no puede estar vacio");
				}
				if (textfieldidplat.getText().isEmpty()) {
					throw new Exception("Id_plataforma no puede estar vacio");
				}

				precio = Double.valueOf(textfieldprecio.getText());
				cantidad = Integer.valueOf(textfieldcantidad.getText());
				idProducto = Integer.valueOf(textfieldidprod.getText());
				idPlataforma = Integer.valueOf(textfieldidplat.getText());

				if (precio < 0) {
					throw new Exception("El precio no puede ser negativo");
				}
				if (cantidad < 0) {
					throw new Exception("La cantidad no puede ser negativa");
				}
				if (idProducto <= 0) {
					throw new Exception("El id_producto no puede ser negativo o cero");
				}

				if (idPlataforma <= 0) {
					throw new Exception("El id_plataforma no puede ser negativo o cero");
				}

				tprodenplat.colocar_datos(1, precio, cantidad, idProducto, idPlataforma);

				Controller.getInstancia().accion(new Context(Evento.ALTA_PRODUCTO_PLATAFORMA, (Object) tprodenplat));

				dispose();
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
			this.dispose();
		});

		jaceptar.add(cancelar);
	}

	private void rellenearMainPanel(JPanel jpanel) {

		JLabel precioLabel = new JLabel("INSERTAR PRECIO:");
		JLabel cantidadLabel = new JLabel("INSERTAR CANTIDAD:");
		JLabel idplatlabel = new JLabel("INSERTAR ID_PLATAFORMA:");
		JLabel idprodlabel = new JLabel("INSERTAR ID_PRODUCTO:");

		precioLabel.setPreferredSize(new Dimension(200, 20));
		cantidadLabel.setPreferredSize(new Dimension(200, 20));
		idplatlabel.setPreferredSize(new Dimension(200, 20));
		idprodlabel.setPreferredSize(new Dimension(200, 20));

		JPanel cantidadPanel = new JPanel();
		JPanel precioPanel = new JPanel();
		JPanel idPlatPanel = new JPanel();
		JPanel idProdPanel = new JPanel();

		jpanel.setLayout(new BoxLayout(jpanel, BoxLayout.Y_AXIS));

		textfieldprecio = new JTextField();
		textfieldprecio.setPreferredSize(new Dimension(200, 20));

		precioPanel.add(precioLabel);
		precioPanel.add(textfieldprecio);

		textfieldcantidad = new JTextField();
		textfieldcantidad.setPreferredSize(new Dimension(200, 20));

		cantidadPanel.add(cantidadLabel);
		cantidadPanel.add(textfieldcantidad);

		textfieldidplat = new JTextField();
		textfieldidplat.setPreferredSize(new Dimension(200, 20));

		idPlatPanel.add(idplatlabel);
		idPlatPanel.add(textfieldidplat);

		textfieldidprod = new JTextField();
		textfieldidprod.setPreferredSize(new Dimension(200, 20));

		idProdPanel.add(idprodlabel);
		idProdPanel.add(textfieldidprod);

		jpanel.add(precioPanel);
		jpanel.add(cantidadPanel);
		jpanel.add(idProdPanel);
		jpanel.add(idPlatPanel);
	}

	@Override
	public void update(Context context) {
		if (context.getEvento() == Evento.VALTA_PRODUCTO_PLATAFORMA) {
			this.setVisible(true);
		} else {
			if (context.getEvento() == Evento.RES_ALTA_PRODUCTO_PLATAFORMA_OK) {
				JOptionPane.showMessageDialog(null, "Exito al dar de alta producto en plataforma");
			} else if (context.getEvento() == Evento.RES_ALTA_PRODUCTO_PLATAFORMA_KO) {
				JOptionPane.showMessageDialog(null, "Error al dar de alta producto en plataforma.");
			}

			Controller.getInstancia().accion(new Context(Evento.PRODUCTO_EN_PLATAFORMA, null));
			this.dispose();
		}
	}

}
