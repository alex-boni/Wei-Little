/**
 * 
 */
package Presentacion.Habilidad;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

public class VistaHabilidad extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaHabilidad() {
		super("[HABILIDAD]");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(800, 300));
		JPanel controlPanel = new JPanel();
		rellenaControlPanel(controlPanel);
		JPanel jvolverVP = new JPanel();
		rellenarvolverVPPanel(jvolverVP);
		mainPanel.add(controlPanel, BorderLayout.PAGE_START);
		mainPanel.add(jvolverVP, BorderLayout.PAGE_END);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);

	}

	private void rellenarvolverVPPanel(JPanel jvolverVP) {
		JButton volverVP = new JButton("VOLVER A VISTA PRINCIPAL");
		volverVP.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VISTA_PRINCIPAL, null));
			this.dispose();
		});
		jvolverVP.add(volverVP);
	}

	private void rellenaControlPanel(JPanel controlPanel) {
		// alta habilidad button //
		JButton altaHabilidad = new JButton("ALTA");
		altaHabilidad.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VALTA_HABILIDAD, null));
			this.dispose();
		});
		controlPanel.add(altaHabilidad);

		// baja habilidad button //
		JButton bajaHabilidad = new JButton("BAJA");
		bajaHabilidad.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VBAJA_HABILIDAD, null));
			this.dispose();
		});
		controlPanel.add(bajaHabilidad);

		// modificar habilidad button //
		JButton modificarHabilidad = new JButton("MODIFICAR");
		modificarHabilidad.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VMODIFICAR_HABILIDAD, null));
			this.dispose();
		});
		controlPanel.add(modificarHabilidad);

		// buscar habilidad button //
		JButton buscarHabilidad = new JButton("BUSCAR");
		buscarHabilidad.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VBUSCAR_HABILIDAD, null));
			this.dispose();
		});
		controlPanel.add(buscarHabilidad);

		JButton listarTodoHabilidad = new JButton("LISTAR TODO");
		listarTodoHabilidad.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VLISTAR_HABILIDAD_ALL, null));
			this.dispose();
		});
		controlPanel.add(listarTodoHabilidad);

		JButton listarTodoHabilidadPorTrabajador = new JButton("LISTAR TODO POR TRABAJADOR");
		listarTodoHabilidadPorTrabajador.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VLISTAR_HABILIDAD_DEL_TRABAJADOR, null));
			this.dispose();
		});
		controlPanel.add(listarTodoHabilidadPorTrabajador);
	}

	@Override
	public void update(Context c) {
		setVisible(true);

	}
}