
package Presentacion.Plataforma;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Dimension;

public class VistaPlataforma extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaPlataforma() {
		super("[PLATAFORMA]");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(600, 300));
		JPanel controlPanel = new JPanel();
		rellenarControlPanel(controlPanel);
		JPanel jvolverVP = new JPanel();
		rellenarVolverVPPanel(jvolverVP);
		mainPanel.add(controlPanel, BorderLayout.WEST);
		mainPanel.add(jvolverVP, BorderLayout.PAGE_END);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	private void rellenarVolverVPPanel(JPanel jvolverVP) {
		JButton volverVP = new JButton("VOLVER A VISTA PRINCIPAL");
		volverVP.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VISTA_PRINCIPAL, null));
			this.dispose();
		});
		jvolverVP.add(volverVP);
	}

	private void rellenarControlPanel(JPanel controlPanel) {

		JButton alta = new JButton("ALTA");
		alta.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VALTA_PLATAFORMA, null));
			this.dispose();

		});
		controlPanel.add(alta);

		JButton baja = new JButton("BAJA");
		baja.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VBAJA_PLATAFORMA, null));
			this.dispose();

		});
		controlPanel.add(baja);

		JButton modificar = new JButton("MODIFICAR");
		modificar.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VMODIFICAR_PLATAFORMA, null));
			this.dispose();

		});
		controlPanel.add(modificar);

		JButton buscar = new JButton("BUSCAR");
		buscar.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VMOSTRAR_PLATAFORMA, null));
			this.dispose();

		});
		controlPanel.add(buscar);

		JButton listar_todo = new JButton("LISTAR TODO");
		listar_todo.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VMOSTRAR_ALL_PLATAFORMAS, null));
			this.dispose();

		});
		controlPanel.add(listar_todo);

	}

	@Override
	public void update(Context contexto) {
		setVisible(true);

	}

}