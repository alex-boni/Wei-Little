
package Presentacion.Producto;

import javax.swing.JFrame;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Negocio.Producto.TComplemento;
import Negocio.Producto.TProducto;
import Negocio.Producto.TVideojuego;

import javax.swing.JButton;

import java.awt.Dimension;

public class VistaBuscarProducto extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private List<JTextField> jTextField;

	private List<JButton> jButton;

	TProducto tProducto;

	public VistaBuscarProducto() {
		super("[BUSCAR PRODUCTO]");
		jTextField = new ArrayList<JTextField>();
		jButton = new ArrayList<JButton>();
		this.initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(600, 300));

		JPanel jpanel = new JPanel();
		rellenearMainPanel(jpanel, "id");
		JPanel jpanel1 = new JPanel();
		JLabel oLabel = new JLabel("O prefieres:");
		jpanel1.setPreferredSize(new Dimension(600, 25));
		jpanel1.add(oLabel);
		JPanel jpanel2 = new JPanel();
		rellenearMainPanel(jpanel2, "nombre");
		JPanel jaceptar = new JPanel();
		rellenarAceptarPanel(jaceptar);
		JPanel jaceptar2 = new JPanel();
		rellenarAceptarPanel(jaceptar2);
		mainPanel.add(jpanel);
		mainPanel.add(jaceptar);

		activarActionListenerAceptar();

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(false);
	}

	private void activarActionListenerAceptar() {

		jButton.get(0).addActionListener((e) -> {
			try {
				int id = Integer.parseInt(jTextField.get(0).getText());
				if (id > 0) {
					Controller.getInstancia().accion(new Context(Evento.BUSCAR_PRODUCTO, id));
					this.dispose();
				} else
					JOptionPane.showMessageDialog(this, "Por favor, introduzca un id correcto", "Advertencia",
							JOptionPane.WARNING_MESSAGE);
			} catch (NumberFormatException n) {
				JOptionPane.showMessageDialog(this, "Por favor, introduzca un id correcto", "Advertencia",
						JOptionPane.WARNING_MESSAGE);
			}

		});

	}

	private void rellenarAceptarPanel(JPanel jaceptar) {
		JButton aceptar = new JButton("ACEPTAR");
		jButton.add(aceptar);
		jaceptar.add(aceptar);
		JButton cancelar = new JButton("CANCELAR");
		cancelar.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.PRODUCTO, null));
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

	public void update(Context contexto) {

		if (contexto.getEvento() == Evento.VBUSCAR_PRODUCTO) {
			setVisible(true);
		} else {
			if (contexto.getEvento() == Evento.RES_BUSCAR_PRODUCTO_OK) {
				if (contexto.getDatos() instanceof TComplemento) {
					TComplemento tC = (TComplemento) contexto.getDatos();
					String mhtml = "<html><body>" + "<p>Producto encontrado: </p>" + "<p>ID: " + tC.get_id() + "</p>"
							+ "<p>NOMBRE: " + tC.get_nombre() + "</p>" + "<p>MARCA: " + tC.get_marca() + "</p>"
							+ "<p>PESO: " + tC.get_peso() + "</p>" + "<p>ACTIVO: "
							+ (tC.get_activo() == 1 ? "SI" : "NO") + "</p>" + "</body></html>";
					JOptionPane.showMessageDialog(null, mhtml, "Producto Encontrado", JOptionPane.INFORMATION_MESSAGE);
				} else if (contexto.getDatos() instanceof TVideojuego) {
					TVideojuego tV = (TVideojuego) contexto.getDatos();
					String mhtml = "<html><body>" + "<p>Producto encontrado: </p>" + "<p>ID: " + tV.get_id() + "</p>"
							+ "<p>NOMBRE: " + tV.get_nombre() + "</p>" + "<p>MARCA: " + tV.get_marca() + "</p>"
							+ "<p>RESTRICCIÓN DE EDAD: " + tV.get_restriccionEdad() + "</p>" + "<p>ACTIVO: "
							+ (tV.get_activo() == 1 ? "SI" : "NO") + "</p>" + "</body></html>";
					JOptionPane.showMessageDialog(null, mhtml, "Producto Encontrado", JOptionPane.INFORMATION_MESSAGE);
				}

			} else if (contexto.getEvento() == Evento.RES_BUSCAR_PRODUCTO_KO)
				JOptionPane.showMessageDialog(null, "Producto no encontrado en la Base de Datos.");
			Controller.getInstancia().accion(new Context(Evento.PRODUCTO, null));

			this.dispose();
		}
	}

}