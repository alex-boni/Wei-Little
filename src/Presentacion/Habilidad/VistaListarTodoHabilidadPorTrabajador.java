
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
import javax.swing.JTextField;

import Negocio.Trabajador.TVinculacionTrabHab;

import javax.swing.JLabel;

import java.awt.Dimension;

public class VistaListarTodoHabilidadPorTrabajador extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField idTrabajadorField;

	public VistaListarTodoHabilidadPorTrabajador() {
		super("[LISTAR HABILIDADES POR TRABAJADOR]");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(600, 300));

		JPanel idPanel = new JPanel();
		JLabel t = new JLabel("Introduzca ID trabajador");
		this.idTrabajadorField = new JTextField(10);
		idPanel.add(t);
		idPanel.add(idTrabajadorField);
		mainPanel.add(idPanel);

		JPanel jmostrar = new JPanel();
		rellenarmostrarPanel(jmostrar);
		mainPanel.add(jmostrar);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void rellenarmostrarPanel(JPanel jmostrar) {
		JButton mostrar = new JButton("MOSTRAR");
		mostrar.addActionListener((e) -> {
			try {
				int id = Integer.parseInt(idTrabajadorField.getText());
				if (id <= 0) {
					throw new Exception("Debes introducir un id númerico mayor que 0.");
				}
				Controller.getInstancia().accion(new Context(Evento.LISTAR_HABILIDAD_DEL_TRABAJADOR, id));
				this.dispose();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Por favor, introduzca un id valido", "Error",
						JOptionPane.ERROR_MESSAGE);

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
			}
		});
		jmostrar.add(mostrar);

		JButton volver = new JButton("VOLVER");
		volver.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.HABILIDAD, null));
			this.dispose();
		});
		jmostrar.add(volver);
	}

	@Override
	public void update(Context c) {

		if (c.getEvento() == Evento.VLISTAR_HABILIDAD_DEL_TRABAJADOR) {
			this.setVisible(true);
		} else {
			if (c.getEvento() == Evento.RES_LISTAR_HABILIDAD_DEL_TRABAJADOR_OK) {
				@SuppressWarnings("unchecked")
				Set<TVinculacionTrabHab> ths = (Set<TVinculacionTrabHab>) c.getDatos();
				String mhtml = "<html><head>" + "<style>" + "table { width: 100%; border-collapse: collapse;}"
						+ "th, td {border: 1px solid #ddd; padding:8px;}" + "th{background-color: #4CAF50; color:#fff;}"
						+ "</style>" + "</head> <body>" + "<p>Habilidades encontradas:</p>";
				if (ths.size() == 0) {
					JOptionPane.showMessageDialog(null, "No tiene ninguna habilidad el trabajador");
				} else {
					mhtml += "<table><tr><th> ID TRABAJADOR</th><th>ID HABILIDAD</th><th>ACTIVO</th></tr>";
					for (TVinculacionTrabHab th : ths) {
						mhtml += "<tr> <td>" + th.get_id_trabajador() + "</td> <td>" + th.get_id_habilidad()
								+ "</td><td>" + th.get_activo() + "</td> </tr>";
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

			} else if (c.getEvento() == Evento.RES_LISTAR_HABILIDAD_DEL_TRABAJADOR_KO)
				JOptionPane.showMessageDialog(null, "Error al listar todas las habilidades por trabajador.");
			Controller.getInstancia().accion(new Context(Evento.HABILIDAD, null));
			this.dispose();
		}
	}
}