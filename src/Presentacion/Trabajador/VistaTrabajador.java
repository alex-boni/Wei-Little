
package Presentacion.Trabajador;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

public class VistaTrabajador extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaTrabajador() {
		super("[TRABAJADOR]");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(800, 300));

		JPanel controlPanel = new JPanel();
		mainPanel.add(controlPanel);
		rellenaControlPanel(controlPanel);

		JPanel jvolverVP = new JPanel();
		mainPanel.add(jvolverVP, BorderLayout.PAGE_END);
		rellenarvolverVPPanel(jvolverVP);

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
		jvolverVP.add(volverVP, BorderLayout.SOUTH);
	}

	private void rellenaControlPanel(JPanel controlPanel) {

		controlPanel.setLayout(new BorderLayout());
		JPanel panelnorte = new JPanel();
		JPanel panelsur = new JPanel();

		JButton alta = new JButton("ALTA");
		alta.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VALTA_TRABAJADOR, null));
			this.dispose();

		});
		panelnorte.add(alta);

		JButton baja = new JButton("BAJA");
		baja.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VBAJA_TRABAJADOR, null));
			this.dispose();

		});
		panelnorte.add(baja);

		JButton modificar = new JButton("MODIFICAR");
		modificar.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VMODIFICAR_TRABAJADOR, null));
			this.dispose();

		});
		panelnorte.add(modificar);

		JButton buscar = new JButton("BUSCAR");
		buscar.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VMOSTRAR_TRABAJADOR, null));
			this.dispose();

		});
		panelnorte.add(buscar);

		JButton listar_todo = new JButton("LISTAR TODO");
		listar_todo.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VMOSTRAR_ALL_TRABAJADOR, null));
			this.dispose();

		});
		panelnorte.add(listar_todo);

		JButton listar_habilidad = new JButton("LISTAR TRABAJADORES POR HABILIDAD");
		listar_habilidad.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VMOSTRAR_TRABAJADOR_X_HABILIDAD, null));
			this.dispose();

		});
		panelnorte.add(listar_habilidad);

		JButton vincular_habilidad = new JButton("VINCULAR HABILIDAD A TRABAJADOR");
		vincular_habilidad.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VVINCULAR_HABILIDAD_TRABAJADOR, null));
			this.dispose();

		});
		panelsur.add(vincular_habilidad);

		JButton desvincular_habilidad = new JButton("DESVINCULAR HABILIDAD DE TRABAJADOR");
		desvincular_habilidad.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VDESVINCULAR_HABILIDAD_TRABAJADOR, null));
			this.dispose();

		});
		panelsur.add(desvincular_habilidad);

		JButton buscar_tipo = new JButton("BUSCAR TRABAJADOR POR TIPO");
		buscar_tipo.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VBUSCAR_TRABAJADOR_X_TIPO, null));
			this.dispose();

		});
		panelsur.add(buscar_tipo);

		controlPanel.add(panelnorte, BorderLayout.NORTH);
		controlPanel.add(panelsur, BorderLayout.CENTER);

	}

	@Override
	public void update(Context context) {
		setVisible(true);
	}
}