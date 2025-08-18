package Negocio.ProveedorJPA;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.NamedQueries;
import java.util.Set;
import Negocio.ModeloJPA.VinculacionModeloProveedor;

@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "CIF") })
@Entity
@NamedQueries({
		@NamedQuery(name = "Negocio.ProveedorJPA.Proveedor.findByid_proveedor", query = "select obj from Proveedor obj where :id_proveedor = obj.id_proveedor "),
		@NamedQuery(name = "Negocio.ProveedorJPA.Proveedor.findByversion", query = "select obj from Proveedor obj where :version = obj.version "),
		@NamedQuery(name = "Negocio.ProveedorJPA.Proveedor.findByCIF", query = "SELECT p FROM Proveedor p WHERE p.CIF = :cif"),
		@NamedQuery(name = "Negocio.ProveedorJPA.Proveedor.findBynombre", query = "select obj from Proveedor obj where :nombre = obj.nombre "),
		@NamedQuery(name = "Negocio.ProveedorJPA.Proveedor.findAll", query = "SELECT p FROM Proveedor p") })
public class Proveedor implements Serializable {

	private static final long serialVersionUID = 0;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer id_proveedor;

	@Version
	private Integer version;
	private String CIF;
	private String nombre;
	private int activo;

	@OneToMany(mappedBy = "proveedor")
	private Set<VinculacionModeloProveedor> vinculaciones;

	public Proveedor() {
	}

	public Proveedor(TProveedor proveedor) {
		this.set_id_proveedor(proveedor.get_id_proveedor());
		this.set_CIF(proveedor.get_CIF());
		this.set_nombre(proveedor.get_nombre());
		this.set_activo(proveedor.get_activo());
	}

	public int get_id_proveedor() {
		return this.id_proveedor;
	}

	public String get_CIF() {
		return this.CIF;
	}

	public String get_nombre() {
		return this.nombre;
	}

	public int get_activo() {
		return this.activo;
	}

	public void set_id_proveedor(int id) {
		this.id_proveedor = id;
	}

	public void set_CIF(String cif) {
		this.CIF = cif;
	}

	public void set_nombre(String nombre) {
		this.nombre = nombre;
	}

	public void set_activo(int activo) {
		this.activo = activo;
	}

	public Set<VinculacionModeloProveedor> get_vinculaciones() {
		return this.vinculaciones;
	}

	public void set_vinculaciones(Set<VinculacionModeloProveedor> vinculaciones) {
		this.vinculaciones = vinculaciones;
	}

	public TProveedor toTransfer() {
		TProveedor proveedor = new TProveedor();
		proveedor.set_activo(this.activo);
		proveedor.set_CIF(this.CIF);
		proveedor.set_nombre(this.nombre);
		proveedor.set_id_proveedor(this.id_proveedor);
		return proveedor;
	}
}