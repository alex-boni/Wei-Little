
package Integracion.Trabajador;

import java.util.Set;

import Negocio.Trabajador.TVinculacionTrabHab;

public interface DAOVinculacionTrabajadorHabilidad {

	public TVinculacionTrabHab create(TVinculacionTrabHab tVinculacionTrabHab);

	public TVinculacionTrabHab read(int id_trabajador, int id_habilidad);

	public int delete(int id_trabajador, int id_habilidad);

	public int update(TVinculacionTrabHab tVinculacionTrabHab);

	public Set<TVinculacionTrabHab> read_all();

	public Set<TVinculacionTrabHab> read_all_by_habilidad(int id_habilidad);

	public Set<TVinculacionTrabHab> read_all_by_trabajador(int id_trabajador);
}