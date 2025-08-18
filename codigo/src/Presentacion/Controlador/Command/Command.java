package Presentacion.Controlador.Command;

import Presentacion.Controlador.Context;

public interface Command {

	public abstract Context execute(Object transfer);
}
