/**
 * 
 */
package Negocio.AlquilerJPA;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TCarritoAlquiler {

	private TAlquiler tAlquiler;
	private Set<TLineaAlquiler> tLineasAlquiler;
	private int id_maquina;
	private int duracion;

	public TAlquiler get_tAlquiler() {
		return this.tAlquiler;
	}

	public Set<TLineaAlquiler> get_tLineasAlquiler() {
		return this.tLineasAlquiler;
	}

	public int get_id_maquina() {
		return this.id_maquina;
	}

	public int get_duracion() {
		return this.duracion;
	}

	public void set_tAlquiler(TAlquiler t_alquiler) {
		this.tAlquiler = t_alquiler;
	}

	public void set_tLineasAlquiler(Set<TLineaAlquiler> t_linea_alquiler) {
		this.tLineasAlquiler = t_linea_alquiler;
	}

	public void set_id_maquina(int maquina_id) {
		this.id_maquina = maquina_id;
	}

	public void set_duracion(int duracion) {
		this.duracion = duracion;
	}

	public Iterator<TLineaAlquiler> iterator() {

		return new Iterator<TLineaAlquiler>() {

			List<TLineaAlquiler> list = new ArrayList<>(tLineasAlquiler);
			int i = 0;

			@Override
			public boolean hasNext() {

				return i < list.size();
			}

			@Override
			public TLineaAlquiler next() {

				return list.get(i++);
			}

			@Override
			public void remove() {

				list.remove(i);
				if (hasNext())
					i++;
			}

		};
	}
}