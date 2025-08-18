package Presentacion.EmpleadoJPA;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VistaEmpleado extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaEmpleado() {
		super("Empleado");
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

		JButton altaButton = botonVistaPrincipal(Evento.VALTA_EMPLEADO, "ALTA", "Alta");
		JButton bajaButton = botonVistaPrincipal(Evento.VBAJA_EMPLEADO, "BAJA", "Baja");
		JButton modificarButton = botonVistaPrincipal(Evento.VMODIFICAR_EMPLEADO, "MODIFICAR", "Modificar");
		JButton listarButton = botonVistaPrincipal(Evento.VLISTAR_EMPLEADO_ALL, "LISTAR", "Listar");
		JButton buscarButton = botonVistaPrincipal(Evento.VBUSCAR_EMPLEADO, "BUSCAR", "Buscar");

		supPanel.add(altaButton);
		supPanel.add(bajaButton);
		supPanel.add(modificarButton);
		supPanel.add(listarButton);
		supPanel.add(buscarButton);
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