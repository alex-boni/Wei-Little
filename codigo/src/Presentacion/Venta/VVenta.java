package Presentacion.Venta;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Dimension;

public class VVenta extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VVenta() {
		super("[VENTA]");
		initGUI();
	}

	private void initGUI() {

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		setContentPane(mainPanel);

		JPanel supPanel = new JPanel();
		mainPanel.add(supPanel, BorderLayout.PAGE_START);
		rellenarPanelSuperior(supPanel);

		JPanel midPanel = new JPanel();
		mainPanel.add(midPanel, BorderLayout.PAGE_END);
		rellenarPanelCentrar(midPanel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1000, 300));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void rellenarPanelSuperior(JPanel supPanel) {

		JButton modificarButton = botonVistaPrincipal(Evento.MODIFICAR_VENTA_VISTA, "MODIFICAR", "Modificar");
		JButton devolverButton = botonVistaPrincipal(Evento.DEVOLVER_VENTA_VISTA, "DEVOLUCION", "Devolver");
		JButton listarButton = botonVistaPrincipal(Evento.LISTAR_VENTAS_ALL_VISTA, "LISTAR", "Lista");
		JButton abrirVentaButton = botonVistaPrincipal(Evento.ABRIR_VENTA_VISTA, "ABRIR", "Abrir");
		JButton buscarButton = botonVistaPrincipal(Evento.BUSCAR_VENTA_VISTA, "BUSCAR", "Buscar");
		JButton eliminarButton = botonVistaPrincipal(Evento.BAJA_VENTA_VISTA, "ELIMINAR", "Baja");
		JButton queryButton = botonVistaPrincipal(Evento.CALCULAR_TOTAL_POR_PRODUCTOPLATAFORMA_QUERY_VISTA,
				"CALCULAR TOTAL PRODUCTO",
				"Calcular el importe total del trabajador que mas ha vendido un producto en plataforma");

		supPanel.add(abrirVentaButton);
		supPanel.add(buscarButton);
		supPanel.add(modificarButton);
		supPanel.add(devolverButton);
		supPanel.add(listarButton);
		supPanel.add(eliminarButton);
		supPanel.add(queryButton);
	}

	private void rellenarPanelCentrar(JPanel midPanel) {

		JButton volverButton = botonVistaPrincipal(Evento.VISTA_PRINCIPAL, "VOLVER A VISTA PRINCIPAL", "Volver");

		midPanel.add(volverButton);
	}

	private JButton botonVistaPrincipal(Evento evento, String texto, String tooltip) {
		JButton boton = new JButton(texto);
		boton.setToolTipText(tooltip);
		boton.addActionListener(e -> accionSinDatos(evento));
		return boton;
	}

	private void accionSinDatos(Evento evento) {
		Controller.getInstancia().accion(new Context(evento, null));
		dispose();
	}

	@Override
	public void update(Context context) {
		setVisible(true);
	}

}