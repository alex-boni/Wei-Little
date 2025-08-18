/**
 * 
 */
package Presentacion.MaquinaJPA;

import javax.swing.JFrame;

import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Negocio.MaquinaJPA.TArcade;
import Negocio.MaquinaJPA.TMaquina;
import Negocio.MaquinaJPA.TRecreativa;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;

public class VistaBuscarMaquina extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	TMaquina tmaquinares;

	public VistaBuscarMaquina() {
		super("[BUSCAR MAQUINA]");
		initGUI();
	}

	private void initGUI() {

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.setContentPane(mainPanel);

		JPanel supPanel = new JPanel(new BorderLayout());
		mainPanel.add(supPanel, BorderLayout.PAGE_START);
		rellenarPanelSuperior(supPanel);

		JPanel bottomPanel = new JPanel();
		mainPanel.add(bottomPanel, BorderLayout.PAGE_END);
		rellenarPanelInferior(bottomPanel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(600, 300));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void rellenarPanelSuperior(JPanel supPanel) {

		JPanel textFieldPanel = new JPanel();
		supPanel.add(textFieldPanel, BorderLayout.PAGE_START);

		JTextField idTextField = new JTextField();
		idTextField.setVisible(true);
		idTextField.setPreferredSize(new Dimension(80, 30));
		JLabel idLabel = new JLabel("<html><p>id:</p></html>");
		idLabel.setPreferredSize(new Dimension(80, 30));
		textFieldPanel.add(idLabel);
		textFieldPanel.add(idTextField);

		JPanel okPanel = new JPanel();
		supPanel.add(okPanel, BorderLayout.PAGE_END);

		JButton ok_button = new JButton("OK");
		ok_button.setVisible(true);
		ok_button.addActionListener((e) -> {

			try {
				int id = Integer.parseInt(idTextField.getText());
				Controller.getInstancia().accion(new Context(Evento.BUSCAR_MAQUINA_NEGOCIO, id));
				this.dispose();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Error: El campo no puede estar vacio o ser letras");
			}
		});
		okPanel.add(ok_button);
	}

	private void rellenarPanelInferior(JPanel bottomPanel) {

		JButton cancelButton = new JButton("CANCEL");
		cancelButton.setToolTipText("cancel");
		cancelButton.setVisible(true);
		cancelButton.addActionListener((e) -> {
			Controller.getInstancia().accion(new Context(Evento.MAQUINA, null));
			this.dispose();
		});
		bottomPanel.add(cancelButton);
	}

	public void update(Context context) {
		if (context.getEvento() == Evento.BUSCAR_MAQUINA_VISTA)
			this.setVisible(true);
		else {
			if (context.getEvento() == Evento.RES_BUSCAR_MAQUINA_OK) {
				tmaquinares = (TMaquina) context.getDatos();
				if (tmaquinares instanceof TArcade) {
					TArcade arcade = (TArcade) tmaquinares;
					String activoTexto = arcade.get_activo() == 1 ? "SI" : "NO"; // Convertir activo a SI o NO
					String alquiladoTexto = arcade.get_alquilado() == 1 ? "SI" : "NO";

					String mhtml = "<html><body>" + "<p>Maquina encontrada: </p>" + "<p>TIPO: Arcade</p>"
							+ "<p>ID MAQUINA: " + arcade.get_id() + "</p>" + "<p>ID MODELO: " + arcade.get_id_modelo()
							+ "</p>" + "<p>NOMBRE: " + arcade.get_nombre() + "</p>" + "<p>NUMERO DE SERIE: "
							+ arcade.get_num_serie() + "</p>" + "<p>PRECIO HORA ACTUAL: "
							+ arcade.get_precio_hora_actual() + "</p>" + "<p>PRECIO PANTALLA: "
							+ arcade.get_precio_pantalla() + "</p>" + "<p>COEF VALOR MUL: " + arcade.get_coe_valor_mul()
							+ "</p>" + "<p>ALQUILADA: " + alquiladoTexto + "</p>" + "<p>ACTIVO: " + activoTexto + "</p>"
							+ // Añadido el atributo activo
							"</body></html>";

					JOptionPane.showMessageDialog(null, mhtml, "Maquina encontrada", JOptionPane.INFORMATION_MESSAGE);
				} else if (tmaquinares instanceof TRecreativa) {
					TRecreativa rec = (TRecreativa) tmaquinares;
					String activoTexto = rec.get_activo() == 1 ? "SI" : "NO"; // Convertir activo a SI o NO
					String alquiladoTexto = rec.get_alquilado() == 1 ? "SI" : "NO";

					String mhtml = "<html><body>" + "<p>Maquina encontrada: </p>" + "<p>TIPO: Recreativa</p>"
							+ "<p>ID MAQUINA: " + rec.get_id() + "</p>" + "<p>ID MODELO: " + rec.get_id_modelo()
							+ "</p>" + "<p>NOMBRE: " + rec.get_nombre() + "</p>" + "<p>NUMERO DE SERIE: "
							+ rec.get_num_serie() + "</p>" + "<p>PRECIO HORA ACTUAL: " + rec.get_precio_hora_actual()
							+ "</p>" + "<p>PRECIO MANTENIMIENTO: " + rec.get_precio_mantenimiento() + "</p>"
							+ "<p>COEF VALOR DIV: " + rec.get_coe_valor_div() + "</p>" + "<p>ALQUILADA: "
							+ alquiladoTexto + "</p>" + "<p>ACTIVO: " + activoTexto + "</p>" + // Añadido el atributo
																								// activo
							"</body></html>";

					JOptionPane.showMessageDialog(null, mhtml, "Maquina encontrada", JOptionPane.INFORMATION_MESSAGE);
				}

			} else if (context.getEvento() == Evento.RES_BUSCAR_MAQUINA_KO)
				JOptionPane.showMessageDialog(null, "Error al buscar Máquina. ");
			Controller.getInstancia().accion(new Context(Evento.MAQUINA, null));
			this.dispose();
		}
	}
}