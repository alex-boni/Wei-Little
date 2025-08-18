
package Negocio.Plataforma;

import java.util.Set;

public interface SAPlataforma {
	public int altaPlataforma(TPlataforma tPlataforma);

	public int bajaPlataforma(int id_plataforma);

	public int modificarPlataforma(TPlataforma tPlataforma);

	public TPlataforma buscarPlataforma(int idPlataforma);

	public Set<TPlataforma> listarTodasPlataforma();
}