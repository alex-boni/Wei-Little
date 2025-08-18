package Presentacion.ProductoEnPlataforma;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class VProductoEnPlataforma extends JFrame implements IGUI {

	public VProductoEnPlataforma() {
		super("[PRODUCTO EN PLATAFORMA]");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(600, 300));
		JPanel controlPanel = new JPanel();
		rellenaControlPanel(controlPanel);
		JPanel jvolverVP = new JPanel();
		rellenarvolverVPPanel(jvolverVP);
		mainPanel.add(controlPanel);
		mainPanel.add(jvolverVP);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
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
			Controller.getInstancia().accion(new Context(Evento.VALTA_PRODUCTO_PLATAFORMA, null));
			this.dispose();

		});
		controlPanel.add(alta);

		JButton baja = new JButton("BAJA");
		baja.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VBAJA_PRODUCTO_PLATAFORMA, null));
			this.dispose();

		});
		controlPanel.add(baja);

		JButton modificar = new JButton("MODIFICAR");
		modificar.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VMODIFICAR_PRODUCTO_PLATAFORMA, null));
			this.dispose();

		});
		controlPanel.add(modificar);

		JButton buscar = new JButton("BUSCAR");
		buscar.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.VBUSCAR_PRODUCTO_PLATAFORMA, null));
			this.dispose();

		});
		controlPanel.add(buscar);

		JButton listar_todo = new JButton("LISTAR");
		listar_todo.addActionListener((e) -> {

			Controller.getInstancia().accion(new Context(Evento.VLISTAR_TODO_PP, null));
			this.dispose();

		});
		controlPanel.add(listar_todo);

		JButton calcular_cantidad = new JButton("CALCULAR CANTIDAD");
		calcular_cantidad.addActionListener((e) -> {

			Controller.getInstancia().accion(new Context(Evento.VCALCULAR_CANTIDAD_PRODUCTO_EN_PLATAFORMA, null));
			this.dispose();

		});
		controlPanel.add(calcular_cantidad);
	}

	@Override
	public void update(Context context) {
	}

}
