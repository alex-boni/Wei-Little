package Negocio.ClienteJPA;

import java.util.Set;

public interface SACliente {

	public int alta_cliente(TCliente tCliente);

	public int baja_cliente(int id);

	public int modificar_cliente(TCliente tCliente);

	public TCliente buscar_cliente(int id);

	public Set<TCliente> listar_clientes();
}