/**
 * 
 */
package Presentacion.Habilidad;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Negocio.Habilidad.THabilidad;

import java.awt.BorderLayout;
import java.awt.Dimension;

public class VistaListarTodoHabilidad extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaListarTodoHabilidad() {
		super("[LISTAR HABILIDADES]");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		this.setPreferredSize(new Dimension(600, 300));

		JPanel jmostrar = new JPanel();
		rellenarmostrarPanel(jmostrar);

		mainPanel.add(jmostrar, BorderLayout.CENTER);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setContentPane(mainPanel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void rellenarmostrarPanel(JPanel jmostrar) {
		JButton mostrar = new JButton("MOSTRAR");
		mostrar.addActionListener((e) -> {
			int a = 1;
			Controller.getInstancia().accion(new Context(Evento.LISTAR_HABILIDAD_ALL, (Object) a));
			this.dispose();
		});
		jmostrar.add(mostrar);

		// volver button
		JButton volver = new JButton("VOLVER");
		volver.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.HABILIDAD, null));
			this.dispose();
		});
		jmostrar.add(volver);
	}

	public void update(Context c) {

		if (c.getEvento() == Evento.VLISTAR_HABILIDAD_ALL) {
			this.setVisible(true);
		} else {
			if (c.getEvento() == Evento.RES_LISTAR_HABILIDAD_ALL_OK) {
				@SuppressWarnings("unchecked")
				Set<THabilidad> ths = (Set<THabilidad>) c.getDatos();
				String mhtml = "<html><head>" + "<style>" + "table { width: 100%; border-collapse: collapse;}"
						+ "th, td {border: 1px solid #ddd; padding:8px;}" + "th{background-color: #4CAF50; color:#fff;}"
						+ "</style>" + "</head> <body>" + "<p>Habilidades encontradas:</p>";
				if (ths.size() == 0) {
					JOptionPane.showMessageDialog(null, "No hay habilidades activas en la Base de Datos.");
				} else {
					mhtml += "<table><tr><th> ID</th><th>NOMBRE</th><th>NIVEL</th><th>ACTIVO</th></tr>";
					for (THabilidad th : ths) {
						mhtml += "<tr> <td>" + th.get_id() + "</td> <td>" + th.get_nombre() + "</td> <td>"
								+ th.get_nivel() + "</td> <td>" + (th.get_activo() == 1 ? "SI" : "NO") + "</td> </tr>";
					}
					mhtml += "</table></body></html>";

					// Hace el renderizado a HTML para que se muestre la tabla HTML y pueda añadir
					// un scroll
					JEditorPane editorPane = new JEditorPane("text/html", mhtml);
					editorPane.setEditable(false);

					// Poner el JEditorPane en un JScrollPane
					JScrollPane scrollPane = new JScrollPane(editorPane);
					scrollPane.setPreferredSize(new Dimension(600, 400));

					// Crear un JDialog y mostrarlo
					JDialog dialog = new JDialog(this, "Habilidades encontradas", true);
					dialog.getContentPane().add(scrollPane);
					dialog.setSize(new Dimension(620, 450));
					dialog.setLocationRelativeTo(this);
					dialog.setVisible(true);
				}

			} else if (c.getEvento() == Evento.RES_LISTAR_HABILIDAD_ALL_KO)
				JOptionPane.showMessageDialog(null, "Error al listar todas las habilidades.");
			Controller.getInstancia().accion(new Context(Evento.HABILIDAD, null));
			this.dispose();
		}

	}
}