
package Presentacion.FactoriaPresentacion;

public abstract class FactoriaVistas {

	private static FactoriaVistas instancia;

	public static synchronized FactoriaVistas getInstancia() {
		if (instancia == null)
			instancia = new FactoriaVistasImp();
		return instancia;
	}

	public abstract IGUI generarVistas(Evento evento);

}