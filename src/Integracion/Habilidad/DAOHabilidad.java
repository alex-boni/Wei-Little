/**
 * 
 */
package Integracion.Habilidad;

import java.util.Set;

import Negocio.Habilidad.THabilidad;

public interface DAOHabilidad {

	public int create(THabilidad tHabilidad);

	public THabilidad read(int id_habilidad);

	public int update(THabilidad tHabilidad);

	public int delete(int id_habilidad);

	public Set<THabilidad> read_all();

	public THabilidad read_by_name(String name);

}