package Presentacion.Controlador;

public abstract class Controller {
	private static Controller instancia;

	public static synchronized Controller getInstancia() {
		if (instancia == null)
			instancia = new ControladorImp();
		return instancia;
	}

	public abstract void accion(Context contexto);

}