package Negocio.AlquilerJPA;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import java.util.Date;
import java.util.Set;
import javax.persistence.OneToMany;
import Negocio.EmpleadoJPA.Empleado;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import Negocio.ClienteJPA.Cliente;
import javax.persistence.ManyToOne;

@Entity
@NamedQueries({
		@NamedQuery(name = "Negocio.AlquilerJPA.Alquiler.findByid_alquiler", query = "select obj from Alquiler obj where :id_alquiler = obj.id_alquiler "),
		@NamedQuery(name = "Negocio.AlquilerJPA.Alquiler.findByversion", query = "select obj from Alquiler obj where :version = obj.version "),
		@NamedQuery(name = "Negocio.AlquilerJPA.Alquiler.findByfecha", query = "select obj from Alquiler obj where :fecha = obj.fecha "),
		@NamedQuery(name = "Negocio.AlquilerJPA.Alquiler.findBytotal", query = "select obj from Alquiler obj where :total = obj.total "),
		@NamedQuery(name = "Negocio.AlquilerJPA.Alquiler.findByactivo", query = "select obj from Alquiler obj where :activo = obj.activo "),
		@NamedQuery(name = "Negocio.AlquilerJPA.Alquiler.findByempleado", query = "select obj from Alquiler obj where :empleado = obj.empleado "),
		@NamedQuery(name = "Negocio.AlquilerJPA.Alquiler.findBycliente", query = "select obj from Alquiler obj where :cliente = obj.cliente ") })
public class Alquiler implements Serializable {

	private static final long serialVersionUID = 0;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer id_alquiler;

	@Version
	private Integer version;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date fecha;
	private double total;
	private int activo;

	@OneToMany(mappedBy = "alquiler")
	private Set<LineaAlquiler> lineasAlquiler;

	@ManyToOne
	private Empleado empleado;

	@ManyToOne
	private Cliente cliente;

	public Alquiler() {
	}

	public Alquiler(TAlquiler tAlquiler) {
		this.activo = tAlquiler.get_activo();
		this.fecha = tAlquiler.get_fecha();
		this.total = tAlquiler.get_total();
	}

	public int get_activo() {
		return this.activo;

	}

	public double get_total() {
		return this.total;
	}

	public int get_id_alquiler() {
		return this.id_alquiler;
	}

	public int get_version() {
		return this.version;
	}

	public Date get_fecha() {
		return this.fecha;
	}

	public Empleado get_empleado() {
		return empleado;
	}

	public Cliente get_cliente() {
		return cliente;
	}

	public Set<LineaAlquiler> get_lineas_alquiler() {
		return this.lineasAlquiler;
	}

	public void set_id_alquiler(int id_alquiler) {
		this.id_alquiler = id_alquiler;
	}

	public void set_fecha(Date fecha) {
		this.fecha = fecha;
	}

	public void set_total(double total) {
		this.total = total;

	}

	public void set_empleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public void set_cliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void set_lineas_alquiler(Set<LineaAlquiler> lineasAlquiler) {
		this.lineasAlquiler = lineasAlquiler;

	}

	public void set_activo(int activo) {
		this.activo = activo;
	}

	public TAlquiler toTransfer() {
		TAlquiler tAlquiler = new TAlquiler();
		tAlquiler.set_activo(this.activo);
		tAlquiler.set_fecha(this.fecha);
		tAlquiler.set_id_alquiler(this.id_alquiler);
		tAlquiler.set_total(this.total);
		tAlquiler.set_id_cliente(this.cliente.get_id_cliente());
		tAlquiler.set_id_empleado(this.empleado.get_id_empleado());
		return tAlquiler;
	}
}