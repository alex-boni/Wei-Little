package Presentacion.AlquilerJPA;

import javax.swing.JFrame;

import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;

public class VistaAlquiler extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaAlquiler() {
		super("[ALQUILER]");
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

		JButton modificarButton = botonVistaPrincipal(Evento.MODIFICAR_ALQUILER_VISTA, "MODIFICAR", "Modificar");
		JButton cancelarButton = botonVistaPrincipal(Evento.CANCELAR_ALQUILER_VISTA, "CANCELAR", "Cancelar");
		JButton listarButton = botonVistaPrincipal(Evento.LISTAR_ALQUILERES_ALL_VISTA, "LISTAR", "Lista");
		JButton abrirAlquilerButton = botonVistaPrincipal(Evento.ABRIR_ALQUILER_VISTA, "ABRIR", "Abrir");
		JButton buscarButton = botonVistaPrincipal(Evento.BUSCAR_ALQUILER_VISTA, "BUSCAR", "Buscar");

		supPanel.add(abrirAlquilerButton);
		supPanel.add(buscarButton);
		supPanel.add(modificarButton);
		supPanel.add(cancelarButton);
		supPanel.add(listarButton);
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