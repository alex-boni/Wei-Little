package Negocio.AlquilerJPA;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class LineaAlquilerID implements Serializable {

	private static final long serialVersionUID = 0;

	private int alquiler;

	private int maquina;

	public LineaAlquilerID() {
	}

	public LineaAlquilerID(int alquiler, int maquina) {
		this.alquiler = alquiler;
		this.maquina = maquina;
	}

	public int get_id_maquina() {
		return maquina;
	}

	public int get_id_alquiler() {
		return alquiler;
	}

	public void set_id_maquina(int id_maquina) {
		this.maquina = id_maquina;
	}

	public void set_id_alquiler(int id_alquiler) {
		this.alquiler = id_alquiler;
	}

	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof LineaAlquilerID))
			return false;
		LineaAlquilerID pk = (LineaAlquilerID) obj;
		if (!(alquiler == pk.alquiler))
			return false;
		if (!(maquina == pk.maquina))
			return false;
		return true;
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + alquiler;
		hash = hash * prime + maquina;
		return hash;
	}
}