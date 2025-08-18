package Presentacion.Controlador;

import Presentacion.Controlador.Command.CommandFactory;
import Presentacion.FactoriaPresentacion.FactoriaVistas;
import Presentacion.Controlador.Command.Command;

public class ControladorImp extends Controller {

	public void accion(Context context) {

		Command command = CommandFactory.getInstance().getCommand(context.getEvento());

		if (command != null) {
			context = command.execute(context.getDatos());
		}

		FactoriaVistas.getInstancia().generarVistas(context.getEvento()).update(context);

	}
}
