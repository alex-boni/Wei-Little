
package Negocio.Trabajador;

import java.util.Set;

public interface SATrabajador {

	public int alta_trabajador(TTrabajador tTrabajador);

	public int baja_trabajador(int id_trabajador);

	public TTrabajador listar_por_id(int id_trabajador);

	public int modificar_trabajador(TTrabajador tTrabajador);

	public Set<TTrabajador> listar_trabajadores();

	public int vincular_habilidad(TVinculacionTrabHab vinculacion);

	public int desvincular_habilidad(TVinculacionTrabHab vinculacion);

	public Set<TVinculacionTrabHab> listar_por_habilidad(int id_habilidad);

	public Set<TVinculacionTrabHab> listar_por_trabajador(int id_trabajador);

	public Set<TTrabajador> listar_por_tipo(String tipo);
}