package Presentacion.Venta;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Negocio.Venta.TLineaVenta;

import java.awt.BorderLayout;
import java.awt.Dimension;

public class VDevolverVenta extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VDevolverVenta() {
		super("[DEVOLUCION]");
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

	private void rellenarPanelSuperior(JPanel supPanel) {

		JPanel textFieldPanel = new JPanel();
		supPanel.add(textFieldPanel, BorderLayout.PAGE_START);

		JTextField searchText = new JTextField();
		searchText.setVisible(true);
		searchText.setPreferredSize(new Dimension(80, 30));
		JLabel idProductoEnPlaformaLabel = new JLabel("<html><p>ID del producto:</p></html>");
		idProductoEnPlaformaLabel.setPreferredSize(new Dimension(80, 30));
		textFieldPanel.add(idProductoEnPlaformaLabel);
		textFieldPanel.add(searchText);

		JTextField cantidadTextField = new JTextField();
		cantidadTextField.setVisible(true);
		cantidadTextField.setPreferredSize(new Dimension(80, 30));
		JLabel cantidadLabel = new JLabel("<html><p>Cantidad:</p></html>");
		cantidadLabel.setPreferredSize(new Dimension(45, 30));
		textFieldPanel.add(cantidadLabel);
		textFieldPanel.add(cantidadTextField);

		JTextField searchVentaText = new JTextField();
		searchVentaText.setVisible(true);
		searchVentaText.setPreferredSize(new Dimension(80, 30));
		JLabel idVentaLabel = new JLabel("<html><p>ID de la venta:</p></html>");
		idVentaLabel.setPreferredSize(new Dimension(70, 30));
		textFieldPanel.add(idVentaLabel);
		textFieldPanel.add(searchVentaText);

		JPanel okPanel = new JPanel();
		supPanel.add(okPanel, BorderLayout.PAGE_END);

		JButton ok_button = new JButton("OK");
		ok_button.setVisible(true);
		ok_button.addActionListener((e) -> {

			try {
				int idVenta = Integer.valueOf(searchVentaText.getText()),
						idProductoFinal = Integer.valueOf(searchText.getText()),
						cantidad = Integer.valueOf(cantidadTextField.getText());
				if (idVenta <= 0 || idProductoFinal <= 0 || cantidad <= 0)
					JOptionPane.showMessageDialog(null, "Error: No puede haber datos negativos o cero");
				else {
					TLineaVenta tLineaVenta = new TLineaVenta();
					tLineaVenta.set_factura(idVenta);
					tLineaVenta.set_producto(idProductoFinal);
					tLineaVenta.set_cantidad(cantidad);
					Controller.getInstancia().accion(new Context(Evento.DEVOLVER_VENTA_NEGOCIO, tLineaVenta));
					this.dispose();
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Error: No puede haber campos vacios o con letras");
			}
		});
		okPanel.add(ok_button);
	}

	private void rellenarPanelInferior(JPanel bottomPanel) {

		JButton cancelButton = new JButton("CANCEL");
		cancelButton.setToolTipText("cancel");
		cancelButton.setVisible(true);
		cancelButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VENTA, null));
			this.dispose();
		});
		bottomPanel.add(cancelButton);
	}

	public void update(Context context) {

		Evento evento = context.getEvento();

		if (evento == Evento.DEVOLVER_VENTA_VISTA) {
			setVisible(true);
		}

		else {
			updateVenta(evento);
		}
	}

	private void updateVenta(Evento evento) {

		if (evento == Evento.RES_DEVOLVER_VENTA_OK) {
			mensaje("Devolucion del producto con exito");
		}

		else if (evento == Evento.RES_DEVOLVER_VENTA_KO) {
			mensaje("Error en la devolucion del producto, compruebe datos");
		}

		Controller.getInstancia().accion(new Context(Evento.VENTA, null));
		this.dispose();
	}

	private void mensaje(String texto) {
		JOptionPane.showMessageDialog(null, texto);
	}
}