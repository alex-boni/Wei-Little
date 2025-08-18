package Presentacion.Producto;

import javax.swing.JFrame;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.util.Set;
import javax.swing.JPanel;

import Negocio.Producto.TComplemento;
import Negocio.Producto.TProducto;
import Negocio.Producto.TVideojuego;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.Dimension;

public class VistaListarPorTipoProducto extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaListarPorTipoProducto() {
		super("[LISTAR PRODUCTOS POR TIPO]");
		initGUI();
	}

	private void initGUI() {

		JPanel mainPanel = new JPanel();
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(600, 300));
		JPanel jpanel = new JPanel();
		JComboBox<String> tipoComboBox = new JComboBox<>();
		rellenarMainPanel(jpanel, tipoComboBox);
		mainPanel.add(jpanel);

		JPanel jmostrar = new JPanel();
		rellenarmostrarPanel(jmostrar, tipoComboBox);
		mainPanel.add(jmostrar);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(false);

	}

	private void rellenarMainPanel(JPanel jpanel, JComboBox<String> tipoComboBox) {

		JLabel tipoLabel = new JLabel(" SELECCIONAR TIPO:");
		jpanel.add(tipoLabel);

		tipoComboBox.addItem("Introduzca el tipo del producto");
		tipoComboBox.addItem("Videojuego");
		tipoComboBox.addItem("Complemento");
		jpanel.add(tipoComboBox);

	}

	private void rellenarmostrarPanel(JPanel jmostrar, JComboBox<String> tipoComboBox) {
		JButton mostrar = new JButton("MOSTRAR");
		mostrar.addActionListener((e) -> {
			Controller.getInstancia()
					.accion(new Context(Evento.LISTAR_PRODUCTOS_X_TIPO, (String) tipoComboBox.getSelectedItem()));
			this.dispose();
		});
		jmostrar.add(mostrar);

		JButton volver = new JButton("VOLVER");
		volver.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.PRODUCTO, null));
			this.dispose();
		});
		jmostrar.add(volver);
	}

	public void update(Context contexto) {

		if (contexto.getEvento() == Evento.VLISTAR_PRODUCTOS_X_TIPO) {
			setVisible(true);
		} else {
			if (contexto.getEvento() == Evento.RES_LISTAR_PRODUCTOS_X_TIPO_OK) {
				@SuppressWarnings("unchecked")
				Set<TProducto> tps = (Set<TProducto>) contexto.getDatos();

				String mhtml = "<html><head>" + "<style>" + "table { width: 100%; border-collapse: collapse;}"
						+ "th, td {border: 1px solid #ddd; padding:8px;}" + "th{background-color: #007BFF; color:#fff;}"
						+ "</style>" + "</head> <body>" + "<p>Productos encontrados:</p>";
				if (tps.size() == 0) {
					JOptionPane.showMessageDialog(null, "Productos no encontrados en la Base de Datos.");
				} else {
					mhtml += "<table><tr><th> ID</th><th>NOMBRE</th><th>MARCA</th><th>ACTIVO</th><th>PESO</th><th>RESTRICCION EDAD</th></tr>";
					for (TProducto tp : tps) {
						mhtml += "<tr><td>" + tp.get_id() + "</td> <td>" + tp.get_nombre() + "</td> <td>"
								+ tp.get_marca() + "</td> <td>" + (tp.get_activo() == 1 ? "SI" : "NO") + "</td> <td>";
						if (tp instanceof TComplemento) {
							TComplemento tC = (TComplemento) tp;
							mhtml += tC.get_peso() + "</td> <td> N/A </td> </tr>";
						} else if (tp instanceof TVideojuego) {
							TVideojuego tV = (TVideojuego) tp;
							mhtml += "N/A </td><td>" + tV.get_restriccionEdad() + "</td> </tr>";
						}
					}
					mhtml += "</table>";
					mhtml += "</body></html>";
					JOptionPane.showMessageDialog(null, mhtml, "Productos Encontrados",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} else if (contexto.getEvento() == Evento.RES_LISTAR_PRODUCTOS_X_TIPO_KO) {
				JOptionPane.showMessageDialog(null, "Error al listar los productos por tipo.");
			}
			Controller.getInstancia().accion(new Context(Evento.PRODUCTO, null));
			this.dispose();
		}
	}

}
