package Presentacion.Trabajador;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.table.AbstractTableModel;

import Negocio.Trabajador.TTrabajador;

public class ModeloTablaTrabajador extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[] header = { "id_trabajador", "Nombre", "dni", "activo" };
	List<TTrabajador> trabajadores;

	public ModeloTablaTrabajador() {
		trabajadores = new ArrayList<>();
	}

	@Override
	public String getColumnName(int column) {
		return header[column];
	}

	@Override
	public int getRowCount() {
		return trabajadores.size();
	}

	@Override
	public int getColumnCount() {
		return header.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return trabajadores.get(rowIndex).get_id();
		case 1:
			return trabajadores.get(rowIndex).get_nombre();
		case 2:
			return trabajadores.get(rowIndex).get_dni();
		case 3:
			return trabajadores.get(rowIndex).get_activo();
		default:
			return null;
		}
	}

	public void setLista(Set<TTrabajador> lista) {
		if (lista != null) {
			List<TTrabajador> sortedList = lista.stream().sorted(Comparator.comparingInt(TTrabajador::get_id))
					.collect(Collectors.toList());
			trabajadores.clear();
			trabajadores.addAll(sortedList);
		}
	}

}