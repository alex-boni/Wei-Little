
package Integracion.Trabajador;

import java.util.Set;

import Negocio.Trabajador.TTrabajador;

public interface DAOTrabajador {

	public int create(TTrabajador tTrabajador);

	public TTrabajador read(int id_trabajador);

	public int update(TTrabajador tTrabajador);

	public int delete(int id_trabajador);

	public Set<TTrabajador> read_all();

	public TTrabajador read_by_dni(String dni);

	public Set<TTrabajador> read_by_tipo(String tipo);
}