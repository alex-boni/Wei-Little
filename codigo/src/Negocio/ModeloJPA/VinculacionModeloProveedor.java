package Negocio.ModeloJPA;

import java.io.Serializable;

import javax.persistence.*;

import Negocio.ProveedorJPA.Proveedor;

@Entity
@NamedQueries({
		@NamedQuery(name = "Negocio.ModeloJPA.VinculacionModeloProveedor.findByid_modelo", query = "select obj from VinculacionModeloProveedor obj where :id_modelo = obj.modelo"),
		@NamedQuery(name = "Negocio.ModeloJPA.VinculacionModeloProveedor.findByid_proveedor", query = "select obj from VinculacionModeloProveedor obj where :id_proveedor = obj.proveedor") })
public class VinculacionModeloProveedor implements Serializable {

	private static final long serialVersionUID = 0;

	@EmbeddedId
	private VinculacionModeloProveedorID id_vinculacion;

	@Version
	private Integer version;

	@ManyToOne
	@MapsId
	private Proveedor proveedor;

	@ManyToOne
	@MapsId
	private Modelo modelo;

	public VinculacionModeloProveedor() {
	}

	public VinculacionModeloProveedor(Modelo modelo, Proveedor proveedor) {
		this.id_vinculacion = new VinculacionModeloProveedorID(modelo.get_id(), proveedor.get_id_proveedor());
		this.proveedor = proveedor;
		this.modelo = modelo;
	}

	public Proveedor get_proveedor() {
		return this.proveedor;
	}

	public Modelo get_modelo() {
		return this.modelo;
	}

	public void set_proveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public void set_modelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public TVinculacionModeloProveedor toTransfer() {

		TVinculacionModeloProveedor vinculacion = new TVinculacionModeloProveedor();

		vinculacion.set_id_model(this.modelo.get_id());
		vinculacion.set_id_provider(this.proveedor.get_id_proveedor());

		return vinculacion;
	}
}
