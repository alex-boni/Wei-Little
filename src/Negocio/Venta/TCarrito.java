package Negocio.Venta;

import java.util.Iterator;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class TCarrito {

	private Set<TLineaVenta> _lista_lineaVenta;
	private TVenta _tVenta;
	private int id_producto_final;
	private int cantidad;

	public void set_id_producto_final(int id_producto_final) {

		this.id_producto_final = id_producto_final;
	}

	public int get_id_producto_final() {

		return this.id_producto_final;
	}

	public void set_cantidad(int cantidad) {

		this.cantidad = cantidad;
	}

	public int get_cantidad() {

		return this.cantidad;
	}

	public void set_lista_lineaVenta(Set<TLineaVenta> lista_lineaVenta) {

		this._lista_lineaVenta = lista_lineaVenta;
	}

	public Set<TLineaVenta> get_lista_lineaVenta() {

		return this._lista_lineaVenta;
	}

	public void set_venta(TVenta tVenta) {

		this._tVenta = tVenta;
	}

	public TVenta get_venta() {

		return this._tVenta;
	}

	public Iterator<TLineaVenta> iterator() {

		return new Iterator<TLineaVenta>() {

			List<TLineaVenta> list = new ArrayList<>(_lista_lineaVenta);
			int i = 0;

			@Override
			public boolean hasNext() {

				return i < list.size();
			}

			@Override
			public TLineaVenta next() {

				return list.get(i++);
			}

			@Override
			public void remove() {

				list.remove(i);
				if (hasNext())
					i++;
			}

		};
	}
}