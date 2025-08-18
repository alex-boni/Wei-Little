package Presentacion.Controlador;

import Presentacion.FactoriaPresentacion.Evento;

public class Context {

	private Evento evento;
	private Object datos;

	public Context(Evento e, Object d) {
		evento = e;
		datos = d;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Object getDatos() {
		return datos;
	}

	public void setDatos(Object datos) {
		this.datos = datos;
	}

}
