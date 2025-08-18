package Presentacion.ModeloJPA;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

public class VistaModelo extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaModelo() {
		super("[MODELO]");
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
		mainPanel.add(midPanel, BorderLayout.CENTER);
		rellenarPanelMid(midPanel);

		JPanel bottomPanel = new JPanel();
		mainPanel.add(bottomPanel, BorderLayout.PAGE_END);
		rellenarPanelAbajo(bottomPanel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1000, 300));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void rellenarPanelMid(JPanel midPanel) {

		JButton eliminarButton = botonVistaPrincipal(Evento.BAJA_MODELO_VISTA, "ELIMINAR", "Baja");
		JButton vincularButton = botonVistaPrincipal(Evento.VINCULAR_PROVEEDOR_MODELO_VISTA, "VINCULAR PROVEEDOR",
				"Vincular");
		JButton desvincularButton = botonVistaPrincipal(Evento.DESVINCULAR_PROVEEDOR_MODELO_VISTA,
				"DESVINCULAR PROVEEDOR", "Desvincular");
		JButton calcularButton = botonVistaPrincipal(Evento.CALCULAR_COSTE_SEGURO_MODELO_VISTA, "COSTE SEGURO",
				"Calcular Coste");

		midPanel.add(eliminarButton);
		midPanel.add(vincularButton);
		midPanel.add(desvincularButton);
		midPanel.add(calcularButton);

	}

	private void rellenarPanelSuperior(JPanel supPanel) {

		JButton modificarButton = botonVistaPrincipal(Evento.MODIFICAR_MODELO_VISTA, "MODIFICAR", "Modificar");
		JButton cancelarButton = botonVistaPrincipal(Evento.LISTAR_MODELO_X_PROVEEDOR_VISTA,
				"LISTAR MODELOS POR PROVEEDOR", "Listar Modelos Por Proveedor");
		JButton listarButton = botonVistaPrincipal(Evento.LISTAR_MODELO_ALL_VISTA, "LISTAR", "Lista");
		JButton altaButton = botonVistaPrincipal(Evento.ALTA_MODELO_VISTA, "ALTA", "Alta");
		JButton buscarButton = botonVistaPrincipal(Evento.BUSCAR_MODELO_VISTA, "BUSCAR", "Buscar");

		supPanel.add(altaButton);
		supPanel.add(buscarButton);
		supPanel.add(modificarButton);
		supPanel.add(listarButton);
		supPanel.add(cancelarButton);

	}

	private void rellenarPanelAbajo(JPanel bottomPanel) {

		JButton volverButton = botonVistaPrincipal(Evento.VISTA_PRINCIPAL, "VOLVER A VISTA PRINCIPAL", "Volver");

		bottomPanel.add(volverButton);
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
