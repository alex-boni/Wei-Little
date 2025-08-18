/**
 * 
 */
package Negocio.Habilidad;

import java.util.Set;

import Negocio.Trabajador.TVinculacionTrabHab;

public interface SAHabilidad {

	public int altaHabilidad(THabilidad tHabilidad);

	public int bajaHabilidad(int id_habilidad);

	public int modificarHabilidad(THabilidad tHabilidad);

	public THabilidad buscarHabilidad(int id_habilidad);

	public Set<THabilidad> listarTodasHabilidades(int aceptar);

	public Set<TVinculacionTrabHab> listarTodoHabilidadPorTrabajador(int id_trabajador);

}