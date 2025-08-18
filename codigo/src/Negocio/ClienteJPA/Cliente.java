package Negocio.ClienteJPA;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import java.util.Set;
import Negocio.AlquilerJPA.Alquiler;
import javax.persistence.OneToMany;

@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "dni") })
@Entity
@NamedQueries({
		@NamedQuery(name = "Negocio.ClienteJPA.Cliente.findByid_cliente", query = "select c from Cliente c where c.id_cliente= :id_cliente "),
		@NamedQuery(name = "Negocio.ClienteJPA.Cliente.findBydni", query = "select c from Cliente c where c.dni= :dni "),
		@NamedQuery(name = "Negocio.ClienteJPA.Cliente.findBynombre", query = "select c from Cliente c where c.nombre= :nombre "),
		@NamedQuery(name = "Negocio.ClienteJPA.Cliente.findByactivo", query = "select c from Cliente c where c.activo= :activo ") })
public class Cliente implements Serializable {

	private static final long serialVersionUID = 0;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer id_cliente;
	@Version
	private Integer version;
	private String dni;
	private String nombre;
	private int activo;

	@OneToMany(mappedBy = "cliente")
	private Set<Alquiler> alquileres;

	public Cliente() {
	}

	public Cliente(TCliente tCliente) {
		this.id_cliente = tCliente.get_id_cliente();
		this.dni = tCliente.get_dni();
		this.nombre = tCliente.get_nombre();
		this.activo = tCliente.get_activo();
	}

	public TCliente toTransfer() {

		TCliente tCliente = new TCliente();
		tCliente.set_id_cliente(get_id_cliente());
		tCliente.set_dni(get_dni());
		tCliente.set_nombre(get_nombre());
		tCliente.set_activo(get_activo());
		return tCliente;
	}

	public int get_id_cliente() {

		return this.id_cliente;
	}

	public int get_version() {

		return this.version;
	}

	public String get_dni() {

		return this.dni;
	}

	public String get_nombre() {

		return this.nombre;
	}

	public Set<Alquiler> get_lista_factura() {

		return this.alquileres;
	}

	public int get_activo() {

		return activo;
	}

	public void set_id_cliente(int id) {

		this.id_cliente = id;
	}

	public void set_dni(String dni) {

		this.dni = dni;
	}

	public void set_nombre(String nombre) {

		this.nombre = nombre;
	}

	public void set_lista_factura(Set<Alquiler> lista_factura) {

		this.alquileres = lista_factura;
	}

	public void set_activo(int activo) {

		this.activo = activo;
	}
}