package Negocio.ModeloJPA;

public class TVinculacionModeloProveedor {

	private int id_proveedor;
	private int id_modelo;

	public TVinculacionModeloProveedor(int id_modelo, int id_proveedor) {
		this.id_proveedor = id_proveedor;
		this.id_modelo = id_modelo;
	}

	public TVinculacionModeloProveedor() {
	}

	public int get_id_provider() {
		return this.id_proveedor;
	}

	public int get_id_model() {
		return this.id_modelo;
	}

	public void set_id_provider(int id) {
		this.id_proveedor = id;
	}

	public void set_id_model(int id) {
		this.id_modelo = id;
	}
}