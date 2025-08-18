
package Presentacion.Plataforma;

import javax.swing.JFrame;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Negocio.Plataforma.TPlataforma;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;

import java.awt.Dimension;

public class VistaListarTodoPlataforma extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaListarTodoPlataforma() {
		super("[LISTAR PLATAFORMAS]");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(600, 300));

		JPanel jmostrar = new JPanel();
		rellenarMostrarPanel(jmostrar);
		mainPanel.add(jmostrar);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

	}

	private void rellenarMostrarPanel(JPanel jmostrar) {
		JButton mostrar = new JButton("MOSTRAR");
		mostrar.addActionListener((e) -> {
			Context contexto = new Context(Evento.MOSTRAR_ALL_PLATAFORMAS, null);
			Controller.getInstancia().accion(contexto);
			this.dispose();
		});
		jmostrar.add(mostrar);

		JButton volver = new JButton("VOLVER");
		volver.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.PLATAFORMA, null));
			this.dispose();
		});
		jmostrar.add(volver);
	}

	@Override
	public void update(Context context) {
		if (context.getEvento() == Evento.VMOSTRAR_ALL_PLATAFORMAS)
			this.setVisible(true);
		else {
			if (context.getEvento() == Evento.RES_MOSTRAR_ALL_PLATAFORMAS_OK) {

				@SuppressWarnings("unchecked")
				Set<TPlataforma> tps = (Set<TPlataforma>) context.getDatos();
				String mhtml = "<html><head>" + "<style>" + "table { width: 100%; border-collapse: collapse;}"
						+ "th, td {border: 1px solid #ddd; padding:8px;}" + "th{background-color: #007BFF; color:#fff;}"
						+ "</style>" + "</head> <body>" + "<p>Plataformas encontradas:</p>";
				if (tps.size() == 0) {
					JOptionPane.showMessageDialog(null, "No hay plataformas en la Base de Datos.");
				} else {
					mhtml += "<table><tr><th> ID</th><th>NOMBRE</th><th>ACTIVO</th></tr>";
					for (TPlataforma tp : tps) {
						mhtml += "<tr><td>" + tp.get_id() + "</td> <td>" + tp.get_nombre() + "</td> <td>"
								+ (tp.get_activo() == 1 ? "SI" : "NO") + "</td> </tr>";
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
					JDialog dialog = new JDialog(this, "Plataformas encontradas", true);
					dialog.getContentPane().add(scrollPane);
					dialog.setSize(new Dimension(620, 450));
					dialog.setLocationRelativeTo(this);
					dialog.setVisible(true);
				}

			} else if (context.getEvento() == Evento.RES_MOSTRAR_ALL_PLATAFORMAS_KO) {
				JOptionPane.showMessageDialog(null, "Error al listar todas las plataformas.");
			}
			Controller.getInstancia().accion(new Context(Evento.PLATAFORMA, null));
			this.dispose();
		}
	}

}