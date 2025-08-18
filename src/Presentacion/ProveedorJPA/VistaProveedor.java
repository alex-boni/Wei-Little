package Presentacion.ProveedorJPA;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

public class VistaProveedor extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaProveedor() {
		super("[PROVEEDOR]");
		this.initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(600, 300));
		JPanel controlPanel = new JPanel();
		rellenaControlPanel(controlPanel);
		JPanel jvolverVP = new JPanel();
		rellenarvolverVPPanel(jvolverVP);
		mainPanel.add(controlPanel, BorderLayout.WEST);
		mainPanel.add(jvolverVP, BorderLayout.PAGE_END);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
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

		JButton alta = new JButton("ALTA");
		alta.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.ALTA_PROVEEDOR_VISTA, null));
			this.dispose();

		});
		controlPanel.add(alta);

		JButton baja = new JButton("BAJA");
		baja.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.BAJA_PROVEEDOR_VISTA, null));
			this.dispose();

		});
		controlPanel.add(baja);

		JButton buscar = new JButton("BUSCAR");
		buscar.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.BUSCAR_PROVEEDOR_VISTA, null));
			this.dispose();

		});
		controlPanel.add(buscar);

		JButton modificar = new JButton("MODIFICAR");
		modificar.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.MODIFICAR_PROVEEDOR_VISTA, null));
			this.dispose();

		});
		controlPanel.add(modificar);

		JButton listar_todo = new JButton("LISTAR TODO");
		listar_todo.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.LISTAR_PROVEEDOR_ALL_VISTA, null));
			this.dispose();

		});
		controlPanel.add(listar_todo);

		JButton listar_tipo = new JButton("LISTAR POR MODELO");
		listar_tipo.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.LISTAR_PROVEEDOR_MODELO_ALL_VISTA, null));
			this.dispose();

		});
		controlPanel.add(listar_tipo);

	}

	@Override
	public void update(Context context) {

	}

}
