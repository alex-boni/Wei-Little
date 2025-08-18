package Presentacion.Trabajador;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.table.AbstractTableModel;

import Negocio.Trabajador.TVinculacionTrabHab;

public class ModeloTablaTrabajadorHabilidad extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[] header = { "id_trabajador", "id_habilidad", "activo" };
	List<TVinculacionTrabHab> vinculos;

	public ModeloTablaTrabajadorHabilidad() {
		vinculos = new ArrayList<>();
	}

	@Override
	public String getColumnName(int column) {
		return header[column];
	}

	@Override
	public int getRowCount() {
		return vinculos.size();
	}

	@Override
	public int getColumnCount() {
		return header.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return vinculos.get(rowIndex).get_id_trabajador();
		case 1:
			return vinculos.get(rowIndex).get_id_habilidad();
		case 2:
			return vinculos.get(rowIndex).get_activo();
		default:
			return null;
		}
	}

	public void setLista(Set<TVinculacionTrabHab> lista) {
		if (lista != null) {
			List<TVinculacionTrabHab> sortedList = lista.stream()
					.sorted(Comparator.comparingInt(TVinculacionTrabHab::get_id_trabajador))
					.collect(Collectors.toList());
			vinculos.clear();
			vinculos.addAll(sortedList);
		}
	}

}