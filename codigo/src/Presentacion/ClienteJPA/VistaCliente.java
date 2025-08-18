package Presentacion.ClienteJPA;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

public class VistaCliente extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaCliente() {
		super("[CLIENTE]");
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

	}

	private void rellenarPanelCentrar(JPanel midPanel) {

		JButton volverVP = new JButton("VOLVER A VISTA PRINCIPAL");
		volverVP.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VISTA_PRINCIPAL, null));
			this.dispose();
		});
		midPanel.add(volverVP);
	}

	private void rellenarPanelSuperior(JPanel supPanel) {

		// alta cliente button //
		JButton altaCliente = new JButton("ALTA");
		altaCliente.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VALTA_CLIENTE, null));
			this.dispose();
		});
		supPanel.add(altaCliente);

		// baja cliente button //
		JButton bajaCliente = new JButton("BAJA");
		bajaCliente.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VBAJA_CLIENTE, null));
			this.dispose();
		});
		supPanel.add(bajaCliente);

		// modificar cliente button //
		JButton modificarCliente = new JButton("MODIFICAR");
		modificarCliente.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VMODIFICAR_CLIENTE, null));
			this.dispose();
		});
		supPanel.add(modificarCliente);

		// buscar cliente button //
		JButton buscarCliente = new JButton("BUSCAR");
		buscarCliente.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VBUSCAR_CLIENTE, null));
			this.dispose();
		});
		supPanel.add(buscarCliente);

		// listar todo cliente button //
		JButton listarTodoCliente = new JButton("LISTAR TODO");
		listarTodoCliente.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VLISTAR_CLIENTES_ALL, null));
			this.dispose();
		});
		supPanel.add(listarTodoCliente);

	}

	@Override
	public void update(Context c) {
		setVisible(true);

	}
}
