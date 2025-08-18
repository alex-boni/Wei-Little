package Integracion.Plataforma;

import java.util.Set;

import Negocio.Plataforma.TPlataforma;

public interface DAOPlataforma {

	public int create(TPlataforma tPlataforma);

	public TPlataforma read(int id_plataforma);

	public int update(TPlataforma tPlataforma);

	public int delete(int id_plataforma);

	public Set<TPlataforma> read_all();

	public TPlataforma read_by_name(String nombre);
}