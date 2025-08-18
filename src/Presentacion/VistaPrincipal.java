
package Presentacion;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class VistaPrincipal extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaPrincipal() {
		super("[TIENDA DE VIDEOJUEGOS]");
		initIGUI();
	}

	private void initIGUI() {
		JPanel mainPanel = new JPanel();
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(600, 200));
		JPanel controlPanel = new JPanel();
		rellenaControlPanel(controlPanel);
		mainPanel.add(controlPanel, BorderLayout.WEST);

		executeMessageOnClose();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);

	}

	private void executeMessageOnClose() {
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {

			}

			@Override
			public void windowClosing(WindowEvent e) {
				int n = JOptionPane.showOptionDialog(getWindow(VistaPrincipal.this), "Are sure you want to quit?",
						"Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

				if (n == 0) {
					System.exit(0);
				}
			}

			@Override
			public void windowClosed(WindowEvent e) {

			}

			@Override
			public void windowIconified(WindowEvent e) {

			}

			@Override
			public void windowDeiconified(WindowEvent e) {

			}

			@Override
			public void windowActivated(WindowEvent e) {

			}

			@Override
			public void windowDeactivated(WindowEvent e) {

			}
		});
	}

	private void rellenaControlPanel(JPanel c) {
		c.setLayout(new BorderLayout());
		JToolBar _toolaBar = new JToolBar();
		JToolBar _toolaBarJPA = new JToolBar();
		JPanel separador = new JPanel();
		separador.setPreferredSize(new Dimension(0, 30));
		c.add(_toolaBar, BorderLayout.PAGE_START);
		c.add(separador, BorderLayout.CENTER);
		c.add(_toolaBarJPA, BorderLayout.PAGE_END);

		_toolaBar.add(crearPanelConEtiquetaYBoton("Plataforma", "resources/plataforma.png", e -> {
			Context context = new Context(Evento.PLATAFORMA, null);
			Controller.getInstancia().accion(context);
			this.dispose();
		}));

		_toolaBar.add(crearPanelConEtiquetaYBoton("Producto", "resources/producto.png", e -> {
			Context context = new Context(Evento.PRODUCTO, null);
			Controller.getInstancia().accion(context);
			this.dispose();
		}));

		_toolaBar.add(crearPanelConEtiquetaYBoton("Producto-Plataforma", "resources/producto_plataforma.png", e -> {
			Context context = new Context(Evento.PRODUCTO_EN_PLATAFORMA, null);
			Controller.getInstancia().accion(context);
			this.dispose();
		}));

		_toolaBar.add(crearPanelConEtiquetaYBoton("Habilidad", "resources/habilidad.png", e -> {
			Context context = new Context(Evento.HABILIDAD, null);
			Controller.getInstancia().accion(context);
			this.dispose();
		}));

		_toolaBar.add(crearPanelConEtiquetaYBoton("Trabajador", "resources/trabajador.png", e -> {
			Context context = new Context(Evento.TRABAJADOR, null);
			Controller.getInstancia().accion(context);
			this.dispose();
		}));

		_toolaBar.add(crearPanelConEtiquetaYBoton("Venta", "resources/venta.png", e -> {
			Context context = new Context(Evento.VENTA, null);
			Controller.getInstancia().accion(context);
			this.dispose();
		}));

		_toolaBarJPA.add(crearPanelConEtiquetaYBoton("Cliente", "resources/cliente.png", e -> {
			Context context = new Context(Evento.CLIENTE, null);
			Controller.getInstancia().accion(context);
			this.dispose();
		}));

		_toolaBarJPA.add(crearPanelConEtiquetaYBoton("Empleado", "resources/empleado.png", e -> {
			Context context = new Context(Evento.EMPLEADO, null);
			Controller.getInstancia().accion(context);
			this.dispose();
		}));

		_toolaBarJPA.add(crearPanelConEtiquetaYBoton("Proveedor", "resources/proveedor.png", e -> {
			Context context = new Context(Evento.PROVEEDOR, null);
			Controller.getInstancia().accion(context);
			this.dispose();
		}));

		_toolaBarJPA.add(crearPanelConEtiquetaYBoton("Modelo", "resources/modelo.png", e -> {
			Context context = new Context(Evento.MODELO, null);
			Controller.getInstancia().accion(context);
			this.dispose();
		}));

		_toolaBarJPA.add(crearPanelConEtiquetaYBoton("Maquina", "resources/maquina.png", e -> {
			Context context = new Context(Evento.MAQUINA, null);
			Controller.getInstancia().accion(context);
			this.dispose();
		}));

		_toolaBarJPA.add(crearPanelConEtiquetaYBoton("Alquiler", "resources/alquiler.png", e -> {
			Context context = new Context(Evento.ALQUILER, null);
			Controller.getInstancia().accion(context);
			this.dispose();
		}));

		_toolaBar.setFloatable(false);
		_toolaBarJPA.setFloatable(false);
	}

	private JPanel crearPanelConEtiquetaYBoton(String texto, String iconoPath, ActionListener action) {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel label = new JLabel(texto, SwingConstants.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 11));
		JButton button = new JButton(new ImageIcon(iconoPath));
		button.setToolTipText(texto);
		button.addActionListener(action);

		panel.add(label, BorderLayout.NORTH); // Etiqueta encima
		panel.add(button, BorderLayout.CENTER); // Botón debajo
		return panel;
	}

	@Override
	public void update(Context c) {
		setVisible(true);
		// Context context=new Context(Evento.VISTA_PRINCIPAL,null);
		// Controller.getInstancia().accion(context);
		// dispose();
	}

	static Frame getWindow(Component c) {
		Frame w = null;
		if (c != null) {
			if (c instanceof Frame)
				w = (Frame) c;
			else
				w = (Frame) SwingUtilities.getWindowAncestor(c);
		}
		return w;
	}
}