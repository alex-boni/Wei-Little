package Main;

import javax.swing.UIManager;

import Integracion.EMFSingleton.EMFSingleton;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;

public class Main {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Controller.getInstancia().accion(new Context(Evento.VISTA_PRINCIPAL, null));
		// EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
	}
}
