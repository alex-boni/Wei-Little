/**
 * 
 */
package Negocio.MaquinaJPA;

import java.util.Set;

public interface SAMaquina {

	public int altaMaquina(TMaquina tMaquina);

	public int bajaMaquina(int id_maquina);

	public int modificarMaquina(TMaquina tMaquina);

	public TMaquina buscarMaquina(int id_maquina);

	public Set<TMaquina> listarTodasMaquinas();

	public Set<TMaquina> listarTodoMaquinaPorModelo(int id_modelo);
}