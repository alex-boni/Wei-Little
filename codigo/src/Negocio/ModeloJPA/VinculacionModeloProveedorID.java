package Negocio.ModeloJPA;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class VinculacionModeloProveedorID implements Serializable {

	private static final long serialVersionUID = 0;

	int modelo;
	int proveedor;

	public VinculacionModeloProveedorID() {
	}

	public VinculacionModeloProveedorID(int modelo, int proveedor) {
		this.modelo = modelo;
		this.proveedor = proveedor;
	}

	int get_modelo() {
		return this.modelo;
	}

	int get_proveedor() {
		return this.proveedor;
	}

	void set_modelo(int modelo) {
		this.modelo = modelo;
	}

	void set_proveedor(int proveedor) {
		this.proveedor = proveedor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(modelo, proveedor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VinculacionModeloProveedorID other = (VinculacionModeloProveedorID) obj;
		return modelo == other.modelo && proveedor == other.proveedor;
	}

}
